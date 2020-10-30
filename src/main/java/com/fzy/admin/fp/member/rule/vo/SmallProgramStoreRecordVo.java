package com.fzy.admin.fp.member.rule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 17:47 2019/5/21
 * @ Description:
 **/
@Data
public class SmallProgramStoreRecordVo {

    private String id; //交易记录id
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//交易时间
    private BigDecimal tradingMoney;// 金额
    private String tradeTypeText; //交易类型

    public SmallProgramStoreRecordVo(String id, Date createTime, BigDecimal tradingMoney, String tradeTypeText) {
        this.id = id;
        this.createTime = createTime;
        this.tradingMoney = tradingMoney;
        this.tradeTypeText = tradeTypeText;
    }
}
