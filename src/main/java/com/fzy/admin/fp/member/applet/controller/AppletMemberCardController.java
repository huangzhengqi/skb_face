package com.fzy.admin.fp.member.applet.controller;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.domain.MemberReceiveCard;
import com.fzy.admin.fp.member.member.repository.MemberLevelRepository;
import com.fzy.admin.fp.member.member.repository.MemberReceiveCardRepository;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.member.member.service.MemberCardService;
import com.fzy.admin.fp.member.member.vo.AppletMemberCardCashVo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author drj.
 * @Date Created in 17:44 2019/6/10
 * @Description 会员小程序控制层
 **/
@Slf4j
@RestController
@RequestMapping("/member/member_card/applet")
@Api(value = "AppletMemberCardController", tags = {"会员小程序控制层"})
public class AppletMemberCardController extends BaseContent {

    @Resource
    private MemberReceiveCardRepository memberReceiveCardRepository;

    @Resource
    private MemberCardService memberCardService;

    @Autowired
    private MemberRepository memberRepository;

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Resource
    private MerchantService merchantService;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Resource
    private MerchantAppletConfigRepository merchantAppletConfigRepository;

    @ApiOperation(value = "查询该用户是否领取过会员卡", notes = "查询该用户是否领取过会员卡")
    @GetMapping("/find_by_member_id")
    @Transactional
    public synchronized Resp findByMemberId(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId, @TokenInfo(property = "merchantId") String merchantId, @UserId String memberId) {
        Integer receive = memberReceiveCardRepository.countByMemberId(memberId);
        if (receive > 0) {
            return Resp.success("");
        }
        AppletMemberCardCashVo appletMemberCardCashVo;
        //去缓存查询是否存在
        MemberConstant.MEMBERCARD_APPLET_CACHE.prune();
        appletMemberCardCashVo = MemberConstant.MEMBERCARD_APPLET_CACHE.get(merchantId, false);
        if (!ParamUtil.isBlank(appletMemberCardCashVo)) {
            return Resp.success(appletMemberCardCashVo);
        }
        //获取accessToken
        Map<String, String> wxConfigMap = wxConfigServiceFeign.getWxConfig(companyId);
        if ("error".equals(wxConfigMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String accessToken = memberCardService.getAccessToken(wxConfigMap.get("appId"), wxConfigMap.get("appSecret"));
        // 商户信息
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findKey(merchantId);
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigRepository.findByMerchantId(merchantId);
        if (ParamUtil.isBlank(merchantAppletConfig)) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前商户未配置小程序");
        }
        String cardId = memberCardService.cardId(accessToken, merchantUserDTO.getName(), merchantAppletConfig.getUserName());
        log.info("cardId ----------------  " + cardId);
        //获取apiTicket
        String apiTicket = memberCardService.apiTicket(accessToken);
        String nonceStr = ParamUtil.uuid();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        //获取签名
        String signature = sign(apiTicket, nonceStr, timestamp, cardId);
        //将数据信息存入缓存
        appletMemberCardCashVo = new AppletMemberCardCashVo();
        appletMemberCardCashVo.setApiTicket(apiTicket);
        appletMemberCardCashVo.setCardId(cardId);
        appletMemberCardCashVo.setNonceStr(nonceStr);
        appletMemberCardCashVo.setTimestamp(timestamp);
        appletMemberCardCashVo.setSignature(signature);
        MemberConstant.MEMBERCARD_APPLET_CACHE.put(merchantId, appletMemberCardCashVo);
        return Resp.success(appletMemberCardCashVo);
    }

    @ApiOperation(value = "根据商户id查询会员卡信息", notes = "根据商户id查询会员卡信息")
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(@TokenInfo(property = "merchantId") String merchantId) {
        log.info("member/member_card/applet/find_by_merchant_id,merchantId,{}", merchantId);
        // 获取商户的会员卡
        MemberCard memberCard = memberCardService.getRepository().findByMerchantId(merchantId);
        if (ParamUtil.isBlank(memberCard)) {
            memberCard = new MemberCard();
            memberCard.setMerchantId(merchantId);
            memberCard.setCardNumber(RandomUtil.randomNumbers(12));
            memberCardService.save(memberCard);
        }
        // 获取商户名称跟头像
        Merchant merchant = merchantService.getRepository().findOne(merchantId);
        if (ParamUtil.isBlank(merchant)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "获取商户失败");
        }
        memberCard.setMerchantPhotoId(merchant.getPhotoId());
        memberCard.setMerchantName(merchant.getName());
        return Resp.success(memberCard);
    }

    @ApiOperation(value = "设置会员领取过会员卡", notes = "设置会员领取过会员卡")
    @PostMapping("/receive")
    public Resp receive(@UserId String memberId) {
        MemberReceiveCard memberReceiveCard = new MemberReceiveCard();
        memberReceiveCard.setMemberId(memberId);
        memberReceiveCardRepository.save(memberReceiveCard);
        return Resp.success("操作成功");
    }


    /**
     * 获取签名
     *
     * @param api_ticket
     * @param nonce_str
     * @param timestamp
     * @param cardId
     * @return
     */
    private String sign(String api_ticket, String nonce_str, String timestamp, String cardId) {
        String signature = "";
        String param[] = new String[4];
        param[0] = nonce_str;
        param[1] = timestamp;
        param[2] = api_ticket;
        param[3] = cardId;
        //对参数的value值进行字符串的字典序排序
        Arrays.sort(param);

        StringBuilder sb = new StringBuilder();
        for (String b : param) {
            sb.append(b);
        }
        //对上面拼接的字符串进行sha1加密，得到signature
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sb.toString().getBytes("UTF-8"));
            signature = bytesToHexString(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }


    /**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    @ApiOperation(value = "获取会员等级", notes = "获取会员等级")
    @GetMapping({"get_member_level"})
    public Resp<MemberLevel> getMemberLevel(@UserId String memberId) {
        Resp<MemberLevel> resp = new Resp<MemberLevel>();
        Member member = (Member) this.memberRepository.findOne(memberId);
        if (member == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "会员不存在");
        }
        if (member.getMemberLevelId() == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该会员暂无会员等级");
        }
        MemberLevel memberLevel = (MemberLevel) this.memberLevelRepository.findOne(member.getMemberLevelId());
        if (memberLevel == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "会员等级不存在");
        }
        return Resp.success(memberLevel);
    }


    @ApiOperation(value = "删除会员卡", notes = "删除会员卡")
    @PostMapping("/card_delete")
    public Resp cardDelete(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId, String cardId) {

        log.info("cardId ----------- " + cardId);
        //获取accessToken
        Map<String, String> wxConfigMap = wxConfigServiceFeign.getWxConfig(companyId);
        if ("error".equals(wxConfigMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String accessToken = memberCardService.getAccessToken(wxConfigMap.get("appId"), wxConfigMap.get("appSecret"));

        String errmsg = memberCardService.cardDelete(accessToken, cardId);
        return Resp.success(errmsg);
    }
}
