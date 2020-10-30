package com.fzy.admin.fp.invoice.feign.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("企业信息设置")
public class EnterpriseInfoSetting {
    @ApiModelProperty("机身编号")
    @JacksonXmlProperty(localName = "jsbh")
    private String bodySn;
    @ApiModelProperty("营业地址")
    @JacksonXmlProperty(localName = "dz")
    private String businessAddress;

    public void setBodySn(String bodySn) {
        this.bodySn = bodySn;
    }

    @ApiModelProperty("电话")
    @JacksonXmlProperty(localName = "dh")
    private String telephone;
    @ApiModelProperty("银行名称")
    @JacksonXmlProperty(localName = "yh")
    private String blankName;
    @ApiModelProperty("银行账号")
    @JacksonXmlProperty(localName = "zh")
    private String blankAccount;

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBlankName(String blankName) {
        this.blankName = blankName;
    }

    public void setBlankAccount(String blankAccount) {
        this.blankAccount = blankAccount;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EnterpriseInfoSetting)) return false;
        EnterpriseInfoSetting other = (EnterpriseInfoSetting) o;
        if (!other.canEqual(this)) return false;
        Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
        if ((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn)) return false;
        Object this$businessAddress = getBusinessAddress(), other$businessAddress = other.getBusinessAddress();
        if ((this$businessAddress == null) ? (other$businessAddress != null) : !this$businessAddress.equals(other$businessAddress))
            return false;
        Object this$telephone = getTelephone(), other$telephone = other.getTelephone();
        if ((this$telephone == null) ? (other$telephone != null) : !this$telephone.equals(other$telephone))
            return false;
        Object this$blankName = getBlankName(), other$blankName = other.getBlankName();
        if ((this$blankName == null) ? (other$blankName != null) : !this$blankName.equals(other$blankName))
            return false;
        Object this$blankAccount = getBlankAccount(), other$blankAccount = other.getBlankAccount();
        return !((this$blankAccount == null) ? (other$blankAccount != null) : !this$blankAccount.equals(other$blankAccount));
    }

    protected boolean canEqual(Object other) {
        return other instanceof EnterpriseInfoSetting;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $bodySn = getBodySn();
        result = result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
        Object $businessAddress = getBusinessAddress();
        result = result * 59 + (($businessAddress == null) ? 43 : $businessAddress.hashCode());
        Object $telephone = getTelephone();
        result = result * 59 + (($telephone == null) ? 43 : $telephone.hashCode());
        Object $blankName = getBlankName();
        result = result * 59 + (($blankName == null) ? 43 : $blankName.hashCode());
        Object $blankAccount = getBlankAccount();
        return result * 59 + (($blankAccount == null) ? 43 : $blankAccount.hashCode());
    }

    public String toString() {
        return "EnterpriseInfoSetting(bodySn=" + getBodySn() + ", businessAddress=" + getBusinessAddress() + ", telephone=" + getTelephone() + ", blankName=" + getBlankName() + ", blankAccount=" + getBlankAccount() + ")";
    }


    public String getBodySn() {
        return this.bodySn;
    }


    public String getBusinessAddress() {
        return this.businessAddress;
    }


    public String getTelephone() {
        return this.telephone;
    }


    public String getBlankName() {
        return this.blankName;
    }


    public String getBlankAccount() {
        return this.blankAccount;
    }
}

