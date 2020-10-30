package com.fzy.admin.fp.distribution.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.util.DateUtil;
import com.fzy.admin.fp.common.util.StringToHex;
import com.fzy.admin.fp.common.util.SunminUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.dto.PushContentDTO;
import com.fzy.admin.fp.distribution.app.dto.VoiceDTO;
import com.fzy.admin.fp.file.util.md5.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author fyz123
 * @create 2020/7/14 11:02
 * @Description: sunmin 云打印Controller
 */
@RestController
@RequestMapping("/dist/app/print")
@Api(value="AppPrintSunminController", tags={"SUNMIN云打印接口相关"})
public class AppPrintSunminController extends BaseContent {

    @Value("${sunmin.appId}")
    private String appId;

    @Value("${sunmin.appKey}")
    private String appKey;

    @PostMapping("/printerAdd")
    @ApiOperation(value = "打印机绑定接口", notes = "打印机绑定接口")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="msn",dataType="String",value="设备号"),@ApiImplicitParam(paramType="query",name="shopId",dataType="String",value="绑定商户Id")})
    public JSONObject printerAdd(String msn,String shopId){
        //key-value参数
        String param= SunminUtil.getSignToken(printParam(msn,shopId));
        String printerUrl="https://openapi.sunmi.com//v1/printer/printerAdd";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }


    @PostMapping("/printerUnBind")
    @ApiOperation(value = "打印机解绑接口", notes = "打印机解绑接口")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="msn",dataType="String",value="设备号ID"),
            @ApiImplicitParam(paramType="query",name="shopId",dataType="String",value="商户Id ，需要与绑定时一致,必传，否则无法将该打印机从该店铺下解绑",required = true)})
    public JSONObject printerUnBind(String msn,String shopId){
        //key-value参数
        String param= SunminUtil.getSignToken(printParam(msn,shopId));
        String printerUrl="https://openapi.sunmi.com//v1/printer/printerUnBind";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    @PostMapping("/queryBindMachine")
    @ApiOperation(value = "查询店铺下已绑定设备状态", notes = "查询店铺下已绑定设备状态")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="shopId",dataType="String",value="合作伙伴商户Id ，需要与绑定时一致",required = true)})
    public JSONObject queryBindMachine(String shopId){
        //key-value参数
        String param= SunminUtil.getSignToken(printParam("",shopId));
        String printerUrl="https://openapi.sunmi.com//v1/machine/queryBindMachine";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    @PostMapping("/pushVoice")
    @ApiOperation(value = "语音内容推送", notes = "语音内容推送")
    public JSONObject pushVoice(VoiceDTO voiceDTO){
        Map<String,Object> voice=new HashMap<>();
        voice.put("app_id",appId);
        voice.put("msn",voiceDTO.getMsn());
        voice.put("call_content",voiceDTO.getCallContent());
        voice.put("call_url",voiceDTO.getCallUrl());
        voice.put("expTimestamp",voiceDTO.getExpTimestamp());
        voice.put("cycle",voiceDTO.getCycle());
        voice.put("delay",voiceDTO.getDelay());
        //UNIX 时间搓
        String timestamp=DateUtil.getTinestamp();
        voice.put("timestamp", timestamp+appKey);
        //验签加密
        String token=SunminUtil.getSignToken(voice);
        String sign=MD5Utils.getMD5String(token).toUpperCase();
        voice.put("sign",sign);
        voice.put("timestamp",timestamp);
        //传参方式为key-value方式转换
        String param= SunminUtil.getSignToken(voice);
        String printerUrl="https://openapi.sunmi.com//v1/printer/pushVoice";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    @PostMapping("/pushContent")
    @ApiOperation(value = "推送订单详情", notes = "推送订单详情")
    public JSONObject pushContent(PushContentDTO pushContentDTO){
        Map<String,Object> voice=new HashMap<>();
        voice.put("app_id",appId);
        voice.put("msn",pushContentDTO.getMsn());
        voice.put("pushId", pushContentDTO.getPushId());
        StringBuilder  stringBuffer=new StringBuilder();
        //TODO 打印内容格式
        stringBuffer .append("\t菜鸟驿站\n");
        stringBuffer .append("台号\t"+002+"工号"+01);
        stringBuffer .append("时间\t"+ DateUtil.getTimeFormat(new Date())+"\n");
        String sutf8= StringToHex.toUtf8(stringBuffer.toString());
        String hex= StringToHex.str2HexStr(sutf8);
        voice.put("orderData",hex);
        voice.put("voiceCnt",pushContentDTO.getVoiceCnt()+appKey);
        voice.put("orderCnt",pushContentDTO.getOrderCnt());
        voice.put("orderType",pushContentDTO.getOrderType());
        voice.put("voice",pushContentDTO.getVoice());
        //UNIX 时间搓
        String timestamp=DateUtil.getTinestamp();
        voice.put("timestamp", timestamp);
        //验签加密
        String sign=MD5Utils.getMD5String(SunminUtil.getSignToken(voice)).toUpperCase();
        voice.put("sign",sign);
        voice.put("voiceCnt",pushContentDTO.getVoiceCnt());
        //key-value方式转换
        String param= SunminUtil.getSignToken(voice);
        String printerUrl="https://openapi.sunmi.com//v1/printer/pushContent";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    @PostMapping("/clearPrintList")
    @ApiOperation(value = "清空待打印队列", notes = "清空待打印队列")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="msn",dataType="String",value="设备号Id",required = true)})
    public JSONObject clearPrintList(String msn){
        //key-value参数
        String param= SunminUtil.getSignToken(printParam(msn,""));
        String printerUrl="https://openapi.sunmi.com//v1/printer/clearPrintList";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    @PostMapping("/getPrintStatus")
    @ApiOperation(value = "查询订单打印状态", notes = "查询订单打印状态")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="msn",dataType="String",value="设备号Id",required = true),
            @ApiImplicitParam(paramType="query",name="pushId",dataType="String",value="订单Id,查询打印票据ID。为避免造成服务器压力，请勿频繁调用此接口，相同ID查询建议间隔1分钟以上",required = true)})
    public JSONObject getPrintStatus(String msn,String pushId){
        Map<String,Object> voice=new HashMap<>();
        voice.put("app_id",appId);
        voice.put("msn",msn);
        String timestamp=DateUtil.getTinestamp();
        voice.put("timestamp",timestamp+appKey);
        voice.put("pushId","1181894961781121032");
        //验签加密
        String sign=MD5Utils.getMD5String(SunminUtil.getSignToken(voice)).toUpperCase();
        voice.put("sign",sign);
        voice.put("timestamp",timestamp);
        //key-value参数
        String param= SunminUtil.getSignToken(voice);
        String printerUrl="https://openapi.sunmi.com//v1/printer/clearPrintList";
        String result= SunminUtil.sendXmlPost(printerUrl,param);
        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }


    /**
     * 公共加密方法
     * @param msn 设备号
     * @param shopId 商户ID
     * @return
     */
    public  Map<String,Object> printParam(String msn,String shopId){
        Map<String,Object> map=new HashMap<>();
        map.put("app_id",appId);
        map.put("msn",msn);
        map.put("shop_id",shopId);
        //UNIX 时间搓
        String timestamp=DateUtil.getTinestamp();
        map.put("timestamp", timestamp+appKey);
        //验签加密
        String sign=MD5Utils.getMD5String(SunminUtil.getSignToken(map)).toUpperCase();
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        return map;
    }


}
