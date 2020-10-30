package com.fzy.admin.fp.invoice.feign.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品编码")
@Data
public class GoodsItem {
    @ApiModelProperty("版本")
    @JacksonXmlProperty(localName = "BB")
    private String version;
    @ApiModelProperty("启用时间")
    @JacksonXmlProperty(localName = "QYSJ")
    private String enableTime;
    @ApiModelProperty("过渡截止时间")
    @JacksonXmlProperty(localName = "GDQJZSJ")
    private String transitionDeadline;
    @ApiModelProperty("商品编码")
    @JacksonXmlProperty(localName = "SPBM")
    private String goodsCode;
    @ApiModelProperty("商品名称")
    @JacksonXmlProperty(localName = "SPMC")
    private String goodsName;
    @ApiModelProperty("商品编码简称")
    @JacksonXmlProperty(localName = "SPBMJC")
    private String goodsAbbreviation;
    @ApiModelProperty("说明")
    @JacksonXmlProperty(localName = "SM")
    private String description;
    @ApiModelProperty("增值税税率")
    @JacksonXmlProperty(localName = "ZZSSL")
    private String vatRate;
    @ApiModelProperty("关键字")
    @JacksonXmlProperty(localName = "GJZ")
    private String keyword;
    @ApiModelProperty("汇总项")
    @JacksonXmlProperty(localName = "HZX")
    private String summaryItem;
    @ApiModelProperty(value = "可用状态", notes = " Y:可用 N:不可用 ")
    @JacksonXmlProperty(localName = "KYZT")
    private String availableState;
    @ApiModelProperty("增值税特殊管理")
    @JacksonXmlProperty(localName = "ZZSTSGL")
    private String vatSpecialManagement;
    @ApiModelProperty("增值税政策依据")
    @JacksonXmlProperty(localName = "ZZSZCYJ")
    private String vatPolicyBasis;
    @ApiModelProperty("增值税特殊管理代码")
    @JacksonXmlProperty(localName = "ZZSTSNRDM")
    private String vatSpecialManagementCode;
    @ApiModelProperty("消费税特殊管理")
    @JacksonXmlProperty(localName = "XFSGL")
    private String saleTaxSpecialManagement;
    @ApiModelProperty("消费税政策依据")
    @JacksonXmlProperty(localName = "XFSZCYJ")
    private String saleTaxPolicyBasis;
    @ApiModelProperty("消费税政策依据代码")
    @JacksonXmlProperty(localName = "XFSTSNRDM")
    private String saleTaxPolicyBasisCode;
    @ApiModelProperty("统计编码")
    @JacksonXmlProperty(localName = "TJJBM")
    private String statisticalCoding;
    @ApiModelProperty("海关品目")
    @JacksonXmlProperty(localName = "HGJCKSPPM")
    private String customsItems;
    @ApiModelProperty("商品编码的上级节点")
    @JacksonXmlProperty(localName = "PID")
    private String parentCode;
    @ApiModelProperty("更新时间")
    @JacksonXmlProperty(localName = "GXSJ")
    private String updateDate;

}
