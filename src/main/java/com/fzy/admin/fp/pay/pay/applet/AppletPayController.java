package com.fzy.admin.fp.pay.pay.applet;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.vo.JsPayResponse;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 21:44 2019/6/11
 * @ Description:
 **/
@RestController
@RequestMapping("/pay/pay/applet")
public class AppletPayController extends BaseContent {


    @GetMapping("/get_prepay_info")
    public PayRes getPrepayInfo() throws Exception {
        /**
         * 初始化配置
         */
        MyWxConfig config = initWxConfig();
        // 构建通用参数
        Map<String, String> params = new TreeMap<>();
        // 额外参数
        params.put("out_trade_no", RandomUtil.randomNumbers(20)); // 订单号
        params.put("total_fee", new BigDecimal("1").multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString()); // 价格
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("sub_mch_id", "1534197951");
        params.put("body", "微信js支付");
        params.put("openid", "o7dyp5SuHnE5dnP1Da8S_t2cY3dM");
        params.put("notify_url", "www.baidu.com");
        params.put("trade_type", "JSAPI");
        Map<String, String> response = new WXPay(config).unifiedOrder(params);
        if (WXPayConstants.FAIL.equals(response.get("return_code"))) {
            return new PayRes(response.get("return_msg"), PayRes.ResultStatus.FAIL);
        }
        if (WXPayConstants.FAIL.equals(response.get("result_code"))) {
            return new PayRes(response.get("err_code_des"), PayRes.ResultStatus.FAIL);
        }

        Map<String, Object> map = new HashMap<>();
        String prepay_id = response.get("prepay_id");
        // ******************************************
        //
        //  前端调起微信支付必要参数，需要重新签名
        //
        // ******************************************
        Map<String, String> wxPayMap = new HashMap<>();
        String packages = "prepay_id=" + prepay_id;
        wxPayMap.put("appId", "wxfa776fa70d7c9534");
        wxPayMap.put("timeStamp", String.valueOf(new Date().getTime() / 1000L));
        wxPayMap.put("nonceStr", WXPayUtil.generateNonceStr());
        wxPayMap.put("package", packages);
        wxPayMap.put("signType", "MD5");
        // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
        String sign = WXPayUtil.generateSignature(wxPayMap, "f58dcd6e014c179b80ee69ac80f669f8");
        // 返回给前端的数据格式
        JsPayResponse jsPayResponse = new JsPayResponse();
        jsPayResponse.setAppId("wxfa776fa70d7c9534");
        jsPayResponse.setTimeStamp(wxPayMap.get("timeStamp"));
        jsPayResponse.setNonceStr(wxPayMap.get("nonceStr"));
        jsPayResponse.setPkg(wxPayMap.get("package"));
        jsPayResponse.setSignType("MD5");
        jsPayResponse.setPaySign(sign);
        jsPayResponse.setOrderNumber(RandomUtil.randomNumbers(20));
        jsPayResponse.setPrice(new BigDecimal("1").multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString());
        map.put("jsPayResponse", jsPayResponse);
        return new PayRes("预下单成功", PayRes.ResultStatus.SUCCESS, map);
    }

    public MyWxConfig initWxConfig() throws Exception {
        // 通过商户id获取微信支付配置参数
        return new MyWxConfig("wxfa776fa70d7c9534", "1531391831", "e9a9230d05e7cba245ae2f0de2020356",
                "WorkDir\\Users\\Administrator\\Desktop\\LY_PAY\\trunk\\lysj-pay\\src\\main\\resources\\apiclient_cert_1125388591497887744.p12");
    }
}
