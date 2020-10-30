package com.fzy.admin.fp.file.upload.vo;

import lombok.Data;

/**
 * @author ZhangWenJian
 * @data 2019/1/24--12:18
 * @description
 */
@Data
public class FileInfoVo {
    private String UploadInfoMd5InfoRelationId; //关系表ID
    private String name;             //文件名
    private String project;          //项目
    private String module;            //模块
    private String md5InfoName;      //md5码文件名
    private String path;             //路径

    public FileInfoVo(String uploadInfoMd5InfoRelationId, String name, String project, String module, String md5InfoName, String path) {
        UploadInfoMd5InfoRelationId = uploadInfoMd5InfoRelationId;
        this.name = name;
        this.project = project;
        this.module = module;
        this.md5InfoName = md5InfoName;
        this.path = path;
    }
}
