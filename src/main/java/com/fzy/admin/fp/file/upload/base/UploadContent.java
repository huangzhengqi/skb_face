package com.fzy.admin.fp.file.upload.base;

import com.fzy.admin.fp.file.upload.service.Md5InfoService;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.service.UploadInfoService;
import com.fzy.admin.fp.file.upload.service.Md5InfoService;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.service.UploadInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--14:33
 * @description
 */
@Component
public class UploadContent {

    @Value("${file.upload.path}")
    public String prefixPath;

    @Resource
    protected Md5InfoService md5InfoService;
    @Resource
    protected UploadInfoService uploadInfoService;
    @Resource
    protected UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;
}
