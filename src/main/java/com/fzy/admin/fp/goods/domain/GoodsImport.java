package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品导出
 */

@Entity
@Table(name="goods_import")
@Data
public class GoodsImport extends BaseEntity {

    @ApiModelProperty("总数量")
    private Integer totalNum;

    @ApiModelProperty("成功数")
    private Integer successNum;

    @ApiModelProperty("失败数")
    private Integer failNum;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;
}
