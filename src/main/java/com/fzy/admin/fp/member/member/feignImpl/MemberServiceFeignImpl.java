package com.fzy.admin.fp.member.member.feignImpl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.sdk.member.feign.MemberServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by zk on 2019-05-19 11:23
 * @description
 */
@Service
@Slf4j
public class MemberServiceFeignImpl implements MemberServiceFeign {
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;
    @Value("${secret.tokenRefresh}")
    private int tokenRefresh;
    @Resource
    private MemberService memberService;

    @Override
    public String getIss() {
        return MemberConstant.AUTH_NAME;
    }

    @Override
    public Resp authentication(String token, String path,String pageUrl) {
        Map<String, Object> payloadMap = JwtUtil.getPayloadMap(token);
        String userId = payloadMap.get("sub").toString();
        String iss = payloadMap.get("iss").toString();
        if (ParamUtil.isBlank(userId) || ParamUtil.isBlank(iss) || !MemberConstant.AUTH_NAME.equals(iss)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token信息异常,请重新登录");
        }
        Member member = memberService.findOne(userId);
        if (member == null) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "查无该token信息,请重新登录");
        }
        String key = member.getMemberNum();
        DecodedJWT jwt = JwtUtil.verifier(token, key);
        //匹配路径是否符合用户权限

        //判断用户token是否需要刷新
        Date expires = jwt.getExpiresAt();
        Date now = new Date();
        if ((now.getTime() - expires.getTime()) < tokenRefresh * 60 * 1000) {
            token = JwtUtil.copyToken(payloadMap, tokenExpiration, key);
        }
        return Resp.success(token);
    }

    @Override
    public String findByOpenIdAndMerchantId(String officialOpenId, String merchantId) {
        Member member = memberService.getRepository().findByOfficialOpenIdAndMerchantIdAndDelFlag(officialOpenId, merchantId, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(member)) {
            return null;
        }
        return member.getId();
    }
}
