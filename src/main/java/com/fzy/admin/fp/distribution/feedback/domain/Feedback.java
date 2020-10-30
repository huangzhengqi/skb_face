package com.fzy.admin.fp.distribution.feedback.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-6 17:55:26
 * @Desp
 **/
@Data
@Entity
@Table(name = "lysj_dist_feedback")
public class Feedback extends CompanyBaseEntity{
    private String content;//内容

    private String img;//图片

    private String userId;//用户id

    private String reply;//回复内容

    @Column(columnDefinition = "int(1) default 0")
    private Integer status;//状态0未回复 1已回复

    @Column(columnDefinition = "int(1) default 0")
    private Integer type;//是否已读 0未读 1已读

}
