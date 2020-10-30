package com.fzy.admin.fp.merchant.management.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.service.MemberCardService;
import com.fzy.admin.fp.merchant.management.AuthConstant;
import com.fzy.admin.fp.merchant.management.VerifyCodeApplicationRunner;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.auth.feign.MessageServiceFeign;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by zk on 2019-01-02 17:53
 * @description
 */
@RestController
@RequestMapping("/merchant/admin")
@Slf4j
public class MerchantManagementController extends BaseContent {

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MessageServiceFeign messageServiceFeign;

    @Resource
    private MerchantService merchantService;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Resource
    private MemberCardService memberCardService;

    @Value("${secret.key}")
    private String key;
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;

    @PostMapping("/login")
    public Resp login(String username, String password) {
        MerchantUser user = merchantUserService.findByUsername(username);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户名不存在");
        }
        if (user.getStatus().equals(MerchantUser.Status.DISABLE.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户已被禁用");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
        }
        Merchant merchant = merchantService.findOne(user.getMerchantId());
        if (merchant.getStatus().equals(Merchant.Status.GIFT.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该商户未签约");
        }
        String token = JWT.create().withIssuer(AuthConstant.AUTH_NAME).withSubject(user.getId())
                .withClaim("merchantId", user.getMerchantId())
                .withClaim(CommonConstant.SERVICE_PROVIDERID, user.getServiceProviderId())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(key));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userType", user.getUserType());
        map.put("name", user.getName());
        map.put("phone", merchant.getPhone());
        map.put("serviceId", user.getServiceProviderId());
        map.put("useId", user.getId());

        return Resp.success(map, "登录成功");
    }


    /*
     * @author drj
     * @date 2019-05-07 20:40
     * @Description :根据用户名查询手机号码
     */
    @GetMapping("/phone_find_by_username")
    public Resp phoneFindByUsername(String username) {
        MerchantUser user = merchantUserService.getRepository().findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户名输入有误");
        }
        if (ParamUtil.isBlank(user.getPhone())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户未设置手机号,请联系服务商");
        }
        return Resp.success(user.getPhone(), "");
    }


    /*
     * @author drj
     * @date 2019-05-07 21:04
     * @Description :发送手机验证码
     */
    @PostMapping("/send_sms")
    public Resp sendSms(String phone) {
        String code = RandomUtil.randomNumbers(6);
        boolean flag = messageServiceFeign.sendSms(phone, code);
        if (!flag) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "短信发送失败,请重试");
        }
        //将手机号码跟验证码存入缓存中
        VerifyCodeApplicationRunner.verifyCodeCache.put(phone, code);
        return Resp.success("短信发送成功");
    }


    /*
     * @author drj
     * @date 2019-05-07 20:58
     * @Description :校验手机验证码
     */
    @PostMapping("/verify_code")
    public Resp verifyCode(String phone, String code) {
        //清理过期缓存
        VerifyCodeApplicationRunner.verifyCodeCache.prune();
        //从缓存中获取该手机的校验码
        String phoneCode = VerifyCodeApplicationRunner.verifyCodeCache.get(phone, false);
        if (ParamUtil.isBlank(phoneCode)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码已过期,请重试");
        }
        if (!phoneCode.equals(code)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码输入有误");
        }
        return Resp.success("验证码校验成功");
    }


    /*
     * @author drj
     * @date 2019-05-07 20:59
     * @Description :设置新密码
     */
    @PostMapping("reset_new_pwd")
    public Resp resetNewPwd(String username, String password) {
        MerchantUser user = merchantUserService.getRepository().findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (ParamUtil.isBlank(password)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入密码");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码应为英文、数字、下划线组成的6-20位字符");
        }
        user.setPassword(BCrypt.hashpw(password));
        merchantUserService.save(user);
        return Resp.success("设置成功");
    }

    /**
     * @author Created by wtl on 2019/6/3 10:50
     * @Description 支付H5页面根据userId（收银员）查询商户名称，如果是从卡包使用卡券支付则没有userId
     */
    @GetMapping("/find_merchant_name")
    public Resp findMerchantName(String userId, String merchantId) {
        String merchantName = "";
        // 收银员id为空获取默认商户名称
        if (ParamUtil.isBlank(userId)) {
            if (!ParamUtil.isBlank(merchantId)) {
                Merchant merchant = merchantService.getRepository().findOne(merchantId);
                merchantName = merchant.getName();
            }
        } else {
            MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
            if (!ParamUtil.isBlank(merchantUserDTO)) {
                merchantName = merchantUserDTO.getName();
            }
        }
        return Resp.success(merchantName, "成功获取商户名称");
    }

    /**
     * @author Created by wtl on 2019/7/5 14:45
     * @Description H5支付注册页面获取商户会员卡详情
     */
    @GetMapping("/find_card_detail")
    public Resp findCardDetail(String userId, String merchantId) {
        log.info("find_card_detail,userId---merchantId,{},{}", userId, merchantId);
        if (ParamUtil.isBlank(userId) && ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "收银员id和商户id不能同时为空");
        }
        String mchId = ""; // 商户id
        if (!ParamUtil.isBlank(merchantId)) {
            mchId = merchantId;
        } else {
            MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
            if (ParamUtil.isBlank(merchantUserDTO)) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户不存在");
            }
            mchId = merchantUserDTO.getMerchantId();
        }
        // 获取商户的会员卡
        MemberCard memberCard = memberCardService.getRepository().findByMerchantId(mchId);
        if (ParamUtil.isBlank(memberCard)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "获取商户会员卡信息失败");
        }
        // 获取商户
        Merchant merchant = merchantService.getRepository().findOne(mchId);
        memberCard.setMerchantPhotoId(merchant.getPhotoId());
        memberCard.setMerchantName(merchant.getName());
        return Resp.success(memberCard);
    }

    /*
     * @author drj
     * @date 2019-05-31 10:18
     * @Description :商户头像回显
     */
    @GetMapping("/show_photo")
    public Resp showPhoto(@TokenInfo(property = "merchantId") String merchantId) {
        Merchant merchant = merchantService.findOne(merchantId);
        if (ParamUtil.isBlank(merchant.getPhotoId())) {
            return Resp.success("", "");
        }
        return Resp.success(merchant.getPhotoId());
    }


}
