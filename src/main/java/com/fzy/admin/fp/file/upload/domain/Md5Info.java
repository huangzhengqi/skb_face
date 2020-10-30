package com.fzy.admin.fp.file.upload.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--11:44
 * @description md5表 主要用于存储上传文件后得md5管理 文件路径记录
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_fms_md5_info")
public class Md5Info extends BaseEntity {

    private String md5;
    private String path;

    public Md5Info() {
    }

    public Md5Info(String md5, String path) {
        this.md5 = md5;
        this.path = path;
    }
}
