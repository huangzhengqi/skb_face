package com.fzy.admin.fp.notice.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


/**
 * @author hzq
 * @description 公告表
 * @create 2019-10-15 09:33:52
 */

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_notice")
public class Notice extends CompanyBaseEntity {

    private String title; //公告标题

    private String content; //公告内容

    private String companyId; //代理商id

    private int type; //商户类型

}
