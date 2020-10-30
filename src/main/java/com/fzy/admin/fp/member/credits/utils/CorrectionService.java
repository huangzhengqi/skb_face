package com.fzy.admin.fp.member.credits.utils;


import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.repository.CreditsProductRepository;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.repository.CouponRepository;
import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.repository.CreditsProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/16 15:35
 * @Description 时刻对当前限时活动判断是否过期，修正非人为停止的活动状态
 */
@Service
public class CorrectionService {

    @Resource
    private CreditsProductRepository creditsProductRepository;

    @Resource
    private CouponRepository couponRepository;

    //修改积分活动
    public void correction(List<CreditsProduct> creditsProducts) {
        //对所有活动状态进行检测
        for (CreditsProduct creditsProduct1 : creditsProducts) {
            if (creditsProduct1.getInterrupt().equals(0)) {
                long a = System.currentTimeMillis();
                long b = creditsProduct1.getExchangeStart().getTime();
                long c = creditsProduct1.getExchangeEnd().getTime();
                System.out.println("当前时间：" + a);
                System.out.println("活动开始时间" + b);
                System.out.println("活动结束时间：" + c);
                if (a < b && (!(creditsProduct1.getStatus().equals(0)))) {
                    creditsProduct1.setStatus(CreditsProduct.Status.NO_START.getCode());
                    creditsProductRepository.save(creditsProduct1);
                } else if (a > c && (!(creditsProduct1.getStatus().equals(2)))) {
                    creditsProduct1.setStatus(CreditsProduct.Status.END.getCode());
                    creditsProductRepository.save(creditsProduct1);
                } else if (a > b && a < c && (!(creditsProduct1.getStatus().equals(1)))) {
                    creditsProduct1.setStatus(CreditsProduct.Status.START.getCode());
                    creditsProductRepository.save(creditsProduct1);
                }
            }
        }
        //检测修正完毕
    }

    //修改卡券活动
    public void correctionCoupon(List<Coupon> couponList) {

        //对所有活动状态进行检测
        for (Coupon coupon : couponList) {
            if (coupon.getInterrupt().equals(0)) {
                long a = System.currentTimeMillis();
                long b = coupon.getActTimeStart().getTime();
                long c = coupon.getActTimeEnd().getTime();
                /*System.out.println("当前时间："+ a);
                System.out.println("活动开始时间"+ b);
                System.out.println("活动结束时间："+ c);*/
                if (a < b && (!(coupon.getActStatus().equals(1)))) {
                    coupon.setActStatus(1);
                    couponRepository.save(coupon);
                } else if (a > c && (!(coupon.getActStatus().equals(3)))) {
                    coupon.setActStatus(3);
                    couponRepository.save(coupon);
                } else if (a > b && a < c && (!(coupon.getActStatus().equals(2)))) {
                    coupon.setActStatus(2);
                    couponRepository.save(coupon);
                }
            }
        }
        //检测修正完毕
    }

}
