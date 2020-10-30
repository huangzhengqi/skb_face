package com.fzy.admin.fp.member.rule.service;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.repository.StoredRuleRepository;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author ：drj.
 * @Date  ：Created in 16:43 2019/5/14
 * @Description:  储值规则业务层
 **/
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class StoredRuleService implements BaseService<StoredRule> {

    @Resource
    private StoredRuleRepository storedRuleRepository;

    @Resource
    private CouponService couponService;


    @Override
    public StoredRuleRepository getRepository() {
        return storedRuleRepository;
    }


    public List<StoredRule> findUnCouponByMerchantId(String merchantId) {
        List<StoredRule> storedRuleList = this.storedRuleRepository.findByMerchantIdAndDelFlagAndStatusOrderByUpdateTimeDesc(merchantId, CommonConstant.NORMAL_FLAG, StoredRule.Status.ENABLE.getCode());
        for (StoredRule storedRule : storedRuleList){
            //如果赠送的是卡券
            if (StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())) {
                Coupon coupon = couponService.findOne(storedRule.getCouponId());
                if (ParamUtil.isBlank(coupon)) {
                    throw new BaseException("卡券不存在呢", Resp.Status.PARAM_ERROR.getCode());
                }
                storedRule.setCouponMoney(coupon.getMoney() == null ? BigDecimal.ZERO : coupon.getMoney());
            }
        }
        return storedRuleList;
    }


    public Page<StoredRule> findByMerchantId(StoredRule model, PageVo pageVo, String merchantId) {
        model.setMerchantId(merchantId);
        //获取启用状态下的储值规则
        model.setStatus(StoredRule.Status.ENABLE.getCode());
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<StoredRule> page = findAll(specification, pageable);
        for (StoredRule storedRule : page) {
            //如果赠送的是卡券
            if (StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())) {
                Coupon coupon = couponService.findOne(storedRule.getCouponId());
                if (ParamUtil.isBlank(coupon)) {
                    throw new BaseException("卡券不存在 ", Resp.Status.PARAM_ERROR.getCode());
                }
                storedRule.setCouponMoney(coupon.getMoney() == null ? BigDecimal.ZERO : coupon.getMoney());
            }
        }
        return page;
    }


    /**
     * @author drj
     * @date 2019-05-14 19:21
     * @Description :修改储值规则状态
     */
    public String updateStoredRule(String id, Integer status) {
        StoredRule storedRule = storedRuleRepository.findOne(id);
        if (ParamUtil.isBlank(storedRule)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        storedRule.setStatus(status);
        storedRuleRepository.save(storedRule);
        return "操作成功";
    }

    /**
     * @author drj
     * @date 2019-05-14 19:22
     * @Description:添加储值规则
     */
    public String saveRewrite(StoredRule model, String merchantId) {
        //根据储值名称查重
        StoredRule storedRule = storedRuleRepository.findByNameAndDelFlagAndMerchantId(model.getName(), CommonConstant.NORMAL_FLAG,merchantId);
        if (!ParamUtil.isBlank(storedRule)) {
            throw new BaseException("有重复的储值规则", Resp.Status.PARAM_ERROR.getCode());
        }
        //若赠送类型为金额,判断赠送金额是否大于储值金额
        if (StoredRule.GiftType.MONEY.getCode().equals(model.getGiftType())) {
            if (model.getGiftMoney().compareTo(model.getStoredMoney()) >= 1) {
                throw new BaseException("赠送金额不能大于储值金额", Resp.Status.PARAM_ERROR.getCode());
            }
        }
        //储值数量初始化
        model.setStoredAmount(0);
        //获取商户id
        model.setMerchantId(merchantId);
        storedRuleRepository.save(model);
        return "保存成功";
    }


    /**
     * 后台显示所有规则
     *
     * @param model
     * @param pageVo
     * @param merchantId
     * @return
     */
    public Page<StoredRule> findAllRule(StoredRule model, PageVo pageVo, String merchantId) {
        model.setMerchantId(merchantId);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<StoredRule> page = findAll(specification, pageable);
        for (StoredRule storedRule : page) {
            //如果赠送的是卡券
            if (StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())) {
                Coupon coupon = couponService.findOne(storedRule.getCouponId());
                if (ParamUtil.isBlank(coupon)) {
                    throw new BaseException("卡券不存在", Resp.Status.PARAM_ERROR.getCode());
                }
                storedRule.setCouponMoney(coupon.getMoney() == null ? BigDecimal.ZERO : coupon.getMoney());
            }
        }
        return page;
    }


}
