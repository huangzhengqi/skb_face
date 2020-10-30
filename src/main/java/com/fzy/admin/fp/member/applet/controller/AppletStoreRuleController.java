package com.fzy.admin.fp.member.applet.controller;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.domain.StoredRule;
import com.fzy.admin.fp.member.rule.service.StoredRuleService;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author ：drj.
 * @Date ：Created in 15:14 2019/5/31
 * @Description: 小程序获取储值规则
 **/
@Slf4j
@RestController
@RequestMapping("/member/store_rule/applet")
@Api(value = "AppletStoreRuleController", tags = "小程序获取储值规则")
public class AppletStoreRuleController extends BaseContent {

    @Resource
    private StoredRuleService storedRuleService;

    @Resource
    private MemberService memberService;

    @Resource
    private OrderService orderService;

    @Resource
    private StoreServiceFeign storeServiceFeign;

    @ApiOperation(value = "根据商户id获取储值规则", notes = "根据商户id获取储值规则")
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(StoredRule model, PageVo pageVo, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRuleService.findByMerchantId(model, pageVo, merchantId));
    }

    @ApiOperation(value = "设备上会员充值按钮根据商户id获取储值规则", notes = "设备上会员充值按钮根据商户id获取储值规则")
    @GetMapping({"/find_un_coupon_by_merchant_id"})
    public Resp findUnCouponByMerchantId(@TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRuleService.findUnCouponByMerchantId(merchantId));
    }


    @PostMapping("get_prepay_info")
    public Resp getPrepayInfo(@TokenInfo(property = "merchantId") String merchantId, @UserId String merberId) {
        //获取商户默认门店
        MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(merchantId);
        //获取会员信息
        Member member = memberService.findOne(merberId);
        //创建储值记录
        StoredRecored storedRecored = new StoredRecored();
        storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
        storedRecored.setMemberId(merberId);
        storedRecored.setStoreId(merchantDefaultStore.getStoreId());
        storedRecored.setPhone(member.getPhone());
        storedRecored.setStoreName(merchantDefaultStore.getStoreName());
        storedRecored.setMemberNum(member.getMemberNum());
        storedRecored.setTradeType(StoredRecored.TradeType.RECHARGE.getCode());
        storedRecored.setSource(StoredRecored.Source.H5.getCode());
        storedRecored.setPayWay(StoredRecored.PayWay.WECHAT.getCode());
        storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
        storedRecored.setOperationUser(member.getName());
        storedRecored.setGiftMoney(BigDecimal.ZERO);
        storedRecored.setTradingMoney(member.getBalance());
        storedRecored.setPostTradingMoney(member.getBalance());
        storedRecored.setDiscountMoney(BigDecimal.ZERO);
        //调用微信JSAPI,返回唤醒支付4大参数
        return null;
    }

    @GetMapping("/wx_order_call_back")
    public void wxOrderCallBack() throws Exception {
        log.info("进入支付回调");
        BufferedReader br = request.getReader();

        String str = "";
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        Map<String, String> resultMap = WXPayUtil.xmlToMap(sb.toString());
        //支付订单号
        String out_trade_no = new String(resultMap.get("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //回调订单号
        String trade_no = new String(resultMap.get("transaction_id").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String return_code = new String(resultMap.get("return_code").getBytes("ISO-8859-1"), "UTF-8");
        //交易金额
        String total_fee = new String(resultMap.get("total_fee").getBytes("ISO-8859-1"), "UTF-8");

        this.orderService.rechargeCallBackByOrderNum(out_trade_no);

    }


}
