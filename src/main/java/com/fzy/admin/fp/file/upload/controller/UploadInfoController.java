package com.fzy.admin.fp.file.upload.controller;

import cn.hutool.core.util.ReUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import com.fzy.admin.fp.file.upload.service.UploadFileService;
import com.fzy.admin.fp.file.upload.service.UploadInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangWenJian
 * @data 2019/1/4--17:12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/fms/upload")
public class UploadInfoController extends BaseController<UploadInfo> {
    @Resource
    private UploadInfoService uploadInfoService;

    @Resource
    private UploadFileService uploadFileService;

    @Override
    public BaseService<UploadInfo> getService() {
        return uploadInfoService;
    }

    @Value("${file.upload.path}")
    public String prefixPath;

    @Value("${file.upload.certPath}")
    private String certPath; // 微信支付证书路径

    @Override
    public Resp list(UploadInfo uploadInfo, PageVo pageVo) {
        return null;
    }

    ;

    @PostMapping("/files_upload/{project}/{module}")
    public Resp filesUpload(MultipartFile[] files, String uploaderId, @PathVariable String project, @PathVariable String module) {
        System.err.println(files.length);
        if ("undefined".equals(project) || ParamUtil.isBlank(project)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请填写来源项目!");
        }
        if ("undefined".equals(module) || ParamUtil.isBlank(module)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请填写来源模块!");
        }
        return uploadFileService.filesUpload(files, uploaderId, project, module);
    }

    /**
     * @author Created by wtl on 2019/5/8 22:10
     * @Description 上传文件，返回文件路径
     */
    @PostMapping("/file_upload")
    public Resp fileUpload(MultipartFile file, String module) {
        if (module == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请指定模块名!");
        }
        if (file == null || file.isEmpty()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "文件为空!");
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = "."+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String fileName = ParamUtil.uuid() + suffix;
        String path = certPath + module;
        File dest = new File(path, fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return Resp.success(dest.getAbsolutePath(), "上传成功");
    }

    @PostMapping("/path/file_upload")
    public Resp pathFileUpload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "文件为空!");
        }
        //获取上传文件后缀名
        String OriginalfileName = file.getOriginalFilename();// 获取文件名
        String suffixName = OriginalfileName.substring(OriginalfileName.lastIndexOf(".")); // 获取文件的后缀名
        log.info("文件后缀名：" + suffixName);
        //校验后缀名是否符合规范
        boolean flag = ReUtil.isMatch(".(jpg|png|jpeg|JPG|PNG|JPEG)$", suffixName);
        if (!flag) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "图片仅支持jpg、png、jpeg、JPG、PNG、JPEG格式");
        }
        //创建文件
        String fileName = ParamUtil.uuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File dest = new File(prefixPath, fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("path", dest.getAbsolutePath());
        return Resp.success(map, "上传成功");
    }


    @GetMapping("/list_rewrite")
    public Resp listRewrite(UploadInfo entity, PageVo pageVo) {
        return uploadInfoService.listRewrite(entity, pageVo);
    }

    @GetMapping("/resource/{fileId}")
    public void showFiles(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId) throws IOException {
        if ("undefined".equals(fileId) || ParamUtil.isBlank(fileId)) {
            return;
        }
        uploadFileService.showFiles(request, response, false, fileId);
    }


    @GetMapping("/resource/path/{fileId}")
    public String showFilePath(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId) throws IOException {
        if ("undefined".equals(fileId) || ParamUtil.isBlank(fileId)) {
            return "";
        }
        return uploadFileService.showFilePath(request, response, false, fileId);
    }


    @GetMapping("/resource/thumbnail/{fileId}")
    public void showThumbnailFiles(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId) throws IOException {
        if ("undefined".equals(fileId) || ParamUtil.isBlank(fileId)) {
            return;
        }
        uploadFileService.showFiles(request, response, true, fileId);
    }

}
