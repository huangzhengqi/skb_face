package com.fzy.admin.fp.member.member.feignImpl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.member.credits.service.CreditsRulerService;
import com.fzy.admin.fp.merchant.YunlabaUtil;
import com.fzy.admin.fp.merchant.merchant.domain.FeiyuConfig;
import com.fzy.admin.fp.merchant.merchant.repository.FeiYuConfigRepository;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.member.app.service.AppStoreService;
import com.fzy.admin.fp.member.coupon.service.CheckCodeService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.sdk.member.domain.MemberPayParam;
import com.fzy.admin.fp.sdk.member.domain.MemberRefundParam;
import com.fzy.admin.fp.sdk.member.feign.MemberPayServiceFeign;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by zk on 2019-05-19 11:23
 * @description
 */
@Service
@Slf4j
@Transactional
public class MemberPayServiceFeignImpl implements MemberPayServiceFeign {

    @Resource
    private MemberService memberService;

    @Resource
    private StoredRecoredService storedRecoredService;

    @Resource
    private CreditsRulerService creditsRulerService;

    @Resource
    private CreditsInfoService creditsInfoService;

    @Resource
    private AppStoreService appStoreService;

    @Resource
    private CheckCodeService checkCodeService;
    @Resource
    private FeiYuConfigRepository feiYuConfigRepository;


    /**
     * @author Created by wtl on 2019/6/29 14:39
     * @Description 生成会员消费记录和变更积分，有使用卡券需要核销
     */
    private void createMemberRecord(MemberPayParam model, Member member) {
        // 保存原始积分
        int originScores = member.getScores();
        /*===================生成会员消费记录(和储值记录表共用)=====================*/
        StoredRecored storedRecored = new StoredRecored();
        storedRecored.setOrderNumber(model.getOrderNumber());
        storedRecored.setMerchantId(model.getMerchantId());
        storedRecored.setMemberId(member.getId());
        storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
        storedRecored.setStoreId(model.getStoreId());
        storedRecored.setPhone(member.getPhone());
        storedRecored.setStoreName(model.getStoreName());
        storedRecored.setMemberNum(member.getMemberNum());
        storedRecored.setTradeType(StoredRecored.TradeType.CONSUME.getCode());
        storedRecored.setSource(StoredRecored.Source.ANDROID.getCode());
        storedRecored.setPayWay(model.getPayWay());
        storedRecored.setPayStatus(StoredRecored.PayStatus.SUCCESSPAY.getCode());
        storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
        storedRecored.setOperationUser(model.getUserName());
        storedRecored.setGiftMoney(BigDecimal.ZERO);
        storedRecored.setTradingMoney(model.getActPayPrice());
        storedRecored.setPostTradingMoney(member.getBalance());
        storedRecored.setDiscountMoney(model.getDisCountPrice());
        storedRecored.setScores(0); // 本次积分
        /*===================计算会员积分=====================*/
        CreditsRuler creditsRuler = creditsRulerService.findByMerchantId(model.getMerchantId());
        if (!ParamUtil.isBlank(creditsRuler)) {

            if(creditsRuler.getConsumptionAmount() !=null && !(creditsRuler.getConsumptionAmount().compareTo(BigDecimal.ZERO) == 0)){
                // 赠送积分
                int score = model.getActPayPrice().divideToIntegralValue(creditsRuler.getConsumptionAmount()).multiply(new BigDecimal(creditsRuler.getCredits())).intValue();
                // 会员消费记录表记录本次消费获得积分，退款需要退回
                storedRecored.setScores(score);
                // 更新会员积分
                member.setScores(member.getScores() + score);
            }else {
                // 会员消费记录表记录本次消费获得积分，退款需要退回
                storedRecored.setScores(0);
                // 更新会员积分
                member.setScores(member.getScores() + 0);
            }

        }
        storedRecored.setRemainScore(storedRecored.getScores());
        storedRecoredService.save(storedRecored);
        // 积分发生变化，积分明细表添加记录
        if (member.getScores() > originScores) {
            CreditsInfo creditsInfo = new CreditsInfo();
            creditsInfo.setMemberNum(member.getMemberNum());
            creditsInfo.setPhone(member.getPhone());
            creditsInfo.setTransactionType(CreditsInfo.Trade.CONSUM_CONSUM.getMessage());
            creditsInfo.setInfoType(CreditsInfo.Trade.CONSUM_CONSUM.getCode());
            creditsInfo.setTradeTime(new Date());
            creditsInfo.setTradeScores(member.getScores() - originScores);
            creditsInfo.setAvaCredits(member.getScores());
            creditsInfo.setMerchantId(member.getMerchantId());
            creditsInfo.setTradeNum(1); // 消费积分增加
            creditsInfo.setStoreId(model.getStoreId());
            creditsInfo.setStoreName(model.getStoreName());
            creditsInfoService.save(creditsInfo);
        }
        // 有使用卡券需要核销
        if (!ParamUtil.isBlank(model.getCode())) {
            checkCodeService.checkCode(model.getMerchantId(), model.getCode());
        }
    }

    /**
     * @author Created by wtl on 2019/6/18 23:29
     * @Description 会员卡付款码和H5会员卡支付
     */
    @Override
    public PayRes memberPay(@RequestBody MemberPayParam model) {
        log.info("会员卡支付参数：{}", model);
        Member member = null;
        // 缓存中获取付款码和会员卡编号
        if (!ParamUtil.isBlank(model.getAuthCode())) {
            String memberNum = MemberConstant.MEMBER_PAY_CODE_CACHE.get(model.getAuthCode(), false);
            if (ParamUtil.isBlank(memberNum)) {
                return new PayRes("付款码已经失效，请刷新", PayRes.ResultStatus.FAIL);
            }
            // 商户id和会员卡号才能判断唯一
            member = memberService.findByMerchantIdAndMemberNum(model.getMerchantId(), memberNum);
        }
        log.info("111111111111");
        // H5会员支付
        if (!ParamUtil.isBlank(model.getMemberId())) {
            member = memberService.getRepository().findOne(model.getMemberId());
        }
        if (ParamUtil.isBlank(member)) {
            return new PayRes("获取会员信息失败", PayRes.ResultStatus.FAIL);
        }
        /*===================会员卡扣钱=====================*/
        if (member.getBalance().compareTo(model.getActPayPrice()) < 0) {
            return new PayRes("会员卡余额不足，请充值", PayRes.ResultStatus.FAIL);
        }
        // 会员卡扣钱
        member.setBalance(member.getBalance().subtract(model.getActPayPrice()));
        // ------------------生成会员卡消费记录和变更积分-------------------------------
        createMemberRecord(model, member);

        log.info("22222222222");

        member.setLastPayDate(new Date());
        memberService.save(member);
        // 支付后移除code
        if (ParamUtil.isBlank(model.getAuthCode())) {
            MemberConstant.MEMBER_PAY_CODE_CACHE.remove(model.getAuthCode());
        }

        log.info("33333333333333333");

        //会员付款支付通知单独处理
        List<FeiyuConfig> feiyuConfigs = feiYuConfigRepository.findAllByStoreIdAndStatus(model.getStoreId(), 1);
        String price = model.getActPayPrice().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
        for (FeiyuConfig feiyuConfig:feiyuConfigs) {
            YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "8", feiyuConfig.getIsSuffix());
        }

        return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);
    }

    /**
     * @author Created by wtl on 2019/6/29 14:39
     * @Description H5会员支付（非会员卡支付，微信支付回调）后生成会员消费记录和变更积分
     */
    public void wxMemberPayRedirect(@RequestBody MemberPayParam model) {
        Member member = memberService.getRepository().findOne(model.getMemberId());
        // 生成会员消费记录
        createMemberRecord(model, member);
    }

    public void creditsInfoSave(Member member, int originScores, CreditsInfo.Trade consumConsum, int i, String storeId, String storeName) {
        CreditsInfo creditsInfo = new CreditsInfo();
        creditsInfo.setMemberNum(member.getMemberNum());
        creditsInfo.setPhone(member.getPhone());
        creditsInfo.setTransactionType(consumConsum.getMessage());
        creditsInfo.setInfoType(consumConsum.getCode());
        creditsInfo.setTradeTime(new Date());
        creditsInfo.setTradeScores(Integer.valueOf(member.getScores().intValue() - originScores));
        creditsInfo.setAvaCredits(member.getScores());
        creditsInfo.setMerchantId(member.getMerchantId());
        creditsInfo.setTradeNum(Integer.valueOf(i));
        creditsInfo.setStoreId(storeId);
        creditsInfo.setStoreName(storeName);
        this.creditsInfoService.save(creditsInfo);
    }

    /**
     * @author Created by wtl on 2019/6/18 23:13
     * @Description 会员卡退款，充值的不能退款
     * 1.修改订单号对应的会员消费记录表状态（部分退款，全部退款）
     * 2.把退款金额退到对应的会员卡上，会员卡的积分相应减少
     * 3.生成积分明细记录
     */
    @Override
    public PayRes memberRefund(@RequestBody MemberRefundParam model) {
        try {
            // 获得退款订单号的消费记录，并修改状态为已退款或部分退款
            StoredRecored storedRecored = storedRecoredService.getRepository().findByOrderNumber(model.getOrderNumber());
            storedRecored.setPayStatus(model.getStatus());
            // 退款金额退到会员卡
            Member member = memberService.findByMerchantIdAndMemberNum(storedRecored.getMerchantId(), storedRecored.getMemberNum());
            // 保存会员原有的积分
            int originScores = member.getScores();
            member.setBalance(member.getBalance().add(model.getRefundAmount()));
            // 计算退款金额对应的积分，向上取整:退款金额*本次积分/交易金额，
            int score = model.getRefundAmount().multiply(new BigDecimal(storedRecored.getScores())).divide(storedRecored.getTradingMoney(), 0, BigDecimal.ROUND_UP).intValue();
            // 退款积分不能大于可退积分
            if (score > storedRecored.getRemainScore()) {
                score = storedRecored.getRemainScore();
            }
            storedRecored.setRemainScore(storedRecored.getRemainScore() - score);
            storedRecoredService.save(storedRecored);
            member.setScores(member.getScores() - score);
            memberService.save(member);
            // 生成积分明细记录
            // 积分发生变化，积分明细表添加记录
            if (member.getScores() < originScores) {
                CreditsInfo creditsInfo = new CreditsInfo();
                creditsInfo.setMemberNum(member.getMemberNum());
                creditsInfo.setPhone(member.getPhone());
                creditsInfo.setTransactionType(CreditsInfo.Trade.REFUND.getMessage());
                creditsInfo.setInfoType(CreditsInfo.Trade.REFUND.getCode());
                creditsInfo.setTradeTime(new Date());
                creditsInfo.setTradeScores(member.getScores() - originScores);
                creditsInfo.setAvaCredits(member.getScores());
                creditsInfo.setMerchantId(member.getMerchantId());
                creditsInfo.setTradeNum(2);
                creditsInfo.setStoreId(model.getStoreId());
                creditsInfo.setStoreName(model.getStoreName());
                creditsInfoService.save(creditsInfo);
            }
            return new PayRes("退款成功", PayRes.ResultStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PayRes("会员卡退款失败", PayRes.ResultStatus.FAIL);
    }

    /**
     * @author Created by wtl on 2019/6/19 18:56
     * @Description 会员小程序充值后，生成储值记录等操作
     */
    @Override
    public void memberStoreRecord(@RequestBody OrderVo orderVo) {
        JSONObject jsonObject = JSONUtil.parseObj(orderVo.getAppletStore());
        // 快照还原bean
        StoredRule storedRule = jsonObject.toBean(StoredRule.class);
        appStoreService.createStoreRecord(orderVo, storedRule, storedRule.getMemberId());
    }
}
