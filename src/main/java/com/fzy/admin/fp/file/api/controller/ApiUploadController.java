package com.fzy.admin.fp.file.api.controller;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.api.base.ApiContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.api.base.ApiContent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangWenJian
 * @data 2019/1/11--9:55
 * @description
 */
@RestController
@RequestMapping("/api/v1/upload")
public class ApiUploadController extends ApiContent {

    @PostMapping("/files_upload/{project}/{model}")
    public Resp filesUpload(MultipartFile[] files, String uploaderId, @PathVariable String project, @PathVariable String model) {
        return uploadFileService.filesUpload(files, uploaderId, project, model);
    }
}
