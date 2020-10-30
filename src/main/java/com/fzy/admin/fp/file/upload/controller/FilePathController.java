package com.fzy.admin.fp.file.upload.controller;

import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lb
 * @date 2019/6/14 16:17
 * @Description
 */
@RestController
@RequestMapping(value = "/fms/file_path")
public class FilePathController {

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @GetMapping(value = "/path")
    public FileInfoVo getFilePath(String id) {
        System.out.println(uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id));
        return uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
    }

    @GetMapping(value = "/path_more")
    public Map<String, String> getFilePathMore(String[] ids) {
        return uploadInfoMd5InfoRelationService.getFilePathMore(ids);
    }

}
