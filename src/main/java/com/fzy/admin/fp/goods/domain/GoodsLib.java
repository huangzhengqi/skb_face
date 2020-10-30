package com.fzy.admin.fp.goods.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "goods_lib")
public class GoodsLib extends BaseEntity {

    @ApiModelProperty("条形码")
    private String code;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("公司")
    private String manuName;

    @ApiModelProperty("公司地址")
    private String manuAddress;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("类型")
    private String goodsType;

    @ApiModelProperty("产地")
    private String ycg;

    @ApiModelProperty("品牌")
    private String trademark;

    @ApiModelProperty("备注")
    private String remark;

    public GoodsLib(String code, String goodsName, String manuName, String manuAddress, String spec, String price, String img, String goodsType, String ycg, String trademark, String remark) {
        this.code = code;
        this.goodsName = goodsName;
        this.manuName = manuName;
        this.manuAddress = manuAddress;
        this.spec = spec;
        this.price = price;
        this.img = img;
        this.goodsType = goodsType;
        this.ycg = ycg;
        this.trademark = trademark;
        this.remark = remark;
    }

    public GoodsLib() {

    }

}
