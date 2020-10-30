package com.fzy.admin.fp.member.applet.service;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.applet.dto.AppletMemberPayDTO;
import com.fzy.admin.fp.member.applet.dto.AppletOrderDTO;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.coupon.service.ReceiveCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.pc.service.PcOrderService;
import com.fzy.admin.fp.order.pc.vo.PayResult;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.order.feign.OrderServiceFeign;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.fzy.admin.fp.sdk.pay.feign.WxPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wtl on 2019-06-18 18:31
 * @description 小程序会员支付业务
 */
@Service
@Slf4j
public class AppletMemberPayService {

    @Resource
    public MemberService memberService;
    @Resource
    private OrderServiceFeign orderServiceFeign;
    @Resource
    private WxPayServiceFeign wxPayServiceFeign;
    @Resource
    private MerchantBusinessService merchantBusinessService;
    @Resource
    private StoredRuleService storedRuleService;
    @Resource
    private PcOrderService pcOrderService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private ReceiveCouponService receiveCouponService;
    @Resource
    private StoredRecoredService storedRecoredService;


    /**
     * @author Created by wtl on 2019/6/18 18:32
     * @Description 小程序充值预下单
     * 1.判断储值规则或者是自定义金额
     * 2.生成订单
     * 3.调用小程序预下单
     * 4.回调成功生成储值记录和积分明细记录（如果储值加积分开关打开的话）
     */
    public Map<String, String> storeMoney(String id, AppletMemberPayDTO appletMemberPayDTO) throws Exception {
        StoreMoneyRecharge storeMoneyRecharge = (new StoreMoneyRecharge(id, appletMemberPayDTO)).invoke();
        Member member = storeMoneyRecharge.getMember();
        BigDecimal totalPrice = storeMoneyRecharge.getTotalPrice();
        String appletStore = storeMoneyRecharge.getAppletStore();


        String orderNumber = this.orderServiceFeign.createOrder(member.getMerchantId(), appletStore, totalPrice, member.getId());
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("下单失败", Resp.Status.PARAM_ERROR.getCode());
        }

        return appletPay(appletMemberPayDTO.getOpenId(), member.getMerchantId(), orderNumber, totalPrice);
    }

    /**
     * @author Created by wtl on 2019/6/19 17:27
     * @Description 小程序预下单
     */
    private Map<String, String> appletPay(String openId, String merchantId, String orderNumber, BigDecimal totalPrice) throws Exception {
        // 获取商户的小程序配置
        AppletConfigVO appletConfigVO = this.merchantBusinessService.findAppletByMerchantId(merchantId);
        if (ParamUtil.isBlank(appletConfigVO)) {
            throw new BaseException("还未配置小程序参数", Resp.Status.PARAM_ERROR.getCode());
        }

        WxPayParam wxPayParam = new WxPayParam();
        wxPayParam.setAppletAppId(appletConfigVO.getAppId());
        wxPayParam.setMerchantId(merchantId);

        wxPayParam.setOpenid(openId);
        wxPayParam.setOut_trade_no(orderNumber);
        wxPayParam.setTotal_fee(totalPrice.toPlainString());
        PayRes payRes = this.wxPayServiceFeign.appletPay(wxPayParam);
        return JacksonUtil.toStringMap((String) payRes.getObject());

    }


    /**
     * @author Created by wtl on 2019/6/18 15:37
     * @Description code换取openId
     */
    private String getOpenId(String code, String appId, String appKey) {
        // 请求微信接口获取openId
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";
        String result = HttpUtil.get(StrFormatter.format(url, appId, appKey, code));
        Map<String, String> map = JacksonUtil.toStringMap(result);
        log.info("result is,{}", result);
        log.info("openid is,{}", map.get("openid"));
        return map.get("openid");
    }


    public static void main(String args[]) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";
        String result = HttpUtil.get(StrFormatter.format(url, "wx1a7213ec531b3eba", "90fd6ca271fc15b730852d2b5cfcca37", "023gEpdE1iD0y80FUoeE10OndE1gEpdN"));
        System.out.print(result);
    }

    public Resp storeMoneyMember(String memberId, AppletMemberPayDTO appletMemberPayDTO, OrderDto orderDto) throws Exception {
        StoreMoneyRecharge storeMoneyRecharge = (new StoreMoneyRecharge(memberId, appletMemberPayDTO)).invoke();
        String appletStore = storeMoneyRecharge.getAppletStore();
        orderDto.setAppletStore(appletStore);
        orderDto.setPayClient(Order.PayClient.OTHER.getCode());
        orderDto.setPayType(Integer.valueOf(3));
        //会员充值流程
        PayResult payResult = this.pcOrderService.scanPay(orderDto);
        //支付成功修改会员表信息  储值规则表
        StoredRule storedRule = storedRuleService.findOne(appletMemberPayDTO.getStoreRuleId());
        if (Order.Status.SUCCESSPAY.getCode().equals(payResult.getStatus())) {
            //会员表
            Member member = memberService.findOne(memberId);
            //创建存储规则明细
            StoredRecored storedRecored = storedRecoredService.createStoredRecored(member, storedRule, payResult,appletMemberPayDTO.getMoney());
            //会员规则表
            List<MemberLevel> memberLevelList = memberLevelService.findByMerchantIdOrderByMemberLimitAmountAsc(member.getMerchantId());
            //如果是自定义的金额
            if (storedRule == null) {
                member.setBalance(member.getBalance().add(appletMemberPayDTO.getMoney()));
            } else {
                //送金额
                if (StoredRule.GiftType.MONEY.getCode().equals(storedRule.getGiftType())) {
                    //余额
                    member.setBalance(member.getBalance().add(appletMemberPayDTO.getMoney().add(storedRule.getGiftMoney())));
                }
                //送积分
                if (StoredRule.GiftType.INTEGRAL.getCode().equals(storedRule.getGiftType())) {
                    //会员表的积分
                    int memberScores = member.getScores().intValue();
                    //储值规则表的赠送数额
                    int storedRuleGiftMoney = storedRule.getGiftMoney().intValue();
                    //会员表积分
                    member.setScores(memberScores + storedRuleGiftMoney);
                    //充值余额
                    member.setBalance(member.getBalance().add(appletMemberPayDTO.getMoney()));
                    //送积分
                    storedRecored.setScores(storedRuleGiftMoney);
                    storedRecored.setRemainScore(storedRecored.getScores());
                }
                //送优惠券
                if(StoredRule.GiftType.COUPONS.getCode().equals(storedRule.getGiftType())){
                    //充值余额
                    member.setBalance(member.getBalance().add(appletMemberPayDTO.getMoney()));
                    //添加到个人卡券记录
                    ReceiveCoupon receiveCoupon = new ReceiveCoupon();
                    receiveCoupon.setMemberId(member.getId());
                    receiveCoupon.setCouponId(storedRule.getCouponId());
                    receiveCouponService.receiveCoupon(member.getMerchantId(), receiveCoupon);
                    //送优惠券
                    storedRecored.setCouponId(storedRule.getCouponId());
                }
            }

            if (memberLevelList != null || !memberLevelList.isEmpty()) {
                for (MemberLevel memberLevel : memberLevelList) {
                    int a = member.getBalance().compareTo(memberLevel.getMemberLimitAmount());
                    //还没达到会员标准
                    if (a == -1) {
                        continue;
                    } else {
                        member.setMemberLevelId(memberLevel.getId());
                        continue;
                    }
                }
            }
            storedRecoredService.save(storedRecored);
            member.setLastPayDate(new Date());
            //更改会员表信息
            memberService.update(member);

            return Resp.success(payResult);
        }
        return (new Resp()).error(Resp.Status.PARAM_ERROR, payResult.getName());
    }

    public Map<String, String> order(String id, AppletOrderDTO appletOrderDTO) throws Exception {
        Member member = (Member) this.memberService.findOne(id);
        if (ParamUtil.isBlank(member)) {
            throw new BaseException("未找到会员信息", Resp.Status.PARAM_ERROR.getCode());
        }


        String orderNumber = this.orderServiceFeign.createSmdcOrder(member.getMerchantId(), appletOrderDTO);
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("下单失败", Resp.Status.PARAM_ERROR.getCode());
        }

        return appletPay(appletOrderDTO.getOpenid(), member.getMerchantId(), orderNumber, appletOrderDTO.getTotal().divide(new BigDecimal(100)));
    }


    private class StoreMoneyRecharge {
        private String id;
        private AppletMemberPayDTO appletMemberPayDTO;
        private Member member;
        private BigDecimal totalPrice;
        private String appletStore;

        public StoreMoneyRecharge(String id, AppletMemberPayDTO appletMemberPayDTO) {
            this.id = id;
            this.appletMemberPayDTO = appletMemberPayDTO;
        }


        public Member getMember() {
            return this.member;
        }


        public BigDecimal getTotalPrice() {
            return this.totalPrice;
        }


        public String getAppletStore() {
            return this.appletStore;
        }


        public StoreMoneyRecharge invoke() {
            if (ParamUtil.isBlank(this.appletMemberPayDTO.getStoreRuleId()) || ParamUtil.isBlank(this.appletMemberPayDTO.getMoney())) {
                throw new BaseException("请选择充值规则", Resp.Status.PARAM_ERROR.getCode());
            }
            this.member = (Member) AppletMemberPayService.this.memberService.findOne(this.id);
            if (ParamUtil.isBlank(this.member)) {
                throw new BaseException("未找到会员信息", Resp.Status.PARAM_ERROR.getCode());
            }

            StoredRule storedRule = new StoredRule();

            if ("end".equals(this.appletMemberPayDTO.getStoreRuleId())) {
                if (this.appletMemberPayDTO.getMoney().scale() > 2) {
                    throw new BaseException("充值金额最小为0.01", Resp.Status.PARAM_ERROR.getCode());
                }
                this.totalPrice = this.appletMemberPayDTO.getMoney();
                storedRule.setStoredMoney(this.totalPrice);
            } else {

                storedRule = (StoredRule) AppletMemberPayService.this.storedRuleService.findOne(this.appletMemberPayDTO.getStoreRuleId());
                this.totalPrice = storedRule.getStoredMoney();
            }
            storedRule.setMemberId(this.member.getId());
            this.appletStore = JSONUtil.parseObj(storedRule).toString();
            return this;
        }
    }
}
