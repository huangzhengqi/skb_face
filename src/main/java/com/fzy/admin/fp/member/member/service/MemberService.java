package com.fzy.admin.fp.member.member.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.auth.vo.ChartDataVO;
import com.fzy.admin.fp.auth.vo.PieChartDataVO;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.enumeration.CodeEnum;
import com.fzy.admin.fp.common.enumeration.EnumUtils;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberConsumeActivityLevelEnum;
import com.fzy.admin.fp.member.member.dto.DateConditionDTO;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-14 10:29
 * @description
 */
@Slf4j
@Service
@Transactional
public class MemberService implements BaseService<Member> {
    @Resource
    private MemberRepository memberRepository;
    @Resource
    private StoredRecoredService storedRecoredService;

    @Override
    public MemberRepository getRepository() {
        return memberRepository;
    }

    private List<String> dayList = Arrays.asList("0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00",
            "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00",
            "22:00", "23:00", "24:00");

    //------------------------------------统计相关-----------------------------------------------
    public Map<String, Long> memberAnalyseCard(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        //获取日期范围内新增会员
        long newMember = countNewMemberByMerchantIdAndCreateTime(merchantId, dateCondition);
        //获取该商户会员总量
        long allMember = countNewMemberByMerchantId(merchantId);
        Map<String, Long> result = new HashMap<>();
        result.put("newMember", newMember);
        result.put("allMember", allMember);
        return result;
    }

    public ChartDataVO memberAnalyseChartByTime(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        List<Member> memberList = findByMerchantIdAndCreateTime(merchantId, dateCondition);
        List<String> xData;
        List<String> yData = new ArrayList<>();
        List<String> num = new ArrayList<>();
        Map<String, Long> timeAndCountMap;
        long memberCountBeforeBeginDate = countNewMemberByMerchantIdAndCreateTimeBefore(merchantId, dateCondition.getStartTime());
        if (DateUtil.isSameDay(dateCondition.getStartTime(), dateCondition.getEndTime())) {
            //如果时间区间在同一天
            xData = dayList;
            timeAndCountMap = memberList.stream()
                    .collect(Collectors.groupingBy(m -> DateUtil.format(m.getCreateTime(), "H:00"), Collectors.counting()));
        } else {
            //如果时间区间不在同一天
            xData = dateRangeList(dateCondition.getStartTime(), dateCondition.getEndTime(), DateField.DAY_OF_WEEK, "yyyy-MM-dd");
            timeAndCountMap = memberList.stream()
                    .collect(Collectors.groupingBy(m -> DateUtil.format(m.getCreateTime(), "yyyy-MM-dd"), Collectors.counting()));
        }
        for (String time : xData) {
            //这个时间点新增的人数
            Long count = timeAndCountMap.get(time);
            yData.add(count == null ? "0" : count.toString());
            //这个时间点的总人数
            memberCountBeforeBeginDate = count == null ? memberCountBeforeBeginDate : (memberCountBeforeBeginDate + count);
            num.add(String.valueOf(memberCountBeforeBeginDate));
        }
        ChartDataVO chartDataVO = new ChartDataVO(num, xData, yData);
        return chartDataVO;
    }

    /**
     * @author Created by zk on 2019/6/4 10:26
     * @Description 用于后台
     */
    public Map<String, List<PieChartDataVO>> memberAnalyseChartByChannel(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        List<Member> totalMembers = findByMerchantId(merchantId);
        List<Member> memberList = findByMerchantIdAndCreateTime(merchantId, dateCondition);
        List<PieChartDataVO> totalMemberCount = new ArrayList<>();
        List<PieChartDataVO> newMemberCount = new ArrayList<>();
        final Map<Integer, Long> totalMemberCountMap = totalMembers.stream()
                .collect(Collectors.groupingBy(Member::getChannel, Collectors.counting()));
        final Map<Integer, Long> newMemberCountMap = memberList.stream()
                .collect(Collectors.groupingBy(Member::getChannel, Collectors.counting()));
        pieChartConvert(totalMemberCount, totalMemberCountMap, Member.Channel.class);
        pieChartConvert(newMemberCount, newMemberCountMap, Member.Channel.class);
        Map<String, List<PieChartDataVO>> result = new HashMap<>();
        result.put("totalMemberCount", totalMemberCount);
        result.put("newMemberCount", newMemberCount);
        return result;
    }

    /**
     * @author Created by zk on 2019/6/4 10:26
     * @Description 用于APP
     */
    public List<PieChartDataVO> appMemberAnalyseChartByChannel(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        List<Member> memberList = findByMerchantIdAndCreateTime(merchantId, dateCondition);
        List<PieChartDataVO> totalMemberCount = new ArrayList<>();
        final Map<Integer, Long> totalMemberCountMap = memberList.stream()
                .collect(Collectors.groupingBy(Member::getChannel, Collectors.counting()));
        pieChartConvert(totalMemberCount, totalMemberCountMap, Member.Channel.class);
        return totalMemberCount;
    }

    public Map<String, List<PieChartDataVO>> memberAnalyseChartBySex(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        List<Member> totalMembers = findByMerchantId(merchantId);
        List<Member> memberList = findByMerchantIdAndCreateTime(merchantId, dateCondition);
        List<PieChartDataVO> totalMemberCount = new ArrayList<>();
        List<PieChartDataVO> newMemberCount = new ArrayList<>();
        final Map<Integer, Long> totalMemberCountMap = totalMembers.stream()
                .collect(Collectors.groupingBy(Member::getSex, Collectors.counting()));
        final Map<Integer, Long> newMemberCountMap = memberList.stream()
                .collect(Collectors.groupingBy(Member::getSex, Collectors.counting()));
        pieChartConvert(totalMemberCount, totalMemberCountMap, Member.Sex.class);
        pieChartConvert(newMemberCount, newMemberCountMap, Member.Sex.class);
        Map<String, List<PieChartDataVO>> result = new HashMap<>();
        result.put("totalMemberCount", totalMemberCount);
        result.put("newMemberCount", newMemberCount);
        return result;
    }

    /**
     * @author Created by zk on 2019/6/4 10:30
     * @Description 用于APP
     */
    public List<PieChartDataVO> appMemberAnalyseChartBySex(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        List<Member> memberList = findByMerchantIdAndCreateTime(merchantId, dateCondition);
        List<PieChartDataVO> totalMemberCount = new ArrayList<>();
        final Map<Integer, Long> totalMemberCountMap = memberList.stream().filter(o -> o.getSex() != null).collect(Collectors.groupingBy(Member::getSex, Collectors.counting()));
        pieChartConvert(totalMemberCount, totalMemberCountMap, Member.Sex.class);
        return totalMemberCount;
    }

    /**
     * @author Created by zk on 2019/6/5 15:15
     * @Description 会员活跃度分析
     */
    public List<PieChartDataVO> memberAnalyseConsumeActivityLevel(String merchantId, DateConditionDTO dateCondition) {
        dateCondition.formatBeginAndEnd();
        //获取消费记录在日期区间内并且属于这家商户的用户信息
        final List<Member> memberList = memberRepository.findByMerchantIdAndLastPayDateBetween(merchantId, dateCondition.getStartTime(), dateCondition.getEndTime());
        //获取会员id
        final List<String> memberIds = memberList.stream().map(Member::getId).collect(Collectors.toList());
        //获取消费记录
        final List<StoredRecored> storedRecoredList = storedRecoredService.findForConsumeActivityLevel(merchantId, dateCondition.getStartTime(), dateCondition.getEndTime(), memberIds);
        final Map<String, Long> memberConsumeTimesMap = storedRecoredList.stream()
                .collect(Collectors.groupingBy(StoredRecored::getMemberId, Collectors.counting()));
        final List<Long> times = new ArrayList<>(memberConsumeTimesMap.values());
        List<PieChartDataVO> pieChartDataVOS = new ArrayList<>();
        for (MemberConsumeActivityLevelEnum memberConsumeActivityLevel : MemberConsumeActivityLevelEnum.values()) {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setName(memberConsumeActivityLevel.getDescription());
            final long count = times.stream().filter(memberConsumeActivityLevel.getJudge()).count();
            pieChartDataVO.setValue((int) count);
            pieChartDataVOS.add(pieChartDataVO);
        }
        return pieChartDataVOS;
    }
    //-------------------------------------------------------------------------------------------

    //根据手机号查找会员
    public Member findByPhoneAndMerchantId(String phone, String merchantId) {
        return memberRepository.findByPhoneAndMerchantIdAndDelFlag(phone, merchantId, CommonConstant.NORMAL_FLAG);
    }

    public int countMemberByPhoneAndMerchantIdAndDelFlag(String phone, String merchantId) {
        return memberRepository.countByMerchantIdAndPhoneAndDelFlag(merchantId, phone, CommonConstant.NORMAL_FLAG);
    }

    public boolean countMemberByMemberNumAndMerchantId(String memberNum, String merchantId) {
        return memberRepository.countByMemberNumAndMerchantIdAndDelFlag(memberNum, merchantId, CommonConstant.NORMAL_FLAG) > 0;
    }

    public boolean countMemberByMemberNum(String memberNum) {
        return memberRepository.countByMemberNumAndDelFlag(memberNum, CommonConstant.NORMAL_FLAG) > 0;
    }

    public Member findByMerchantIdAndMemberNum(String merchantId, String memberNum) {
        return memberRepository.findByMerchantIdAndMemberNum(merchantId, memberNum);
    }

    public long countNewMemberByMerchantIdAndCreateTime(String merchantId, DateConditionDTO dateCondition) {
        return memberRepository.countByMerchantIdAndAndCreateTimeBetween(merchantId, dateCondition.getStartTime(), dateCondition.getEndTime());
    }

    public long countNewMemberByMerchantIdAndCreateTimeBefore(String merchantId, Date date) {
        return memberRepository.countByMerchantIdAndAndCreateTimeBefore(merchantId, date);
    }

    public long countNewMemberByMerchantId(String merchantId) {
        return memberRepository.countByMerchantId(merchantId);
    }

    public List<Member> findByMerchantIdAndCreateTime(String merchantId, DateConditionDTO dateConditionDTO) {
        return memberRepository.findByMerchantIdAndCreateTimeBetween(merchantId, dateConditionDTO.getStartTime(), dateConditionDTO.getEndTime());
    }

    public List<Member> findByMerchantId(String merchantId) {
        return memberRepository.findByMerchantIdOrderByCreateTimeDesc(merchantId);
    }

    public Member findByOpenId(String openId) {
        return memberRepository.findByOpenIdAndDelFlag(openId, CommonConstant.NORMAL_FLAG);
    }

    public boolean isNewMember(String merchantId, String phone) {
        return memberRepository.countByMerchantIdAndPhone(merchantId, phone) <= 0;
    }

    public List<Member> findByPhoneLikeOrMemberNumLike(String phoneOrNum) {
        phoneOrNum = "%" + phoneOrNum + "%";
        return memberRepository.findByPhoneLikeOrMemberNumLikeOrderByLastPayDateDescCreateTimeDesc(phoneOrNum, phoneOrNum);
    }


    private void pieChartConvert(List<PieChartDataVO> newMemberCount, Map<Integer, Long> newMemberCountMap, Class<? extends CodeEnum> tClass) {
        newMemberCountMap.forEach((key, value) -> {
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setName(EnumUtils.findByCode(key, tClass));
            pieChartDataVO.setValue(value.intValue());
            newMemberCount.add(pieChartDataVO);
        });
    }

    private List<String> dateRangeList(Date startDate, Date endDate, DateField dateField, String format) {
        List<DateTime> range = DateUtil.rangeToList(startDate, endDate, dateField);
        List<String> xData = range.stream().map(dateTime -> DateUtil.format(dateTime, format)).collect(Collectors.toList());
        return xData;
    }


    public Member check(Member member) {
        Member oldmember = memberRepository.findByMerchantIdAndPhone(member.getMerchantId(),member.getPhone());
        return oldmember;
    }

    public Member findByMerchantIdAndOpenIdAndDelFlag(String merchantId, String openId, Integer delFlag) {
        return this.memberRepository.findByMerchantIdAndOpenIdAndDelFlag(merchantId, openId, delFlag);
    }

    public Member findByMerchantIdAndBuyerIdAndDelFlag(String merchantId, String buyerId, Integer delFlag) {
        return this.memberRepository.findByMerchantIdAndBuyerIdAndDelFlag(merchantId, buyerId, delFlag);
    }

    public Member findByBuyerId(String buyerId,String merchantId){
        return memberRepository.findByBuyerIdAndMerchantId(buyerId,merchantId);
    }

}
