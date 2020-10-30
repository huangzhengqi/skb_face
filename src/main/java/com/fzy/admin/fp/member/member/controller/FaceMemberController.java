package com.fzy.admin.fp.member.member.controller;

import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.MemberRule;
import com.fzy.admin.fp.goods.domain.PayRule;
import com.fzy.admin.fp.goods.service.MemberRuleService;
import com.fzy.admin.fp.goods.service.PayRuleService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.member.vo.DiscountRuleVO;
import com.fzy.admin.fp.member.sem.vo.FaceAliMemberVO;
import com.fzy.admin.fp.member.member.vo.FaceMemberVO;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import com.fzy.admin.fp.member.sem.service.MemberAliLevelService;
import com.fzy.assist.wraps.BeanWrap;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member/face_member")
public class FaceMemberController {
    private static final Logger log = LoggerFactory.getLogger(FaceMemberController.class);

    @Resource
    private MemberService memberService;

    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private MemberAliLevelService memberAliLevelService;

    @Resource
    private PayRuleService payRuleService;

    @Resource
    private MemberRuleService memberRuleService;

    @GetMapping({"/detail"})
    public Resp faceMemberDetail(String merchantId, String openId, String buyerId) {
        Member member;
        log.info("111111111111111111111");
        if (StringUtil.isEmpty(buyerId)) {
            member = this.memberService.findByMerchantIdAndOpenIdAndDelFlag(merchantId, openId, CommonConstant.NORMAL_FLAG);
        } else {
            member = this.memberService.findByMerchantIdAndBuyerIdAndDelFlag(merchantId, buyerId, CommonConstant.NORMAL_FLAG);
        }
        log.info("member  ------------:         " + member );
        FaceMemberVO faceMemberVO = null;
        if (member != null) {
            faceMemberVO = new FaceMemberVO();
            BeanWrap.copyProperties(member, faceMemberVO);

            MemberLevel memberLevel = memberLevelService.findOne(member.getMemberLevelId());
            if(null == memberLevel) {
                if(("1").equals(member.getMemberLevelId())) {
                    memberLevel = new MemberLevel();
                    memberLevel.setGiftMoney(BigDecimal.valueOf(0));
                    memberLevel.setGiftScore(0);
                    memberLevel.setId("1");
                    memberLevel.setMerchantId(merchantId);
                    memberLevel.setDiscount(BigDecimal.valueOf(1));
                    memberLevel.setMemberLimitAmount(BigDecimal.valueOf(0));
                    memberLevel.setRightExplain("普通会员无权益");
                    memberLevel.setName("普通会员");
                }
            } else{
                if(memberLevel.getDiscount() == null){
                    memberLevel.setDiscount(new BigDecimal(1));
                }
            }
            faceMemberVO.setMemberLevel(memberLevel);
        }
        return Resp.success(faceMemberVO);
    }

//    /**
//     * 支付宝会员会员详情
//     * @param merchantId
//     * @param buyerId
//     * @return
//     */
//    @GetMapping({"/detail_ali"})
//    public Resp faceAliMemberDetail(String merchantId,String buyerId) {
//        Member member = null;
//        if(StringUtils.isNotEmpty(buyerId)) {
//            member = memberService.findByMerchantIdAndBuyerIdAndDelFlag(merchantId,buyerId,CommonConstant.NORMAL_FLAG);
//        }
//        FaceAliMemberVO faceAliMemberVO = null;
//        if(null != member) {
//            faceAliMemberVO = new FaceAliMemberVO();
//            BeanUtils.copyProperties(member,faceAliMemberVO);
//            MemberAliLevel memberLevel = memberAliLevelService.findOne(member.getMemberLevelId());
//            faceAliMemberVO.setMemberAliLevel(memberLevel);
//        }
//        return Resp.success(faceAliMemberVO);
//    }

    @GetMapping({"/discount_rule"})
    public Resp discountRule(String merchantId) {
        Date date = new Date();

        List<MemberRule> ruleList = this.memberRuleService.findByStatusAndMerchantId(Integer.valueOf(1), merchantId).stream().filter(memberRule -> (date.compareTo(memberRule.getStartTime()) >= 0 && date.compareTo(memberRule.getEndTime()) <= 0)).collect(Collectors.toList());
        List<String> rules = ruleList.stream().map(MemberRule::getRule).collect(Collectors.toList());
        Set<DiscountRuleVO> vos = new HashSet<DiscountRuleVO>();
        for (int i = 0; i < ruleList.size(); i++) {
            String id = ruleList.get(i).getId();
            String rule = rules.get(i);
            distinct(vos, rule, id);
        }
        return Resp.success(filterVos(vos));
    }

    @GetMapping({"/pay_rule"})
    public Resp payRule(String merchantId) {
        PayRule rule = this.payRuleService.findByCompanyId(merchantId);
        return Resp.success(this.payRuleService.getPayRule(rule));
    }


    private void distinct(Set<DiscountRuleVO> vos, String test, String id) {
        List<DiscountRuleVO> discountRuleVOS = JSON.parseArray(test, DiscountRuleVO.class);
        discountRuleVOS.forEach(discountRuleVO -> {
            discountRuleVO.setId(id);
            vos.add(discountRuleVO);
        });
    }

    private List<DiscountRuleVO> filterVos(Set<DiscountRuleVO> vos) {
        Map<BigDecimal, List<DiscountRuleVO>> collect = vos.stream().collect(Collectors.groupingBy(DiscountRuleVO::getFull));
        List<DiscountRuleVO> myData = new ArrayList<DiscountRuleVO>();
        for (BigDecimal key : collect.keySet()) {
            DiscountRuleVO discountRuleVO = (DiscountRuleVO)((List)collect.get(key)).stream().max(Comparator.comparing(DiscountRuleVO::getLess)).get();
            myData.add(discountRuleVO);
        }
        return myData.stream().sorted(Comparator.comparing(DiscountRuleVO::getFull).reversed()).collect(Collectors.toList());
    }
}
