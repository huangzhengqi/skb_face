package com.fzy.admin.fp.goods.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 商品导入
 */

@Data
@Entity
@Table(name = "goods_import_record")
public class GoodsImportRecord extends BaseEntity{

    @ApiModelProperty("导出id")
    private String importId;

    @ApiModelProperty("商品名称(商品名称不能重复)")
    @Excel(name = "商品名称(商品名称不能重复)", height = 20.0D, width = 30.0D)
    private String goodsName;

    @ApiModelProperty("商品编号")
    @Excel(name = "商品编号", height = 20.0D, width = 30.0D)
    private String itemNumber;

    @ApiModelProperty("价格")
    @Column(columnDefinition = "decimal(10,2)")
    @Excel(name = "价格", height = 20.0D, width = 30.0D)
    private BigDecimal goodsPrice;

    @ApiModelProperty("条形码（条形码必须唯一）")
    @Excel(name = "条形码（条形码必须唯一）", height = 20.0D, width = 30.0D)
    private String goodsCode;

    @ApiModelProperty("类目")
    @Excel(name = "类目", height = 20.0D, width = 30.0D)
    private String goodsCategory;

    @ApiModelProperty("库存")
    @Column(columnDefinition = "int(11) default 1")
    @Excel(name = "库存", height = 20.0D, width = 30.0D)
    private Integer stockNum;

    @ApiModelProperty("状态 （1成功 2失败）")
    private Integer status;

    @ApiModelProperty("导入状态")
    @Excel(name = "导入状态", height = 20.0D, width = 30.0D)
    @Transient
    private String statusName;

    @ApiModelProperty("失败原因")
    @Excel(name = "失败原因", height = 20.0D, width = 30.0D)
    private String reason;

    @ApiModelProperty("行业分类 0超市 1自助点餐 2医药 3加油站 4景区")
    @Excel(name = "行业分类 （0超市 1自助点餐 2医药 3加油站 4景区）", height = 20.0D, width = 30.0D)
    private String industryCategory;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;
}
