package com.fzy.admin.fp.member.coupon.service;


import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.CouponTest;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.dto.CouponAnalysis;
import com.fzy.admin.fp.member.coupon.dto.DayNum;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.repository.StoredRecoredRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/6/6 10:49
 * @Description 营销分析
 */
@Service
public class AnalysisService {

    @Resource
    private PersonCouponService personCouponService;

    @Resource
    private StoredRecoredRepository storedRecoredRepository;

    public CouponAnalysis getAnalysis(String merchantId, List<Coupon> coupons) {

        //卡券活动信息
        Coupon coupon = coupons.get(0);
        //该卡券类型已使用的所有记录
        List<PersonCoupon> personCoupons = personCouponService.findByMerchntIdAndCouponIdAndStatus(merchantId,
                coupon.getId(), PersonCoupon.Status.USE.getCode());

        CouponAnalysis couponAnalysis = new CouponAnalysis();
        //投放
        couponAnalysis.setTotalNum(coupon.getTotalInventory());
        //领取
        couponAnalysis.setReceiveNum(coupon.getChangeInventory());
        //使用
        couponAnalysis.setUsedNum(personCoupons.size());
        BigDecimal b = new BigDecimal(personCoupons.size());
        BigDecimal c = new BigDecimal(coupon.getChangeInventory());
        if (coupon.getChangeInventory() > 0) {
            //转化
            couponAnalysis.setConversion(b.divide(c, 2, BigDecimal.ROUND_HALF_UP));
        }
        //抵用
        couponAnalysis.setPreferentialMoney(coupon.getMoney().multiply(b));

        //带动消费 查询使用该券产生的流水
        List<StoredRecored> storedRecoreds = storedRecoredRepository.findByCouponId(coupon.getId());
        BigDecimal tradeMoney = new BigDecimal(0);
        for (StoredRecored storedRecored : storedRecoreds) {
            tradeMoney = tradeMoney.add(storedRecored.getTradingMoney());
        }
        couponAnalysis.setOrderTotalMoney(tradeMoney);
        //活动拉新
        //1.消费者首次用该券消费记录并且无其他的消费记录与流水记录
        List<CouponTest> testList = personCouponService.getByCount(merchantId, 2);
        //2.查到只用过一张卡券的用户集合
        List<CouponTest> couponTests = testList.stream()
                .filter(e -> e.getCouCount().equals(Long.valueOf(1))).collect(Collectors.toList());
        System.out.println("-------->>>" + couponTests);

        //3.判断会员用的是这张券
        int newNum = 0;
        for (CouponTest couponTest : couponTests) {
            String memberId = couponTest.getMemberId();
            List<PersonCoupon> personCoupons1 = personCouponService.findByCouponId(merchantId, memberId, coupon.getId());
            if (personCoupons1 != null && personCoupons1.size() == 1) {
                //扩展该会员消费记录只有一条再进行自增
                if (storedRecoredRepository.countByMemberId(memberId) == 1) {
                    newNum++;
                }
            }
        }
        couponAnalysis.setNewNum(newNum);
        System.out.println("拉新人数--->" + newNum);

        //4.回头客人数
        List<CouponTest> oldTests = testList.stream()
                .filter(e -> e.getCouCount() >= 1).collect(Collectors.toList());
        int oldNum = 0;
        //多条会员记录的id集合
        List<String> memberIds = new ArrayList<>();
        //对应记录生成时间
        Map<String, Date> members = new HashMap<>();
        for (CouponTest couponTest1 : oldTests) {
            String memberId = couponTest1.getMemberId();
            List<PersonCoupon> personCoupons1 = personCouponService.findByCouponId(merchantId, memberId, coupon.getId());
            if (personCoupons1 != null && personCoupons1.size() > 1) {
                //扩展该用户流水记录只有多条再进行自增
                if (storedRecoredRepository.countByMemberId(memberId) > 1) {
                    oldNum++;
                    memberIds.add(memberId);
                    //存会员号和时间
                    members.put(memberId, personCoupons1.get(0).getUpdateTime());
                }
            }
        }
        couponAnalysis.setRepeatCustomersNum(oldNum);
        System.out.println("回头人数--->" + oldNum);

        int saveNum = 0;
        //5.挽回流失人数
        if (oldNum > 0) {
            //查找客户三个月内是否存在消费订单
            for (String memberId : memberIds) {
                Date date = members.get(memberId);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, -3);
                //三月前时间
                Date date1 = calendar.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String ddate = simpleDateFormat.format(date);
                String date11 = simpleDateFormat.format(date1);
                List<StoredRecored> storedRecoredList = storedRecoredRepository.getBytimes(memberId, date11, ddate);
                if (storedRecoredList != null && storedRecoredList.size() > 0) {
                    saveNum++;
                }
            }
        }
        couponAnalysis.setSaveLoseNum(saveNum);
        System.out.println("----挽留人数->>" + saveNum);

        if (newNum != 0 || oldNum != 0) {
            double aa = (double) newNum;
            double bb = (double) saveNum;
            //新人比率
            double cc = aa / (aa + oldNum + bb);
            //回头比率
            double dd = oldNum / (aa + oldNum + bb);
            //挽回流失人数
            double ee = bb / (aa + oldNum + bb);
            couponAnalysis.setNewAccountedFor(new BigDecimal(cc));
            couponAnalysis.setRepeatAccountedFor(new BigDecimal(dd));
            couponAnalysis.setSaveAccountedFor(new BigDecimal(ee));
            System.out.println("-----比率>>" + cc + dd + ee);
        } else {
            couponAnalysis.setNewAccountedFor(new BigDecimal(0));
            couponAnalysis.setRepeatAccountedFor(new BigDecimal(0));
            couponAnalysis.setSaveAccountedFor(new BigDecimal(0));
        }

        System.out.println("---->>>比率通过");
        //核销会员用该卡券消费距离上一次多少天 7 30 90
        List<PersonCoupon> unique = personCoupons.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(Comparator.comparing(PersonCoupon::getMemberId))
                                ), ArrayList::new
                        )
                );
        //得到会员名字不一样的集合判断集合中会员上一次消费距离计算数量
        int sevenDayNum = 0;
        int oneMonthNum = 0;
        int threeMonthNum = 0;
        for (PersonCoupon personCoupon : unique) {
            String memberId = personCoupon.getMemberId();
            Date time = personCoupon.getUpdateTime();
            Long dtime = time.getTime();
            Long etime = dtime - (7 * 24 * 60 * 60 * 1000);
            Date sstime = new Date(etime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String stime = sdf.format(sstime);
            String ttime = sdf.format(time);
            System.out.println(stime + "-------" + ttime);
            List<StoredRecored> storedRecoredList = storedRecoredRepository.getBytimes(memberId, stime, ttime);
            if (storedRecoredList.size() > 0) {
                sevenDayNum++;
            } else {
                Long tttime = dtime - (24 * 60 * 60 * 1000 * 30);
                Date monthTime = new Date(tttime);
                String mmonthTime = sdf.format(monthTime);
                if (storedRecoredRepository.getBytimes(memberId, mmonthTime, ttime).size() > 0) {
                    oneMonthNum++;
                } else {
                    Long ltime = dtime - (24 * 60 * 60 * 1000 * 30 * 3);
                    Date threeMonthTime = new Date(ltime);
                    String tthreeMonthTime = sdf.format(threeMonthTime);
                    if (storedRecoredRepository.getBytimes(memberId, tthreeMonthTime, ttime).size() > 0) {
                        threeMonthNum++;
                    }
                }
            }
        }
        couponAnalysis.setSevenDayNum(sevenDayNum);
        couponAnalysis.setOneMonthNum(oneMonthNum);
        couponAnalysis.setThreeMonthNum(threeMonthNum);
        System.out.println("-----会员回头通过>>>");
        //占比计算
        int totalCon = sevenDayNum + oneMonthNum + threeMonthNum;
        if (totalCon > 0) {
            double seCon = (double) sevenDayNum / totalCon;
            double onCon = (double) oneMonthNum / totalCon;
            double thCon = (double) threeMonthNum / totalCon;
            couponAnalysis.setSevenConversion(new BigDecimal(seCon));
            couponAnalysis.setOneMonthConversion(new BigDecimal(onCon));
            couponAnalysis.setThreeMonthConversion(new BigDecimal(thCon));
        } else {
            couponAnalysis.setSevenConversion(new BigDecimal(0));
            couponAnalysis.setOneMonthConversion(new BigDecimal(0));
            couponAnalysis.setThreeMonthConversion(new BigDecimal(0));
        }

        System.out.println("------>>>占比通过");
        //折线图
        List<DayNum> dayNums = personCouponService.getDays(merchantId, coupon.getId());
        System.out.println("--------->" + dayNums);
        if (dayNums != null) {
            String[] date = new String[dayNums.size()];
            for (int i = 0; i < dayNums.size(); i++) {
                date[i] = dayNums.get(i).getDays();
            }
            Long[] count = new Long[dayNums.size()];
            for (int i = 0; i < dayNums.size(); i++) {
                count[i] = dayNums.get(i).getCount();
            }
            couponAnalysis.setTime(date);
            couponAnalysis.setNewNumber(count);
        }

        return couponAnalysis;
    }
}
