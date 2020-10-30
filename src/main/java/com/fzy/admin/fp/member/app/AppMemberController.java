package com.fzy.admin.fp.member.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.member.app.vo.AppMemberVO;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.dto.DateConditionDTO;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.auth.vo.ChartDataVO;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.app.vo.AppMemberVO;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.dto.DateConditionDTO;
import com.fzy.admin.fp.member.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-23 14:39
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/member/member/app")
public class AppMemberController extends BaseContent {
    @Resource
    private MemberService memberService;

    @Resource
    private PersonCouponService personCouponService;

    @GetMapping("/find_member_list")
    public Resp findMemberList(@TokenInfo(property = "merchantId") String merchantId, String phoneOrNum, int pageNum) {
        List<Member> memberList = memberService.findByPhoneLikeOrMemberNumLike(phoneOrNum)
                .stream().filter(member -> merchantId.equals(member.getMerchantId())).collect(Collectors.toList());
        List<AppMemberVO> appMemberVOList = memberList.stream().map(member -> {
            AppMemberVO appMemberVO = new AppMemberVO();
            BeanUtil.copyProperties(member, appMemberVO);
            return appMemberVO;
        }).skip((pageNum - 1) * 10).limit(10).collect(Collectors.toList());
        int totalPage = PageUtil.totalPage(appMemberVOList.size(), 10);
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("totalPage", totalPage);
        dataMap.put("totalCount", memberList.size());
        dataMap.put("totalElement", memberList.size());
        dataMap.put("data", appMemberVOList);
        log.info(JSON.toJSONString(dataMap));
        return Resp.success(dataMap);
    }

    @GetMapping("/member_detail")
    public Resp<Member> memberDetail(String id) {
        Member member = memberService.findOne(id);
        final List<PersonCoupon> personCouponList = personCouponService.findAll(member.getMerchantId(), member.getId());
        final Map<Integer, Long> map = personCouponList.stream().collect(Collectors.groupingBy(PersonCoupon::getStatus, Collectors.counting()));
        Long noUse = map.get(PersonCoupon.Status.NO_USE.getCode());
        Long used = map.get(PersonCoupon.Status.USE.getCode());
        Long invalid = map.get(PersonCoupon.Status.INVALID.getCode());
        member.setCouponCount(noUse == null ? 0 : noUse.intValue());
        member.setUsedCouponCount(used == null ? 0 : used.intValue());
        member.setInvalidCouponCount(invalid == null ? 0 : invalid.intValue());
        return Resp.success(member);
    }

    //根据手机号查找会员--积分商城登录
    @GetMapping(value = "/find_member_by_phone")
    public Resp findMemberByPhone(String phone, @TokenInfo(property = "merchantId") String merchantId) {
        Member members = memberService.findByPhoneAndMerchantId(phone, merchantId);
        if (members == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员不存在");
        }/*else if (members.size()>1){
            return new Resp().error(Resp.Status.PARAM_ERROR,"手机号多卡重复");
        }*/
        return Resp.success(members);
    }

    //-----------------会员分析------------------
    @GetMapping("/member_analyse_card")
    public Resp memberAnalyseCard(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        final Map<String, Long> result = memberService.memberAnalyseCard(merchantId, dateCondition);
        return Resp.success(result);
    }

    @GetMapping("/member_analyse_chart_by_time")
    public Resp memberAnalyseChartByTime(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        final ChartDataVO chartDataVO = memberService.memberAnalyseChartByTime(merchantId, dateCondition);
        return Resp.success(chartDataVO);
    }

    @GetMapping("/member_analyse_chart_by_channel")
    public Resp memberAnalyseChartByChannel(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        Map<String, List<PieChartDataVO>> result = memberService.memberAnalyseChartByChannel(merchantId, dateCondition);
        return Resp.success(result);
    }

    @GetMapping("/member_analyse_chart_by_sex")
    public Resp memberAnalyseChartBySex(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {

        return Resp.success(memberService.memberAnalyseChartBySex(merchantId, dateCondition));
    }

    @GetMapping("/member_analyse_consume_activity_level")
    public Resp memberAnalyseConsumeActivityLevel(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        final List<PieChartDataVO> pieChartDataVOS = memberService.memberAnalyseConsumeActivityLevel(merchantId, dateCondition);
        return Resp.success(pieChartDataVOS);
    }
    //---------------------------------------------
}
