package com.fzy.admin.fp.member;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.fzy.admin.fp.member.member.dto.OpenIdAndKeyDTO;
import com.fzy.admin.fp.member.member.vo.AppletMemberCardCashVo;
import com.fzy.admin.fp.member.member.dto.OpenIdAndKeyDTO;
import com.fzy.admin.fp.member.member.vo.AppletMemberCardCashVo;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;

/**
 * @author Created by zk on 2019-05-15 21:14
 * @description
 */
public interface MemberConstant {
    Cache<String, String> PHONE_VERIFICATION_CODE_CACHE = CacheUtil.newTimedCache(15 * 60 * 1000);

    // 会员付款码生成缓存，1m10s过期
    Cache<String, String> MEMBER_PAY_CODE_CACHE = CacheUtil.newTimedCache(60 * 1000 + 10000);

    Cache<String, AppletConfigVO> MERCHANT_APPKEY_CACHE = CacheUtil.newLFUCache(1500);

    Cache<String, OpenIdAndKeyDTO> MEMBER_CODE_KEY_CACHE = CacheUtil.newTimedCache(30 * 60 * 1000);

    String AUTH_NAME = "member";
    /**
     * 2个小时
     */
    Cache<String, AppletMemberCardCashVo> MEMBERCARD_APPLET_CACHE = CacheUtil.newTimedCache(2 * 60 * 60 * 1000);
}
