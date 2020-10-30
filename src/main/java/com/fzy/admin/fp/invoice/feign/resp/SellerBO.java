package com.fzy.admin.fp.invoice.feign.resp;

import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("服务商响信息")
public class SellerBO extends FamilyBaseBO {
    @ApiModelProperty("服务商ID")
    private String channelId;
    @ApiModelProperty("商户名称")
    private String clientName;
    @ApiModelProperty("省份编号")
    private String provinceno;
    @ApiModelProperty("按策略商户到子服务商")
    private String dispatch;
    @ApiModelProperty("纳税人识别号")
    private String nsrsbh;

    public SellerBO setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    @ApiModelProperty("注册地址")
    private String businessAddress;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("开户银行")
    private String openBank;
    @ApiModelProperty("账号")
    private String accountNo;
    @ApiModelProperty("商户 ID")
    private String merchantid;
    @ApiModelProperty("托管服务标识")
    private String zdytgwfbs;

    public SellerBO setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public SellerBO setProvinceno(String provinceno) {
        this.provinceno = provinceno;
        return this;
    }

    public SellerBO setDispatch(String dispatch) {
        this.dispatch = dispatch;
        return this;
    }

    public SellerBO setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
        return this;
    }

    public SellerBO setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
        return this;
    }

    public SellerBO setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public SellerBO setOpenBank(String openBank) {
        this.openBank = openBank;
        return this;
    }

    public SellerBO setAccountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public SellerBO setMerchantid(String merchantid) {
        this.merchantid = merchantid;
        return this;
    }

    public SellerBO setZdytgwfbs(String zdytgwfbs) {
        this.zdytgwfbs = zdytgwfbs;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SellerBO)) return false;
        SellerBO other = (SellerBO) o;
        if (!other.canEqual(this)) return false;
        Object this$channelId = getChannelId(), other$channelId = other.getChannelId();
        if ((this$channelId == null) ? (other$channelId != null) : !this$channelId.equals(other$channelId))
            return false;
        Object this$clientName = getClientName(), other$clientName = other.getClientName();
        if ((this$clientName == null) ? (other$clientName != null) : !this$clientName.equals(other$clientName))
            return false;
        Object this$provinceno = getProvinceno(), other$provinceno = other.getProvinceno();
        if ((this$provinceno == null) ? (other$provinceno != null) : !this$provinceno.equals(other$provinceno))
            return false;
        Object this$dispatch = getDispatch(), other$dispatch = other.getDispatch();
        if ((this$dispatch == null) ? (other$dispatch != null) : !this$dispatch.equals(other$dispatch)) return false;
        Object this$nsrsbh = getNsrsbh(), other$nsrsbh = other.getNsrsbh();
        if ((this$nsrsbh == null) ? (other$nsrsbh != null) : !this$nsrsbh.equals(other$nsrsbh)) return false;
        Object this$businessAddress = getBusinessAddress(), other$businessAddress = other.getBusinessAddress();
        if ((this$businessAddress == null) ? (other$businessAddress != null) : !this$businessAddress.equals(other$businessAddress))
            return false;
        Object this$tel = getTel(), other$tel = other.getTel();
        if ((this$tel == null) ? (other$tel != null) : !this$tel.equals(other$tel)) return false;
        Object this$openBank = getOpenBank(), other$openBank = other.getOpenBank();
        if ((this$openBank == null) ? (other$openBank != null) : !this$openBank.equals(other$openBank)) return false;
        Object this$accountNo = getAccountNo(), other$accountNo = other.getAccountNo();
        if ((this$accountNo == null) ? (other$accountNo != null) : !this$accountNo.equals(other$accountNo))
            return false;
        Object this$merchantid = getMerchantid(), other$merchantid = other.getMerchantid();
        if ((this$merchantid == null) ? (other$merchantid != null) : !this$merchantid.equals(other$merchantid))
            return false;
        Object this$zdytgwfbs = getZdytgwfbs(), other$zdytgwfbs = other.getZdytgwfbs();
        return !((this$zdytgwfbs == null) ? (other$zdytgwfbs != null) : !this$zdytgwfbs.equals(other$zdytgwfbs));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SellerBO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $channelId = getChannelId();
        result = result * 59 + (($channelId == null) ? 43 : $channelId.hashCode());
        Object $clientName = getClientName();
        result = result * 59 + (($clientName == null) ? 43 : $clientName.hashCode());
        Object $provinceno = getProvinceno();
        result = result * 59 + (($provinceno == null) ? 43 : $provinceno.hashCode());
        Object $dispatch = getDispatch();
        result = result * 59 + (($dispatch == null) ? 43 : $dispatch.hashCode());
        Object $nsrsbh = getNsrsbh();
        result = result * 59 + (($nsrsbh == null) ? 43 : $nsrsbh.hashCode());
        Object $businessAddress = getBusinessAddress();
        result = result * 59 + (($businessAddress == null) ? 43 : $businessAddress.hashCode());
        Object $tel = getTel();
        result = result * 59 + (($tel == null) ? 43 : $tel.hashCode());
        Object $openBank = getOpenBank();
        result = result * 59 + (($openBank == null) ? 43 : $openBank.hashCode());
        Object $accountNo = getAccountNo();
        result = result * 59 + (($accountNo == null) ? 43 : $accountNo.hashCode());
        Object $merchantid = getMerchantid();
        result = result * 59 + (($merchantid == null) ? 43 : $merchantid.hashCode());
        Object $zdytgwfbs = getZdytgwfbs();
        return result * 59 + (($zdytgwfbs == null) ? 43 : $zdytgwfbs.hashCode());
    }

    public String toString() {
        return "SellerBO(super=" + super.toString() + ", channelId=" + getChannelId() + ", clientName=" + getClientName() + ", provinceno=" + getProvinceno() + ", dispatch=" + getDispatch() + ", nsrsbh=" + getNsrsbh() + ", businessAddress=" + getBusinessAddress() + ", tel=" + getTel() + ", openBank=" + getOpenBank() + ", accountNo=" + getAccountNo() + ", merchantid=" + getMerchantid() + ", zdytgwfbs=" + getZdytgwfbs() + ")";
    }


    public String getChannelId() {
        return this.channelId;
    }


    public String getClientName() {
        return this.clientName;
    }


    public String getProvinceno() {
        return this.provinceno;
    }


    public String getDispatch() {
        return this.dispatch;
    }


    public String getNsrsbh() {
        return this.nsrsbh;
    }


    public String getBusinessAddress() {
        return this.businessAddress;
    }


    public String getTel() {
        return this.tel;
    }


    public String getOpenBank() {
        return this.openBank;
    }


    public String getAccountNo() {
        return this.accountNo;
    }


    public String getMerchantid() {
        return this.merchantid;
    }


    public String getZdytgwfbs() {
        return this.zdytgwfbs;
    }
}
