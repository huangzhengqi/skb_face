package com.fzy.admin.fp.member.app.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-06-11 14:41
 * @description APP获取充值规则
 */
@Data
public class AppStoreRuleVO {

    private String storeRuleId; // 充值规则ID
    private String name; //  充值规则名称
    private String description; // 充值具体描述
    private BigDecimal storedMoney;//储值金额

}
