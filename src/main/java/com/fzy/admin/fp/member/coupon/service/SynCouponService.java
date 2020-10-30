package com.fzy.admin.fp.member.coupon.service;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lb
 * @date 2019/6/11 10:28
 * @Description 逻辑返回前端需要的参数
 */
@Service
public class SynCouponService {

    public Map<String, String> synWX(String cardId, String personCouponId, String appId, String appSecret) {
        Map<String, String> synMap = new HashMap<>();
        synMap.put("cardId", cardId);
        //code是当前个人卡券的编号
        synMap.put("code", personCouponId);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        synMap.put("timestamp", timestamp);
        String api_ticket = WXUtil.getApiTicket(appId, appSecret);

        synMap.put("api_ticket", api_ticket);
        String nonce_str = RandomUtil.randomString(30);
        synMap.put("nonce_str", nonce_str);
        System.out.println("------------->>" + personCouponId + "---" + timestamp + "----" + api_ticket + "-----" + nonce_str + "-----" + cardId);
        String signature = WXUtil.getSignature(personCouponId, timestamp, api_ticket, nonce_str, cardId);
        synMap.put("signature", signature);
        return synMap;
    }
}
