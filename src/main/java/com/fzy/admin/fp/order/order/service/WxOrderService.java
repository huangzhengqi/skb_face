package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.applet.dto.AppletOrderDTO;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.util.OrderUtil;
import com.fzy.admin.fp.sdk.member.feign.MemberPayServiceFeign;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.WxPayParam;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.WxPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-24 17:15
 * @description 微信支付业务
 */
@Service
@Slf4j
@Transactional
public class WxOrderService extends BaseContent {

    @Resource
    private OrderService orderService;
    @Resource
    private CommissionService commissionService;

    @Resource
    private WxPayServiceFeign wxPayServiceFeign;

    public WxPayServiceFeign getWxPayServiceFeign() {
        return wxPayServiceFeign;
    }

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Resource
    private MemberPayServiceFeign memberPayServiceFeign;

    @Value("${wxpay.successXml}")
    public String successXml;

    @Value("${wxpay.failXml}")
    public String failXml;

    @Resource
    private MerchantService merchantService;

    /**
     * @author Created by wtl on 2019/4/24 21:03
     * @Description 构建微信支付通用参数
     */
    public WxPayParam createWxPayParam(Order model) {
        // 构建微信支付参数
        WxPayParam wxPayParam = new WxPayParam();
        wxPayParam.setAuth_code(model.getAuthCode());
        wxPayParam.setOut_trade_no(model.getOrderNumber());
        wxPayParam.setTotal_fee(model.getActPayPrice().toPlainString());
        wxPayParam.setMerchantId(model.getMerchantId());
        return wxPayParam;
    }


    /**
     * @author Created by wtl on 2019/5/9 11:08
     * @Description 微信网页授权
     */
    public String wxWebOauth(String companyId, String merchantId, String uuid) {
        log.info("companyId,{}----merchantId,{}", companyId, merchantId);
        if (ParamUtil.isBlank(companyId) && ParamUtil.isBlank(merchantId)) {
            throw new BaseException("参数错误，服务商ID或者商户ID不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 微信支付配置
        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(companyId);
        if ("error".equals(configMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String appId = configMap.get("appId");
        // 用户授权后腾讯重定向的地址 &转义成 %26 否则&后的参数会被微信截取掉
        String url = getDomain() + "/order/callback/wx_oauth" + "?merchantId=" + merchantId + "%26uuid=" + uuid;
        log.info("hxq:url,{}", url);
        // 微信授权链接
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        // 授权链接补全参数
        redirectUrl = StrFormatter.format(redirectUrl, appId, url);
        log.info("微信授权地址，{}", redirectUrl);
        return redirectUrl;
    }


    /**
     * 微信网页授权2
     *
     * @param companyId
     * @param merchantId
     * @param uuid
     * @return
     */
    public String wxWebOauth2(String companyId, String merchantId, String uuid, String userId) {
        log.info("companyId,{}----merchantId,{}", companyId, merchantId);
        if (ParamUtil.isBlank(companyId) && ParamUtil.isBlank(merchantId)) {
            throw new BaseException("参数错误，服务商ID或者商户ID不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 微信支付配置
        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(companyId);
        if ("error".equals(configMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String appId = configMap.get("appId");
        // 用户授权后腾讯重定向的地址 &转义成 %26 否则&后的参数会被微信截取掉
        String url = getDomain() + "/order/callback/wx_oauth2" + "?merchantId=" + merchantId + "%26uuid=" + uuid + "%26userId=" + userId;
        log.info("hxq:url,{}", url);
        // 微信授权链接
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        // 授权链接补全参数
        redirectUrl = StrFormatter.format(redirectUrl, appId, url);
        log.info("微信授权地址2，{}", redirectUrl);
        return redirectUrl;
    }

    /**
     * @author Created by wtl on 2019/4/23 14:39
     * @Description 微信支付获取openId
     */
    public String getOpenId(String code, String merchantId) {
        if (ParamUtil.isBlank(code)) {
            throw new BaseException("code参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }
        if (ParamUtil.isBlank(merchantId)) {
            throw new BaseException("商户id出问题", Resp.Status.INNER_ERROR.getCode());
        }
        // 微信支付配置，根据商户id获取服务商id
        Merchant merchant = merchantService.getRepository().findOne(merchantId);
        if (ParamUtil.isBlank(merchant)) {
            throw new BaseException("商户获取失败", Resp.Status.INNER_ERROR.getCode());
        }
        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(merchant.getServiceProviderId());
        if ("error".equals(configMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        log.info("configMap--------->,{}", configMap);
        // 获取openid的链接
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
        url = StrFormatter.format(url, configMap.get("appId"), configMap.get("appSecret"), code);
        log.info("openid url----------->>>,{}", url);
        String tokenJson = HttpUtil.get(url);
        Map<String, String> map = JacksonUtil.toStringMap(tokenJson);
        log.info("get openid result------------>>>,{}", map);
        if (map.get("openid") == null) {
            throw new BaseException("参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }
        return map.get("openid");
    }


    /**
     * @param order 订单
     * @author Created by wtl on 2019/4/23 11:32
     * @Description 微信jspay
     */
    public Map<String, Object> wxJsPay(String openId, Order order) throws Exception {
        if (ParamUtil.isBlank(openId)) {
            throw new BaseException("openid错误", Resp.Status.PARAM_ERROR.getCode());
        }
        // 构建微信通用支付参数
        WxPayParam wxPayParam = createWxPayParam(order);
        // 额外参数
        wxPayParam.setOpenid(openId);
        PayRes payRes = wxPayServiceFeign.wxJsPay(wxPayParam);
        log.info("微信官方支付结果：{}", payRes);
        Map<String, Object> map = (Map<String, Object>) payRes.getObject();
//        map.put("openWay", PayChannelConstant.OpenWay.PARAMS.getCode());
        map.put("channel", PayChannelConstant.Channel.OFFICIAL.getCode());
        return map;
    }

    /**
     * 微信扫码支付
     */
    public void wxScanPay(Order order, Order.InterFaceWay interFaceWay) throws Exception {
        // 构建微信通用支付参数
        WxPayParam wxPayParam = createWxPayParam(order);
        // 微信扫码支付
        PayRes payRes = wxPayServiceFeign.wxScanPay(wxPayParam, order);
        // 支付失败
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            order.setName(Order.Status.FAILPAY.getStatus());
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 支付成功或开放接口给第三方，不需要这里进行轮询查询订单
        if (PayRes.ResultStatus.SUCCESS.getCode().equals(payRes.getStatus().getCode())) {
            // 支付成功
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setPayTime(new Date());
            order.setName(Order.Status.SUCCESSPAY.getStatus());
            return;
        }
        // 开放接口给第三方，不需要这里进行轮询查询订单
        if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
            order.setPayTime(new Date());
            return;
        }
        boolean queryFlag = OrderUtil.payQuery(() -> wxPayServiceFeign.wxOrderQuery(order.getMerchantId(), order), payRes);
        // 支付成功
        order.setStatus(Order.Status.SUCCESSPAY.getCode());
        order.setName("微信支付成功");
        // 支付时间
        order.setPayTime(new Date());
        if (!queryFlag) {
            // 订单错误或者超时，撤销订单
            wxPayServiceFeign.wxReverse(order.getMerchantId(), order.getOrderNumber());
            // 撤销支付
//            order.setStatus(Order.Status.CANCELPAY.getCode());
            order.setStatus(Order.Status.PLACEORDER.getCode());
            order.setName("微信撤销支付");
        }
    }


    /**
     * @author Created by wtl on 2019/4/23 15:37
     * @Description 微信支付回调
     */
    public void wxOrderCallBack() throws Exception {
        log.info("进入支付回调");
        BufferedReader br = this.request.getReader();

        String str = "";
        StringBuilder sb = new StringBuilder();
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        Map<String, String> resultMap = WXPayUtil.xmlToMap(sb.toString());
        log.info("微信支付回调结果" + JSONUtil.toJsonStr(resultMap));

        String out_trade_no = new String(((String) resultMap.get("out_trade_no")).getBytes("ISO-8859-1"), "UTF-8");

        String trade_no = new String(((String) resultMap.get("transaction_id")).getBytes("ISO-8859-1"), "UTF-8");

        String return_code = new String(((String) resultMap.get("return_code")).getBytes("ISO-8859-1"), "UTF-8");

        String total_fee = new String(((String) resultMap.get("total_fee")).getBytes("ISO-8859-1"), "UTF-8");

        String settlementTotalFee = resultMap.get("settlement_total_fee");
        log.info("回调回来应结订单金额的数据 =========     " + settlementTotalFee);

        Order order = this.orderService.getRepository().findByOrderNumberAndDelFlag(out_trade_no, CommonConstant.NORMAL_FLAG);
        if (null == order || !order.getStatus().equals(Order.Status.PLACEORDER.getCode())) {
            this.response.getWriter().write(this.failXml);
            this.response.getWriter().flush();
            return;
        }
        Map<String, String> configMap = this.wxConfigServiceFeign.getWxConfig(order.getServiceProviderId());

        BigDecimal payMoney = order.getActPayPrice();

        boolean signResult = WXPayUtil.isSignatureValid(resultMap, (String) configMap.get("appKey"));
        if (signResult && (new BigDecimal(total_fee)).compareTo(payMoney.multiply(new BigDecimal("100")).stripTrailingZeros()) == 0 && out_trade_no.equals(order.getOrderNumber())) {
            if ("SUCCESS".equals(return_code)) {
                if (settlementTotalFee != null) {
                    //应结订单金额
                    BigDecimal settlementTotalFee1 = new BigDecimal(settlementTotalFee);
                    order.setDisCountPrice(settlementTotalFee1);
                    log.info("计算应结订单金额  ============    " + order.getActPayPrice().subtract(settlementTotalFee1));
                    order.setActPayPrice(order.getActPayPrice().subtract(settlementTotalFee1));
                }
                order.setName(Order.Status.SUCCESSPAY.getStatus());
                order.setStatus(Order.Status.SUCCESSPAY.getCode());
                order.setPayTime(new Date());
                this.orderService.getRepository().save(order);

                this.commissionService.EditCommissionStatus(order.getId(), order.getStatus());

                try {
                    if (!ParamUtil.isBlank(order.getAppletStore())) {

                        OrderVo orderVo = new OrderVo();

                        CopyOptions copyOptions = CopyOptions.create();

                        copyOptions.setIgnoreNullValue(true);

                        BeanUtil.copyProperties(order, orderVo, copyOptions);

                        //会员小程序充值，回调订单支付成功后添加储值记录
                        this.memberPayServiceFeign.memberStoreRecord(orderVo);

                        log.info("会员小程序充值，回调订单支付成功后添加储值记录已完成");

                        this.orderService.rechargeCallBackByOrderNum(order.getOrderNumber());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("微信支付生成储值记录异常");
                }


                try {
                    if (!ParamUtil.isBlank(order.getMemberId())) {
                        this.orderService.getMemberOrderService().createMemberPayRecord(order);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("微信支付生成消息记录异常");
                }


                try {
                    if (order.getOrderType() != null && order.getOrderType().intValue() == 1) {
                        log.info("扫码点餐信息{}", order.getSmdcOrderNum());
                        AppletOrderDTO appletOrderDTO = (AppletOrderDTO) JSON.parseObject(order.getSmdcOrderNum(), AppletOrderDTO.class);
                        String i = appletOrderDTO.getI();
                        String hid = appletOrderDTO.getHid();
                        String orderId = appletOrderDTO.getOrder_id();
                        BigDecimal total = appletOrderDTO.getTotal();
                        String orderNum = order.getOrderNumber();
                        String openId = appletOrderDTO.getOpenid();
                        String url = "http://pay-adm.h5h5h5.cn/app/fp_smdc.php?i={}&hid={}&c=entry&do=mobile&action=facepay&oid={}&m=dy_more&total={}&transactionid={}&openid={}";
                        url = StrFormatter.format(url, new Object[]{i, hid, orderId, total, orderNum, openId});
                        String result = HttpUtil.get(url);
                        log.info("回调扫码点餐{}", result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("微信支付回调扫码点餐异常");
                }

                this.response.getWriter().write(this.successXml);
                this.response.getWriter().flush();
            }
        } else {
            log.error("回调发生错误，支付订单号为{}，回调订单号为{}，交易金额为{}", new Object[]{out_trade_no, trade_no, total_fee});
            this.response.getWriter().write(this.failXml);
            this.response.getWriter().flush();
        }
    }

    /**
     * @param refundPayPrice 本次退款金额
     * @author Created by wtl on 2019/4/24 21:02
     * @Description 微信退款申请
     */
    public void wxRefund(Order order, BigDecimal refundPayPrice) throws Exception {
        // 构建微信通用支付参数
        WxPayParam wxPayParam = createWxPayParam(order);
        wxPayParam.setRefund_fee(refundPayPrice.toPlainString());
        // 调用微信退款
        PayRes payRes = wxPayServiceFeign.wxRefund(wxPayParam);
        orderService.refundResult(order, refundPayPrice, payRes);
    }

}
