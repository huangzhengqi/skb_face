package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alipay.api.internal.util.StringUtils;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.sdk.member.domain.MemberPayParam;
import com.fzy.admin.fp.sdk.member.domain.MemberRefundParam;
import com.fzy.admin.fp.sdk.member.feign.MemberPayServiceFeign;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Created by wtl on 2019-05-20 22:21
 * @description 会员支付
 */
@Service
@Slf4j
public class MemberOrderService extends BaseContent {

    @Resource
    private MemberPayServiceFeign memberPayServiceFeign;

    @Resource
    private OrderService orderService;


    @Resource
    private MemberService memberService;


    /**
     * @author Created by wtl on 2019/5/20 22:56
     * @Description 会员卡支付
     */
    public void memberPay(Order model, String memberId) {
        MemberPayParam memberPayParam = new MemberPayParam();
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(model, memberPayParam, copyOptions);
        if (!ParamUtil.isBlank(memberId)) {
            memberPayParam.setMemberId(memberId);
        }
        // 调用会员卡支付业务
        PayRes payRes = memberPayServiceFeign.memberPay(memberPayParam);
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 支付成功
        model.setStatus(Order.Status.SUCCESSPAY.getCode());
        orderService.save(model);
    }

//    public void memberPay(Order model, String memberId) {
//        MemberPayParam memberPayParam = new MemberPayParam();
//
//        CopyOptions copyOptions = CopyOptions.create();
//
//        copyOptions.setIgnoreNullValue(true);
//
//        BeanUtil.copyProperties(model, memberPayParam, copyOptions);
//        if (!ParamUtil.isBlank(memberId)) {
//            memberPayParam.setMemberId(memberId);
//        }
//
//        PayRes payRes = this.memberPayServiceFeign.memberPay(memberPayParam);
//        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
//            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
//        }
//
//        model.setStatus(Order.Status.SUCCESSPAY.getCode());
//        equPayMemberCallBack(model, memberId);
//
//        this.orderService.delete(model);
//    }


    /**
     * @param refundPrice 输入的退款金额
     * @author Created by wtl on 2019/5/20 23:06
     * @Description 会员卡退款，根据订单编号找到会员卡编号，会员卡编号和商户id查询会员信息
     */
    public void refund(Order order, BigDecimal refundPrice) {
        // 累加退款金额
        order.setRefundPayPrice(order.getRefundPayPrice().add(refundPrice));
        // 是否已全部退款
        if (order.getActPayPrice().compareTo(order.getRefundPayPrice()) == 0) {
            order.setStatus(Order.Status.REFUNDTOTAL.getCode());
        } else {
            order.setStatus(Order.Status.REFUNDPART.getCode());
        }
        // 会员卡退款流程
        MemberRefundParam memberRefundParam = new MemberRefundParam();
        memberRefundParam.setOrderNumber(order.getOrderNumber());
        memberRefundParam.setRefundAmount(refundPrice);
        memberRefundParam.setStatus(order.getStatus());
        memberRefundParam.setStoreId(order.getStoreId());
        memberRefundParam.setStoreName(order.getStoreName());
        PayRes payRes = memberPayServiceFeign.memberRefund(memberRefundParam);
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        orderService.save(order);
    }

    /**
     * @author Created by wtl on 2019/6/29 15:12
     * @Description 会员H5支付生成会员消费记录和变更积分，使用卡券则核销卡券
     */
    public void createMemberPayRecord(Order order) {
//        MemberPayParam memberPayParam = new MemberPayParam();
//        //获取Hutool拷贝实例
//        CopyOptions copyOptions = CopyOptions.create();
//        //忽略为null值得属性
//        copyOptions.setIgnoreNullValue(true);
//        //进行属性拷贝
//        BeanUtil.copyProperties(order, memberPayParam, copyOptions);
//        memberPayServiceFeign.wxMemberPayRedirect(memberPayParam);
        MemberPayParam memberPayParam = new MemberPayParam();

        CopyOptions copyOptions = CopyOptions.create();

        copyOptions.setIgnoreNullValue(true);

        BeanUtil.copyProperties(order, memberPayParam, copyOptions);
    }

    public void equPayMemberCallBack(Order model, String memberId) {
        if (!StringUtils.isEmpty(memberId)) {
            Member member = (Member)this.memberService.findOne(memberId);
            if (model.getScore() != null && model.getScore().intValue() > 0) {
                Integer orgScores = member.getScores();
                member.setScores(Integer.valueOf(member.getScores().intValue() - model.getScore().intValue()));
                this.memberPayServiceFeign.creditsInfoSave(member, orgScores.intValue(), CreditsInfo.Trade.CONSUM_EXPENSE, 2, model.getStoreId(), model.getStoreName());
            }
            if (member.getFreezeBalance() !=null && !(member.getFreezeBalance().compareTo(BigDecimal.ZERO) == 0)) {
                member.setBalance(member.getBalance().add(member.getFreezeBalance()));
                member.setFreezeBalance(BigDecimal.ZERO);
                this.memberService.save(member);
            }
        }
    }
}
