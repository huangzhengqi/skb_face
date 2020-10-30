package com.fzy.admin.fp.member.coupon.service;


import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.repository.PersonCouponRepository;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author lb
 * @date 2019/6/28 15:15
 * @Description 模板消息
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class MessageTemplateService {

    @Resource
    private PersonCouponRepository personCouponRepository;

    @Resource
    private MemberService memberService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Scheduled(initialDelay = 10000, fixedDelay = 60000 * 60 * 24)
    public void sendMessage() {

        // TODO: 模板消息id暂时写死，需要表配置
        String templateId = "EusoDHwabGV794qdLk6c4sncmKYvx2jWG8OzbOlFDoc";
        //获取到需要发送提醒的卡券
        List<PersonCoupon> personCouponList = personCouponRepository.getPersonCouponByRemindTimes();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (!personCouponList.isEmpty()) {
            for (PersonCoupon personCoupon : personCouponList) {
                String memberId = personCoupon.getMemberId();
                // 通过会员id获取商户id再获取服务商id
                Member member = memberService.getRepository().findOne(memberId);
                if (ParamUtil.isBlank(member)) {
                    log.error("personCoupon scheduled member is null,memberId:{}", memberId);
                }
                Merchant merchant = merchantService.getRepository().findOne(member.getMerchantId());
                if (ParamUtil.isBlank(merchant)) {
                    log.error("person coupon  scheduled merchant is null,merchantId:{}", member.getMerchantId());
                }
                // 获取服务商公众号参数
                Map<String, String> wxConfigMap = wxConfigServiceFeign.getWxConfig(merchant.getServiceProviderId());
                if ("error".equals(wxConfigMap.get("msg"))) {
                    log.error("请先配置微信支付参数");
                }
                String appId = wxConfigMap.get("appId");
                String appSecret = wxConfigMap.get("apppSecret");
                String openId = memberService.findOne(memberId).getOfficialOpenId();
                String couponName = personCoupon.getName();
                String code = personCoupon.getCode();
                String time = sdf.format(personCoupon.getValidTimeEnd());
                if (openId == null) {
                    System.out.println("未获取openId,无法发送过期提醒");
                } else {
                    String result = WXUtil.sendMessage(appId, appSecret, openId, templateId, couponName, code, time);
                    if (JacksonUtil.toObjectMap(result).get("errcode").equals(0)) {
                        System.out.println("卡券过期提醒发送成功！");
                        personCoupon.setRemindTimes(0);
                        personCouponRepository.save(personCoupon);
                    }
                }
            }
        }
    }

}
