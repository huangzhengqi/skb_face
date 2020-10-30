package com.fzy.admin.fp.merchant.management.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zk
 * @description 用户角色关联表
 * @create 2018-07-25 15:50:59
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_user_role")
public class MerchantUserRole extends BaseEntity {

    private String userId;//用户ID

    private String roleId;//角色ID

}