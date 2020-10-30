package com.fzy.admin.fp.member.member.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.coupon.service.ReceiveCouponService;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.dto.WebRegisterDTO;
import com.fzy.admin.fp.member.member.service.MemberCardService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.sem.domain.MemberCardTemplate;
import com.fzy.admin.fp.member.sem.service.MemberCardTemplateService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.sdk.auth.feign.MessageServiceFeign;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;

/**
 * @author Created by zk on 2019-05-14 10:35
 * @description 用于会员注册
 */
@RestController
@Slf4j
@RequestMapping("/member/admin")
public class MemberManagementController extends BaseContent {
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;
    @Resource
    private MemberService memberService;
    @Resource
    private MessageServiceFeign messageServiceFeign;
    @Resource
    private MemberCardService memberCardService;

    @Resource
    private CreditsInfoService creditsInfoService;

    @Resource
    private ReceiveCouponService receiveCouponService;

    @Resource
    private StoreServiceFeign storeServiceFeign;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Resource
    private MerchantRepository merchantRepository;

    private String defaultMemberLevelId = "1";

    @Resource
    private MemberCardTemplateService memberCardTemplateService;

    @Resource
    private MerchantBusinessService merchantBusinessService;
    private final String userInfoUrl = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";


    @GetMapping("/phone_verification_code")
    public Resp phoneVerificationCode(String phone, @TokenInfo(property = "merchantId") String merchantId) {
        if (memberService.countMemberByPhoneAndMerchantIdAndDelFlag(phone, merchantId) > 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该手机号已注册");
        }
        String code = RandomUtil.randomNumbers(6);
        String key = StrFormatter.format("{}_{}", merchantId, phone);
        if (!messageServiceFeign.sendSms(phone, code)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码发送失败，请重试");
        }
        MemberConstant.PHONE_VERIFICATION_CODE_CACHE.put(key, code);
        return Resp.success("手机验证码发送成功，15分钟内有效");
    }


    @GetMapping("/default_register")
    public Resp defaultRegister(Member member, String appId) {
        AppletConfigVO appletConfigVO = merchantBusinessService.findByAppId(appId);
        if (ParamUtil.isBlank(appletConfigVO)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "系统错误，查无该商户");
        }
        member.setMerchantId(appletConfigVO.getMerchantId());
        member = initMember(member);
        member.setChannel(Member.Channel.MERCHANT_APP.getCode());
        memberService.save(member);
        return null;
    }

    /**
     * @author Created by wtl on 2019/6/20 21:54
     * @Description 注册后保存额外赠送
     */
    private void extra(Member member, Integer presentScores, String couponId) {
        //若会员卡有送积分
        if (null != presentScores) {
            member.setScores(member.getScores() + presentScores);
            // 生成积分明细表
            CreditsInfo creditsInfo = new CreditsInfo();
            creditsInfo.setMemberNum(member.getMemberNum());
            creditsInfo.setPhone(member.getPhone());
            creditsInfo.setInfoType(CreditsInfo.Trade.CARD_GIVEN.getCode());
            creditsInfo.setTransactionType(CreditsInfo.Trade.CARD_GIVEN.getMessage());
            creditsInfo.setTradeTime(new Date());
            creditsInfo.setTradeScores(member.getScores());
            creditsInfo.setAvaCredits(member.getScores());
            creditsInfo.setMerchantId(member.getMerchantId());
            creditsInfo.setTradeNum(1);
            // 操作人为商户的默认门店
            MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(member.getMerchantId());
            creditsInfo.setStoreId(merchantDefaultStore.getStoreId());
            creditsInfo.setStoreName(merchantDefaultStore.getStoreName());
            creditsInfoService.save(creditsInfo);
        }
        //若会员卡有送卡券
        if (null != couponId) {
            ReceiveCoupon receiveCoupon = new ReceiveCoupon();
            receiveCoupon.setMemberId(member.getId());
            receiveCoupon.setCouponId(couponId);
            receiveCouponService.receiveCoupon(member.getMerchantId(), receiveCoupon);
        }
    }

    /**
     * 商户app添加会员、H5页面添加会员共用部分抽取出来 type=1 商户app添加  type=2 h5添加会员
     * @param member
     * @param type
     * @return
     */
    public boolean register(Member member, Integer type) {
        boolean resule = true;
        //检查号码是否已注册
        Member oldmenber = memberService.check(member);
        if (oldmenber != null && type == 1) {
            return false;
        }
        if (oldmenber != null && type == 2) {
            oldmenber.setOfficialOpenId(member.getOfficialOpenId());
            memberService.save(oldmenber);
            return true;
        }
        String key = StrFormatter.format("{}_{}", member.getMerchantId(), member.getPhone());
        MemberConstant.PHONE_VERIFICATION_CODE_CACHE.prune();
        member = initMember(member);
        member.setChannel(Member.Channel.MERCHANT_APP.getCode());
        MemberConstant.PHONE_VERIFICATION_CODE_CACHE.remove(key);
        memberService.save(member);
        //查询该商户是否存在会员卡
        MemberCard memberCard = memberCardService.getRepository().findByMerchantId(member.getMerchantId());
        if (!ParamUtil.isBlank(memberCard)) {
            // 额外赠送
            extra(member, memberCard.getPresentScores(), memberCard.getCouponId());
        }
        memberService.save(member);
        return resule;
    }


    /**
     * 商户添加会员
     */
    @PostMapping("/register")
    @Transactional
    public Resp register(@TokenInfo(property = "merchantId") String merchantId, Member member) {

        member.setMerchantId(merchantId);
        final BindingResult valid = Validation.valid(member);
        if (!valid.isFlag()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, valid.getMessage().get(0));
        }
        if (!register(member, 1)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "号码已存在！");
        }
        ;
        return Resp.success("注册成功");
    }

    @PostMapping({"/equipment_register"})
    @Transactional
    public Resp equipmentRegister(String merchantId, String mobilePhone, String nickName, String avatarUrl, Integer gender, String openId, String buyerId) {
        String token = memberRegister(mobilePhone, nickName, avatarUrl, gender, openId, merchantId, buyerId);
        return Resp.success(token, "注册成功");
    }

    private String memberRegister(String mobilePhone, String nickName, String avatarUrl, Integer gender, String openId, String merchantId, String buyerId) {
        if (StringUtils.isEmpty(openId) && StringUtils.isEmpty(buyerId)) {
            throw new BaseException("openId与buyerId不能同时为空", Resp.Status.PARAM_ERROR.getCode());
        }
        Member member = new Member();

        if (!StringUtils.isEmpty(mobilePhone)) {
            member = this.memberService.findByPhoneAndMerchantId(mobilePhone, merchantId);
        }
        else if (StringUtils.isEmpty(buyerId)) {
            member = this.memberService.findByMerchantIdAndOpenIdAndDelFlag(merchantId, openId, CommonConstant.NORMAL_FLAG);
        } else {
            member = this.memberService.findByMerchantIdAndBuyerIdAndDelFlag(merchantId, buyerId, CommonConstant.NORMAL_FLAG);
        }

        if (member == null) {
            member = new Member();
            if (StringUtils.isEmpty(buyerId)) {

                member.setChannel(Member.Channel.EQUIPMENT_WECHAT.getCode());
            } else {
                member.setChannel(Member.Channel.EQUIPMENT_ALI.getCode());
            }

            member.setSex(Member.Sex.MEM.getCode());
            member = initMember(member);

            member.setMerchantId(merchantId);
            member = (Member)this.memberService.save(member);
            MemberCard memberCard = this.memberCardService.getRepository().findByMerchantId(merchantId);
            if (!ParamUtil.isBlank(memberCard))
            {
                extra(member, memberCard.getPresentScores(), memberCard.getCouponId());
            }
        }
        member.setMerchantId(merchantId);

        if (!StringUtils.isEmpty(mobilePhone)) {
            member.setPhone(mobilePhone);
        }
        if (!StringUtils.isEmpty(openId)) {
            member.setOpenId(openId);
        }
        if (!StringUtils.isEmpty(buyerId)) {
            member.setBuyerId(buyerId);
        }
        if (StringUtils.hasText(nickName)) {
            member.setNickname(nickName);
        }
        if (!StringUtils.hasText(nickName)) {
            member.setNickname(member.getMemberNum());
        }

        if (StringUtils.hasText(avatarUrl)) {
            member.setHead(avatarUrl);
        }
        if (gender != null) {
            member.setSex(gender);
        }
        if (StringUtils.isEmpty(member.getMemberLevelId())) {
            member.setMemberLevelId(this.defaultMemberLevelId);
        }
        member = (Member)this.memberService.save(member);
        String key = member.getMemberNum();
        Merchant merchant = (Merchant)this.merchantRepository.findOne(member.getMerchantId());
        return JWT.create().withIssuer("member").withSubject(member.getId())
                .withClaim("merchantId", member.getMerchantId())
                .withClaim("serviceProviderId", merchant.getServiceProviderId())
                .withExpiresAt(new Date(System.currentTimeMillis() + (this.tokenExpiration * 60 * 1000)))
                .sign(Algorithm.HMAC512(key));
    }


    @PostMapping({"/equipment_register_ali"})
    @Transactional
    public Resp equipmentRegister(String merchantId, String buyerId) {
        String token = memberRegister(merchantId, buyerId);
        return Resp.success(token, "注册成功");
    }

    private String memberRegister(String merchantId, String buyerId) {
        if (StringUtils.isEmpty(buyerId)) {
            throw new BaseException("buyerId不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if(StringUtils.isEmpty(merchantId)) {
            throw new BaseException("merchantId不能为空",Resp.Status.PARAM_ERROR.getCode());
        }

        Member  member = memberService.findByMerchantIdAndBuyerIdAndDelFlag(merchantId, buyerId, CommonConstant.NORMAL_FLAG);

        if (null == member) {
            member = new Member();
            if (!StringUtils.isEmpty(buyerId)) {
                member.setChannel(Member.Channel.EQUIPMENT_ALI.getCode());
            }
            member.setSex(Member.Sex.MEM.getCode());
            member = initMember(member);
            member = memberService.save(member);
            MemberCardTemplate memberCardTemplate = memberCardTemplateService.findMax(merchantId);
            if (!ParamUtil.isBlank(memberCardTemplate))
            {
                extra(member, memberCardTemplate.getPresentScores(), memberCardTemplate.getCouponId());
            }
        }
        member.setMerchantId(merchantId);
        member.setBuyerId(buyerId);
        member = memberService.save(member);
        String key = member.getMemberNum();
        Merchant merchant = merchantRepository.findOne(member.getMerchantId());
        return JWT.create().withIssuer("member").withSubject(member.getId())
                .withClaim("merchantId", member.getMerchantId())
                .withClaim("serviceProviderId", merchant.getServiceProviderId())
                .withExpiresAt(new Date(System.currentTimeMillis() + (this.tokenExpiration * 60 * 1000)))
                .sign(Algorithm.HMAC512(key));
    }


    /**
     * @param userId     收银员id，商户出示收款二维码或者用户扫移动支付二维码附带，和merchantId不能同时为空
     * @param merchantId 商户id，卡包点击使用卡券附带
     * @param phone      手机号码
     * @author Created by wtl on 2019/6/26 22:24
     * @Description H5页面发送短信获取验证码
     */
    @GetMapping("/web_verification_code")
    public Resp webVerificationCode(String userId, String merchantId, String phone) {
        if (ParamUtil.isBlank(userId) && ParamUtil.isBlank(merchantId)) {
            throw new BaseException("收银员ID和商户id不能同时为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(phone)) {
            throw new BaseException("手机号不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 商户id
        String mchId = "";
        // 根据收银员id获取商户id
        if (!ParamUtil.isBlank(userId)) {
            MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
            if (ParamUtil.isBlank(merchantUserDTO)) {
                throw new BaseException("商户信息错误", Resp.Status.PARAM_ERROR.getCode());
            }
            mchId = merchantUserDTO.getMerchantId();
        }
        if (!ParamUtil.isBlank(merchantId)) {
            mchId = merchantId;
        }
        // 根据是否有公众号openid判断是否已加入会员，商户app添加会员只是保存手机号信息无法获取openid
        Member member = memberService.findByPhoneAndMerchantId(phone, mchId);
        if (!ParamUtil.isBlank(member) && !ParamUtil.isBlank(member.getOfficialOpenId())) {
            throw new BaseException("该手机号已注册", Resp.Status.PARAM_ERROR.getCode());
        }
        String code = RandomUtil.randomNumbers(6);
        String key = StrFormatter.format("{}_{}", mchId, phone);
        if (!messageServiceFeign.sendSms(phone, code)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码发送失败，请重试");
        }
        MemberConstant.PHONE_VERIFICATION_CODE_CACHE.put(key, code);
        return Resp.success("手机验证码发送成功，15分钟内有效");
    }

    /**
     * @author Created by wtl on 2019/6/26 16:37
     * @Description H5页面添加会员
     */
    @PostMapping("/web_register")
    @Transactional
    public Resp webRegister(@Valid WebRegisterDTO webRegisterDTO) {
        log.info("web_register,userId-merchantId,{},{}", webRegisterDTO.getUserId(), webRegisterDTO.getMerchantId());
        if (ParamUtil.isBlank(webRegisterDTO.getUserId()) && ParamUtil.isBlank(webRegisterDTO.getMerchantId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "收银员ID和商户ID不能同时为空");
        }
        String merchantId = "";

        if (!ParamUtil.isBlank(webRegisterDTO.getUserId())) {
            MerchantUserDTO merchantUserDTO = this.merchantUserFeign.findUser(webRegisterDTO.getUserId());
            if (ParamUtil.isBlank(merchantUserDTO)) {
                throw new BaseException("商户信息错误", Resp.Status.PARAM_ERROR.getCode());
            }
            merchantId = merchantUserDTO.getMerchantId();
        }

        if (!ParamUtil.isBlank(webRegisterDTO.getMerchantId())) {
            merchantId = webRegisterDTO.getMerchantId();
        }
        Member member = this.memberService.findByPhoneAndMerchantId(webRegisterDTO.getPhone(), merchantId);
        if (ParamUtil.isBlank(member)) {
            member = new Member();
            member.setMerchantId(merchantId);
            member.setPhone(webRegisterDTO.getPhone());
        }
        member.setOfficialOpenId(webRegisterDTO.getOpenId());
        if (StringUtils.isEmpty(member.getSex())) {
            member.setSex(Member.Sex.MEM.getCode());
        }
        if (StringUtils.isEmpty(member.getMemberLevelId())) {
            member.setMemberLevelId(this.defaultMemberLevelId);
        }
        if (!register(member, Integer.valueOf(2))) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "号码已存在");
        }
        if (StringUtils.isEmpty(member.getNickname())) {
            member.setNickname(member.getMemberNum());
        }
        this.memberService.save(member);
        return Resp.success(member.getId(), "注册成功");
    }


    /**
     * @author Created by zk on 2019/5/31 17:15
     * @Description 小程序默认注册接口
     */
//    @PostMapping("/applet_register")
//    @Transactional
//    public Resp appletRegister(String appId,
//                               String mobilePhone,
//                               String nickName,
//                               String avatarUrl,
//                               Integer gender,
//                               String openId) {
//        AppletConfigVO appletConfigVO = MemberConstant.MERCHANT_APPKEY_CACHE.get(appId);
//
//        Member member = memberService.findByPhoneAndMerchantId(mobilePhone, appletConfigVO.getMerchantId());
//        if (member == null) {
//            member = new Member();
//            member.setPhone(mobilePhone);
//            member.setSex(Member.Sex.MEM.getCode());
//            member = initMember(member);
//            member.setChannel(Member.Channel.WECHAT_APPLET.getCode());
//            member.setMerchantId(appletConfigVO.getMerchantId());
//            member = memberService.save(member);
//            MemberCard memberCard = memberCardService.getRepository().findByMerchantId(appletConfigVO.getMerchantId());
//            if (!ParamUtil.isBlank(memberCard)) {
//                // 额外赠送
//                extra(member, memberCard.getPresentScores(), memberCard.getCouponId());
//            }
//        }
//        member.setOpenId(openId);
//        member.setMerchantId(appletConfigVO.getMerchantId());
//        if (StringUtils.hasText(nickName)) {
//            member.setNickname(nickName);
//        }
//        if (StringUtils.hasText(avatarUrl)) {
//            member.setHead(avatarUrl);
//        }
//        if (gender != null) {
//            member.setSex(gender);
//        }
//
//        member = memberService.save(member);
//        String key = member.getMemberNum();
//        String token = JWT.create().withIssuer(MemberConstant.AUTH_NAME).withSubject(member.getId())
//                .withClaim("merchantId", member.getMerchantId())
//                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000))
//                .sign(Algorithm.HMAC512(key));
//        return Resp.success(token, "注册成功");
//    }

    @PostMapping({"/applet_register"})
    @Transactional
    public Resp appletRegister(String appId, String mobilePhone, String nickName, String avatarUrl, Integer gender, String openId) {
        AppletConfigVO appletConfigVO = (AppletConfigVO)MemberConstant.MERCHANT_APPKEY_CACHE.get(appId);
        if (appletConfigVO == null) {
            throw new BaseException("请先配置小程序参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String token = memberRegister(mobilePhone, nickName, avatarUrl, gender, openId, appletConfigVO.getMerchantId(), null);
        return Resp.success(token, "注册成功");
    }

    /**
     * 小程序用户登陆
     *
     * @param appId
     * @return
     */
    @GetMapping("/login")
    public Resp login1(@NotNull String appId, @NotNull String openId) {
        AppletConfigVO appletConfigVO = MemberConstant.MERCHANT_APPKEY_CACHE.get(appId);

        Member member = memberService.findByOpenId(openId);

        //如果member为空，则说明尚未注册
        if (member == null) {
            return Resp.success(-1);
        }
        Merchant merchant = merchantRepository.findOne(member.getMerchantId());
        String key = member.getMemberNum();
        String token = JWT.create().withIssuer(MemberConstant.AUTH_NAME).withSubject(member.getId())
                .withClaim("merchantId", member.getMerchantId())
                .withClaim("serviceProviderId", merchant.getServiceProviderId())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(key));
        return Resp.success(token, null);

    }


    private Member initMember(Member member) {
        member.setBalance(BigDecimal.ZERO);
        member.setScores(0);
        StringBuilder sb = new StringBuilder(DateUtil.format(new Date(), "yyyyMMdd"));
        String memberNum;
        while (true) {
            memberNum = sb.append(RandomUtil.randomNumbers(6)).toString();
            if (!memberService.countMemberByMemberNum(memberNum)) {
                member.setMemberNum(memberNum);
                break;
            }
        }
        return member;
    }

    /**
     * Created by hgg on 2018/2/24.
     * <p>
     * 小程序AES解密
     */

    private String AESDecode(String encryptData, String ivData, String sessionKey) throws Exception {
        return decrypt(Base64.decodeBase64(sessionKey), Base64.decodeBase64(ivData), Base64.decodeBase64(encryptData));
    }

    private String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return new String(cipher.doFinal(encData), "UTF-8");

    }


    /**
     * 判断是否是支付宝会员
     * @param buyserId
     * @return
     */
    @GetMapping("/member/info")
    @ApiOperation(value = "判断是否是支付宝会员",notes = "判断是否是支付宝会员")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "buyserId",dataType ="String",value = "buyserId"),
                        @ApiImplicitParam(paramType = "query",name = "merchantId",dataType = "String",value = "商户id")})
    @ApiResponse(code = 200,message = "OK",response = Member.class)
    public Resp checkUserMember(String buyserId,String merchantId){
        Member member = memberService.findByBuyerId(buyserId,merchantId);
        if(null == member) {
            return Resp.success(null);
        } else {
            return Resp.success(member);
        }
    }
}
