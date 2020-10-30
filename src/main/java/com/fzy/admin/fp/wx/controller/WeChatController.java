package com.fzy.admin.fp.wx.controller;


import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.wx.service.WeChatService;
import com.fzy.admin.fp.wx.util.MessageUtil;
import com.fzy.admin.fp.wx.util.XmlUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/8/27 19:56
 * @Version 1.0
 * @description 微信公众号
 */
@Controller
@Slf4j
@RequestMapping("/wx/wechant/api")
@Api(value = "微信公众号",tags = {"微信公众号"})
public class WeChatController extends BaseContent {

    /**
     * 与接口配置信息中的Token要一致
     */
    private static String WECHAT_TOKEN = "WECHAT_TOKEN_FKbaImfORLteeHWX6x";

    @Autowired
    private WeChatService weChatService;

    /**
     * 验证TOKEN
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value ="/checkToken",method=RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(WECHAT_TOKEN.length());
        log.info("========checkToken Controller========= ");

        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                    log.info("========checkToken success ========= ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                log.error("========checkToken failed========= ");
            }
        }else {
            log.error("========checkToken failed:Only support Get Method =========");
        }
    }

    /**
     * 接收POST 审核事件推送
     *
     * @param req
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    @ResponseBody
    public void responseEvent(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String message = "success";
        log.info("消息管理,微信请求");
        try {
            //把微信返回的xml信息转义成map
            Map<String, String> map = XmlUtil.xmlToMap(req);
            log.info("审核事件推送  ======== ：{}", map.toString());
            //开发者微信号
            String toUserName = map.get("ToUserName");
            //发送方帐号（一个OpenID）
            String fromUserName = map.get("FromUserName");
            //消息类型
            String msgType = map.get("MsgType");
            //事件类型
            String eventType = map.get("Event");
            //卡券ID
            String cardId = map.get("CardId");
            //审核不通过原因
            String refuseReason = map.get("RefuseReason");
            //如果为事件类型
            if (MessageUtil.MSGTYPE_EVENT.equals(msgType)) {
                if (MessageUtil.MESSAGE_CARD_PASS.equals(eventType)) {
                    log.info("处理生成的卡券通过审核");
                    message = weChatService.cardPassCheck(cardId);
                } else if (MessageUtil.MESSAGE_CARD_NOT_PASS.equals(eventType)) {
                    log.info("处理生成的卡券未通过审核");
                    message = weChatService.cardNotPassCheck(cardId,refuseReason);
                } else if (MessageUtil.MESSAGE_USER_GET_CAR.equals(eventType)) {
                    log.info("用户领取卡券");
                    String token = weChatService.userGetCard(map);
                    log.info("token =============== {}",token);
                } else if (null != map.get("EventKey")) {
                }
            }
            out = response.getWriter();
            out.write("success");
            if (!"success".equals(message)) {
                String message2 = new String(message.getBytes(), "UTF-8");
                System.out.println("---------------------------message=" + message);
                out.write(message2);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            log.info("返回消息{}" + message);
            out.flush();
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { WECHAT_TOKEN, timestamp, nonce };
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
