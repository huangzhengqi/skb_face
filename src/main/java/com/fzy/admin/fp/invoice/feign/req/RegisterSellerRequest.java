package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("注册商户请求")
@Data
public class RegisterSellerRequest {
    @ApiModelProperty("服务商ID")
    @JacksonXmlProperty
    private String channelId;
    @ApiModelProperty("商户名称")
    @JacksonXmlProperty(localName = "clientName")
    private String sellerName;
    @ApiModelProperty("省份行政编码")
    @JacksonXmlProperty(localName = "provinceno")
    private String provinceCode;
    @ApiModelProperty("按策略分配商户到子服务商")
    @JacksonXmlProperty(localName = "dispatch")
    private String dispatch;
    @ApiModelProperty("纳税人识别号")
    @JacksonXmlProperty(localName = "nsrsbh")
    private String taxpayerIdentificationNum;
    @ApiModelProperty("注册地址")
    @JacksonXmlProperty(localName = "businessAddress")
    private String registerAddress;
    @ApiModelProperty("电话")
    @JacksonXmlProperty(localName = "tel")
    private String tel;
    @ApiModelProperty("开户银行")
    @JacksonXmlProperty(localName = "openBank")
    private String openBank;
    @ApiModelProperty("账号")
    @JacksonXmlProperty(localName = "accountNo")
    private String accountNo;

    @ApiModelProperty("绑定手机")
    @JacksonXmlProperty(localName = "phone")
    private String phone;
    @ApiModelProperty("业务员手机号")
    @JacksonXmlProperty(localName = "salerMobile")
    private String salespersonMobile;
    @ApiModelProperty("设备编号或白钥匙编号")
    @JacksonXmlProperty(localName = "sbbh")
    private String deviceSn;
    @ApiModelProperty("核心板编号")
    @JacksonXmlProperty(localName = "hxbbh")
    private String coreBoardSn;
    @ApiModelProperty("项目名称")
    @JacksonXmlProperty(localName = "xmmc")
    private String projectName;
    @ApiModelProperty("默认商品")
    @JacksonXmlProperty(localName = "defautGoods")
    private String defaultGoods;
    @ApiModelProperty("默认商品编码")
    @JacksonXmlProperty(localName = "goodsId")
    private String goodsId;
    @ApiModelProperty("默认商品税率")
    @JacksonXmlProperty(localName = "taxRate")
    private String taxRate;
    @ApiModelProperty("开票人")
    @JacksonXmlProperty(localName = "kpr")
    private String issuer;

//    public void setSellerName(String sellerName) {
//        this.sellerName = sellerName;
//    }
//
//    public void setChannelId(String channelId) {
//        this.channelId = channelId;
//    }

//    public void setProvinceCode(String provinceCode) {
//        this.provinceCode = provinceCode;
//    }
//
//    public void setDispatch(String dispatch) {
//        this.dispatch = dispatch;
//    }
//
//    public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
//        this.taxpayerIdentificationNum = taxpayerIdentificationNum;
//    }
//
//    public void setRegisterAddress(String registerAddress) {
//        this.registerAddress = registerAddress;
//    }
//
//    public void setTel(String tel) {
//        this.tel = tel;
//    }
//
//    public void setOpenBank(String openBank) {
//        this.openBank = openBank;
//    }
//
//    public void setAccountNo(String accountNo) {
//        this.accountNo = accountNo;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setSalespersonMobile(String salespersonMobile) {
//        this.salespersonMobile = salespersonMobile;
//    }
//
//    public void setDeviceSn(String deviceSn) {
//        this.deviceSn = deviceSn;
//    }
//
//    public void setCoreBoardSn(String coreBoardSn) {
//        this.coreBoardSn = coreBoardSn;
//    }
//
//    public void setProjectName(String projectName) {
//        this.projectName = projectName;
//    }
//
//    public void setDefaultGoods(String defaultGoods) {
//        this.defaultGoods = defaultGoods;
//    }
//
//    public void setGoodsId(String goodsId) {
//        this.goodsId = goodsId;
//    }
//
//    public void setTaxRate(String taxRate) {
//        this.taxRate = taxRate;
//    }
//
//    public void setIssuer(String issuer) {
//        this.issuer = issuer;
//    }
//
//    public boolean equals(Object o) {
//        if (o == this) return true;
//        if (!(o instanceof RegisterSellerRequest)) return false;
//        RegisterSellerRequest other = (RegisterSellerRequest) o;
//        if (!other.canEqual(this)) return false;
//        Object this$channelId = getChannelId(), other$channelId = other.getChannelId();
//        if ((this$channelId == null) ? (other$channelId != null) : !this$channelId.equals(other$channelId))
//            return false;
//        Object this$sellerName = getSellerName(), other$sellerName = other.getSellerName();
//        if ((this$sellerName == null) ? (other$sellerName != null) : !this$sellerName.equals(other$sellerName))
//            return false;
//        Object this$provinceCode = getProvinceCode(), other$provinceCode = other.getProvinceCode();
//        if ((this$provinceCode == null) ? (other$provinceCode != null) : !this$provinceCode.equals(other$provinceCode))
//            return false;
//        Object this$dispatch = getDispatch(), other$dispatch = other.getDispatch();
//        if ((this$dispatch == null) ? (other$dispatch != null) : !this$dispatch.equals(other$dispatch)) return false;
//        Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
//        if ((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum))
//            return false;
//        Object this$registerAddress = getRegisterAddress(), other$registerAddress = other.getRegisterAddress();
//        if ((this$registerAddress == null) ? (other$registerAddress != null) : !this$registerAddress.equals(other$registerAddress))
//            return false;
//        Object this$tel = getTel(), other$tel = other.getTel();
//        if ((this$tel == null) ? (other$tel != null) : !this$tel.equals(other$tel)) return false;
//        Object this$openBank = getOpenBank(), other$openBank = other.getOpenBank();
//        if ((this$openBank == null) ? (other$openBank != null) : !this$openBank.equals(other$openBank)) return false;
//        Object this$accountNo = getAccountNo(), other$accountNo = other.getAccountNo();
//        if ((this$accountNo == null) ? (other$accountNo != null) : !this$accountNo.equals(other$accountNo))
//            return false;
//        Object this$phone = getPhone(), other$phone = other.getPhone();
//        if ((this$phone == null) ? (other$phone != null) : !this$phone.equals(other$phone)) return false;
//        Object this$salespersonMobile = getSalespersonMobile(), other$salespersonMobile = other.getSalespersonMobile();
//        if ((this$salespersonMobile == null) ? (other$salespersonMobile != null) : !this$salespersonMobile.equals(other$salespersonMobile))
//            return false;
//        Object this$deviceSn = getDeviceSn(), other$deviceSn = other.getDeviceSn();
//        if ((this$deviceSn == null) ? (other$deviceSn != null) : !this$deviceSn.equals(other$deviceSn)) return false;
//        Object this$coreBoardSn = getCoreBoardSn(), other$coreBoardSn = other.getCoreBoardSn();
//        if ((this$coreBoardSn == null) ? (other$coreBoardSn != null) : !this$coreBoardSn.equals(other$coreBoardSn))
//            return false;
//        Object this$projectName = getProjectName(), other$projectName = other.getProjectName();
//        if ((this$projectName == null) ? (other$projectName != null) : !this$projectName.equals(other$projectName))
//            return false;
//        Object this$defaultGoods = getDefaultGoods(), other$defaultGoods = other.getDefaultGoods();
//        if ((this$defaultGoods == null) ? (other$defaultGoods != null) : !this$defaultGoods.equals(other$defaultGoods))
//            return false;
//        Object this$goodsId = getGoodsId(), other$goodsId = other.getGoodsId();
//        if ((this$goodsId == null) ? (other$goodsId != null) : !this$goodsId.equals(other$goodsId)) return false;
//        Object this$taxRate = getTaxRate(), other$taxRate = other.getTaxRate();
//        if ((this$taxRate == null) ? (other$taxRate != null) : !this$taxRate.equals(other$taxRate)) return false;
//        Object this$issuer = getIssuer(), other$issuer = other.getIssuer();
//        return !((this$issuer == null) ? (other$issuer != null) : !this$issuer.equals(other$issuer));
//    }
//
//    protected boolean canEqual(Object other) {
//        return other instanceof RegisterSellerRequest;
//    }
//
//    public int hashCode() {
//        int PRIME = 59;
//        int result = 1;
//        Object $channelId = getChannelId();
//        result = result * 59 + (($channelId == null) ? 43 : $channelId.hashCode());
//        Object $sellerName = getSellerName();
//        result = result * 59 + (($sellerName == null) ? 43 : $sellerName.hashCode());
//        Object $provinceCode = getProvinceCode();
//        result = result * 59 + (($provinceCode == null) ? 43 : $provinceCode.hashCode());
//        Object $dispatch = getDispatch();
//        result = result * 59 + (($dispatch == null) ? 43 : $dispatch.hashCode());
//        Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
//        result = result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
//        Object $registerAddress = getRegisterAddress();
//        result = result * 59 + (($registerAddress == null) ? 43 : $registerAddress.hashCode());
//        Object $tel = getTel();
//        result = result * 59 + (($tel == null) ? 43 : $tel.hashCode());
//        Object $openBank = getOpenBank();
//        result = result * 59 + (($openBank == null) ? 43 : $openBank.hashCode());
//        Object $accountNo = getAccountNo();
//        result = result * 59 + (($accountNo == null) ? 43 : $accountNo.hashCode());
//        Object $phone = getPhone();
//        result = result * 59 + (($phone == null) ? 43 : $phone.hashCode());
//        Object $salespersonMobile = getSalespersonMobile();
//        result = result * 59 + (($salespersonMobile == null) ? 43 : $salespersonMobile.hashCode());
//        Object $deviceSn = getDeviceSn();
//        result = result * 59 + (($deviceSn == null) ? 43 : $deviceSn.hashCode());
//        Object $coreBoardSn = getCoreBoardSn();
//        result = result * 59 + (($coreBoardSn == null) ? 43 : $coreBoardSn.hashCode());
//        Object $projectName = getProjectName();
//        result = result * 59 + (($projectName == null) ? 43 : $projectName.hashCode());
//        Object $defaultGoods = getDefaultGoods();
//        result = result * 59 + (($defaultGoods == null) ? 43 : $defaultGoods.hashCode());
//        Object $goodsId = getGoodsId();
//        result = result * 59 + (($goodsId == null) ? 43 : $goodsId.hashCode());
//        Object $taxRate = getTaxRate();
//        result = result * 59 + (($taxRate == null) ? 43 : $taxRate.hashCode());
//        Object $issuer = getIssuer();
//        return result * 59 + (($issuer == null) ? 43 : $issuer.hashCode());
//    }
//
//    public String toString() {
//        return "RegisterSellerRequest(channelId=" + getChannelId() + ", sellerName=" + getSellerName() + ", provinceCode=" + getProvinceCode() + ", dispatch=" + getDispatch() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ", registerAddress=" + getRegisterAddress() + ", tel=" + getTel() + ", openBank=" + getOpenBank() + ", accountNo=" + getAccountNo() + ", phone=" + getPhone() + ", salespersonMobile=" + getSalespersonMobile() + ", deviceSn=" + getDeviceSn() + ", coreBoardSn=" + getCoreBoardSn() + ", projectName=" + getProjectName() + ", defaultGoods=" + getDefaultGoods() + ", goodsId=" + getGoodsId() + ", taxRate=" + getTaxRate() + ", issuer=" + getIssuer() + ")";
//    }
//
//
//    public String getChannelId() {
//        return this.channelId;
//    }
//
//
//    public String getSellerName() {
//        return this.sellerName;
//    }
//
//
//    public String getProvinceCode() {
//        return this.provinceCode;
//    }
//
//
//    public String getDispatch() {
//        return this.dispatch;
//    }
//
//
//    public String getTaxpayerIdentificationNum() {
//        return this.taxpayerIdentificationNum;
//    }
//
//
//    public String getRegisterAddress() {
//        return this.registerAddress;
//    }
//
//
//    public String getTel() {
//        return this.tel;
//    }
//
//
//    public String getOpenBank() {
//        return this.openBank;
//    }
//
//
//    public String getAccountNo() {
//        return this.accountNo;
//    }
//
//
//    public String getPhone() {
//        return this.phone;
//    }
//
//
//    public String getSalespersonMobile() {
//        return this.salespersonMobile;
//    }
//
//
//    public String getDeviceSn() {
//        return this.deviceSn;
//    }
//
//
//    public String getCoreBoardSn() {
//        return this.coreBoardSn;
//    }
//
//
//    public String getProjectName() {
//        return this.projectName;
//    }
//
//
//    public String getDefaultGoods() {
//        return this.defaultGoods;
//    }
//
//
//    public String getGoodsId() {
//        return this.goodsId;
//    }
//
//
//    public String getTaxRate() {
//        return this.taxRate;
//    }
//
//
//    public String getIssuer() {
//        return this.issuer;
//    }
}
