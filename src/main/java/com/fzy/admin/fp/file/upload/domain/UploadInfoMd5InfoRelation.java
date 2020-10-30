package com.fzy.admin.fp.file.upload.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--14:11
 * @description uploadInfo 与 md5Info 关系表
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_fms_md5_info_relation")
public class UploadInfoMd5InfoRelation extends BaseEntity {

    private String uploadInfoId;
    private String md5InfoId;
    private Integer rank;

    public UploadInfoMd5InfoRelation() {
    }

    public UploadInfoMd5InfoRelation(String uploadInfoId, String md5InfoId, Integer rank) {
        this.uploadInfoId = uploadInfoId;
        this.md5InfoId = md5InfoId;
        this.rank = rank;
    }
}
