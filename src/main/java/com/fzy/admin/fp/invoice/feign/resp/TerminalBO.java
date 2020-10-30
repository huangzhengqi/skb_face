package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("终端设置查询结果")
public class TerminalBO extends FamilyBaseBO {
    @ApiModelProperty("设备group")
    @JacksonXmlProperty(localName = "list")
    private TerminalGroup terminalGroup;
    @JacksonXmlProperty(localName = "count", isAttribute = true)
    private Integer count;

    public void setTerminalGroup(TerminalGroup terminalGroup) {
        this.terminalGroup = terminalGroup;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TerminalBO)) return false;
        TerminalBO other = (TerminalBO) o;
        if (!other.canEqual(this)) return false;
        Object this$terminalGroup = getTerminalGroup(), other$terminalGroup = other.getTerminalGroup();
        if ((this$terminalGroup == null) ? (other$terminalGroup != null) : !this$terminalGroup.equals(other$terminalGroup))
            return false;
        Object this$count = getCount(), other$count = other.getCount();
        return !((this$count == null) ? (other$count != null) : !this$count.equals(other$count));
    }

    protected boolean canEqual(Object other) {
        return other instanceof TerminalBO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $terminalGroup = getTerminalGroup();
        result = result * 59 + (($terminalGroup == null) ? 43 : $terminalGroup.hashCode());
        Object $count = getCount();
        return result * 59 + (($count == null) ? 43 : $count.hashCode());
    }

    public String toString() {
        return "TerminalBO(terminalGroup=" + getTerminalGroup() + ", count=" + getCount() + ")";
    }


    public TerminalGroup getTerminalGroup() {
        return this.terminalGroup;
    }


    public Integer getCount() {
        return this.count;
    }

    @ApiModel("终端设备信息")
    public static class TerminalGroup {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Terminal> group;

        public String toString() {
            return "TerminalBO.TerminalGroup(group=" + getGroup() + ")";
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $group = getGroup();
            return result * 59 + (($group == null) ? 43 : $group.hashCode());
        }

        protected boolean canEqual(Object other) {
            return other instanceof TerminalGroup;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof TerminalGroup)) return false;
            TerminalGroup other = (TerminalGroup) o;
            if (!other.canEqual(this)) return false;
            Object this$group = getGroup(), other$group = other.getGroup();
            return !((this$group == null) ? (other$group != null) : !this$group.equals(other$group));
        }

        public void setGroup(List<TerminalBO.Terminal> group) {
            this.group = group;
        }


        public List<TerminalBO.Terminal> getGroup() {
            return this.group;
        }
    }

    @ApiModel("终端设备信息")
    public static class Terminal {
        @ApiModelProperty("序号")
        @JacksonXmlProperty(localName = "xh", isAttribute = true)
        private Integer orderIndex;
        @ApiModelProperty("终端id")
        @JacksonXmlProperty(localName = "terminalid")
        private String terminalId;
        @ApiModelProperty("纳税人识别号")
        @JacksonXmlProperty(localName = "nsrsbh")
        private String taxpayerIdentificationNum;
        @ApiModelProperty("商户ID")
        @JacksonXmlProperty(localName = "merchantid")
        private String merchantId;
        @ApiModelProperty("门店ID")
        @JacksonXmlProperty(localName = "storeid")
        private String storeId;
        @ApiModelProperty("设备id")
        @JacksonXmlProperty(localName = "sbid")
        private String deviceId;

        public void setOrderIndex(Integer orderIndex) {
            this.orderIndex = orderIndex;
        }

        @ApiModelProperty("设备类型")
        @JacksonXmlProperty(localName = "sblx")
        private String deviceType;
        @ApiModelProperty("终端类别")
        @JacksonXmlProperty(localName = "devicetype")
        private String terminalType;
        @ApiModelProperty("机身编号")
        @JacksonXmlProperty(localName = "jsbh")
        private String bodySn;
        @ApiModelProperty("税控盘号")
        @JacksonXmlProperty(localName = "skph")
        private String taxControlNumber;
        @ApiModelProperty("终端代码")
        @JacksonXmlProperty(localName = "kpddm")
        private String terminalCode;
        @ApiModelProperty("终端名称")
        @JacksonXmlProperty(localName = "kpdmc")
        private String terminalName;
        @ApiModelProperty("创建时间")
        @JacksonXmlProperty(localName = "createdate")
        private String createDate;

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public void setTaxpayerIdentificationNum(String taxpayerIdentificationNum) {
            this.taxpayerIdentificationNum = taxpayerIdentificationNum;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public void setTerminalType(String terminalType) {
            this.terminalType = terminalType;
        }

        public void setBodySn(String bodySn) {
            this.bodySn = bodySn;
        }

        public void setTaxControlNumber(String taxControlNumber) {
            this.taxControlNumber = taxControlNumber;
        }

        public void setTerminalCode(String terminalCode) {
            this.terminalCode = terminalCode;
        }

        public void setTerminalName(String terminalName) {
            this.terminalName = terminalName;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Terminal)) return false;
            Terminal other = (Terminal) o;
            if (!other.canEqual(this)) return false;
            Object this$orderIndex = getOrderIndex(), other$orderIndex = other.getOrderIndex();
            if ((this$orderIndex == null) ? (other$orderIndex != null) : !this$orderIndex.equals(other$orderIndex))
                return false;
            Object this$terminalId = getTerminalId(), other$terminalId = other.getTerminalId();
            if ((this$terminalId == null) ? (other$terminalId != null) : !this$terminalId.equals(other$terminalId))
                return false;
            Object this$taxpayerIdentificationNum = getTaxpayerIdentificationNum(), other$taxpayerIdentificationNum = other.getTaxpayerIdentificationNum();
            if ((this$taxpayerIdentificationNum == null) ? (other$taxpayerIdentificationNum != null) : !this$taxpayerIdentificationNum.equals(other$taxpayerIdentificationNum))
                return false;
            Object this$merchantId = getMerchantId(), other$merchantId = other.getMerchantId();
            if ((this$merchantId == null) ? (other$merchantId != null) : !this$merchantId.equals(other$merchantId))
                return false;
            Object this$storeId = getStoreId(), other$storeId = other.getStoreId();
            if ((this$storeId == null) ? (other$storeId != null) : !this$storeId.equals(other$storeId)) return false;
            Object this$deviceId = getDeviceId(), other$deviceId = other.getDeviceId();
            if ((this$deviceId == null) ? (other$deviceId != null) : !this$deviceId.equals(other$deviceId))
                return false;
            Object this$deviceType = getDeviceType(), other$deviceType = other.getDeviceType();
            if ((this$deviceType == null) ? (other$deviceType != null) : !this$deviceType.equals(other$deviceType))
                return false;
            Object this$terminalType = getTerminalType(), other$terminalType = other.getTerminalType();
            if ((this$terminalType == null) ? (other$terminalType != null) : !this$terminalType.equals(other$terminalType))
                return false;
            Object this$bodySn = getBodySn(), other$bodySn = other.getBodySn();
            if ((this$bodySn == null) ? (other$bodySn != null) : !this$bodySn.equals(other$bodySn)) return false;
            Object this$taxControlNumber = getTaxControlNumber(), other$taxControlNumber = other.getTaxControlNumber();
            if ((this$taxControlNumber == null) ? (other$taxControlNumber != null) : !this$taxControlNumber.equals(other$taxControlNumber))
                return false;
            Object this$terminalCode = getTerminalCode(), other$terminalCode = other.getTerminalCode();
            if ((this$terminalCode == null) ? (other$terminalCode != null) : !this$terminalCode.equals(other$terminalCode))
                return false;
            Object this$terminalName = getTerminalName(), other$terminalName = other.getTerminalName();
            if ((this$terminalName == null) ? (other$terminalName != null) : !this$terminalName.equals(other$terminalName))
                return false;
            Object this$createDate = getCreateDate(), other$createDate = other.getCreateDate();
            return !((this$createDate == null) ? (other$createDate != null) : !this$createDate.equals(other$createDate));
        }

        protected boolean canEqual(Object other) {
            return other instanceof Terminal;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $orderIndex = getOrderIndex();
            result = result * 59 + (($orderIndex == null) ? 43 : $orderIndex.hashCode());
            Object $terminalId = getTerminalId();
            result = result * 59 + (($terminalId == null) ? 43 : $terminalId.hashCode());
            Object $taxpayerIdentificationNum = getTaxpayerIdentificationNum();
            result = result * 59 + (($taxpayerIdentificationNum == null) ? 43 : $taxpayerIdentificationNum.hashCode());
            Object $merchantId = getMerchantId();
            result = result * 59 + (($merchantId == null) ? 43 : $merchantId.hashCode());
            Object $storeId = getStoreId();
            result = result * 59 + (($storeId == null) ? 43 : $storeId.hashCode());
            Object $deviceId = getDeviceId();
            result = result * 59 + (($deviceId == null) ? 43 : $deviceId.hashCode());
            Object $deviceType = getDeviceType();
            result = result * 59 + (($deviceType == null) ? 43 : $deviceType.hashCode());
            Object $terminalType = getTerminalType();
            result = result * 59 + (($terminalType == null) ? 43 : $terminalType.hashCode());
            Object $bodySn = getBodySn();
            result = result * 59 + (($bodySn == null) ? 43 : $bodySn.hashCode());
            Object $taxControlNumber = getTaxControlNumber();
            result = result * 59 + (($taxControlNumber == null) ? 43 : $taxControlNumber.hashCode());
            Object $terminalCode = getTerminalCode();
            result = result * 59 + (($terminalCode == null) ? 43 : $terminalCode.hashCode());
            Object $terminalName = getTerminalName();
            result = result * 59 + (($terminalName == null) ? 43 : $terminalName.hashCode());
            Object $createDate = getCreateDate();
            return result * 59 + (($createDate == null) ? 43 : $createDate.hashCode());
        }

        public String toString() {
            return "TerminalBO.Terminal(orderIndex=" + getOrderIndex() + ", terminalId=" + getTerminalId() + ", taxpayerIdentificationNum=" + getTaxpayerIdentificationNum() + ", merchantId=" + getMerchantId() + ", storeId=" + getStoreId() + ", deviceId=" + getDeviceId() + ", deviceType=" + getDeviceType() + ", terminalType=" + getTerminalType() + ", bodySn=" + getBodySn() + ", taxControlNumber=" + getTaxControlNumber() + ", terminalCode=" + getTerminalCode() + ", terminalName=" + getTerminalName() + ", createDate=" + getCreateDate() + ")";
        }


        public Integer getOrderIndex() {
            return this.orderIndex;
        }


        public String getTerminalId() {
            return this.terminalId;
        }


        public String getTaxpayerIdentificationNum() {
            return this.taxpayerIdentificationNum;
        }


        public String getMerchantId() {
            return this.merchantId;
        }


        public String getStoreId() {
            return this.storeId;
        }


        public String getDeviceId() {
            return this.deviceId;
        }


        public String getDeviceType() {
            return this.deviceType;
        }


        public String getTerminalType() {
            return this.terminalType;
        }


        public String getBodySn() {
            return this.bodySn;
        }


        public String getTaxControlNumber() {
            return this.taxControlNumber;
        }


        public String getTerminalCode() {
            return this.terminalCode;
        }


        public String getTerminalName() {
            return this.terminalName;
        }


        public String getCreateDate() {
            return this.createDate;
        }
    }

}
