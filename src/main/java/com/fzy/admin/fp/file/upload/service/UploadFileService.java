package com.fzy.admin.fp.file.upload.service;

import cn.hutool.core.util.ImageUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.base.UploadContent;
import com.fzy.admin.fp.file.upload.domain.Md5Info;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import com.fzy.admin.fp.file.upload.domain.UploadInfoMd5InfoRelation;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.file.util.md5.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--14:30
 * @description 上传服务类
 */
@Slf4j
@Service
@Transactional
public class UploadFileService extends UploadContent {

    /**
     * @param files      上传的文件集合
     * @param uploaderId 上传者Id 可无
     * @return com.lysj.admin.utils.web.Resp
     * @author ZhangWenJian
     * @date 2019/1/8 15:33
     * @title filesUpload
     * @description 上传文件方法类
     */
    public Resp filesUpload(MultipartFile[] files, String uploaderId, String project, String module) {
        System.out.println(files);
        if (files == null || files.length == 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请上传文件!");
        }
        String path = prefixPath;
        String thumbnailPath = path + "/thumbnail/";
        File thumbnailDir = new File(thumbnailPath);
        StringBuilder filenames = new StringBuilder();
        StringBuilder uploadDetails = new StringBuilder();
        uploadDetails.append("用户预计存储:" + files.length + "个文件,");
        UploadInfo uploadInfo = uploadInfoService.buildUploadInfo(uploaderId, uploadDetails.toString(), project, module, false);
        List<UploadInfoMd5InfoRelation> uploadInfoMd5InfoRelations = new LinkedList<>();
        String savePathPrefix = "";
        Integer offset = 1;
        for (int i = 0; i < files.length; i++) {
            if (files[i] == null || files[i].isEmpty()) {
                offset -= 1;
                continue;
            }
            String fileName = files[i].getOriginalFilename();// 获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 获取文件的后缀名

            System.out.println(fileName);

            //获取md5编码
            String md5 = MD5Utils.getFileMD5String(files[i]); //获取文件md5编码
            Md5Info md5Info = md5InfoService.getRepository().findByMd5(md5); //查询是否已经存储了相应文件
            String descFileName = "";// 设置新的文件名
            //如果数据库没有本数据进行上传操作
            if (md5Info == null) {
                descFileName = ParamUtil.uuid() + suffixName;
                //执行文件保存操作

                //保存对应md5Info信息 获取
                md5Info = md5InfoService.buildMd5InfoBean(
                        md5,           //文件md5编码值
                        savePathPrefix + descFileName, //存储文件路径
                        descFileName   //文件名称
                );
            } else {
                descFileName = md5Info.getName();
            }

            fileSave(
                    path,          //文件保存路径
                    files[i],      //所需保存文件
                    thumbnailPath, //缩略图保存路径
                    thumbnailDir,  //缩略图保存文件
                    descFileName   //文件名称
            );
            if(suffixName.equals(".mp4")){
                return Resp.success(descFileName, "文件上传成功");
            }

            //构建关系表List
            uploadInfoMd5InfoRelationService.changeListUploadInfoAndMd5InfoRelations(
                    uploadInfoMd5InfoRelations,
                    fileName,
                    uploadInfo.getId(),
                    md5Info.getId(),
                    i + offset
            );

        }
        String msg = "";
        //拼接本次传输详情
        if (1 - offset == 0) {
            uploadDetails.append("全部存储成功!");
            msg = "文件上传成功";
        } else {
            Integer successTime = files.length - (1 - offset);
            Integer falseTime = 1 - offset;
            if (String.valueOf(falseTime).equals(String.valueOf(files.length))) {
                uploadDetails.append("因全部文件都为空,上传失败!");
                uploadInfoService.changeUploadDetails(uploadInfo, uploadDetails.toString(), false);
                return new Resp().error(Resp.Status.PARAM_ERROR, "上传失败,请勿上传空文件!");
            }
            msg = "操作成功!" + "成功上传:" + successTime + ",其中因文件为空失败:" + falseTime + ".";
            uploadDetails.append("成功存储:" + successTime + ",其中因文件为空失败:" + falseTime + ".");
        }
        //保留本次传输详情
        uploadInfoService.changeUploadDetails(uploadInfo, uploadDetails.toString(), true);
        uploadInfoMd5InfoRelationService.getRepository().save(uploadInfoMd5InfoRelations);
        //保留上传信息与md5关联表
        for (UploadInfoMd5InfoRelation uploadInfoMd5InfoRelation : uploadInfoMd5InfoRelations) {
            filenames.append(uploadInfoMd5InfoRelation.getId()).append("-");
        }
        //拼接的路径
        String fileJointName = savePathPrefix + filenames.substring(0, filenames.length() - 1);

        return Resp.success(fileJointName, msg);
    }

    /**
     * @param path          上传文件保存路径
     * @param file          上传文件
     * @param thumbnailPath 缩略图保留地址
     * @param thumbnailDir  缩略图文件类
     * @param descFileName  保留文件名
     * @return void
     * @author ZhangWenJian
     * @date 2019/1/8 13:56
     * @title fileSave
     * @description
     */
    public void fileSave(String path, MultipartFile file, String thumbnailPath, File thumbnailDir, String descFileName) {
        File dest = new File(path, descFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            if (!thumbnailDir.exists()) {
                thumbnailDir.mkdirs();
            }
            try {
                ImageUtil.scale(dest, new File(thumbnailPath + descFileName), 120, 120, Color.white);
            } catch (Exception e) {
//                System.out.println("非图片类型无法进行压缩操作!");
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param response
     * @param thumbnail
     * @param fileId
     * @return void
     * @author ZhangWenJian
     * @date 2019/1/24 13:43
     * @title showFiles
     * @description 流文件展示
     */
    public void showFiles(HttpServletRequest request, HttpServletResponse response, boolean thumbnail, @PathVariable String fileId) throws IOException {
        FileInfoVo fileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(fileId);
        if (fileInfoVo == null) {
            return;
        }
        System.out.println("获取类" + fileInfoVo);
        FileInputStream fileIs = null;
        String path = "";
        if (thumbnail) {
            path = prefixPath + "thumbnail/" + fileInfoVo.getPath();
        } else {
            path = prefixPath + fileInfoVo.getPath();
        }
        try {
            fileIs = new FileInputStream(path);
        } catch (Exception e) {
            log.error("系统找不到图像文件：" + path);
            return;
        }
        int i = fileIs.available(); //得到文件大小
        byte data[] = new byte[i];
        fileIs.read(data);  //读数据
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileInfoVo.getName(), "UTF-8"));

        OutputStream outStream = response.getOutputStream(); //得到向客户端输出二进制数据的对象
        outStream.write(data);  //输出数据
        outStream.flush();
        outStream.close();
        fileIs.close();
    }


    public String showFilePath(HttpServletRequest request, HttpServletResponse response, boolean thumbnail, @PathVariable String fileId) throws IOException {
        FileInfoVo fileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(fileId);
        if (fileInfoVo == null) {
            return "";
        }
        System.out.println("获取类" + fileInfoVo);
        String path = "";
        if (thumbnail) {
            path = prefixPath + "thumbnail/" + fileInfoVo.getPath();
        } else {
            path = prefixPath + fileInfoVo.getPath();
        }
        return path;
    }


}
