package com.fzy.admin.fp.distribution.app.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.dto.PayDTO;
import com.fzy.admin.fp.distribution.app.service.DistPayService;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.app.utils.AddressUtil;
import com.fzy.admin.fp.distribution.app.utils.WxMD5Util;
import com.fzy.admin.fp.distribution.config.AlipayConfig;
import com.fzy.admin.fp.distribution.order.domain.OrderGoods;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.distribution.pc.domain.DistWxConfig;
import com.fzy.admin.fp.distribution.pc.repository.DistWxConfigReposotory;
import com.fzy.admin.fp.distribution.shop.domain.Address;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.domain.ShopCart;
import com.fzy.admin.fp.distribution.shop.service.AddressService;
import com.fzy.admin.fp.distribution.shop.service.DistGoodsService;
import com.fzy.admin.fp.distribution.shop.service.ShopCartService;
import com.fzy.admin.fp.distribution.utils.DistUtil;
import com.fzy.admin.fp.goods.controller.GoodsCategoryController;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@RestController
@RequestMapping("/dist/app/pay")
@Api(value = "PayController", tags = {"分销-支付"})
public class PayController {


    private static final Logger log = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    private DistPayService distPayService;
    @Resource
    private DistUserService distUserService;
    @Resource
    private ShopCartService shopCartService;
    @Resource
    private DistGoodsService distGoodsService;
    @Resource
    private AddressService addressService;
    @Resource
    private ShopOrderService shopOrderService;
    @Resource
    protected TopConfigRepository topConfigRepository;
    @Resource
    public DistWxConfigReposotory distWxConfigReposotory;

    public PayController() {
    }

    @GetMapping(value = "/shop/order")
    @ApiOperation(value = "未支付订单支付", notes = "未支付订单支付")
    public Resp shopPayForOrder(HttpServletRequest request, @UserId String userId, String id) throws Exception {

        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        ShopOrder shopOrder = shopOrderService.getRepository().findByUserIdAndId(userId, id);
        request.setCharacterEncoding("UTF-8");
        String domainName = request.getServerName();

        //2.微信支付
        if(shopOrder.getPayType().equals(Integer.valueOf(2))){

            //微信购买设备异步回调
            final String shopSync = domainName + "/dist/app/pay/shop/wx/notify";
            log.info("微信异步回调地址  " + shopSync);

            //微信app支付同步回调
            final String appAsync = domainName + "/dist/app/pay/alipay/return";
            DistWxConfig distWxConfig = distWxConfigReposotory.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
            MyWxConfig myWxConfig = new MyWxConfig(distWxConfig.getAppId(),distWxConfig.getWxMchId(),distWxConfig.getWxAppKey(),distWxConfig.getWxCertPath());

            //请求预支付订单
            Map<String, String> result = getWxPayBody(request, shopOrder.getOrderNumber(), "设备", shopOrder.getPrice(), appAsync, shopSync, serviceId,distWxConfig,myWxConfig);
            Map<String, String> map = new HashMap<>();

            log.info("请求预支付订单(result)      " + result);
            WxMD5Util md5Util = new WxMD5Util();
            //返回APP端的数据
            //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
            map.put("appid", result.get("appid"));
            map.put("partnerid", result.get("mch_id"));
            map.put("prepayid", result.get("prepay_id"));
            map.put("package", "Sign=WXPay");
            map.put("noncestr", result.get("nonce_str"));
            //单位为秒
            map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            // 这里不要使用请求预支付订单时返回的签名
            map.put("sign", md5Util.getSign(myWxConfig, map));
            log.info("map ========  "  + map.toString() );
            log.info("调起微信支付");
            return Resp.success(map, "调起微信支付");
        }

        //支付宝支付
        //购买设备异步回调
        final String shopSync = domainName + "/dist/app/pay/shop/notify";
        //app支付同步回调
        final String appAsync = domainName + "/dist/app/pay/alipay/return";
        return Resp.success(getPayBody(shopOrder.getOrderNumber(), "设备", shopOrder.getPrice(), appAsync, shopSync, serviceId));
    }

    /**
     * 手机app支付成功同步回调
     *
     * @throws AlipayApiException
     */
    @ApiOperation(value = "商城支付完回调", notes = "商城支付完回调")
    @PostMapping(value = "/shop/return")
    public Resp payShopResult(@RequestBody Map<String, Object> map) throws AlipayApiException {
        log.info("前端请求的参数map --------- " + map);
        String result = (String) map.get("orderNum");
        JSONObject jsonObj = new JSONObject(result);
        jsonObj = new JSONObject((String) jsonObj.get("result"));
        JSONObject alipay_trade_app_pay_response = jsonObj.getJSONObject("alipay_trade_app_pay_response");
        String orderNum = (String) alipay_trade_app_pay_response.get("out_trade_no");
        String total_amount = (String) alipay_trade_app_pay_response.get("total_amount");
        ShopOrder byOrderNumber = shopOrderService.getRepository().findByOrderNumber(orderNum);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(byOrderNumber.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", topConfig.getAliMobilePayId(),
                topConfig.getAliMobilePrivateKey(), "json", "GBK", topConfig.getAliMobilePublicKey(), "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderNum + "\"}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            distPayService.shopAliPay(byOrderNumber, total_amount);
            return Resp.success("支付成功");
        } else {
            return Resp.success("支付失败");
        }
    }

    /**
     * 商城支付
     *
     * @param userId
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/shop")
    @ApiOperation(value = "商城支付", notes = "商城支付")
    public Resp shopPayForAPP(HttpServletRequest request, @UserId String userId, @Valid PayDTO payDTO) throws UnsupportedEncodingException {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        request.setCharacterEncoding("UTF-8");
        String domainName = request.getServerName();
        final String shopSync = domainName + "/dist/app/pay/shop/notify";//购买设备异步回调

        final String appAsync = domainName + "/dist/app/pay/alipay/return";//app支付同步回调

        try {
            DistUser distUser = distUserService.findOne(userId);
            /*if(payDTO.getPayType()==1){
                if (payDTO.getPassword()==null||!BCrypt.checkpw(payDTO.getPassword(), distUser.getPassword())) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
                }
            }*/
            List<ShopCart> allGoods = shopCartService.getRepository().findAllByUserId(userId);
            if (allGoods == null || allGoods.size() == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择商品");
            }
            //算出购物车总共的金额
            BigDecimal price = new BigDecimal("0");
            List<OrderGoods> oderPropertyList = new ArrayList<>();
            StringBuilder shopInfo = new StringBuilder();
            for (ShopCart data : allGoods) {
                OrderGoods orderGoods = new OrderGoods();
                DistGoods distGoods = distGoodsService.findOne(data.getGoodsId());
                shopInfo.append(distGoods.getName() + (StringUtil.isEmpty(data.getProperty()) ? "" : "(" + data.getProperty() + ")") + "*" + data.getNum() + ";");
                if (distGoods == null) {//商品不存在时，清除掉购物车里的商品
                    shopCartService.getRepository().deleteByGoodsId(data.getGoodsId());
                    continue;
                }
//                if(distGoods.getNum()==0){//商品已估清
//                    return new Resp().error(Resp.Status.PARAM_ERROR,distGoods.getName()+"，商品已估清");
//                }
                if (distGoods.getStatus() == 1) {//商品已下架
                    return new Resp().error(Resp.Status.PARAM_ERROR, distGoods.getName() + "，商品已下架");
                }
//                if(data.getNum()>distGoods.getNum()){//商品库存小于需要购买的数量
//                    return new Resp().error(Resp.Status.PARAM_ERROR,distGoods.getName()+"，商品库存小于需要购买的数量");
//                }
                price = price.add(distGoods.getPrice().multiply(new BigDecimal(data.getNum())));
                orderGoods.setImg(distGoods.getImg() == null ? "" : distGoods.getImg().split(",")[0]);
                orderGoods.setNum(data.getNum());
                orderGoods.setPrice(distGoods.getPrice());
                //orderGoods.setProperty(data.getProperty());
                orderGoods.setGoodsId(data.getGoodsId());
                orderGoods.setName(distGoods.getName());
                oderPropertyList.add(orderGoods);
            }
            if (price.compareTo(new BigDecimal("0")) == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请在购物车添加需要购买的商品");
            }
            Address address = addressService.findOne(payDTO.getAddressId());
            //生成订单号
            String orderNub = DistUtil.orderNub();
            //Wallet wallet = walletService.getRepository().findByUserId(distUser.getId());
            ShopOrder shopOrder = new ShopOrder();
            shopOrder.setName(shopInfo.toString().substring(0, shopInfo.length() - 1));
            shopOrder.setOrderNumber(orderNub);
            shopOrder.setPayType(1);
            shopOrder.setPhone(address.getPhone());
            shopOrder.setPlace(address.getPlace() + address.getDetailPlace());
            shopOrder.setUserId(userId);
            shopOrder.setServiceProviderId(distUser.getServiceProviderId());
            shopOrder.setPrice(price);
            shopOrder.setConsignee(address.getName());
            shopOrder.setStatus(0);
            distPayService.addOrder(shopOrder, oderPropertyList);
            return Resp.success(getPayBody(orderNub, "设备", price, appAsync, shopSync, serviceId));
            /*
            if(payDTO.getPayType()==0){
                shopOrder.setBalancePay(new BigDecimal("0"));
                shopOrder.setAliPay(price);
                distPayService.addOrder(shopOrder,oderPropertyList);
                return Resp.success(getPayBody(orderNub,"设备",price,appAsync,shopSync,serviceId));
            }
            if(price.compareTo(wallet.getBalance())==1){//当订单金额多于余额就需要用支付宝支付不足部分
                BigDecimal subtract = price.subtract(wallet.getBalance());
                shopOrder.setAliPay(subtract);
                shopOrder.setBalancePay(wallet.getBalance());
                distPayService.addOrder(shopOrder,oderPropertyList);
                return Resp.success(getPayBody(orderNub,"设备",subtract,appAsync,shopSync,serviceId));
            }
            shopOrder.setAliPay(new BigDecimal("0"));
            shopOrder.setBalancePay(price);
            shopOrder.setStatus(1);
            distPayService.balancePay(shopOrder,wallet,oderPropertyList);
            return Resp.success("支付成功");*/
        } catch (Exception e) {
            e.printStackTrace();
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
    }

    @RequestMapping("/shop/notify")
    public void checkCallBackInfoByShop(HttpServletRequest request) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            log.info("支付宝购买商品回调，sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");


            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            ShopOrder shopOrder = shopOrderService.getRepository().findByOrderNumber(out_trade_no);

            TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(shopOrder.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
            //支付宝交易号
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            //验证回调是不是支付宝发的
            boolean flag = AlipaySignature.rsaCheckV1(params,
                    topConfig.getAliMobilePublicKey(),
                    "UTF-8", "RSA2");
            if (flag) {
                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("TRADE_FINISHED")) {
                    distPayService.shopAliPay(shopOrder, total_amount);
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    distPayService.shopAliPay(shopOrder, total_amount);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 支付宝App支付
     * @param userId
     * @param type 1普通代理 2VIP代理 3合伙人
     * @return
     * @throws IOException
     @GetMapping(value="/ali") public Resp payForAPP(HttpServletRequest request,@UserId String userId, Integer type) throws UnsupportedEncodingException {
     request.setCharacterEncoding("UTF-8");

     String domainName = request.getServerName();

     final String applySync=domainName+"/dist/app/pay/notify";//申请代理异步回调

     final String appAsync=domainName+"/dist/app/pay/alipay/return";//app支付同步回调

     final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
     DistUser distUser = distUserService.getRepository().findByGradeAndId(type,userId);
     if(distUser!=null){
     return new Resp().error(Resp.Status.TOKEN_ERROR,"请勿重复提交订单");
     }
     Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(type, serviceId);
     String subject="软件订制开发"+(type==1?"A":type==2?"B":"C")+"套餐";
     String orderNub = DistUtil.orderNub();
     DistOrder distOrder=new DistOrder();
     distOrder.setOrderNumber(orderNub);
     distOrder.setType(type);
     distOrder.setPrice(costs.getPrice());
     distOrder.setUserId(userId);
     distOrder.setStatus(0);
     distOrder.setName(subject);
     distOrder.setServiceProviderId(serviceId);
     distOrderService.getRepository().save(distOrder);
     return Resp.success(getPayBody(orderNub, subject, costs.getPrice(),appAsync,applySync,serviceId));
     }

     @RequestMapping("/notify") public void checkCallBackInfo(HttpServletRequest request) {
     try {
     //获取支付宝POST过来反馈信息
     Map<String, String> params = new HashMap<String, String>();
     Map requestParams = request.getParameterMap();
     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
     String name = (String) iter.next();
     String[] values = (String[]) requestParams.get(name);
     String valueStr = "";
     for (int i = 0; i < values.length; i++) {
     valueStr = (i == values.length - 1) ? valueStr + values[i]
     : valueStr + values[i] + ",";
     }
     //乱码解决，这段代码在出现乱码时使用。
     //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
     params.put(name, valueStr);
     }
     log.info("支付宝回调，sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
     //支付宝交易号
     String out_trade_no  = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
     //交易状态
     String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
     //交易金额
     String total_amount  = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


     DistOrder distOrder = distOrderService.getRepository().findByOrderNumber(out_trade_no);

     TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(distOrder.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
     //验证回调是不是支付宝发的
     //切记alipaypublickey是支付宝的公钥，请去open.payutils.com对应应用下查看。
     boolean flag = AlipaySignature.rsaCheckV1(params,
     topConfig.getAliMobilePublicKey(),
     "UTF-8", "RSA2");
     if (flag) {
     //请在这里加上商户的业务逻辑程序代码
     //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
     if(trade_status.equals("TRADE_FINISHED")){
     distPayService.payOver(distOrder,total_amount);
     } else if (trade_status.equals("TRADE_SUCCESS")){
     distPayService.payOver(distOrder,total_amount);
     }
     }

     } catch (Exception e) {
     e.printStackTrace();
     }
     }

     *//**
     * 手机app支付成功同步回调
     * @throws AlipayApiException
     *//*
    @RequestMapping(value="/alipay/return",method=RequestMethod.POST)
    public Resp payResult(@RequestBody Map<String,Object> map) throws AlipayApiException {
        String result =(String) map.get("orderNum");
        JSONObject jsonObj = new JSONObject(result);
        jsonObj = new JSONObject((String)jsonObj.get("result"));
        JSONObject alipay_trade_app_pay_response = jsonObj.getJSONObject("alipay_trade_app_pay_response");
        String orderNum= (String)alipay_trade_app_pay_response.get("out_trade_no");
        String total_amount= (String)alipay_trade_app_pay_response.get("total_amount");
        DistOrder orderNumber = distOrderService.getRepository().findByOrderNumber(orderNum);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(orderNumber.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",topConfig.getAliMobilePayId(),
                topConfig.getAliMobilePrivateKey(),"json","GBK", topConfig.getAliMobilePublicKey(),"RSA2");
        AlipayTradeQueryRequest  request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+orderNum+"\"}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()) {
            distPayService.payOver(orderNumber,total_amount);
            return Resp.success("支付成功");
        }else {
            return Resp.success("支付失败");
        }
    }*/

    /*@GetMapping("/transfer")
    public Resp transfer(String num){
        try {
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY,"json","GBK", AlipayConfig.ALIPAY_PUBLIC_KEY,"RSA2");
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            request.setBizContent("{" +
                    "\"out_biz_no\":\""+num+"\"," +
                    "\"payee_type\":\"ALIPAY_LOGONID\"," +
                    "\"payee_account\":\"15115308391\"," +
                    "\"amount\":\"10\"," +
                    "\"payer_show_name\":\"颜阳\"," +
                    "\"payee_real_name\":\"颜阳\"," +
                    "\"remark\":\"转账备注\"" +
                    "  }");
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) { //提现成功,本地业务逻辑略

            } else { // 支付宝提现失败，本地业务逻辑略
               // logger.info("调用支付宝提现接口成功,但提现失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * @param orderNub 订单号
     * @param subject  标题
     * @param price    金额
     * @param asyncUrl 异步回调路径
     * @param syncUrl  同步回调路径
     * @return
     */
    public String  getPayBody(String orderNub, String subject, BigDecimal price, String asyncUrl, String syncUrl, String serviceProviderId) {
        // 获取支付宝服务商通用参数
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceProviderId, CommonConstant.NORMAL_FLAG);
        if (topConfig.getAliMobilePayId() == null || topConfig.getAliMobilePublicKey() == null || topConfig.getAliMobilePrivateKey() == null) {
            return "服务商未配置参数";
        }
        // 超时时间 可空
        String timeout_express = "";
        // 销售产品码 必填
        String product_code = "QUICK_MSECURITY_PAY";

        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, topConfig.getAliMobilePayId(), topConfig.getAliMobilePrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, topConfig.getAliMobilePublicKey(), AlipayConfig.SIGNTYPE);
        AlipayTradeAppPayRequest alipay_request = new AlipayTradeAppPayRequest();
        String result = "";
        // 封装请求支付信息
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        model.setOutTradeNo(orderNub);
        // 订单名称，必填
        model.setSubject(subject);
        // 付款金额，必填
        model.setTotalAmount(String.valueOf(price));
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(asyncUrl);
        //同步
        alipay_request.setReturnUrl(syncUrl);
        try {
            // 调用SDK生成表单
            AlipayTradeAppPayResponse response = client.sdkExecute(alipay_request);
            // 可以直接给客户端请求，无需再做处理。
            result = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 微信支付
     */
    @GetMapping("/shop_wx")
    @ApiOperation(value = "微信支付", notes = "微信支付")
    public Resp shopWx(HttpServletRequest request, @UserId String userId, @Valid PayDTO payDTO) throws UnsupportedEncodingException {

        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        request.setCharacterEncoding("UTF-8");
        String domainName = request.getServerName();
        final String shopSync = domainName + "/dist/app/pay/shop/wx/notify";//微信购买设备异步回调
        log.info("微信异步回调地址  " + shopSync);

        final String appAsync = domainName + "/dist/app/pay/alipay/return";//微信app支付同步回调

        try {
            //分销app用户
            DistUser distUser = distUserService.findOne(userId);

            //查询当前用户购物车数量
            List<ShopCart> allGoods = shopCartService.getRepository().findAllByUserId(userId);
            if (allGoods == null || allGoods.size() == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择商品");
            }

            //算出购物车总共的金额
            BigDecimal price = new BigDecimal("0");
            List<OrderGoods> oderPropertyList = new ArrayList<>();
            StringBuilder shopInfo = new StringBuilder();
            for (ShopCart data : allGoods) {
                OrderGoods orderGoods = new OrderGoods();
                DistGoods distGoods = distGoodsService.findOne(data.getGoodsId());
                shopInfo.append(distGoods.getName() + (StringUtil.isEmpty(data.getProperty()) ? "" : "(" + data.getProperty() + ")") + "*" + data.getNum() + ";");
                if (distGoods == null) {//商品不存在时，清除掉购物车里的商品
                    shopCartService.getRepository().deleteByGoodsId(data.getGoodsId());
                    continue;
                }
                if (distGoods.getStatus() == 1) {//商品已下架
                    return new Resp().error(Resp.Status.PARAM_ERROR, distGoods.getName() + "，当前商品已下架");
                }
                price = price.add(distGoods.getPrice().multiply(new BigDecimal(data.getNum())));
                orderGoods.setImg(distGoods.getImg() == null ? "" : distGoods.getImg().split(",")[0]);
                orderGoods.setNum(data.getNum());
                orderGoods.setPrice(distGoods.getPrice());
                //orderGoods.setProperty(data.getProperty());
                orderGoods.setGoodsId(data.getGoodsId());
                orderGoods.setName(distGoods.getName());
                oderPropertyList.add(orderGoods);
            }
            if (price.compareTo(new BigDecimal("0")) == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请在购物车添加需要购买的商品");
            }
            //收货地址信息
            Address address = addressService.findOne(payDTO.getAddressId());
            //生成订单号
            String orderNub = DistUtil.orderNub();
            //Wallet wallet = walletService.getRepository().findByUserId(distUser.getId());
            ShopOrder shopOrder = new ShopOrder();
            shopOrder.setName(shopInfo.toString().substring(0, shopInfo.length() - 1));
            shopOrder.setOrderNumber(orderNub);
            shopOrder.setPayType(2); //订单类型（2：微信）
            shopOrder.setPhone(address.getPhone());
            shopOrder.setPlace(address.getPlace() + address.getDetailPlace());
            shopOrder.setUserId(userId);
            shopOrder.setServiceProviderId(distUser.getServiceProviderId());
            shopOrder.setPrice(price);
            shopOrder.setConsignee(address.getName());
            shopOrder.setStatus(0);
            distPayService.addOrder(shopOrder, oderPropertyList);

            DistWxConfig distWxConfig = distWxConfigReposotory.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);

            MyWxConfig myWxConfig = new MyWxConfig(distWxConfig.getAppId(),distWxConfig.getWxMchId(),distWxConfig.getWxAppKey(),distWxConfig.getWxCertPath());

            //请求预支付订单
            Map<String, String> result = getWxPayBody(request, orderNub, "设备", price, appAsync, shopSync, serviceId,distWxConfig,myWxConfig);
            Map<String, String> map = new HashMap<>();

            log.info("请求预支付订单(result)      " + result);
            WxMD5Util md5Util = new WxMD5Util();
            //返回APP端的数据
            //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
            map.put("appid", result.get("appid"));
            map.put("partnerid", result.get("mch_id"));
            map.put("prepayid", result.get("prepay_id"));
            map.put("package", "Sign=WXPay");
            map.put("noncestr", result.get("nonce_str"));
            map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//单位为秒
            // 这里不要使用请求预支付订单时返回的签名
            map.put("sign", md5Util.getSign(myWxConfig, map));

            log.info("map ========  "  + map.toString() );
            log.info("调起微信支付");

            return Resp.success(map, "调起微信支付");

        } catch (Exception e) {
            e.printStackTrace();
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
    }

    /**
     * 统一下单
     *
     * @param orderNub 订单号
     * @param subject  标题
     * @param price    金额
     * @param asyncUrl 异步回调路径
     * @param syncUrl  同步回调路径
     * @return
     */
    private Map<String, String> getWxPayBody(HttpServletRequest request, String orderNub, String subject, BigDecimal price, String asyncUrl, String syncUrl, String serviceId,DistWxConfig distWxConfig,MyWxConfig myWxConfig) throws Exception {

        WxMD5Util md5Util = new WxMD5Util();
        Map<String, String> returnMap = new HashMap<>();

        WXPay wxpay = new WXPay(myWxConfig);

        //额外参数
        Map<String, String> data = new HashMap<>();
        data.put("appid", distWxConfig.getAppId());
        data.put("mch_id", distWxConfig.getWxMchId());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", subject);
        //订单号
        data.put("out_trade_no", orderNub);
        //金额
        data.put("total_fee", price.multiply(new BigDecimal(100)).setScale(0).toEngineeringString());
        //(IP地址)
        String ipAddress = AddressUtil.getIpAddr(request);
        log.info("linxu(IP地址)  =======  "  +  ipAddress );
        //获取IP地址
        data.put("spbill_create_ip", ipAddress);
        //异步通知地址（请注意必须是外网）
        data.put("notify_url", syncUrl);
        //交易类型
        data.put("trade_type", "APP");
        String sign1 = md5Util.getSign(myWxConfig, data);
        data.put("sign", sign1);

        log.info("data（模型）  ===  " + data.toString());

        try {
            //使用官方API请求预付订单
            Map<String, String> response = wxpay.unifiedOrder(data);
            log.info("response（返回数据） -------   " + response);
            //获取返回码
            String returnCode = response.get("return_code");
            //若返回码为SUCCESS，则会返回一个result_code,再对该result_code进行判断
            //主要返回以下5个参数
            if (returnCode.equals("SUCCESS")) {
                String resultCode = response.get("result_code");
                returnMap.put("appid", response.get("appid"));
                returnMap.put("mch_id", response.get("mch_id"));
                returnMap.put("nonce_str", response.get("nonce_str"));
                returnMap.put("sign", response.get("sign"));
                //resultCode 为SUCCESS，才会返回prepay_id和trade_type
                if ("SUCCESS".equals(resultCode)) {
                    //获取预支付交易回话标志
                    returnMap.put("trade_type", response.get("trade_type"));
                    returnMap.put("prepay_id", response.get("prepay_id"));
                    return returnMap;
                } else {
                    //此时返回没有预付订单的数据
                    return returnMap;
                }
            } else {
                return returnMap;
            }
        } catch (Exception e) {
            System.out.println(e);
            //系统等其他错误的时候
        }
        return returnMap;

    }

    /**
     * 支付异步结果通知，我们在请求预支付订单时传入的地址
     * 官方文档 ：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3
     */
    @RequestMapping(value = "/shop/wx/notify", method = {RequestMethod.GET, RequestMethod.POST})
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);

        // 获取微信支付参数
        DistWxConfig distWxConfig = distWxConfigReposotory.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(distWxConfig)) {
            throw new BaseException("微信支付参数未配置");
        }

        MyWxConfig myWxConfig = new MyWxConfig(distWxConfig.getAppId(), distWxConfig.getWxMchId(), distWxConfig.getWxAppKey(), distWxConfig.getWxCertPath());

        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = distPayService.payBack(myWxConfig, resXml);
            log.info("完成微信购买商品result " + result);
            return result;
        } catch (Exception e) {
            log.info("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }
}
