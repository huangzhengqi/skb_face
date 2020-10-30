package com.fzy.admin.fp.distribution.shop.dto;

import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yy
 * @Date 2019-11-30 10:25:16
 * @Desp
 **/
@Data
public class DistGoodsDTO {
    private String id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("图片路径")
    private String img;


    @ApiModelProperty("是否为刷脸设备 否0 是1")
    private String type;
/*

    @ApiModelProperty("库存")
    private Integer num;
*/

    @ApiModelProperty("价格")
    @NotNull(message = "请输入价格")
    @Min(value = "0", message = "金额大于0")
    private BigDecimal price;

    @ApiModelProperty("商品介绍")
    @Column(columnDefinition="TEXT")
    private String remark;
/*
    @ApiModelProperty("属性名称")
    private String[] propertyName;

    @ApiModelProperty("属性")
    private String[] property;*/

    @ApiModelProperty("排序")
    private Integer weight;

}
