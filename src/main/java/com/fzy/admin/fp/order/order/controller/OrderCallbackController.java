package com.fzy.admin.fp.order.order.controller;

import cn.hutool.core.text.StrFormatter;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.service.WxOrderService;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.member.feign.MemberServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-07 20:29
 * @description 支付回调接口
 */
@RestController
@RequestMapping("/order/callback")
@Slf4j
public class OrderCallbackController extends BaseContent {

    @Resource
    private OrderService orderService;

    @Resource
    private WxOrderService wxOrderService;

    @Resource
    private MemberServiceFeign memberServiceFeign;

    /**
     * @author Created by wtl on 2019/6/15 20:43
     * @Description 支付宝第三方应用授权商家回调
     */
    @GetMapping("/ali_auth")
    public String aliAuth() {
        log.info("进入支付宝授权回调");
//        response.sendRedirect("http://www.baidu.com");
        return "授权成功";
    }


    /**
     * @author Create微信授权回调，d by wtl on 2019/5/9 11:11
     * @Description
     */
    @GetMapping("/wx_oauth")
    public void wxOauth() throws Exception {
        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");
        if (ParamUtil.isBlank(code)) {
            throw new BaseException("请在微信浏览器中打开");
        }
        // 商户id
        String merchantId = request.getParameter("merchantId");
        String uuid = request.getParameter("uuid");
        log.info("hxq微信merchantId,{}", merchantId);
        log.info("hxq微信uuid,{}", uuid);
        String openId = wxOrderService.getOpenId(code, merchantId);
        log.info("重定向地址，{}", getDomain());
        // 重定向到微信付款界面paySelect，附带参数公众号openId、memberId（为空则不是会员，H5显示加入会员按钮，不为空H5带着请求卡券列表）
        String memberId = memberServiceFeign.findByOpenIdAndMerchantId(openId, merchantId);
        if (ParamUtil.isBlank(memberId)) {
            memberId = "";
        }
        String payUrl = getDomain() + "/web/pay/index.html#/wx/paySelect?openId={}&memberId={}&merchantId={}&uuid={}";

        payUrl = StrFormatter.format(payUrl, openId, memberId, merchantId, uuid);
        response.sendRedirect(payUrl);
    }

    /**
     * 微信授权回调2
     * @throws Exception
     */
    @GetMapping("/wx_oauth2")
    public void wxOauth2() throws Exception {
        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");
        if (ParamUtil.isBlank(code)) {
            throw new BaseException("请在微信浏览器中打开!!!");
        }
        // 商户id
        String merchantId = request.getParameter("merchantId");
        String uuid = request.getParameter("uuid");
        String userId = request.getParameter("userId");
        log.info("hxq微信merchantId,{}", merchantId);
        log.info("hxq微信uuid,{}", uuid);
        log.info("hxq微信userId,{}", userId);
        String openId = wxOrderService.getOpenId(code, merchantId);
        log.info("重定向地址，{}", getDomain());
        // 重定向到微信付款界面paySelect，附带参数公众号openId、memberId（为空则不是会员，H5显示加入会员按钮，不为空H5带着请求卡券列表）
        String memberId = memberServiceFeign.findByOpenIdAndMerchantId(openId, merchantId);
        if (ParamUtil.isBlank(memberId)) {
            memberId = "";
        }
//        String payUrl = getDomain() + "/web/pay2/index.html#/wx/paySelect?openId={}&memberId={}&merchantId={}&uuid={}";
//        payUrl = StrFormatter.format(payUrl, openId, memberId, merchantId, uuid);
        String payUrl = getDomain() + "/web/pay2/index.html#/" + "?userAgent=" + 1 + "&userId=" + userId +"&openId=" + openId ;
        log.info("payUrl,{}",payUrl);
        response.sendRedirect(payUrl);
    }

    /**
     * @author Created by wtl on 2019/4/23 11:52
     * @Description 微信支付回调，接收支付结果
     */
    @PostMapping("/wx_order_callback")
    public void wxOrderCallBack() throws Exception {
        orderService.getWxOrderService().wxOrderCallBack();
    }

    /**
     * @author Created by wtl on 2019/4/24 14:44
     * @Description 支付宝支付回调，直到返回"success"才停止通知
     */
    @PostMapping("/ali_order_callback")
    public String aliOrderCallBack() throws Exception {
        return orderService.getAliOrderService().aliOrderCallBack();
    }

    /**
     * @author Created by wtl on 2019/4/25 22:11
     * @Description 会员宝支付回调，直到返回"success"才停止通知
     */
    @PostMapping("/hyb_order_callback")
    public String hybOrderCallBack() throws Exception {
        return orderService.getHybOrderService().hybOrderCallBack();
    }

    /**
     * @author Created by wtl on 2019/5/21 22:57
     * @Description 易融码支付回调
     */
    @PostMapping("/yrm_order_callback")
    public String yrmOrderCallback() throws Exception {
        return orderService.getYrmOrderService().yrmOrderCallBack();
    }

    /**
     * 随行付回调
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/sxf_order_callback")
    public String sxfOrderCallBack(@RequestBody Map<String,String> resp) {
        return orderService.getSxfOrderService().sxfOrderCallBack(resp);
    }

    /**
     * 天阙随行付回调
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/tq_sxf_order_callback")
    public String tqSxfOrderCallBack(@RequestBody Map<String,String> resp) {
        return orderService.getTqSxfOrderService().sxfOrderCallBack(resp);
    }
    /**
     * @author Created by wtl on 2019/5/31 15:00
     * @Description 秒到支付结果回调
     */
    @PostMapping("/tts_order_callback")
    public String ttsOrderCallback() {
        return orderService.getTtsOrderService().ttsOrderCallBack();
    }

    /**
     * @author Created by wtl on 2019/6/15 20:39
     * @Description 惠闪付回调，没用到，暂时只用条码支付
     */
    @PostMapping("/hsf_order_callback")
    public String hsfOrderCallback() {
        Map<String, String[]> requestParams = request.getParameterMap();
        // 回调结果转map
        Map<String, String> params = OrderUtil.params2Map(requestParams);
        Map map = JacksonUtil.toStringMap(params.get("data"));
        System.err.println(map);
        return "success";
    }

    @PostMapping("/fuiou_order_callback")
    public Integer fuiouOrderCallback() throws Exception {

        return orderService.getFyOrderService().fyOrderCallBack();
    }

}
