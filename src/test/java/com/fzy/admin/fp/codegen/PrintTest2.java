package com.fzy.admin.fp.codegen;

import com.fzy.admin.fp.common.util.SunminUtil;
import com.fzy.admin.fp.file.util.md5.MD5Utils;

import java.util.*;

/**
 * @Author fyz lzy
 * @create 2020/7/13 14:47
 * @Description: 云打印测试接口
 */
public class PrintTest2 {
    public static void main(String[] args)throws Exception {
        String  appid="9b10609935be40c7acba7a715057547e";
        String appkey="826305fb1b594006832c74f87df6bc68";
        String msn="N301P98P40050";
       //拼接参数
        String shopId="1193064394160836608";
        //Unix时间搓
        String timestamp=Long.toString(System.currentTimeMillis()/1000L);
        //绑定
        String url="v1/printer/printerAdd?";
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",appid);
        map.put("msn",msn);
        map.put("shop_id",shopId);
        map.put("timestamp",timestamp+appkey);
        //生成sign签名
//        String sign= MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
//        map.put("timestamp",timestamp);
//        map.put("sign",sign);
//        //转换key-value 形式
//        String param= SunminUtil.getSignToken(map);
//        System.out.println(param);
        //绑定
//        String printerResult= printerAdd(url,param);
//        System.out.println("绑定"+printerResult);
        //查询设备状态
//         url="v1/machine/queryBindMachine?";
//         String queryResult= queryBindMachine(url,param);
//         System.out.println(queryResult);
        //语音内容推送
        url="v1/printer/pushVoice";
        String pushResult= pushVoice(url,map,timestamp);
        System.out.println(pushResult);

        //解绑
//        url="/v1/printer/printerUnBind?";
//        String printerUnResult= printerUnBind(url,param);
//        System.out.println(printerUnResult);
    }


    /**
     * 打印机绑定接口
     * @param url
     * @param param
     * @return
     */
    public static String printerAdd(String url,String param){
        String hurl="https://openapi.sunmi.com/"+url;
        String result= SunminUtil.sendXmlPost(hurl,param);
        return result;
    }

    /**
     * 打印机解绑接口
     * @param param
     * @return
     */
   public static String printerUnBind(String url,String param){
       String hurl="https://openapi.sunmi.com/"+url;
       String result= SunminUtil.sendXmlPost(hurl,param);
        return  result;
   }

    /**
     * 查询店铺下已绑定设备状态
     * @return
     */
   public static String queryBindMachine(String url,String param){
       String hurl="https://openapi.sunmi.com/"+url;
       String result= SunminUtil.sendXmlPost(hurl,param);
       return  result;
   }

    /**
     * 语音内容推送
     * @param url
     * @param map
     * @return
     */
   public static String pushVoice(String url,Map<String,Object> map,String timestamp){
        String hurl="https://openapi.sunmi.com/"+url;
        System.out.println(hurl);
        map.put("call_content","支付宝到账一亿元");
        map.put("cycle","999");
        map.put("call_url","");
        map.put("expTimestamp",1594890989);
        map.put("delay","2");
       String sign=MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
        map.put("timestamp",timestamp);
        map.put("sign",sign);
        String param= SunminUtil.getSignToken(map);
        System.out.println(param);
        String result= SunminUtil.sendXmlPost(hurl,param);
        return  result;
   }
}
