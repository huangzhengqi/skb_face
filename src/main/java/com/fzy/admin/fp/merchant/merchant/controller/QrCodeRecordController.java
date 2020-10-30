package com.fzy.admin.fp.merchant.merchant.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.BlankQrCode;
import com.fzy.admin.fp.merchant.merchant.domain.QrCodeRecord;
import com.fzy.admin.fp.merchant.merchant.service.BlankQrCodeService;
import com.fzy.admin.fp.merchant.merchant.service.QrCodeRecordService;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:35 2019/4/30
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/qr_code_record")
public class QrCodeRecordController extends BaseController<QrCodeRecord> {

    @Resource
    private QrCodeRecordService qrCodeRecordService;
    @Resource
    private BlankQrCodeService blankQrCodeService;


    @Value("${qrcode.path}")
    private String qrcodePath;
    @Resource
    private AuthServiceFeign authServiceFeign;

    @Override
    public QrCodeRecordService getService() {
        return qrCodeRecordService;
    }


    @Override
    public Resp list(QrCodeRecord entity, PageVo pageVo) {
        entity.setServiceProviderId((String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        return super.list(entity, pageVo);
    }

    @PostMapping("/save_rewrite")
    @Transactional
    public Resp saveRewrite(QrCodeRecord entity, @UserId String userId) {
        Map<String, String> userNameMap = authServiceFeign.getUserName();
        if (ParamUtil.isBlank(entity.getGenerateNumber()) || entity.getGenerateNumber() < 1 || entity.getGenerateNumber() > 1000) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "批量生成数量不能小于0，不能超过1000");
        }
        entity.setCreatorName(userNameMap.get(userId));
        String dirPath = "blankcode/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + System.currentTimeMillis() + ".zip";
        List<BlankQrCode> blankQrCodes = new ArrayList<>(entity.getGenerateNumber());
        for (int i = 0; i < entity.getGenerateNumber(); i++) {
            blankQrCodes.add(new BlankQrCode());
        }
        Iterable<BlankQrCode> blankQrCodesList = blankQrCodeService.save(blankQrCodes);
        //生成空二维码
        List<InputStream> inputStreams = new ArrayList<>(entity.getGenerateNumber());
        List<String> qrNames = new ArrayList<>(entity.getGenerateNumber());
        File file = new File("");
        String zipPath = file.getAbsolutePath()+"/"+qrcodePath + dirPath;
        entity.setDownloadUrl(zipPath);
        entity.setServiceProviderId((String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        qrCodeRecordService.save(entity);
        new Thread(() -> {
            blankQrCodesList.forEach(blankQrCode -> {
                String url = qrcodePath + "/#/qrcode" + "?id=" + blankQrCode.getId();
                inputStreams.add(createQrCode(url));
                qrNames.add(blankQrCode.getId() + ".png");
            });
            log.info("hxq zip : {}", FileUtil.file(zipPath).getAbsolutePath());
            ZipUtil.zip(FileUtil.file(zipPath), qrNames.toArray(new String[0]), inputStreams.toArray(new InputStream[0]));
        }).start();
        return Resp.success("操作成功");
    }


    /*
     * @author drj
     * @date 2019-05-27 15:25
     * @Description ：批量下载
     */
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(String id) throws Exception {
        QrCodeRecord qrCodeRecord = qrCodeRecordService.findOne(id);
        if (ParamUtil.isBlank(qrCodeRecord)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        FileSystemResource file = new FileSystemResource(qrCodeRecord.getDownloadUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
                .body(new InputStreamResource(file.getInputStream()));
    }


    /*
     * @author drj
     * @date 2019-05-27 15:25
     * @Description :删除文件
     */
    @PostMapping("/delete_file")
    public Resp deleteFile(String id) {
        QrCodeRecord qrCodeRecord = qrCodeRecordService.findOne(id);
        if (ParamUtil.isBlank(qrCodeRecord)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        File file = new File(qrCodeRecord.getDownloadUrl());
        if (file.exists()) {
            file.delete();
        }
        qrCodeRecord.setDelFlag(CommonConstant.DEL_FLAG);
        qrCodeRecordService.save(qrCodeRecord);
        return Resp.success("操作成功");
    }

    //创建二维码
    private InputStream createQrCode(String url) {
        return new ByteArrayInputStream(QrCodeUtil.generatePng(url, 300, 300));
    }


}
