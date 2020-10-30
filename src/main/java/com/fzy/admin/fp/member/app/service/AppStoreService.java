package com.fzy.admin.fp.member.app.service;


import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.goods.domain.PayRule;
import com.fzy.admin.fp.goods.service.PayRuleService;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.coupon.service.ReceiveCouponService;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.member.credits.service.CreditsRulerService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.app.dto.AppStoreDTO;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.coupon.service.ReceiveCouponService;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.member.credits.service.CreditsRulerService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.order.feign.OrderServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-06-05 11:15
 * @description app储值业务
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class AppStoreService {

    @Resource
    private OrderServiceFeign orderServiceFeign;

    @Resource
    private MemberService memberService;

    @Resource
    private StoredRuleService storedRuleService;

    @Resource
    private StoredRecoredService storedRecoredService;

    @Resource
    private CreditsRulerService creditsRulerService;

    @Resource
    private CreditsInfoService creditsInfoService;

    @Resource
    private ReceiveCouponService receiveCouponService;

    @Resource
    private PayRuleService payRuleService;

    @Resource
    private MemberLevelService memberLevelService;

    /**
     * @author Created by wtl on 2019/6/5 11:04
     * @Description 商户帮会员充值
     * 充值流程：
     * 1.生成订单并调用扫码支付接口付款
     * 2.生成会员储值记录
     * 3.判断储值额外赠送（积分、会员卡金额、卡券）并添加到对应的表
     * 4.判断积分规则是否设置充值消费送积分
     * 5.积分变动的话，生成积分明细记录
     */
    public String storeMoney(String userId, AppStoreDTO appStoreDTO) {
        // 付款码只能用微信或者支付宝的，不能使用会员卡付款码充值
        if (appStoreDTO.getAuthCode().startsWith("MPC-")) {
            throw new BaseException("只能用微信或者支付宝付款", Resp.Status.PARAM_ERROR.getCode());
        }
        // 获取充值规则
        StoredRule storedRule = storedRuleService.findOne(appStoreDTO.getStoreRuleId());
        // 1.调用扫码支付
        OrderVo orderVo = orderServiceFeign.scanPay(userId, storedRule.getStoredMoney(), appStoreDTO.getAuthCode());
        if (!ParamUtil.isBlank(orderVo.getError())) {
            throw new BaseException(orderVo.getError(), Resp.Status.PARAM_ERROR.getCode());
        }
        createStoreRecord(orderVo, storedRule, appStoreDTO.getMemberId());
        return orderVo.getOrderNumber();
    }

    /**
     * @author Created by wtl on 2019/6/19 18:56
     * @Description 生成储值记录等操作
     */
//    public void createStoreRecord(OrderVo orderVo, StoredRule storedRule, String memberId) {
//        // 2.生成会员储值记录
//        Member member = memberService.findOne(memberId);
//        member.setBalance(member.getBalance().add(storedRule.getStoredMoney()));
//        // 先保存会员的积分
//        int originScore = member.getScores();
//        //创建储值记录
//        StoredRecored storedRecored = new StoredRecored();
//        storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
//        storedRecored.setMerchantId(orderVo.getMerchantId());
//        storedRecored.setOrderNumber(orderVo.getOrderNumber());
//        storedRecored.setMemberId(member.getId());
//        storedRecored.setStoreId(orderVo.getStoreId());
//        storedRecored.setPhone(member.getPhone());
//        storedRecored.setStoreName(orderVo.getStoreName());
//        storedRecored.setMemberNum(member.getMemberNum());
//        storedRecored.setTradeType(StoredRecored.TradeType.RECHARGE.getCode());
//        storedRecored.setSource(orderVo.getPayClient());
//        storedRecored.setPayWay(orderVo.getPayWay());
//        storedRecored.setPayTime(orderVo.getPayTime());
//        storedRecored.setStoreRuleId(storedRule.getId());
//        storedRecored.setPayStatus(orderVo.getStatus());
//        storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
//        storedRecored.setOperationUser(orderVo.getUserName());
//        BigDecimal giftMoney = (null == storedRule.getGiftMoney() || !storedRule.getGiftType().equals(StoredRule.GiftType.MONEY.getCode()) ? BigDecimal.ZERO :
//                storedRule.getGiftMoney());
//        storedRecored.setGiftMoney(giftMoney);
//        storedRecored.setTradingMoney(storedRule.getStoredMoney());
//        storedRecored.setPostTradingMoney(member.getBalance().add(giftMoney));
//        storedRecored.setDiscountMoney(BigDecimal.ZERO);
//        storedRecored.setScores(0);
//        // 3.额外赠送，自定义储值规则无额外赠送
//        if (!ParamUtil.isBlank(storedRule.getGiftType())) {
//            //如果储值规则送金额
//            if (StoredRule.GiftType.MONEY.getCode().equals(storedRule.getGiftType())) {
//                member.setBalance(member.getBalance().add(giftMoney));
//            }
//            //如果储值规则送积分
//            if (StoredRule.GiftType.INTEGRAL.getCode().equals(storedRule.getGiftType())) {
//                member.setScores(member.getScores() + storedRule.getGiftMoney().intValue());
//                storedRecored.setScores(storedRecored.getScores() + storedRule.getGiftMoney().intValue());
//                creditsInfoDetails(orderVo, member, originScore, CreditsInfo.Trade.RECHARGE_GIFTS);
//                originScore=member.getScores();
//            }
//            //如果储值规则送卡券
//            if (StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())) {
//                ReceiveCoupon receiveCoupon = new ReceiveCoupon();
//                receiveCoupon.setMemberId(member.getId());
//                receiveCoupon.setCouponId(storedRule.getCouponId());
//                receiveCouponService.receiveCoupon(orderVo.getMerchantId(), receiveCoupon);
//
//            }
//        }
//        // 积分规则：储值消费如果设置为送积分
//        CreditsRuler creditsRuler = creditsRulerService.findByMerchantId(orderVo.getMerchantId());
//        if (creditsRuler.getIsTrue() == 1) {
//            // 积分规则为没消费x元送y积分，如果x为0则不需要计算
//            if (creditsRuler.getConsumptionAmount().compareTo(BigDecimal.ZERO) > 0) {
//                int score = storedRule.getStoredMoney().multiply(new BigDecimal(creditsRuler.getCredits())).divideToIntegralValue(creditsRuler.getConsumptionAmount()).intValue();
//                member.setScores(member.getScores() + score);
//                storedRecored.setScores(storedRecored.getScores() + score);
//            }
//            creditsInfoDetails(orderVo, member, originScore,CreditsInfo.Trade.CONSUM_CONSUM);
//        }
//
//        storedRecored.setRemainScore(storedRecored.getScores());
//        storedRecoredService.save(storedRecored);
//        member.setLastPayDate(new Date());
//        memberService.save(member);
//    }

    public void createStoreRecord(OrderVo orderVo, StoredRule storedRule, String memberId) {
        Member member = (Member)this.memberService.findOne(memberId);
        member.setBalance(member.getBalance().add(storedRule.getStoredMoney()));

        log.info("storedRule 储值规则表  --- "  + storedRule);

        int originScore = member.getScores().intValue();

        StoredRecored storedRecored = new StoredRecored();
        storedRecored.setStoreRuleId(storedRule.getId());

        BigDecimal giftMoney = (null == storedRule.getGiftMoney() || !storedRule.getGiftType().equals(StoredRule.GiftType.MONEY.getCode())) ? BigDecimal.ZERO : storedRule.getGiftMoney();
        storedRecored.setGiftMoney(giftMoney);
        storedRecored.setTradingMoney(storedRule.getStoredMoney());
        storedRecored.setPostTradingMoney(member.getBalance().add(giftMoney));
        setStoredRecord(orderVo, member, storedRecored);

        if (!ParamUtil.isBlank(storedRule.getGiftType())) {

            if (StoredRule.GiftType.MONEY.getCode().equals(storedRule.getGiftType())) {
                PayRule payRule = this.payRuleService.findByCompanyId(storedRecored.getMerchantId());
                payRule = this.payRuleService.getPayRule(payRule);
                if (payRule.getRechargeUse().equals(Integer.valueOf(1))) {
                    member.setBalance(member.getBalance().add(giftMoney));
                } else {
                    member.setFreezeBalance(member.getFreezeBalance().add(giftMoney));
                }
            }

            if (StoredRule.GiftType.INTEGRAL.getCode().equals(storedRule.getGiftType())) {
                member.setScores(Integer.valueOf(member.getScores().intValue() + storedRule.getGiftMoney().intValue()));
                log.info("会员信息1,{}", member);
                storedRecored.setScores(Integer.valueOf(storedRecored.getScores().intValue() + storedRule.getGiftMoney().intValue()));
                creditsInfoDetails(orderVo, member, originScore, CreditsInfo.Trade.RECHARGE_GIFTS);
                originScore = member.getScores().intValue();
            }

            if (StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())) {
                ReceiveCoupon receiveCoupon = new ReceiveCoupon();
                receiveCoupon.setMemberId(member.getId());
                receiveCoupon.setCouponId(storedRule.getCouponId());
                this.receiveCouponService.receiveCoupon(orderVo.getMerchantId(), receiveCoupon);
            }
        }


        CreditsRuler creditsRuler = this.creditsRulerService.findByMerchantId(orderVo.getMerchantId());
        if (creditsRuler.getIsTrue().intValue() == 1) {

            if (creditsRuler.getConsumptionAmount().compareTo(BigDecimal.ZERO) > 0) {
                int score = storedRule.getStoredMoney().multiply(new BigDecimal(creditsRuler.getCredits().intValue())).divideToIntegralValue(creditsRuler.getConsumptionAmount()).intValue();
                member.setScores(Integer.valueOf(member.getScores().intValue() + score));
                storedRecored.setScores(Integer.valueOf(storedRecored.getScores().intValue() + score));
            }
            log.info("会员信息2,{}", member);
            creditsInfoDetails(orderVo, member, originScore, CreditsInfo.Trade.CONSUM_CONSUM);
        }

        storedRecored.setRemainScore(storedRecored.getScores());
        this.storedRecoredService.save(storedRecored);

        if(storedRule.getStoredAmount() == null){
            storedRule.setStoredAmount(1);
        }else {
            storedRule.setStoredAmount(Integer.valueOf(storedRule.getStoredAmount().intValue() + 1));
        }
        this.storedRuleService.save(storedRule);

        member.setLastPayDate(new Date());
        log.info("小程序充值后会员信息   -------------------- "  + member);

        //会员等级表
        List<MemberLevel> memberLevelList = memberLevelService.findByMerchantIdOrderByMemberLimitAmountAsc(member.getMerchantId());
        if(memberLevelList !=null || !memberLevelList.isEmpty()){
            for (MemberLevel memberLevel: memberLevelList){
                log.info("会员等级表 -------------------   " + memberLevel);
                int a = member.getBalance().compareTo(memberLevel.getMemberLimitAmount());
                //还没达到会员标准
                if(a == -1){
                    continue;
                }else {
                    member.setMemberLevelId(memberLevel.getId());
                    continue;
                }
            }
        }
        this.memberService.save(member);
    }


    public void creditsInfoDetails(OrderVo orderVo, Member member, int originScore,CreditsInfo.Trade infoType) {

        // 判断积分是否变化，积分变化需要生成积分明细表
        if (originScore != member.getScores()) {
            CreditsInfo creditsInfo = new CreditsInfo();
            creditsInfo.setMemberNum(member.getMemberNum());
            creditsInfo.setPhone(member.getPhone());
            creditsInfo.setInfoType(infoType.getCode());
            creditsInfo.setTransactionType(infoType.getMessage());
            creditsInfo.setTradeTime(new Date());
            creditsInfo.setTradeScores(member.getScores() - originScore);
            creditsInfo.setAvaCredits(member.getScores());
            creditsInfo.setMerchantId(orderVo.getMerchantId());
            creditsInfo.setStoreId(orderVo.getStoreId());
            creditsInfo.setStoreName(orderVo.getStoreName());
            creditsInfo.setTradeNum(1);
            creditsInfoService.save(creditsInfo);
        }
    }


    public void setStoredRecord(OrderVo orderVo, Member member, StoredRecored storedRecored) {
        storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
        storedRecored.setMerchantId(orderVo.getMerchantId());
        storedRecored.setOrderNumber(orderVo.getOrderNumber());
        storedRecored.setMemberId(member.getId());
        storedRecored.setStoreId(orderVo.getStoreId());
        storedRecored.setPhone(member.getPhone());
        storedRecored.setStoreName(orderVo.getStoreName());
        storedRecored.setMemberNum(member.getMemberNum());
        storedRecored.setTradeType(StoredRecored.TradeType.RECHARGE.getCode());
        storedRecored.setSource(orderVo.getPayClient());
        storedRecored.setPayWay(orderVo.getPayWay());
        storedRecored.setPayTime(orderVo.getPayTime());
        storedRecored.setPayStatus(orderVo.getStatus());
        storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
        storedRecored.setOperationUser(orderVo.getUserName());
        storedRecored.setDiscountMoney(BigDecimal.ZERO);
        storedRecored.setScores(Integer.valueOf(0));
    }
}
