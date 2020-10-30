package com.fzy.admin.fp.member.credits.utils;


import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lb
 * @date 2019/5/20 16:00
 * @Description new 对象
 */
public class ObjectUtil {

    public ObjectUtil() {
    }

    public static CreditsInfo addCreditsInfo(String merchantId, String memberNum,
                                             String phone, Integer tradeScores, String transactionType, Integer avaCredits, String storeId, String storeName) {
        CreditsInfo creditsInfo = new CreditsInfo();
        creditsInfo.setMerchantId(merchantId);
        creditsInfo.setMemberNum(memberNum);
        creditsInfo.setPhone(phone);
        creditsInfo.setTradeScores(0 - tradeScores);
        creditsInfo.setTradeTime(new Date());
        creditsInfo.setTransactionType(transactionType);
        creditsInfo.setTradeNum(2);
        creditsInfo.setAvaCredits(avaCredits);
        creditsInfo.setInfoType(CreditsInfo.Trade.CONSUM_SCORE.getCode());
        creditsInfo.setStoreId(storeId);
        creditsInfo.setStoreName(storeName);
        return creditsInfo;
    }

    public static ExchangeRecord addExchangeRecord(String productName, String imageURL, Integer conCredits,
                                                   String phone, Integer avaCredits, Integer status, String operator, String codeGoods,
                                                   String merchantId, String memberId, String productId, BigDecimal productMoney, String remark) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        exchangeRecord.setProductName(productName);
        exchangeRecord.setImageURL(imageURL);
        exchangeRecord.setConCredits(conCredits);
        exchangeRecord.setPhone(phone);
        exchangeRecord.setAvaCredits(avaCredits);
        exchangeRecord.setStatus(status);
        exchangeRecord.setOperator(operator);
        exchangeRecord.setGoodCodes(codeGoods);
        exchangeRecord.setMerchantId(merchantId);
        exchangeRecord.setMemberId(memberId);
        exchangeRecord.setProductId(productId);
        exchangeRecord.setProductMoney(productMoney);
        exchangeRecord.setRemark(remark);
        return exchangeRecord;
    }
}
