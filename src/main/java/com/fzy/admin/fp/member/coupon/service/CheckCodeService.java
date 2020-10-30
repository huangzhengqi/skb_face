package com.fzy.admin.fp.member.coupon.service;

import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.credits.service.ExchangeProductService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.credits.service.ExchangeProductService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author lb
 * @date 2019/7/1 14:43
 * @Description 调用核销服务
 */
@Service
@Slf4j
public class CheckCodeService {

    @Resource
    private PersonCouponService personCouponService;

    @Resource
    private CouponService couponService;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    @Resource
    protected HttpServletRequest request;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private ExchangeProductService exchangeProductService;

    @Value("${secret.header}")
    private String header;

    public void checkCode(String merchantId, String code) {

        PersonCoupon personCoupon = personCouponService
                .findByMerchantIdAndStatusAndCode(merchantId, PersonCoupon.Status.NO_USE.getCode(), code);
        log.info("personCoupon" + personCoupon);
        if (personCoupon == null) {
            throw new BaseException("条码不存在");
        } else if (personCoupon.getStatus().equals(PersonCoupon.Status.USE.getCode())) {
            throw new BaseException("此条码使用过了");
        }
        timeValid(personCoupon);
        Coupon coupon = couponService.findOne(personCoupon.getCouponId());
        if (coupon == null) {
            throw new BaseException("卡券不存在");
        }
        if (personCoupon.getStatus().equals(PersonCoupon.SynStatus.SYN.getCode())) {
            //对同步到卡包的卡券进行核销预处理
            String appId = aliConfigServiceFeign.getAppId(merchantId);
            String appSecret = aliConfigServiceFeign.getAppSecret(merchantId);
            String cardId = coupon.getCardId();
            String result = WXUtil.checkStatus(cardId, code, appId, appSecret);
            Map<String, Object> map = JacksonUtil.toObjectMap(result);
            if (map.get("errcode").equals(0)) {
                System.out.println("一切正常");
                //开始核销
                String result1 = WXUtil.destoryCoupon(cardId, code, appId, appSecret);
                Map<String, Object> map1 = JacksonUtil.toObjectMap(result1);
                if (map1.get("errcode").equals(0)) {
                    System.out.println("微信卡包核销成功");
                } else {
                    throw new BaseException("卡券核销失败");
                }
            } else {
                throw new BaseException("卡券状态异常");
            }
        }
        personCoupon.setStatus(PersonCoupon.Status.USE.getCode());
        //获取当前登录用户id
        String header1 = request.getHeader(header);
        String currentUserId = JwtUtil.getPayloadProperty(header1, "sub");
        MerchantUser merchantUser = merchantUserService.findOne(currentUserId);
        personCoupon.setUserName(merchantUser.getName());
        personCoupon.setValidTime(new Date());
        personCoupon.setUserId(currentUserId);
        personCouponService.update(personCoupon);


    }

    //验证用券日期合法性
    public void timeValid(PersonCoupon personCoupon) {
        String timeWeek = personCoupon.getUseTimeWeek();
        String[] useTimeWeek = timeWeek.split(",");
        String timeDay = personCoupon.getUseTimeDay();
        String[] useTimeDay = timeDay.split("-");
        String startTime = useTimeDay[0];
        String endTime = useTimeDay[1];
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        String hours = sd.format(date);
        int weekDay = DateUtil.thisDayOfWeek() - 1;
        if (weekDay == 0) {
            weekDay = 7;
        }
        boolean is = true;
        for (String ss : useTimeWeek) {
            if (String.valueOf(weekDay).equals(ss)) {
                try {
                    Date hour = sd.parse(hours);
                    Date start = sd.parse(startTime);
                    Date end = sd.parse(endTime);
                    if (hour.after(start) && hour.before(end)) {
                        is = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (is) {
            throw new BaseException("当前时间段不能使用该优惠券");
        }
    }


    public boolean validCoupons(String timeWeek, String timeDay) {
        String[] useTimeWeek = timeWeek.split(",");
        String[] useTimeDay = timeDay.split("-");
        String startTime = useTimeDay[0];
        String endTime = useTimeDay[1];

        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        String hours = sd.format(date);
        int weekDay = DateUtil.thisDayOfWeek() - 1;
        if (weekDay == 0) {
            weekDay = 7;
        }
        boolean is = true;
        for (String ss : useTimeWeek) {
            if (String.valueOf(weekDay).equals(ss)) {
                try {
                    Date hour = sd.parse(hours);
                    Date start = sd.parse(startTime);
                    Date end = sd.parse(endTime);
                    if (hour.after(start) && hour.before(end)) {
                        is = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return is;
    }
}
