package com.fzy.admin.fp.member.coupon.controller;

import cn.hutool.core.text.StrFormatter;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.CheckCodeService;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/5/27 11:35
 * @Description
 */
@RestController
@RequestMapping(value = "/member/person_coupon")
@Slf4j
public class PersonCouponController extends BaseController<PersonCoupon> {

    @Resource
    private PersonCouponService personCouponService;

    @Resource
    private CheckCodeService checkCodeService;

    @Resource
    private MemberRepository memberRepository;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Override
    public BaseService<PersonCoupon> getService() {
        return personCouponService;
    }


    /**
     * @param userId   收银员id，前端有传收银员id则获取会员优惠券带这家门店参数
     * @param memberId 会员id
     * @author Created by wtl on 2019/6/28 15:02
     * @Description H5页面获取会员对应这家商户的卡券，无token
     */
    @GetMapping("/member_coupon_list")
    public Resp memberCouponList(String userId, String memberId) {
        if (ParamUtil.isBlank(memberId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误，会员id不能为空");
        }
        String storeId = "";
        // 收银员id不为空，则获取门店id
        if (!ParamUtil.isBlank(userId)) {
            MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
            if (ParamUtil.isBlank(merchantUserDTO)) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "获取收银员信息失败");
            }
            storeId = merchantUserDTO.getStoreId();
        }
        //查找所有个人有效卡券
        List<PersonCoupon> personCoupons = personCouponService.getRepository().findPersonCouponByMemberIdAndStatus(memberId, PersonCoupon.Status.NO_USE.getCode());
        // 过期作废，
        Iterator<PersonCoupon> iterator = personCoupons.iterator();
        while (iterator.hasNext()) {
            PersonCoupon personCoupon = iterator.next();
            if (personCoupon.getValidTimeEnd().getTime() < System.currentTimeMillis()) {
                personCoupon.setStatus(PersonCoupon.Status.INVALID.getCode());
                personCouponService.save(personCoupon);
                // 集合中去除过期卡券
                iterator.remove();
                continue;
            }
            // 如果有门店id则只筛选包含门店id的卡券
            if (!ParamUtil.isBlank(storeId) && !personCoupon.getStoreIds().contains(storeId)) {
                iterator.remove();
            }
        }
        log.info("memberId---------->>>>>,{}", memberId);
        // 获取会员卡余额
        Member member = memberRepository.findOne(memberId);
        if (ParamUtil.isBlank(member)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "获取会员信息失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("personCoupons", personCoupons);
        map.put("balance", member.getBalance());
        return Resp.success(map);
    }

    @GetMapping(value = "/coupon_info")
    public Resp getCoupons(@TokenInfo(property = "merchantId") String merchantId, PersonCoupon personCoupon,
                           PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        //查找所有个人卡券，对有效状态进行更新
        List<PersonCoupon> personCoupons = personCouponService.findAll(merchantId, personCoupon.getMemberId());
        for (PersonCoupon personCouponl : personCoupons) {
            if (!personCouponl.getStatus().equals(PersonCoupon.Status.USE.getCode())) {
                if (personCouponl.getValidTimeEnd().getTime() < System.currentTimeMillis()) {
                    personCouponl.setStatus(PersonCoupon.Status.INVALID.getCode());
                    personCouponService.save(personCouponl);
                }
            }
        }
        return list(personCoupon, pageVo);
    }

    @GetMapping(value = "/test")
    public Resp getCounts(@TokenInfo(property = "merchantId") String merchantId, Integer status) {
        return Resp.success(personCouponService.getByCount(merchantId, status));
    }

    @GetMapping(value = "/tes")
    public Resp getDays(@TokenInfo(property = "merchantId") String merchantId, String couponId) {
        return Resp.success(personCouponService.getDays(merchantId, couponId));
    }


    @PostMapping(value = "/update_syn")
    public Resp updateSyn(String personCouponId) {
        PersonCoupon personCoupon = personCouponService.findOne(personCouponId);
        if (personCoupon == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "个人卡券参数错误");
        }
        personCoupon.setSynStatus(PersonCoupon.SynStatus.SYN.getCode());
        personCouponService.update(personCoupon);
        return Resp.success("同步成功");
    }

    //卡券核销接口
    @PostMapping(value = "/check_code")
    @Transactional
    public Resp checkCode(@TokenInfo(property = "merchantId") String merchantId, String code) {
        checkCodeService.checkCode(merchantId, code);
        return Resp.success("卡券使用成功");
    }

    @GetMapping(value = "/wx_login")
    public void WXLogin(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, String merchantId) throws IOException {
        if (merchantId == null) {
            throw new BaseException("请携带商户Id");
        }
        // 获取微信公众号参数
        Map<String, String> wxConfig = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_base&state={}#wechat_redirect";
        String redirectUrl = StrFormatter.format(url, wxConfig.get("appId"), getDomain() + "/member/person_coupon/wx_code", merchantId);
        System.out.println("----重定向地址------>>>" + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping(value = "/wx_code")
    public void WXCode(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, String code, String state) throws IOException {
        // 获取微信公众号参数
        Map<String, String> wxConfig = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
        url = StrFormatter.format(url, wxConfig.get("appId"), wxConfig.get("appSecret"), code);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String redirectUrl = "";
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Map<String, Object> map = new HashMap<>();
                map = JacksonUtil.toObjectMap(result);
                String openId = (String) map.get("openid");
                Member member = memberRepository.findByOfficialOpenIdAndMerchantIdAndDelFlag(openId, state, 1);
                String memberId = "";
                if (member != null) {
                    memberId = member.getId();
                }
                redirectUrl = getDomain() + "/#/wx/paySelect?openId=" + openId + "&memberId=" + memberId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----重定向地址------>>>" + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping({"/my_coupon_info"})
    public Resp getMyCoupons(@TokenInfo(property = "merchantId") String merchantId, PersonCoupon personCoupon, PageVo pageVo) {
        if (merchantId == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        String storeId = personCoupon.getStoreIds();
        personCoupon.setStoreIds(null);
        String memberId = personCoupon.getMemberId();
        personCoupon.setMemberId(null);
        correctPersonCoupons(merchantId, personCoupon);
        personCoupon.setMemberId(memberId);
        Page<PersonCoupon> page = this.personCouponService.list(personCoupon, pageVo);

        List<PersonCoupon> collect = (List)page.getContent().stream().filter(personCoupon1 -> (personCoupon1.getStoreIds().contains(storeId) && !this.checkCodeService.validCoupons(personCoupon1.getUseTimeWeek(), personCoupon1.getUseTimeDay()))).collect(Collectors.toList());
        return Resp.success(collect);
    }

    private void correctPersonCoupons(@TokenInfo(property = "merchantId") String merchantId, PersonCoupon personCoupon) {
        List<PersonCoupon> personCoupons = this.personCouponService.findAll(merchantId, personCoupon.getMemberId());
        for (PersonCoupon personCouponl : personCoupons) {
            if (!personCouponl.getStatus().equals(PersonCoupon.Status.USE.getCode()) &&
                    personCouponl.getValidTimeEnd().compareTo(new Date()) < 0) {
                personCouponl.setStatus(PersonCoupon.Status.INVALID.getCode());
                this.personCouponService.save(personCouponl);
            }
        }
    }


}
