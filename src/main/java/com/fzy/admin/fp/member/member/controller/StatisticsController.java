package com.fzy.admin.fp.member.member.controller;


import com.fzy.admin.fp.auth.vo.ChartDataVO;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.dto.DateConditionDTO;
import com.fzy.admin.fp.member.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-16 22:34
 * @description
 */
@RestController
@RequestMapping("/member/statistics")
@Slf4j
@Component("memberStatisticsController")
public class StatisticsController extends BaseContent {
    @Resource
    private MemberService memberService;

    /*
     * @author Created by zk on 2019/5/16 22:49
     * @Description 会员分析卡片
     */
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
        final Map<String, List<PieChartDataVO>> result = memberService.memberAnalyseChartByChannel(merchantId, dateCondition);
        return Resp.success(result);
    }

    @GetMapping("/member_analyse_chart_by_sex")
    public Resp memberAnalyseChartBySex(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        final Map<String, List<PieChartDataVO>> result = memberService.memberAnalyseChartBySex(merchantId, dateCondition);
        return Resp.success(result);
    }

    @GetMapping("/member_analyse_consume_activity_level")
    public Resp memberAnalyseConsumeActivityLevel(@TokenInfo(property = "merchantId") String merchantId, DateConditionDTO dateCondition) {
        final List<PieChartDataVO> pieChartDataVOS = memberService.memberAnalyseConsumeActivityLevel(merchantId, dateCondition);
        return Resp.success(pieChartDataVOS);
    }
}
