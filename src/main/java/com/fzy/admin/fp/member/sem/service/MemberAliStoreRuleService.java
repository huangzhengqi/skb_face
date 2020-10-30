package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliStoredRule;
import com.fzy.admin.fp.member.sem.repository.MemberAliStoreRuleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class MemberAliStoreRuleService implements BaseService<MemberAliStoredRule> {

    @Resource
    private MemberAliStoreRuleRepository memberAliStoreRuleRepository;

    @Override
    public BaseRepository<MemberAliStoredRule> getRepository() {
        return memberAliStoreRuleRepository;
    }

    public Resp saveStoreRule(MemberAliStoredRule memberAliStoredRule) {
        if(null == memberAliStoredRule.getMerchantId()) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        if (null == memberAliStoredRule.getGiftType()) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"赠送类型未选");
        }
        if(null == memberAliStoredRule.getStoredAmount()) {
            memberAliStoredRule.setStoredAmount(0);
        }
        if (null == memberAliStoredRule.getStatus()) {
            memberAliStoredRule.setStatus(MemberAliStoredRule.Status.DISABLE.getCode());
        }
        if(StringUtils.isBlank(memberAliStoredRule.getName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"储值名称为空");
        }
        if(memberAliStoredRule.getStoredMoney().compareTo(BigDecimal.valueOf(0)) == -1){
            return new Resp().error(Resp.Status.PARAM_ERROR,"储值金额必须大于0");
        }
        if(memberAliStoredRule.getGiftType() == MemberAliStoredRule.GiftType.MONEY.getCode() || memberAliStoredRule.getGiftType() == MemberAliStoredRule.GiftType.INTEGRAL.getCode()){
            if(memberAliStoredRule.getStoredMoney().compareTo(BigDecimal.valueOf(0))== -1){
                return new Resp().error(Resp.Status.PARAM_ERROR,"赠送金额必须大于0");
            }
        }else if(memberAliStoredRule.getGiftType() == MemberAliStoredRule.GiftType.COUPONS.getCode()) {
            if (StringUtils.isBlank(memberAliStoredRule.getCouponId())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "优惠券未选");
            }
        }
        return Resp.success(memberAliStoreRuleRepository.save(memberAliStoredRule));
    }
}
