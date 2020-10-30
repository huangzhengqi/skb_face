package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("自定义商品编码设置响应")
public class CustomizeGoodsCodeBO extends FamilyBaseBO {
    public String toString() {
        return "CustomizeGoodsCodeBO(goodsCode=" + getGoodsCode() + ")";
    }

    @ApiModelProperty(value = "商品编码", notes = "用户自行编码 前 19 位为商品编码； 后 20 位为用户自行编码 ")
    @JacksonXmlProperty(localName = "bm")
    private String goodsCode;

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $goodsCode = getGoodsCode();
        return result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
    }

    protected boolean canEqual(Object other) {
        return other instanceof CustomizeGoodsCodeBO;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CustomizeGoodsCodeBO)) return false;
        CustomizeGoodsCodeBO other = (CustomizeGoodsCodeBO) o;
        if (!other.canEqual(this)) return false;
        Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
        return !((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode));
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }


    public String getGoodsCode() {
        return this.goodsCode;
    }
}
