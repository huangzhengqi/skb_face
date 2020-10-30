package com.fzy.admin.fp.distribution.shop.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
*
 * @Author yy
 * @Date 2019-11-29 13:35:55
 * @Desp
*/
@Data
@Entity
@Table(name="lysj_dist_goods")
public class DistGoods extends CompanyBaseEntity{
    private String img;//商品图片

    //private Integer num;//库存

    private BigDecimal price;//价格

    @Column(columnDefinition="TEXT")
    private String remark;//介绍
/*
    @OneToMany
    @JoinColumn(name = "goodsId", insertable = false, updatable = false, nullable=true)
    private List<GoodsProperty> goodsProperty = new ArrayList<>();//商品属性*/

    private Integer weight;//排序

    @Column(columnDefinition = "int(1) default 0")
    private Integer status;//上架0 下架1


    @Column(columnDefinition = "int(1) default 0")
    private Integer type;//商品是否是刷脸设备 否0 是1
}
