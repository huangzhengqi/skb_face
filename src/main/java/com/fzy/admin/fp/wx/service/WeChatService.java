package com.fzy.admin.fp.wx.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.domain.MemberReceiveCard;
import com.fzy.admin.fp.member.member.repository.MemberReceiveCardRepository;
import com.fzy.admin.fp.member.member.service.MemberCardService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/8/27 20:58
 * @Version 1.0
 * @description
 */
@Service
@Slf4j
@Transactional
public class WeChatService {

    private final String APPID = "wx3b3cf852b89ee070";
    private final String APPSECRET = "01c28149606b912b706fbfbea864d062";
    private final String MEMBER_CARD = "MEMBER_CARD";
    private final String CASH = "CASH";

    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;

    @Resource
    private MemberCardService memberCardService;

    @Resource
    private MemberService memberService;

    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private MemberReceiveCardRepository memberReceiveCardRepository;

    /**
     * 会员卡通过审核
     *
     * @param cardId
     * @return
     */
    public String cardPassCheck(String cardId) {
        MemberCard memberCard = memberCardService.getRepository().findByCardId(cardId);
        memberCard.setStatus(Integer.valueOf(3));
        memberCardService.update(memberCard);
        return "";
    }

    public String cardNotPassCheck(String cardId, String refuseReason) {
        MemberCard memberCard = memberCardService.getRepository().findByCardId(cardId);
        memberCard.setStatus(Integer.valueOf(2));
        memberCard.setRefuseReason(refuseReason);
        memberCardService.update(memberCard);
        return "";
    }

    public String userGetCard(Map<String, String> map) {
        String cardId = map.get("CardId").toString();
        String fromUserName = map.get("FromUserName").toString();
        String unionId = map.get("UnionId").toString();
        String outerStr = map.get("OuterStr").toString();
        String userCardCode = map.get("UserCardCode").toString();
        //获取会员卡类型
        String cardType = getCardType(cardId);
        if (cardType.equals(MEMBER_CARD)) {
            //会员卡
            Member member = new Member();
            member.setOpenId(fromUserName);
            member.setUnionId(unionId);
            member = initMember(member);
            //通过卡券id查询商户
            MemberCard memberCard = memberCardService.getRepository().findByCardId(cardId);
            member.setMerchantId(memberCard.getMerchantId());
            member = memberService.save(member);
            //设置会员领取过会员卡
            MemberReceiveCard memberReceiveCard = new MemberReceiveCard();
            memberReceiveCard.setMemberId(member.getId());
            memberReceiveCard.setUserCardCode(userCardCode);
            memberReceiveCard.setOuterStr(outerStr);
            memberReceiveCardRepository.save(memberReceiveCard);
            String key = member.getMemberNum();
            Merchant merchant = (Merchant) this.merchantRepository.findOne(member.getMerchantId());
            return JWT.create().withIssuer("member").withSubject(member.getId())
                    .withClaim("merchantId", member.getMerchantId())
                    .withClaim("serviceProviderId", merchant.getServiceProviderId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (this.tokenExpiration * 60 * 1000)))
                    .sign(Algorithm.HMAC512(key));
        } else if (cardType.equals(CASH)) {
            //代金券
        }
        return "";
    }

    private String getCardType(String cardId) {
        String accessToken = WXUtil.getAccessToken(APPID, APPSECRET);
        JSONObject jsonObject = WXUtil.detailMemberCard(accessToken, cardId);
        String cardType = jsonObject.get("card_type").toString();
        return cardType;
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
}
