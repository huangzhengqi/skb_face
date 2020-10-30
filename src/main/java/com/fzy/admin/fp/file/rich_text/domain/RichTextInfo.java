package com.fzy.admin.fp.file.rich_text.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author ZhangWenJian
 * @data 2019/2/19--15:27
 * @description 富文本信息
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_fms_rich_text_info")
public class RichTextInfo extends BaseEntity {
    @Lob
    @Column(columnDefinition = "text")
    private String info; //富文本信息

    public RichTextInfo() {
    }

    public RichTextInfo(String info) {
        this.info = info;
    }
}
