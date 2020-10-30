/*
package com.fzy.admin.fp;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.pay.pay.util.FuiouHttpUtils;
import com.fzy.admin.fp.pay.pay.util.FuiouUtils;
import com.github.wxpay.sdk.WXPayUtil;
import com.fzy.admin.fp.pay.pay.util.FuiouHttpUtils;
import com.fzy.admin.fp.pay.pay.util.FuiouUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * @author Created by wtl on 2019-06-23 17:06
 * @description 富友支付测试
 *//*

@Slf4j
public class FuiouPayTest {

    //商户私钥
    public final String INS_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJgAzD8fEvBHQTyxUEeK963mjziM\n" +
            "WG7nxpi+pDMdtWiakc6xVhhbaipLaHo4wVI92A2wr3ptGQ1/YsASEHm3m2wGOpT2vrb2Ln/S7lz1\n" +
            "ShjTKaT8U6rKgCdpQNHUuLhBQlpJer2mcYEzG/nGzcyalOCgXC/6CySiJCWJmPyR45bJAgMBAAEC\n" +
            "gYBHFfBvAKBBwIEQ2jeaDbKBIFcQcgoVa81jt5xgz178WXUg/awu3emLeBKXPh2i0YtN87hM/+J8\n" +
            "fnt3KbuMwMItCsTD72XFXLM4FgzJ4555CUCXBf5/tcKpS2xT8qV8QDr8oLKA18sQxWp8BMPrNp0e\n" +
            "pmwun/gwgxoyQrJUB5YgZQJBAOiVXHiTnc3KwvIkdOEPmlfePFnkD4zzcv2UwTlHWgCyM/L8SCAF\n" +
            "clXmSiJfKSZZS7o0kIeJJ6xe3Mf4/HSlhdMCQQCnTow+TnlEhDTPtWa+TUgzOys83Q/VLikqKmDz\n" +
            "kWJ7I12+WX6AbxxEHLD+THn0JGrlvzTEIZyCe0sjQy4LzQNzAkEAr2SjfVJkuGJlrNENSwPHMugm\n" +
            "vusbRwH3/38ET7udBdVdE6poga1Z0al+0njMwVypnNwy+eLWhkhrWmpLh3OjfQJAI3BV8JS6xzKh\n" +
            "5SVtn/3Kv19XJ0tEIUnn2lCjvLQdAixZnQpj61ydxie1rggRBQ/5vLSlvq3H8zOelNeUF1fT1QJA\n" +
            "DNo+tkHVXLY9H2kdWFoYTvuLexHAgrsnHxONOlSA5hcVLd1B3p9utOt3QeDf6x2i1lqhTH2w8gzj\n" +
            "vsnx13tWqg==";
    //商户公钥
    public final String INS_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYAMw/HxLwR0E8sVBHivet5o84jFhu58aYvqQzHbVompHOsVYYW2oqS2h6OMFSPdgNsK96bRkNf2LAEhB5t5tsBjqU9r629i5/0u5c9UoY0ymk/FOqyoAnaUDR1Li4QUJaSXq9pnGBMxv5xs3MmpTgoFwv+gskoiQliZj8keOWyQIDAQAB";


    //富友公钥  用于验签
    public final String FY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDz2fCOYaaU6sztFql4cOmiFRq2LRk1XuGfrJnMFa09QMXMXOEn9YNYC44zV1AE/q9b0BKGbM74YPoge/7qsW+Heao76Drv6HujP+rXLFbsXT5f9rcID2GCzDc+DXjb+NfwSa8vS9KJ3dau2xm87zpjdQ9zER6VH4UcZTgj7LbzgwIDAQAB";


    //机构号
    public String ins_cd = "08A9999999";

    //商户号
    public String mchnt_cd = "0002900F0370542";//0002900F0370542

    public Map<String, String> buildMicroParam() {
        Map<String, String> map = new HashMap<>();
        map.put("version", "1");
        map.put("ins_cd", ins_cd);
        map.put("mchnt_cd", mchnt_cd);
        map.put("term_id", "12345678");
        map.put("random_str", WXPayUtil.generateNonceStr());
        map.put("sign", "");
        map.put("order_type", "WECHAT");
        map.put("goods_des", "条码支付测试");
        map.put("goods_detail", "");
        map.put("addn_inf", "");
        SimpleDateFormat sdf_no = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar calendar = Calendar.getInstance();
        map.put("mchnt_order_no", sdf_no.format(calendar.getTime()) + (int) (new Random().nextDouble() * 100000));
        map.put("curr_type", "");
        map.put("order_amt", "1");
        map.put("term_ip", "127.0.0.1");
        SimpleDateFormat sdf_ts = new SimpleDateFormat("yyyyMMddHHmmss");
        map.put("txn_begin_ts", sdf_ts.format(calendar.getTime()));
        map.put("goods_tag", "");
        map.put("auth_code", "135155172417875526");
        map.put("sence", "1");
        map.put("reserved_sub_appid", "");
        map.put("reserved_limit_pay", "");
        return map;
    }


    */
/**
     * @author Created by wtl on 2019/6/23 17:06
     * @Description 富友条码支付
     *//*

    @Test
    public void micropay() throws Exception {
        Map<String, String> reqs = buildMicroParam();
        Map<String, String> nvs = new HashMap<>();
        String sign = FuiouUtils.getSign(reqs, INS_PRIVATE_KEY);
        reqs.put("sign", sign);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Iterator it = reqs.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = reqs.get(key);
            root.addElement(key).addText(value);
        }
        // 待编码字符串
        String reqBody = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>" + doc.getRootElement().asXML();
        // 编码后字符串
        reqBody = URLEncoder.encode(reqBody, "GBK");
        nvs.put("req", reqBody);
        StringBuffer result = new StringBuffer("");
        FuiouHttpUtils.post("https://fundwx.fuiou.com/micropay", nvs, result);
        String rspXml = URLDecoder.decode(result.toString(), "GBK");
        //响应报文验签
        Map<String, String> resMap = FuiouUtils.xmlStr2Map(rspXml);
        System.out.println("==============================响应报文转map==============================\r\n" + resMap);
        String str = resMap.get("sign");
        System.out.println("验签结果：" + FuiouUtils.verifySign(resMap, str, FY_PUBLIC_KEY));
        // result_code = "030010" 支付中，000000成功
    }

    */
/**
     * @author Created by wtl on 2019/6/23 20:09
     * @Description 支付查询
     *//*

    @Test
    public void query() throws Exception {
        Map<String, String> reqs = new HashMap<>();
        reqs.put("version", "1");
        reqs.put("ins_cd", ins_cd);
        reqs.put("mchnt_cd", mchnt_cd);
        reqs.put("term_id", "88888888");
        reqs.put("order_type", "WECHAT");// ALIPAY,WECHAT
        reqs.put("mchnt_order_no", "2019062320294169032120"); // 订单号
        reqs.put("random_str", WXPayUtil.generateNonceStr());
        Map<String, String> nvs = new HashMap<>();
        String sign = FuiouUtils.getSign(reqs, INS_PRIVATE_KEY);
        reqs.put("sign", sign);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Iterator it = reqs.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = reqs.get(key);
            root.addElement(key).addText(value);
        }
        // 待编码字符串
        String reqBody = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>" + doc.getRootElement().asXML();
        // 编码后字符串
        reqBody = URLEncoder.encode(reqBody, "GBK");
        nvs.put("req", reqBody);
        StringBuffer result = new StringBuffer("");
        FuiouHttpUtils.post("https://fundwx.fuiou.com/commonQuery", nvs, result);
        String rspXml = URLDecoder.decode(result.toString(), "GBK");
        //响应报文验签
        Map<String, String> resMap = FuiouUtils.xmlStr2Map(rspXml);
        System.out.println("==============================响应报文转map==============================\r\n" + resMap);
        String str = resMap.get("sign");
        System.out.println("验签结果：" + FuiouUtils.verifySign(resMap, str, FY_PUBLIC_KEY));
        // trans_stat: SUCCESS成功，USERPAYING支付中，PAYERROR失败
    }

    */
/**
     * @author Created by wtl on 2019/6/23 20:32
     * @Description 退款
     *//*

    @Test
    public void refund() throws Exception {
        Map<String, String> reqs = new HashMap<>();
        reqs.put("version", "1.0");
        reqs.put("ins_cd", ins_cd);
        reqs.put("mchnt_cd", mchnt_cd);
        reqs.put("term_id", "88888888");
        reqs.put("order_type", "WECHAT");// ALIPAY,WECHAT
        reqs.put("mchnt_order_no", "2019062320294169032120"); // 订单号
        reqs.put("random_str", WXPayUtil.generateNonceStr());
        reqs.put("refund_order_no", System.currentTimeMillis() + RandomUtil.randomNumbers(8));
        reqs.put("total_amt", "1");// 总金额
        reqs.put("refund_amt", "1");
        Map<String, String> nvs = new HashMap<>();
        String sign = FuiouUtils.getSign(reqs, INS_PRIVATE_KEY);
        reqs.put("sign", sign);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Iterator it = reqs.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = reqs.get(key);
            root.addElement(key).addText(value);
        }
        // 待编码字符串
        String reqBody = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?>" + doc.getRootElement().asXML();
        // 编码后字符串
        reqBody = URLEncoder.encode(reqBody, "GBK");
        nvs.put("req", reqBody);
        StringBuffer result = new StringBuffer("");
        FuiouHttpUtils.post("https://fundwx.fuiou.com/commonRefund", nvs, result);
        String rspXml = URLDecoder.decode(result.toString(), "GBK");
        //响应报文验签
        Map<String, String> resMap = FuiouUtils.xmlStr2Map(rspXml);
        System.out.println("==============================响应报文转map==============================\r\n" + resMap);
        String str = resMap.get("sign");
        System.out.println("验签结果：" + FuiouUtils.verifySign(resMap, str, FY_PUBLIC_KEY));
        // trans_stat: SUCCESS成功，USERPAYING支付中，PAYERROR失败
    }


}
*/
