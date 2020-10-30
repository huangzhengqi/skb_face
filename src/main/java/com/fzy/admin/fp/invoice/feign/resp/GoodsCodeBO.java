package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseResultBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("商品编码")
public class GoodsCodeBO implements FamilyBaseResultBO {
    @ApiModelProperty(notes = "消息")
    @JacksonXmlProperty(localName = "returnMessage")
    protected String returnMessage;
    @ApiModelProperty(notes = "返回码")
    @JacksonXmlProperty(localName = "returnCode")
    protected String returnCode;
    @ApiModelProperty("版本号")
    @JacksonXmlProperty(localName = "BBH")
    private String versionNumber;
    @ApiModelProperty("MWJY")
    @JacksonXmlProperty(localName = "MWJY")
    private String mwjy;

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @ApiModelProperty("条数")
    @JacksonXmlProperty(localName = "COUNT")
    private String count;
    @ApiModelProperty("启用时间")
    @JacksonXmlProperty(localName = "QYSJ")
    private String enableTime;
    @ApiModelProperty("过渡期截止时间")
    @JacksonXmlProperty(localName = "GDQJZSJ")
    private String transitionDeadline;
    @ApiModelProperty("商品分类项列表")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "BMXX")
    private List<GoodsItem> goodsItems;
    @ApiModelProperty("优惠政策")
    @JacksonXmlProperty(localName = "YHZC")
    private PreferentialPolicy preferentialPolicy;

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setMwjy(String mwjy) {
        this.mwjy = mwjy;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public void setTransitionDeadline(String transitionDeadline) {
        this.transitionDeadline = transitionDeadline;
    }

    public void setGoodsItems(List<GoodsItem> goodsItems) {
        this.goodsItems = goodsItems;
    }

    public void setPreferentialPolicy(PreferentialPolicy preferentialPolicy) {
        this.preferentialPolicy = preferentialPolicy;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GoodsCodeBO)) return false;
        GoodsCodeBO other = (GoodsCodeBO) o;
        if (!other.canEqual(this)) return false;
        Object this$returnMessage = getReturnMessage(), other$returnMessage = other.getReturnMessage();
        if ((this$returnMessage == null) ? (other$returnMessage != null) : !this$returnMessage.equals(other$returnMessage))
            return false;
        Object this$returnCode = getReturnCode(), other$returnCode = other.getReturnCode();
        if ((this$returnCode == null) ? (other$returnCode != null) : !this$returnCode.equals(other$returnCode))
            return false;
        Object this$versionNumber = getVersionNumber(), other$versionNumber = other.getVersionNumber();
        if ((this$versionNumber == null) ? (other$versionNumber != null) : !this$versionNumber.equals(other$versionNumber))
            return false;
        Object this$mwjy = getMwjy(), other$mwjy = other.getMwjy();
        if ((this$mwjy == null) ? (other$mwjy != null) : !this$mwjy.equals(other$mwjy)) return false;
        Object this$count = getCount(), other$count = other.getCount();
        if ((this$count == null) ? (other$count != null) : !this$count.equals(other$count)) return false;
        Object this$enableTime = getEnableTime(), other$enableTime = other.getEnableTime();
        if ((this$enableTime == null) ? (other$enableTime != null) : !this$enableTime.equals(other$enableTime))
            return false;
        Object this$transitionDeadline = getTransitionDeadline(), other$transitionDeadline = other.getTransitionDeadline();
        if ((this$transitionDeadline == null) ? (other$transitionDeadline != null) : !this$transitionDeadline.equals(other$transitionDeadline))
            return false;
        Object this$goodsItems = getGoodsItems(), other$goodsItems = other.getGoodsItems();
        if ((this$goodsItems == null) ? (other$goodsItems != null) : !this$goodsItems.equals(other$goodsItems))
            return false;
        Object this$preferentialPolicy = getPreferentialPolicy(), other$preferentialPolicy = other.getPreferentialPolicy();
        return !((this$preferentialPolicy == null) ? (other$preferentialPolicy != null) : !this$preferentialPolicy.equals(other$preferentialPolicy));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GoodsCodeBO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $returnMessage = getReturnMessage();
        result = result * 59 + (($returnMessage == null) ? 43 : $returnMessage.hashCode());
        Object $returnCode = getReturnCode();
        result = result * 59 + (($returnCode == null) ? 43 : $returnCode.hashCode());
        Object $versionNumber = getVersionNumber();
        result = result * 59 + (($versionNumber == null) ? 43 : $versionNumber.hashCode());
        Object $mwjy = getMwjy();
        result = result * 59 + (($mwjy == null) ? 43 : $mwjy.hashCode());
        Object $count = getCount();
        result = result * 59 + (($count == null) ? 43 : $count.hashCode());
        Object $enableTime = getEnableTime();
        result = result * 59 + (($enableTime == null) ? 43 : $enableTime.hashCode());
        Object $transitionDeadline = getTransitionDeadline();
        result = result * 59 + (($transitionDeadline == null) ? 43 : $transitionDeadline.hashCode());
        Object $goodsItems = getGoodsItems();
        result = result * 59 + (($goodsItems == null) ? 43 : $goodsItems.hashCode());
        Object $preferentialPolicy = getPreferentialPolicy();
        return result * 59 + (($preferentialPolicy == null) ? 43 : $preferentialPolicy.hashCode());
    }

    public String toString() {
        return "GoodsCodeBO(returnMessage=" + getReturnMessage() + ", returnCode=" + getReturnCode() + ", versionNumber=" + getVersionNumber() + ", mwjy=" + getMwjy() + ", count=" + getCount() + ", enableTime=" + getEnableTime() + ", transitionDeadline=" + getTransitionDeadline() + ", goodsItems=" + getGoodsItems() + ", preferentialPolicy=" + getPreferentialPolicy() + ")";
    }


    public String getReturnMessage() {
        return this.returnMessage;
    }


    public String getReturnCode() {
        return this.returnCode;
    }


    public String getVersionNumber() {
        return this.versionNumber;
    }


    public String getMwjy() {
        return this.mwjy;
    }


    public String getCount() {
        return this.count;
    }


    public String getEnableTime() {
        return this.enableTime;
    }


    public String getTransitionDeadline() {
        return this.transitionDeadline;
    }


    public List<GoodsItem> getGoodsItems() {
        return this.goodsItems;
    }


    public PreferentialPolicy getPreferentialPolicy() {
        return this.preferentialPolicy;
    }
}
