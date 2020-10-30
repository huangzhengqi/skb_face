package com.fzy.admin.fp.member.sem.controller;


import cn.hutool.core.util.ReUtil;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOfflineMaterialImageUploadRequest;
import com.alipay.api.response.AlipayOfflineMaterialImageUploadResponse;
import com.fzy.admin.fp.ali.config.AliConfig;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.vo.UploadImageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;



@Slf4j
@RestController
@RequestMapping(value = "/member/sem/upload")
@Api(value = "UploadController",tags = {"支付宝上传资源文件"})
public class UploadController {


    @Value("${file.upload.path}")
    public String prefixPath;

    @Resource
    private UserRepository userRepository;


    @PostMapping(value="/imageOrVideo")
    @ApiOperation(value="支付宝上传文件",notes = "支付宝上传文件")
    public Resp<UploadImageVO> upload(@UserId String userId, MultipartFile file){

        User user = userRepository.findOne(userId);

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

        UploadImageVO uploadImageVO = new UploadImageVO();
        uploadImageVO.setFileName(fileName);
        uploadImageVO.setPath(dest.getAbsolutePath());
        log.info("=========================================================fileName{}",fileName);
        log.info("======================================================dest.getAbsolutePath{}",dest.getAbsolutePath());

        AlipayClient alipayClient = new DefaultAlipayClient(AliConfig.URL, AliConfig.APPID,AliConfig.APP_PRIVATE_KEY,AliConfig.FORMAT,AliConfig.CHARSET,AliConfig.ALIPAY_PUBLIC_KEY,AliConfig.SIGN_TYPE);
        AlipayOfflineMaterialImageUploadRequest request = new AlipayOfflineMaterialImageUploadRequest();

        String imgType = suffixName.substring(1,suffixName.length());
        request.setImageType(imgType);
        request.setImageName(fileName);
        if(null != user) {
            request.setImagePid(user.getCompanyId());
        }

        FileItem ImageContent = new FileItem(dest.getAbsolutePath());
        request.setImageContent(ImageContent);
        AlipayOfflineMaterialImageUploadResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if(response.isSuccess()){
            log.info(response.getImageUrl());
            log.info(response.getImageId());
            log.info("调用成功");
            uploadImageVO.setImageId(response.getImageId());
            uploadImageVO.setImageUrl(response.getImageUrl());
        } else {
            log.info("调用失败");
        }
        return Resp.success(uploadImageVO, "上传成功");
    }
}
