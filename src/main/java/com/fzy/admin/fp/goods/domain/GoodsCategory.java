package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品分类
 */

@Entity
@Table(name = "goods_category")
@Data
public class GoodsCategory  extends BaseEntity {
    @ApiModelProperty("商品名称")
    private String cagegoryName;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("操作员id")
    private String merchantUserId;

    @Column(columnDefinition = "int(11) default 0")
    @ApiModelProperty("行业分类 0超市 1自助点餐 2医药 3加油站 4景区")
    private Integer industryCategory;

    @ApiModelProperty("门店id")
    private String storeId;
}
