package com.fzy.admin.fp.log.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by zk on 2019-06-13 11:39
 * @description
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_operation_log")
public class OperationLog extends BaseEntity {
    private String name;//方法操作名称

    private String requestUrl;//请求路径

    private String requestType;//请求类型

    @Column(length = 5000)
    private String requestParam;//请求参数

    @Column(length = 1000)
    private String tokenLoad;//用户凭证中的负载

    private String ip;//ip

    private Integer costTime;//花费时间
}
