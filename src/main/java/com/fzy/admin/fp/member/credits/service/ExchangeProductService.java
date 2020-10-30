package com.fzy.admin.fp.member.credits.service;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.domain.ExchangeProduct;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;
import com.fzy.admin.fp.member.credits.utils.ObjectUtil;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/20 10:06
 * @Description 兑换逻辑
 */
@Service
@Slf4j
public class ExchangeProductService {

    @Resource
    private CreditsProductService creditsProductService;

    @Resource
    private MemberService memberService;

    @Resource
    private CreditsInfoService creditsInfoService;

    @Resource
    private ExchangeRecordService exchangeRecordService;

    @Transactional
    public Resp change(ExchangeProduct exchangeProduct, Integer apps, String storeId, String storeName) {
        //先对要兑换的商品进行查询 会员查询
        CreditsProduct creditsProduct = creditsProductService.findOne(exchangeProduct.getProductId());
        Member member = memberService.findOne(exchangeProduct.getMemberId());
        log.info("要兑换的商品{}" + creditsProduct);
        if (creditsProduct == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商品不存在");
        } else if (creditsProduct.getProductNums() - creditsProduct.getExchangeNum() == 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商品已兑完");
        } else if (creditsProduct.getExchangeEnd().getTime() < System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商品活动已结束");
        } else if (creditsProduct.getInterrupt().equals(CreditsProduct.Interrupt.INTERRUPT.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商品已提前结束");
        } else if (creditsProduct.getExchangeStart().getTime() > System.currentTimeMillis()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该商品兑换活动还未开始");
        } else if (member == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员不存在");
        } else if (member.getScores() - creditsProduct.getCredits() < 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "积分不足无法兑换");
        } else if (creditsProduct.getIsLimited() == 1) {
            Integer num = creditsProduct.getLimitedNum();//获得该商品限制数量
            if (num == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "积分商品限制参数错误");
            }
            //获取当前会员兑换商品的次数判断是否超过限制
            List<ExchangeRecord> exchangeRecords = exchangeRecordService.findRecords(exchangeProduct.getMerchantId(),
                    exchangeProduct.getMemberId(), exchangeProduct.getProductId());
            if (exchangeRecords.size() >= num) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "积分商品兑换已经到达上限");
            }
        }
        //开始兑换 先对积分商品扣库存
        creditsProduct.setExchangeNum(creditsProduct.getExchangeNum() + 1);
        log.info("扣库存后的积分商品{}" + creditsProduct);
        creditsProductService.update(creditsProduct);
        //扣会员积分并且生成消费积分记录
        member.setScores(member.getScores() - creditsProduct.getCredits());
        log.info("扣积分后的会员信息{}" + member);
        memberService.update(member);
        CreditsInfo creditsInfo = ObjectUtil.addCreditsInfo(exchangeProduct.getMerchantId(),
                member.getMemberNum(), exchangeProduct.getPhone(), creditsProduct.getCredits(),
                CreditsInfo.Trade.CONSUM_SCORE.getMessage(), member.getScores(), storeId, storeName);
        log.info("生成的积分明细{}" + creditsInfo);
        creditsInfoService.save(creditsInfo);
        //生成兑换记录
        String random = RandomUtil.randomNumbers(13);
        log.info("生成的提货码{}" + random);
        ExchangeRecord exchangeRecord = ObjectUtil.addExchangeRecord(creditsProduct.getProductName(),
                creditsProduct.getImgUrl(), creditsProduct.getCredits(), exchangeProduct.getPhone(), member.getScores(),
                ExchangeRecord.Status.NO_CHANGE.getCode(), exchangeProduct.getOperator(), random, exchangeProduct.getMerchantId(),
                exchangeProduct.getMemberId(), exchangeProduct.getProductId(), creditsProduct.getProductMoney(), creditsProduct.getExchangeMessage());
        log.info("生成的兑换记录{}" + exchangeRecord);
        if (apps != 0) {
            exchangeRecord.setExchangeTime(new Date());
            exchangeRecord.setStatus(ExchangeRecord.Status.CHANGE.getCode());
        }
        ExchangeRecord exchangeRecord1 = exchangeRecordService.save(exchangeRecord);
        return Resp.success(exchangeRecord1, creditsProduct.getExchangeMessage());
    }


    public Resp update(String merchantId, String code) {
        ExchangeRecord exchangeRecord = exchangeRecordService
                .findByMerchantIdAndStatusAndGoodCodes(merchantId, ExchangeRecord.Status.NO_CHANGE.getCode(), code);
        if (exchangeRecord == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "找不到该提货码");
        }
        exchangeRecord.setStatus(ExchangeRecord.Status.CHANGE.getCode());
        exchangeRecordService.update(exchangeRecord);
        //修改积分商品已兑换数量
        CreditsProduct creditsProduct = creditsProductService.findOne(exchangeRecord.getProductId());
        creditsProduct.setExchangedNum(exchangeRecordService.countByProductIdAndStatus(exchangeRecord.getProductId(), ExchangeRecord.Status.CHANGE.getCode()));
        log.info("扣库存后的积分商品{}" + creditsProduct);
        creditsProductService.update(creditsProduct);
        return Resp.success("提货成功!");
    }
}
