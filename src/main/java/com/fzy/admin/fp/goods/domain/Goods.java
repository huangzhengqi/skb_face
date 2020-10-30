package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品表
 */

@Data
@Entity
@Table(name="goods", indexes={@Index(columnList="merchantId", name="merchantId_index")})
public class Goods extends BaseEntity {


    @ApiModelProperty("商品名称")
    private String goodsName;


    @ApiModelProperty("商品编号")
    private String itemNumber;


    @ApiModelProperty("价格")
    @Column(columnDefinition="decimal(10,2)")
    private BigDecimal goodsPrice;


    @ApiModelProperty("上下架")
    @Column(columnDefinition="int(1) default 1")
    private Integer isShelf;


    @ApiModelProperty("排序")
    @Column(columnDefinition="int(11) default 1")
    private Integer sort;


    @ApiModelProperty("库存")
    @Column(columnDefinition="int(11) default 1")
    private Integer stockNum;


    @ApiModelProperty("销量")
    @Column(columnDefinition="int(11) default 0")
    private Integer salesNum;


    @ApiModelProperty("来源  1添加 2导入")
    private Integer source;


    @ApiModelProperty("条形码")
    private String goodsCode;


    @ApiModelProperty("封面")
    private String goodsPic;


    @ApiModelProperty("类目")
    private String goodsCategory;


    @ApiModelProperty("商户id")
    private String merchantId;


    @ApiModelProperty("操作员id")
    private String merchantUserId;


    @Column(columnDefinition="int(11) default 0")
    @ApiModelProperty("系统分类  0默认")
    private Integer systemCategory;


    @Column(columnDefinition="int(11) default 0")
    @ApiModelProperty("行业分类 0超市 1自助点餐 2医药 3加油站 4景区")
    private Integer industryCategory;


    @ApiModelProperty("备注  用逗号隔开")
    private String saleRemark;


    @ApiModelProperty("计价单位")
    private String unit;


    @ApiModelProperty("门店id")
    private String storeId;

}
