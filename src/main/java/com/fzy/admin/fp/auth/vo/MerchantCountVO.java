package com.fzy.admin.fp.auth.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import com.fzy.admin.fp.common.conver.MoneyJsonSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-05-02 11:14
 * @description
 */
@Data
public class MerchantCountVO {

    @Excel(name = "序号",orderNum = "1",height = 20,width = 20)
    private String merchantId;//商户ID

    @Excel(name = "商户名称",orderNum = "4",height = 20,width = 20)
    private String merchantName;//商户名称

    @Excel(name = "大区代理名称",orderNum = "2",height = 20,width = 20)
    private String agentName;//一级代理商名称

    @Excel(name = "省代名称",orderNum = "3",height = 20,width = 20)
    private String subAgentName;//二级代理商名称

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "订单金额",orderNum = "5",height = 20,width = 20)
    private BigDecimal orderAmount = BigDecimal.ZERO;//订单金额

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "退款金额",orderNum = "6",height = 20,width = 20)
    private BigDecimal refundAmount = BigDecimal.ZERO;//退款金额

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "有效交易基数",orderNum = "7",height = 20,width = 20)
    private BigDecimal validDealAmount = BigDecimal.ZERO;//有效交易基数

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "顾客实付",orderNum = "8",height = 20,width = 20)
    private BigDecimal customerPaidAmount = BigDecimal.ZERO;//顾客实付

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @Excel(name = "优惠",orderNum = "9",width = 20,height = 20)
    private BigDecimal discountAmount = BigDecimal.ZERO;//优惠金额
}
