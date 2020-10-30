package com.fzy.admin.fp.codegen;

import com.fzy.admin.fp.common.util.DateUtil;
import com.fzy.admin.fp.common.util.StringToHex;
import com.fzy.admin.fp.common.util.SunminUtil;
import com.fzy.admin.fp.file.util.md5.MD5Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author fyz lzy
 * @create 2020/7/13 14:47
 * @Description: 推送订单详情
 */
public class PrintTest {
    public static void main(String[] args) throws Exception{
       //清空待打印队列
        //clearPrintList();
        //推送订单详情
        pushContent();
        //查询订单状态
        //getPrintStatus();
       //System.out.println(StringToHex.hexStringToString("1b2130e58d97e59bbde8b685e5b8821b21000ae58fb0e58fb73a303120202020202020e5b7a5e58fb73a30310ae697b6e997b43a31323a34353a32380ae58d95e58fb73a30303535343738393731333538350ae59586e59381e5908de7a7b020202020202020202020202020e58d95e4bbb72ae695b0e9878f202020202020e98791e9a29d0a2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d0ae799bee4ba8be588a9e7b2bee8a385e7b396e69e9c2020202020202020372e30302a312020202020202020372e30300ae697bae697bae99baae9a5bc2020202020202020202020202020382e30302a312020202020202020382e30300ae58fafe58fa3e58fafe4b9902020202020202020202020202020322e35302a312020202020202020322e35300ae5969ce4b98be69c97e69e9ce586bb202020202020202020202031302e30302a312020202020202031302e30300ae5b7a7e5858be58a9be9a5bce5b9b2202020202020202020202031302e30302a312020202020202031302e30300a2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d0ae680bbe4bbb6e695b02020342e303020202020202020202020e680bbe8aea1524d4220202020202032372e30300a0a1b2110202020202020e8b0a2e8b0a2e683a0e9a1bee6aca2e8bf8ee4b88be6aca1e58589e4b8b41b21000a"));
    }

    /**
     * 推送订单详情
     * @return
     */
    public static String pushContent()throws Exception{
        String  appid="9b10609935be40c7acba7a715057547e";
        String appkey="826305fb1b594006832c74f87df6bc68";
        String msn="N301P98P40050";
        //拼接参数
        String shopId="1193064394160836608";
        String timestamp=Long.toString(System.currentTimeMillis()/1000L);
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",appid);
        map.put("msn",msn);
        map.put("timestamp",timestamp);
        map.put("pushId", "1181894961781121053");
        StringBuilder  stringBuffer=new StringBuilder();
        stringBuffer .append("\t菜鸟驿站\n");
        stringBuffer .append("台号\t"+002+"工号"+01);
        stringBuffer .append("时间\t"+ DateUtil.getTimeFormat(new Date())+"\n");
        String sutf8=StringToHex.toUtf8(stringBuffer.toString());
        String hex= StringToHex.str2HexStr(sutf8);
        map.put("orderData",hex);
        map.put("voiceCnt","2"+appkey);
        map.put("orderCnt","1");
        map.put("orderType","1");
        map.put("voice","你有新订单,请注意查收");
        String sign=MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        map.put("voiceCnt","2");
        //打印内容转16进制
        String param= SunminUtil.getSignToken(map);
        System.out.println(param);
        String hurl="https://openapi.sunmi.com//v1/printer/pushContent";
        String result= SunminUtil.sendXmlPost(hurl,param);
        System.out.println("推送订单"+result);
        return result;
    }

    /**
     * 清空待打印队列
     * @return
     * @throws Exception
     */
    public static String clearPrintList()throws Exception{
        String  appid="9b10609935be40c7acba7a715057547e";
        String appkey="826305fb1b594006832c74f87df6bc68";
        String msn="N301P98P40050";
        //拼接参数
//String shopId="1193064394160836608";
        String timestamp=Long.toString(System.currentTimeMillis()/1000L);
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",appid);
        map.put("msn",msn);
        map.put("shop_id","");
        map.put("timestamp",timestamp+appkey);
        String sign= MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        String param= SunminUtil.getSignToken(map);
        String hurl="https://openapi.sunmi.com/v1/printer/clearPrintList";
        System.out.println(hurl);
        System.out.println(param);
        String result= SunminUtil.sendXmlPost(hurl,param);
        System.out.println("打印队列"+result);
        return result;
    }


    /**
     * 查询订单打印状态
     * @return
     */
    public static String getPrintStatus(){
        String  appid="9b10609935be40c7acba7a715057547e";
        String appkey="826305fb1b594006832c74f87df6bc68";
        String msn="N301P98P40050";
        //拼接参数
        String shopId="1193064394160836608";
        String timestamp=Long.toString(System.currentTimeMillis()/1000L);
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",appid);
        map.put("msn",msn);
        map.put("shop_id",shopId);
        map.put("timestamp",timestamp+appkey);
        map.put("pushId","1181894961781121032");
        String sign = MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        String param= SunminUtil.getSignToken(map);
        String url="https://openapi.sunmi.com/v1/printer/getPrintStatus";
        String result= SunminUtil.sendXmlPost(url,param);
        System.out.println("订单状态"+result);
        return result;
    }

}
