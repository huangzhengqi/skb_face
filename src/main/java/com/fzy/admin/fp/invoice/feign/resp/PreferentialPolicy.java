package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("增值税优惠政策")
public class PreferentialPolicy {
    @ApiModelProperty("子项列表")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ZZSYHZC")
    private List<PreferentialPolicyItem> policyItems;

    public void setPolicyItems(List<PreferentialPolicyItem> policyItems) {
        this.policyItems = policyItems;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PreferentialPolicy)) return false;
        PreferentialPolicy other = (PreferentialPolicy) o;
        if (!other.canEqual(this)) return false;
        Object this$policyItems = getPolicyItems(), other$policyItems = other.getPolicyItems();
        return !((this$policyItems == null) ? (other$policyItems != null) : !this$policyItems.equals(other$policyItems));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PreferentialPolicy;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $policyItems = getPolicyItems();
        return result * 59 + (($policyItems == null) ? 43 : $policyItems.hashCode());
    }

    public String toString() {
        return "PreferentialPolicy(policyItems=" + getPolicyItems() + ")";
    }

    public PreferentialPolicy(List<PreferentialPolicyItem> policyItems) {
        this.policyItems = policyItems;
    }


    public PreferentialPolicy() {
    }


    public List<PreferentialPolicyItem> getPolicyItems() {
        return this.policyItems;
    }

    @ApiModel("增值税优惠项")
    public static class PreferentialPolicyItem {
        @ApiModelProperty("优惠政策描述")
        @JacksonXmlProperty(localName = "YHZCMC")
        private String desc;

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @ApiModelProperty("税率")
        @JacksonXmlProperty(localName = "SL")
        private String taxRate;

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof PreferentialPolicyItem)) return false;
            PreferentialPolicyItem other = (PreferentialPolicyItem) o;
            if (!other.canEqual(this)) return false;
            Object this$desc = getDesc(), other$desc = other.getDesc();
            if ((this$desc == null) ? (other$desc != null) : !this$desc.equals(other$desc)) return false;
            Object this$taxRate = getTaxRate(), other$taxRate = other.getTaxRate();
            return !((this$taxRate == null) ? (other$taxRate != null) : !this$taxRate.equals(other$taxRate));
        }

        protected boolean canEqual(Object other) {
            return other instanceof PreferentialPolicyItem;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            Object $desc = getDesc();
            result = result * 59 + (($desc == null) ? 43 : $desc.hashCode());
            Object $taxRate = getTaxRate();
            return result * 59 + (($taxRate == null) ? 43 : $taxRate.hashCode());
        }

        public String toString() {
            return "PreferentialPolicy.PreferentialPolicyItem(desc=" + getDesc() + ", taxRate=" + getTaxRate() + ")";
        }

        public PreferentialPolicyItem(String desc, String taxRate) {
            this.desc = desc;
            this.taxRate = taxRate;
        }


        public PreferentialPolicyItem() {
        }


        public String getDesc() {
            return this.desc;
        }


        public String getTaxRate() {
            return this.taxRate;
        }
    }

}
