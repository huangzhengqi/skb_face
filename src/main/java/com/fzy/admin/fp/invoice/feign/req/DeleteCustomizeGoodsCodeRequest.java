package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("删除商品自定义编码")
public class DeleteCustomizeGoodsCodeRequest {
    @ApiModelProperty("商品编码")
    @JacksonXmlProperty(localName = "bm")
    private String goodsCode;

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @ApiModelProperty("机身编码")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodyNumber;
    @ApiModelProperty("纳税人识别号")
    @JacksonXmlProperty(localName = "nsrsbh")
    private String taxpayerIdentificationNum;

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
        this.taxpayerIdentificationNum = taxpayerIdentificationNum;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DeleteCustomizeGoodsCodeRequest)) return false;
        DeleteCustomizeGoodsCodeRequest other = (DeleteCustomizeGoodsCodeRequest) o;
        if (!other.canEqual(this)) return false;
        Object this$goodsCode = getGoodsCode(), other$goodsCode = other.getGoodsCode();
        if ((this$goodsCode == null) ? (other$goodsCode != null) : !this$goodsCode.equals(other$goodsCode))
            return false;
        Object this$bodyNumber = getBodyNumber(), other$bodyNumber = other.getBodyNumber();
        if ((this$bodyNumber == null) ? (other$bodyNumber != null) : !this$bodyNumber.equals(other$bodyNumber))
            return false;
        Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
        return !((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DeleteCustomizeGoodsCodeRequest;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $goodsCode = getGoodsCode();
        result = result * 59 + (($goodsCode == null) ? 43 : $goodsCode.hashCode());
        Object $bodyNumber = getBodyNumber();
        result = result * 59 + (($bodyNumber == null) ? 43 : $bodyNumber.hashCode());
        Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
        return result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
    }

    public String toString() {
        return "DeleteCustomizeGoodsCodeRequest(goodsCode=" + getGoodsCode() + ", bodyNumber=" + getBodyNumber() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ")";
    }


    public String getGoodsCode() {
        return this.goodsCode;
    }


    public String getBodyNumber() {
        return this.bodyNumber;
    }


    public String getTaxpayerIdentificationNum() {
        return this.taxpayerIdentificationNum;
    }
}
