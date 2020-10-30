package com.fzy.admin.fp.file.api.base;

import com.fzy.admin.fp.file.upload.service.UploadFileService;
import com.fzy.admin.fp.file.upload.service.UploadFileService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhangWenJian
 * @data 2019/1/11--9:55
 * @description
 */
@Component
public class ApiContent {
    @Resource
    protected UploadFileService uploadFileService;
}
