package com.fzy.admin.fp.file.upload.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZhangWenJian
 * @data 2019/1/4--17:15
 * @description 上传信息表
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_fms_upload_info")
public class UploadInfo extends BaseEntity {

    private String uploaderId;    //上传者Id
    private String uploadDetails; //上传细节
    private String project;         //项目
    private String module;         //模块
    private boolean result;        //结果

    public UploadInfo() {
    }

    public UploadInfo(String uploaderId, String uploadDetails, String project, String module, boolean result) {
        this.uploaderId = uploaderId;
        this.uploadDetails = uploadDetails;
        this.project = project;
        this.module = module;
    }
}
