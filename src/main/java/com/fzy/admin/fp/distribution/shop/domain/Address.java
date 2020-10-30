package com.fzy.admin.fp.distribution.shop.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-2 11:36:08
 * @Desp 收货地址
 **/

@Entity
@Data
@Table(name = "lysj_dist_address")
public class Address extends BaseEntity {
    private String userId;//用户id

    @Column(columnDefinition = "int(1) default 0")
    private Integer status;//状态为1时是默认地址

    private String place;//地址

    private String detailPlace;//詳細地址

    private String phone;//手機號
}
