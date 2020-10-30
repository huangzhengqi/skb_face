package com.fzy.admin.fp.distribution.shop.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-2 10:31:59
 * @Desp 购物车表
 **/

@Data
@Entity
@Table(name="lysj_dist_shop_cart")
public class ShopCart extends BaseEntity {
    private String userId;//用户id

    private String goodsId;//商品id

    private Integer num;//数量

    private String property;//商品属性
}
