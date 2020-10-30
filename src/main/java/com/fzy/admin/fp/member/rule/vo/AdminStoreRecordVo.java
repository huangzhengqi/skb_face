package com.fzy.admin.fp.member.rule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:42 2019/5/21
 * @ Description:后台储值记录VO
 **/
@Data
public class AdminStoreRecordVo {

    private String storedNum;//交易单号
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//交易时间
    private String phone;//手机号
    private String tradeTypeText;//交易类型
    private String sourceText;//来源
    private String payWayText;//支付方式
    private String operationUser;//操作人
    private BigDecimal giftMoney = BigDecimal.ZERO;//赠送金额
    private BigDecimal tradingMoney = BigDecimal.ZERO;//交易余额
    private BigDecimal postTradingMoney = BigDecimal.ZERO;//交易后余额
    private String storeName;//门店名称
    private Integer scores;//赠送积分

    public AdminStoreRecordVo(String storedNum, Date createTime, String phone, String tradeTypeText, String sourceText, String payWayText, String operationUser, BigDecimal giftMoney, BigDecimal tradingMoney, BigDecimal postTradingMoney, String storeName, Integer scores) {
        this.storedNum = storedNum;
        this.createTime = createTime;
        this.phone = phone;
        this.tradeTypeText = tradeTypeText;
        this.sourceText = sourceText;
        this.payWayText = payWayText;
        this.operationUser = operationUser;
        this.giftMoney = giftMoney;
        this.tradingMoney = tradingMoney;
        this.postTradingMoney = postTradingMoney;
        this.storeName = storeName;
        this.scores = scores;
    }
}
