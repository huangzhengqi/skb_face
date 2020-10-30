package com.fzy.admin.fp.pay.pay.face;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrFormatter;
import com.alibaba.fastjson.JSONArray;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import com.fzy.admin.fp.order.OrderConstant;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.WxPacePayOrderDto;
import com.fzy.admin.fp.order.order.repository.CommissionRepository;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.SxfConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.pay.pay.dto.*;
import com.fzy.admin.fp.pay.pay.repository.SxfConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TqSxfConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.WxConfigRepository;
import com.fzy.admin.fp.pay.pay.util.HttpUtil;
import com.fzy.admin.fp.pay.pay.util.SignUtils;
import com.fzy.admin.fp.pay.pay.vo.*;
import com.fzy.admin.fp.sdk.pay.feign.SxfPayServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.TqSxfPayServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@RestController
@RequestMapping("/pay/pay/face")
@Api(value = "FacePayController", tags = {"刷脸支付相关接口"})
@Slf4j
public class FacePayController {


    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;
    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private HttpServletRequest request;
    @Resource
    private OrderService orderService;
    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private WxConfigRepository wxConfigRepository;
    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;
    @Resource
    private MemberRepository memberRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private CommissionRepository commissionRepository;

    @Resource
    private SxfPayServiceFeign sxfPayServiceFeign;

    @Resource
    private SxfConfigRepository sxfConfigRepository;

    @Resource
    private TqSxfConfigRepository tqSxfConfigRepository;

    @Resource
    private TqSxfPayServiceFeign tqSxfPayServiceFeign;


    /**
     * 获取调用凭证(get_wxpayface_authinfo) https://payapp.weixin.qq.com/face/get_wxpayface_authinfo
     *
     * @param wxPayFaceAuthInfoDTO
     * @return
     */
    @GetMapping("/auth_info")
    @ApiOperation(value = "获取调用凭证", notes = "获取调用凭证")
    public Resp<WxPayFaceAuthInfoVO> getWxpayfaceAuthinfo(WxPayFaceAuthInfoDTO wxPayFaceAuthInfoDTO, @UserId String userId) {

        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(merchantUser.getMerchantId());
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String nowTime = String.valueOf(System.currentTimeMillis() / 1000);
        String url = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";
        WxPayFaceAuthInfoVO wxPayFaceAuthInfoVO = new WxPayFaceAuthInfoVO();
        Map<String, String> params = new TreeMap<>();
        params.put("appid", topConfig.getWxAppId());
        params.put("device_id", wxPayFaceAuthInfoDTO.getDeviceId());
        params.put("mch_id", topConfig.getWxMchId());
        if (!(merchantAppletConfig == null || StringUtils.isEmpty(merchantAppletConfig.getAppId()))) {
            params.put("sub_appid", merchantAppletConfig.getAppId());
        }
        if (!(wxConfig == null || StringUtils.isEmpty(wxConfig.getSubMchId()))) {
            params.put("sub_mch_id", wxConfig.getSubMchId());
        }
        params.put("nonce_str", nonceStr);
        params.put("now", nowTime);
        params.put("rawdata", wxPayFaceAuthInfoDTO.getRawdata());
        params.put("sign_type", "MD5");
        params.put("store_id", wxPayFaceAuthInfoDTO.getStoreId());
        params.put("store_name", wxPayFaceAuthInfoDTO.getStore_name());
        params.put("version", "1");
        String sig = SignUtils.createSign(params, topConfig.getWxAppKey());
        params.put("sign", sig);

        try {
            String xml = WXPayUtil.mapToXml(params);
            String result = HttpUtil.sendXmlPost(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            log.error("获取凭证", resultMap);
            wxPayFaceAuthInfoVO.setReturnCode(resultMap.get("return_code"));
            wxPayFaceAuthInfoVO.setReturnMsg(resultMap.get("return_msg"));
            wxPayFaceAuthInfoVO.setAuthInfo(resultMap.get("authinfo"));
            wxPayFaceAuthInfoVO.setExpiresIn(Integer.valueOf(resultMap.get("expires_in")));
        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "获取登陆凭证失败");
        }

        return Resp.success(wxPayFaceAuthInfoVO);
    }

    @PostMapping({"/face_pay"})
    @ApiOperation(value = "刷脸支付接口", notes = "刷脸支付接口")
    public Resp<WxFacePayVO> facePay(@RequestBody WxFacePayDTO wxFacePayDTO, @UserId String userId) {
        log.info("请求参数参数 ==========>:  {}", JSONArray.toJSON(wxFacePayDTO).toString());
        String url = "https://api.mch.weixin.qq.com/pay/facepay";
        if (ParamUtil.isBlank(userId)) {
            return (new Resp()).error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser merchantUser = (MerchantUser) this.merchantUserService.findOne(userId);
        String serviceId = (String) this.request.getAttribute("serviceProviderId");
        TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        WxConfig wxConfig = this.wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigService.findByMerchantId(merchantUser.getMerchantId());

        WxFacePayVO wxFacePayVO = new WxFacePayVO();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> params = new TreeMap<String, String>();
        params.put("appid", topConfig.getWxAppId());
        if (merchantAppletConfig != null && StringUtils.isNotEmpty(merchantAppletConfig.getAppId())) {
            params.put("sub_appid", merchantAppletConfig.getAppId());
        }

        Order order = this.orderRepository.findByOrderNumber(wxFacePayDTO.getOrderNum());
        //根据订单id查询订单佣金表
        Commission commission = commissionRepository.findByOrderId(order.getId());

        params.put("mch_id", topConfig.getWxMchId());
        params.put("sub_mch_id", wxConfig.getSubMchId());
        params.put("nonce_str", nonceStr);
        params.put("body", wxFacePayDTO.getDescribe());
        params.put("out_trade_no", wxFacePayDTO.getOrderNum());

        params.put("total_fee", Math.round(Double.parseDouble(wxFacePayDTO.getTotalFee().toString()) * 100.0D) + "");
        params.put("spbill_create_ip", wxFacePayDTO.getIp());
        params.put("openid", wxFacePayDTO.getOpenId());
        params.put("face_code", wxFacePayDTO.getFaceCode());
        String sig = SignUtils.createSign(params, topConfig.getWxAppKey());
        params.put("sign", sig);

        //支付方式
        order.setPayType(Integer.valueOf(3));
        order.setAuthCode(wxFacePayDTO.getFaceCode());
        try {
            String xml = WXPayUtil.mapToXml(params);
            String result = HttpUtil.sendXmlPost(url, xml);
            log.info("刷脸支付返回参数 ============> {}", result);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            String returnCode = (String) resultMap.get("return_code");
            String resultCode = resultMap.get("result_code");
            wxFacePayVO.setReturnCode(returnCode);
            wxFacePayVO.setReturnMsg((String) resultMap.get("return_msg"));
            wxFacePayVO.setResultCode((String) resultMap.get("result_code"));

            if ("SUCCESS".equals(resultCode)) {
                order.setName(Order.Status.SUCCESSPAY.getStatus());
                order.setStatus(Integer.valueOf(2));
                order.setEquipmentId(wxFacePayDTO.getDeviceNo());
                this.orderRepository.save(order);
                log.info("支付成功订单编号" + wxFacePayDTO.getOrderNum());
                changeOrder(order);
                wxFacePayVO.setPayTime(order.getPayTime());
                wxFacePayVO.setTotalPrice(order.getTotalPrice());
                wxFacePayVO.setOrderNumber(order.getOrderNumber());
                //修改佣金状态
                commission.setOrderStatus(Integer.valueOf(2));
                commissionRepository.save(commission);
                log.info("支付成功订单佣金表---------  " + commission);
            } else if (resultCode.equals("FAIL")) {
                //错误原因
                return (new Resp()).error(Resp.Status.PARAM_ERROR, resultMap.get("err_code_des"));
            } else if (returnCode.equals("FAIL")) {
                WxRefundDTO wxRefundDTO = new WxRefundDTO();
                wxRefundDTO.setOrderNum(wxFacePayDTO.getOrderNum());
                refund(wxRefundDTO, userId);
            } else {
                WxSearchOrderDTO wxSearchOrderDTO = new WxSearchOrderDTO();
                wxSearchOrderDTO.setOrderNum(wxFacePayDTO.getOrderNum());
                long endtime = System.currentTimeMillis() + 100000L;
                long revolution = 1000L;
                while (true) {
                    long nowtime = System.currentTimeMillis();
                    if (endtime < nowtime) {
                        WxRefundDTO wxRefundDTO = new WxRefundDTO();
                        wxRefundDTO.setOrderNum(wxFacePayDTO.getOrderNum());
                        refund(wxRefundDTO, userId);
                        wxFacePayVO.setReturnCode("FAIL");
                        break;
                    }
                    Thread.sleep(revolution);
                    Resp<WxSearchOrderVO> orderVOResp = searchOrder(wxSearchOrderDTO, userId);
                    if (((WxSearchOrderVO) orderVOResp.getObj()).getTradeState().equals("SUCCESS")) {
                        order.setStatus(Integer.valueOf(2));
                        this.orderRepository.save(order);
                        log.info("支付成功订单编号" + wxFacePayDTO.getOrderNum());
                        changeOrder(order);
                        //修改佣金状态
                        commission.setOrderStatus(Integer.valueOf(2));
                        commissionRepository.save(commission);
                        log.info("支付成功订单佣金表---------  " + commission);
                        break;
                    }
                    if (((WxSearchOrderVO) orderVOResp.getObj()).getTradeState().equals("PAYERROR")) {
                        WxRefundDTO wxRefundDTO = new WxRefundDTO();
                        wxRefundDTO.setOrderNum(wxFacePayDTO.getOrderNum());
                        refund(wxRefundDTO, userId);
                        wxFacePayVO.setReturnCode("FAIL");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "支付异常");
        }
        return Resp.success(wxFacePayVO);
    }

    /**
     * 刷脸支付接口
     *
     * @param wxFacePayMemberDTO
     * @return
     */
    @PostMapping("/face_pay/member")
    @ApiOperation(value = "会员刷脸支付接口", notes = "会员刷脸支付接口")
    public Resp<WxFacePayVO> facePay(@RequestBody WxFacePayMemberDTO wxFacePayMemberDTO) {
        Member member = memberRepository.findByOpenIdAndDelFlag(wxFacePayMemberDTO.getOpenId(), CommonConstant.NORMAL_FLAG);
        WxFacePayVO wxFacePayVO = new WxFacePayVO();
        if (member == null) {
            wxFacePayVO.setResultCode("ERROR");
            wxFacePayVO.setReturnMsg("会员不存在");
        } else {
            wxFacePayVO.setMember(member);
            BigDecimal leftMoney = member.getBalance().subtract(wxFacePayMemberDTO.getTotalFee());
            if (leftMoney.compareTo(new BigDecimal(0)) == -1) {
                wxFacePayVO.setResultCode("ERROR");
                wxFacePayVO.setReturnMsg("会员余额不足");
            } else {
                member.setBalance(leftMoney);
                //生成储值记录明细
                StoredRecored storedRecored = new StoredRecored();
                storedRecored.setMerchantId(member.getMerchantId());
                storedRecored.setCreateTime(new Date());
                storedRecored.setTradeType(StoredRecored.TradeType.CONSUME.getCode());
                storedRecored.setPayWay(StoredRecored.PayWay.CARDS.getCode());
                storedRecored.setTradingMoney(wxFacePayMemberDTO.getTotalFee());
                storedRecored.setGiftMoney(new BigDecimal(0));
                storedRecored.setPostTradingMoney(member.getBalance());

                memberRepository.save(member);
                wxFacePayVO.setResultCode("SUCCESS");
                wxFacePayVO.setReturnMsg("支付成功");
            }
        }
        return Resp.success(wxFacePayVO);
    }

    @PostMapping("/face_pay/member_info")
    @ApiOperation(value = "会员信息", notes = "会员信息")
    public Resp<Member> memberInfo(String openId) {
        Member member = memberRepository.findByOpenIdAndDelFlag(openId, CommonConstant.NORMAL_FLAG);
        return Resp.success(member);
    }

    /**
     * 生成订单
     *
     * @param wxPacePayOrderDto
     * @param userId
     * @return
     */
    @PostMapping("/order")
    @ApiOperation(value = "生成订单", notes = "生成订单")
    public Resp<Order> order(@RequestBody WxPacePayOrderDto wxPacePayOrderDto, @UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        log.info("生成订单参数-----{}", wxPacePayOrderDto.toString());
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        OrderDto orderDto = new OrderDto();
        BeanUtil.copyProperties(wxPacePayOrderDto, orderDto);
        orderDto.setUserId(userId);
        orderDto.setUserName(merchantUser.getUsername());
        orderDto.setStoreName(merchantUser.getStoreName());
        orderDto.setPayClient(Order.PayClient.APP.getCode());
        orderDto.setPayWay(wxPacePayOrderDto.getPayWay());
        Order order = orderService.createOrder(orderDto);
        return Resp.success(order);
    }

    /**
     * 修改订单状态
     *
     * @param Order
     * @return
     */
    @PutMapping("/order")
    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    public Resp changeOrder(@RequestBody Order Order) {
        try {
            orderService.save(Order);
            return Resp.success("修改成功");
        } catch (Exception e) {
            return new Resp().error(Resp.Status.INNER_ERROR, "系统异常");
        }

    }

    /**
     * 查询订单 https://api.mch.weixin.qq.com/pay/facepayquery
     *
     * @param wxSearchOrderDTO
     * @param userId
     * @return
     */
    public Resp<WxSearchOrderVO> searchOrder(@RequestBody WxSearchOrderDTO wxSearchOrderDTO, String userId) {
        String url = "https://api.mch.weixin.qq.com/pay/facepayquery";
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }
        Map<String, String> params = new TreeMap<>();
        params.put("appid", topConfig.getWxAppId());
        params.put("mch_id", topConfig.getWxMchId());
        params.put("sub_mch_id", wxConfig.getSubMchId());
        params.put("out_trade_no", wxSearchOrderDTO.getOrderNum());
        params.put("sign_type", "MD5");
        String sig = SignUtils.createSign(params, topConfig.getWxAppKey());
        params.put("sign", sig);
        WxSearchOrderVO wxSearchOrderVO = new WxSearchOrderVO();
        try {
            String xml = WXPayUtil.mapToXml(params);
            String result = HttpUtil.sendXmlPost(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            log.info("刷脸支付返回结果=========    " + resultMap.toString());
            wxSearchOrderVO.setReturnCode(resultMap.get("return_code"));
            wxSearchOrderVO.setReturnMsg(resultMap.get("return_msg"));
            wxSearchOrderVO.setResultCode(resultMap.get("result_code"));
            wxSearchOrderVO.setTradeState(resultMap.get("trade_state"));
        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "获取订单异常");
        }
        return Resp.success(wxSearchOrderVO);

    }


    /**
     * 退款
     *
     * @param wxRefundDTO
     * @param userId
     * @return
     */
    public Resp<WxRefundVO> refund(@RequestBody WxRefundDTO wxRefundDTO, String userId) {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }
        Order order = orderService.getRepository().findByOrderNumberAndDelFlag(wxRefundDTO.getOrderNum(), CommonConstant.NORMAL_FLAG);
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> params = new TreeMap<>();
        params.put("appid", topConfig.getWxAppId());
        params.put("mch_id", topConfig.getWxMchId());
        params.put("sub_mch_id", wxConfig.getSubMchId());
        params.put("out_refund_no", wxRefundDTO.getOrderNum());
        params.put("out_re-fund_no", wxRefundDTO.getOrderNum());
        params.put("nonce_str", nonceStr);
        params.put("total_fee", params.put("total_fee", Math.round(Double.parseDouble(order.getActPayPrice().toString()) * 100d) + ""));
        params.put("refund_fee", params.put("total_fee", Math.round(Double.parseDouble(order.getActPayPrice().toString()) * 100d) + ""));
        String sig = SignUtils.createSign(params, topConfig.getWxAppKey());
        params.put("sign", sig);
        WxRefundVO wxRefundVO = new WxRefundVO();
        try {
            String xml = WXPayUtil.mapToXml(params);
            String result = HttpUtil.sendXmlPost(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            log.info("退款", resultMap);
            wxRefundVO.setReturnCode(resultMap.get("return_code"));
            wxRefundVO.setResultCode(resultMap.get("result_code"));
        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "退款异常");
        }
        return Resp.success(wxRefundVO);

    }

    /**
     * 撤销订单
     *
     * @param wxSearchOrderDTO
     * @param userId
     * @return
     */
    @PostMapping("/cancel_order")
    @ApiOperation(value = "撤销订单", notes = "撤销订单")
    public Resp<WxCancelOrderVO> cancelOrder(@RequestBody WxSearchOrderDTO wxSearchOrderDTO, @UserId String userId) {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/facepayreverse";
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> params = new TreeMap<>();
        params.put("appid", topConfig.getWxAppId());
        params.put("mch_id", topConfig.getWxMchId());
        params.put("sub_mch_id", wxConfig.getSubMchId());
        params.put("out_trade_no", wxSearchOrderDTO.getOrderNum());
        params.put("nonce_str", nonceStr);
        String sig = SignUtils.createSign(params, topConfig.getWxAppKey());
        params.put("sign", sig);
        WxCancelOrderVO wxCancelOrderVO = new WxCancelOrderVO();
        try {
            String xml = WXPayUtil.mapToXml(params);
            String result = HttpUtil.sendXmlPost(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            log.info("撤销订单", resultMap);
            wxCancelOrderVO.setReturnCode(resultMap.get("return_code"));
            wxCancelOrderVO.setResultCode(resultMap.get("result_code"));
            wxCancelOrderVO.setReCall(resultMap.get("recall"));
        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "撤销订单异常");
        }
        return Resp.success(wxCancelOrderVO);
    }

    /**
     * h5支付后轮询订单状态
     *
     * @param uuid
     * @return
     */
    @PostMapping("/polling/order")
    @ApiOperation(value = "h5支付后轮询订单状态", notes = "h5支付后轮询订单状态")
    public Resp cancelOrder(String uuid) {
        String orderId = OrderConstant.ORDERID.get(uuid);
        log.info("订单id {}", orderId);
        if (orderId == null) {
            return new Resp<>().error(Resp.Status.INNER_ERROR, "用户还未扫码");
        }
        Order order = orderService.findOne(orderId);
        log.info("订单信息hxq:", order);
        if (order.getStatus().equals(Order.Status.SUCCESSPAY.getCode())) {
//            Map<String, String> map = new HashMap<>();
//            String payWay = order.getPayWay().toString();
//            map.put("payWay",payWay);
            return Resp.success(order, "支付成功");
        } else {
            return new Resp<>().error(Resp.Status.INNER_ERROR, "订单未支付");
        }
    }

    /**
     * 生成uuid
     *
     * @return
     */
    @ApiOperation(value = "生成全局唯一uuid", notes = "生成全局唯一uuid")
    @PostMapping("/uuid")
    public Resp<String> uuid() {
        String uuid = UUID.randomUUID().toString();
        while (true) {
            String orderId = OrderConstant.ORDERID.get(uuid);
            if (orderId == null) {
                break;
            } else {
                uuid = UUID.randomUUID().toString();
            }
        }
        return Resp.success(uuid, "");
    }

    /**
     * 特约商户配置-关注配置
     */
    @ApiOperation(value = "特约商户配置-关注配置", notes = "特约商户配置-关注配置")
    @PostMapping("/addrecommendconf")
    public Resp<String> addRecommendConf(@RequestBody AddRecommendConfDTO addRecommendConfDTO, @UserId String userId) {
        String url = "https://api.mch.weixin.qq.com/secapi/mkt/addrecommendconf";
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        MerchantAppletConfig appletConfig = merchantAppletConfigService.findByMerchantId(merchantUser.getMerchantId());
        if (appletConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置小程序相关参数");
        }
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }

        Map<String, String> params = new TreeMap<>();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        params.put("mch_id", topConfig.getWxMchId());
        params.put("sub_mch_id", wxConfig.getSubMchId());
        params.put("sub_appid", appletConfig.getAppId());
        if (addRecommendConfDTO.getSubscribe_appid() != null) {
            params.put("subscribe_appid", addRecommendConfDTO.getSubscribe_appid());
        } else {
            params.put("receipt_appid", addRecommendConfDTO.getReceipt_appid());
        }
        params.put("nonce_str", nonceStr);
        params.put("sign_type", "HMAC-SHA256");

        try {
            String sig = WXPayUtil.generateSignature(params, topConfig.getWxAppKey(), WXPayConstants.SignType.HMACSHA256);
            params.put("sign", sig);
            log.info("=============配置参数" + params);
            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
            String xml = new WXPay(myWxConfig, WXPayConstants.SignType.HMACSHA256).requestWithCert(url, params, myWxConfig.getHttpConnectTimeoutMs(),
                    myWxConfig.getHttpReadTimeoutMs());
            Map<String, String> respData = WXPayUtil.xmlToMap(xml);
            log.info("关注配置:{}", respData.toString());
            return Resp.success(respData.get("result_code") == null ? respData.get("return_code") : respData.get("result_code"), respData.get("return_msg"));

        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "关注配置异常");
        }
        return null;
    }


    @GetMapping(value = "/open_id")
    public Resp WXCode(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, String code) throws IOException {
        // 获取微信公众号参数
        Map<String, String> wxConfig = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";
        url = StrFormatter.format(url, wxConfig.get("frogAppKey"), wxConfig.get("frogAppCert"), code);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String openId = null;
        if (entity != null) {
            String result = EntityUtils.toString(entity);
            Map<String, Object> map = new HashMap<>();
            map = JacksonUtil.toObjectMap(result);
            log.info("map ------------  " + map.toString());
            openId = (String) map.get("openid");
        }
        return Resp.success(openId);
    }


    /**
     * 随行付通道获取微信 AuthInfo
     */
    @GetMapping("/sxf_auth_info")
    @ApiOperation(value = "获取随行付通道获取微信 AuthInfo", notes = "获取随行付通道获取微信 AuthInfo")
    public Resp<SxfPayFaceAuthInfoVO> getsxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, @UserId String userId) throws Exception {

        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }

        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        SxfConfig sxfConfig = sxfConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (sxfConfig == null || StringUtils.isEmpty(sxfConfig.getSubMchId())) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置随行付子商户相关参数");
        }
        return Resp.success(sxfPayServiceFeign.getsxfAuthInfo(sxfPayFaceAuthInfoDTO, sxfConfig));
    }

    @ApiOperation(value = "获取随行付subopenid 接口", notes = "获取 随行付subopenid 接口")
    @GetMapping("/get_sxf_subopenid")
    public Resp getSxfSubopenid(@UserId String userId, String authCode) throws Exception {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }

        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        SxfConfig sxfConfig = sxfConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (sxfConfig == null || StringUtils.isEmpty(sxfConfig.getSubMchId())) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置随行付子商户相关参数");
        }
        return Resp.success(sxfPayServiceFeign.getSxfSubopenid(authCode, sxfConfig));
    }

    /**
     * 天阙随行付通道获取微信 AuthInfo
     */
    @GetMapping("/tq_sxf_auth_info")
    @ApiOperation(value = "获取天阙随行付通道获取微信 AuthInfo", notes = "获取天阙随行付通道获取微信 AuthInfo")
    public Resp<SxfPayFaceAuthInfoVO> getTqSxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, @UserId String userId) throws Exception {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }
        TqSxfConfig tqSxfConfig = tqSxfConfigRepository.findByMerchantIdAndDelFlag(merchantUser.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (tqSxfConfig == null || StringUtils.isEmpty(tqSxfConfig.getSubMchId())) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置天阙随行付子商户相关参数");
        }
        return Resp.success(tqSxfPayServiceFeign.getTqSxfAuthInfo(sxfPayFaceAuthInfoDTO, tqSxfConfig));
    }

}
