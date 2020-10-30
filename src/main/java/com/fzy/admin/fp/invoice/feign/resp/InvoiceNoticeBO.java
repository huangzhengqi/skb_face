package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("发票通知结果")
@Data
public class InvoiceNoticeBO extends FamilyBaseBO {
    @ApiModelProperty("响应数据")
    @JacksonXmlElementWrapper(localName = "returndata")
    @JacksonXmlProperty(localName = "kpxx")
    private List<InvoiceInfo> invoiceInfos;
    @ApiModelProperty("发票请求流水号")
    @JacksonXmlProperty(localName = "fpqqlsh")
    private String invoiceSn;

    protected boolean canEqual(Object other) {
        return other instanceof InvoiceNoticeBO;
    }


    public String toString() {
        return "InvoiceNoticeBO(super=" + super.toString() + ", invoiceInfos=" + getInvoiceInfos() + ", invoiceSn=" + getInvoiceSn() + ")";
    }

    @ApiModel("发票信息")
    @Data
    public static class InvoiceInfo {
        @ApiModelProperty("发票代码")
        @JacksonXmlProperty(localName = "fpdm")
        private String invoiceCode;
        @ApiModelProperty("发票号码")
        @JacksonXmlProperty(localName = "fphm")
        private String invoiceNumber;
        @ApiModelProperty(value = "发票号码", notes = "发票状态 00：已开具的正数发票 01：已开具的负数发票 02：未开具发票的作废发票 03：已开具正数发票的作废发票 04：已开具负数发票的作废发票;")
        @JacksonXmlProperty(localName = "fpzt")
        private String invoiceStatus;
        @ApiModelProperty("税控设备编号")
        @JacksonXmlProperty(localName = "sksbbh")
        private String taxControlEquipmentNum;
        @ApiModelProperty("开票日期")
        @JacksonXmlProperty(localName = "kprq")
        private String billingDate;
        @ApiModelProperty("购货单位识别号")
        @JacksonXmlProperty(localName = "ghdwsbh")
        private String purchaseUnitIdentificationNumber;

        @ApiModelProperty("购货单位名称")
        @JacksonXmlProperty(localName = "ghdwmc")
        private String purchaseUnitName;
        @ApiModelProperty("含计金额")
        @JacksonXmlProperty(localName = "hjje")
        private String totalAmount;
        @ApiModelProperty("含计税额")
        @JacksonXmlProperty(localName = "hjse")
        private String totalNumber;
        @ApiModelProperty("价税合计")
        @JacksonXmlProperty(localName = "jshj")
        private String totalPriceTax;
        @ApiModelProperty("电子发票下载地址")
        @JacksonXmlProperty(localName = "url")
        private String downloadUrl;
        @ApiModelProperty("密码区")
        @JacksonXmlProperty(localName = "mmq")
        private String passwordArea;

    }
}
