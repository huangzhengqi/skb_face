package com.fzy.admin.fp.member.rule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 21:38 2019/5/21
 * @ Description:会员记录详情
 **/
@Data
public class SmallProgramStoreRecordDetailVo {

    private String memberNum;//会员编号
    private String phone;//手机号
    private String tradeTypeText;//交易类型
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String operationUser;//操作人
    private String storeName;//门店名称
    private String orderNumber; // 订单表订单编号
    private BigDecimal tradingMoney;//交易余额

}
