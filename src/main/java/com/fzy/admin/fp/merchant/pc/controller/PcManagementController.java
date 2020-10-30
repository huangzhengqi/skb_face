package com.fzy.admin.fp.merchant.pc.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.merchant.management.AuthConstant;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.AuthConstant;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-29 14:26
 * @description pc商户端
 */
@RestController
@RequestMapping("/merchant/admin/pc")
@Api(value = "PcManagementController", tags = "pc商户端")
public class PcManagementController extends BaseContent {

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantService merchantService;

    @Value("${secret.key}")
    private String key;
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;

    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "登陆")
    public Resp<Map<String, Object>> login(String username, String password) {
        MerchantUser user = merchantUserService.findByUsername(username);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户名不存在");
        }
        if (user.getStatus().equals(MerchantUser.Status.DISABLE.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户已被禁用");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
        }
        Merchant merchant = merchantService.findOne(user.getMerchantId());
        if(merchant.getStatus().equals(Merchant.Status.GIFT.getCode())){
            return new Resp().error(Resp.Status.PARAM_ERROR, "该商户未签约");
        }
        String token = JWT.create().withIssuer(AuthConstant.AUTH_NAME).withSubject(user.getId())
                .withClaim("merchantId", user.getMerchantId())
                .withClaim(CommonConstant.SERVICE_PROVIDERID, user.getServiceProviderId())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(key));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Resp.success(map, "登录成功");
    }


}
