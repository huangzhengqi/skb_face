package com.fzy.admin.fp.member.rule.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.repository.StoredRecoredRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.enumeration.EnumUtils;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.dto.StoredAmountAndRecordDto;
import com.fzy.admin.fp.member.rule.repository.StoredRecoredRepository;
import com.fzy.admin.fp.member.rule.vo.AdminStoreRecordVo;
import com.fzy.admin.fp.member.rule.vo.StoreAmountVo;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.pc.vo.PayResult;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:43 2019/5/14
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class StoredRecoredService implements BaseService<StoredRecored> {

    @Resource
    private StoredRecoredRepository storedRecoredRepository;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Override
    public StoredRecoredRepository getRepository() {
        return storedRecoredRepository;
    }


    public Map<String, Object> findByMerchantId(StoredAmountAndRecordDto model, PageVo pageVo, String merchantId) {
        model.setMerchantId(merchantId);
        Pageable pageable = PageUtil.initPage(pageVo);
        // 查询订单
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                System.out.println(DateUtil.beginOfDay(model.getStart_createTime()).toJdkDate() + "--" + DateUtil.endOfDay(model.getEnd_createTime()));
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtil.beginOfDay(model.getStart_createTime()).toJdkDate()));
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), DateUtil.endOfDay(model.getEnd_createTime()).toJdkDate()));
                if (!ParamUtil.isBlank(model.getPhone())) {
                    predicates.add(cb.like(root.get("phone"), "%" + model.getPhone() + "%"));
                }
                if (!ParamUtil.isBlank(model.getTradeType())) {
                    predicates.add(cb.equal(root.get("tradeType"), model.getTradeType()));
                }
                if (!ParamUtil.isBlank(model.getSource())) {
                    predicates.add(cb.equal(root.get("source"), model.getSource()));
                }
                if (!ParamUtil.isBlank(model.getStoreId())) {
                    predicates.add(cb.like(root.get("storeId"), "%" + model.getStoreId() + "%"));
                }
                if (!ParamUtil.isBlank(model.getPayWay())) {
                    predicates.add(cb.equal(root.get("payWay"), model.getPayWay()));
                }
                Expression<String> exp = root.<String>get("status");
                predicates.add(exp.in(StoredRecored.Status.CARD.getCode(), StoredRecored.Status.ALLIN.getCode())); // 往in中添加所有id 实现in 查询
                predicates.add(cb.equal(root.get("merchantId"),model.getMerchantId()));
                predicates.add(cb.equal(root.get("delFlag"), CommonConstant.NORMAL_FLAG));
                cq.orderBy(cb.desc(root.get("createTime")));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<StoredRecored> page = storedRecoredRepository.findAll(specification, pageable);
        List<StoredRecored> storedRecoredList = page.getContent();
        List<AdminStoreRecordVo> adminStoreRecordVos = storedRecoredList.stream()
                .map(e -> new AdminStoreRecordVo(e.getStoredNum(), e.getCreateTime(), e.getPhone(), EnumUtils.findByCode(e.getTradeType(), StoredRecored.TradeType.class),
                        EnumUtils.findByCode(e.getSource(), StoredRecored.Source.class), EnumUtils.findByCode(e.getPayWay(), StoredRecored.PayWay.class), e.getOperationUser(), e.getGiftMoney(), e.getTradingMoney(), e.getPostTradingMoney(), e.getStoreName(), e.getScores()))
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("content", adminStoreRecordVos);
        return map;
    }

    public StoreAmountVo searchStoredAmount(StoredAmountAndRecordDto model, String merchantId) {
        model.setMerchantId(merchantId);
        List<StoredRecored> storedRecoredList;
        Specification specification = ConditionUtil.createSpecification(model);
        storedRecoredList = storedRecoredRepository.findAll(specification);
        StoreAmountVo storeAmountVo = new StoreAmountVo();
        for (StoredRecored storedRecored : storedRecoredList) {
            //状态为1,充值
            if (storedRecored.getTradeType().equals(1)) {
                storeAmountVo.setActualMoney(storeAmountVo.getActualMoney().add(storedRecored.getTradingMoney()));
                //赠送金额
                storeAmountVo.setGiftMoney(storeAmountVo.getGiftMoney().add(storedRecored.getGiftMoney()));
            }
            //状态为2,消费
            if (storedRecored.getTradeType().equals(2)) {
                //消费扣款
                storeAmountVo.setUsedMoney(storeAmountVo.getUsedMoney().add(storedRecored.getTradingMoney()));
            }
            //状态为3,退款
            if (storedRecored.getTradeType().equals(3)) {
                //退款退回
                storeAmountVo.setRefundMoney(storeAmountVo.getRefundMoney().add(storedRecored.getTradingMoney()));
            }
        }
        //储值余额
        storeAmountVo.setResuletMoney(storeAmountVo.getActualMoney().subtract(storeAmountVo.getUsedMoney()));
        return storeAmountVo;

    }


    public List<StoredRecored> findForConsumeActivityLevel(String merchantId, Date startTime, Date endTime, List<String> memberIds) {
        return storedRecoredRepository.findByMerchantIdAndCreateTimeBetweenAndMemberIdInAndTradeType(merchantId,
                startTime, endTime, memberIds, StoredRecored.TradeType.CONSUME.getCode());
    }

    /**
     * 创建存储规则明细
     * @param member
     * @param storedRule
     * @param payResult
     * @param money
     */
    public StoredRecored createStoredRecored(Member member, StoredRule storedRule, PayResult payResult, BigDecimal money) {
        StoredRecored storedRecored = new StoredRecored();
        storedRecored.setStoreRuleId(storedRule.getId());
        BigDecimal giftMoney = (null == storedRule.getGiftMoney() || !storedRule.getGiftType().equals(StoredRule.GiftType.MONEY.getCode())) ? BigDecimal.ZERO : storedRule.getGiftMoney();
        storedRecored.setGiftMoney(giftMoney);
        storedRecored.setTradingMoney(storedRule.getStoredMoney());
        storedRecored.setPostTradingMoney(member.getBalance().add(money.add(giftMoney)));
        storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
        storedRecored.setMerchantId(member.getMerchantId());
        storedRecored.setOrderNumber(payResult.getOrderNumber());
        storedRecored.setMemberId(member.getId());
        storedRecored.setStoreId(payResult.getStoreId());
        storedRecored.setPhone(member.getPhone());
        storedRecored.setStoreName(payResult.getStoreName());
        storedRecored.setMemberNum(member.getMemberNum());
        storedRecored.setTradeType(StoredRecored.TradeType.RECHARGE.getCode());
        storedRecored.setSource(payResult.getPayClient());
        storedRecored.setPayWay(payResult.getPayWay());
        storedRecored.setPayTime(new Date());
        storedRecored.setPayStatus(payResult.getStatus());
        storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
        storedRecored.setOperationUser(payResult.getUserName());
        storedRecored.setDiscountMoney(BigDecimal.ZERO);
        storedRecored.setScores(Integer.valueOf(0));
        storedRecored.setRemainScore(storedRecored.getScores());
        return storedRecored;
    }
}
