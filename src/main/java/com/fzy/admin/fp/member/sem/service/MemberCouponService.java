package com.fzy.admin.fp.member.sem.service;


import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.domain.ReceiveCoupon;
import com.fzy.admin.fp.member.sem.domain.MemberCoupon;
import com.fzy.admin.fp.member.sem.repository.MemberCouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MemberCouponService implements BaseService<MemberCoupon>{

    @Resource
    private MemberCouponRepository memberCouponRepository;

    @Override
    public BaseRepository<MemberCoupon> getRepository() {
        return memberCouponRepository;
    }


    public Resp<MemberCoupon> saveCoupon(MemberCoupon memberCoupon){
        log.info("-------------------------------------------------------------------------------{}",memberCoupon);
        if(StringUtils.isBlank(memberCoupon.getName())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"名称不能为空");
        }
        if(("2").equals(memberCoupon.getValidType())) {
            if(memberCoupon.getClaimedTime()<=0){
                return  new Resp<>().error(Resp.Status.PARAM_ERROR,"卡券领取后有效期为空");
            }
        }
        return Resp.success(memberCouponRepository.save(memberCoupon),"保存成功");
    }

    public Resp<MemberCoupon> updateCoupon(MemberCoupon memberCoupon){
        if(StringUtils.isBlank(memberCoupon.getName())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"名称不能为空");
        }
        if(("2").equals(memberCoupon.getValidType())) {
            if(memberCoupon.getClaimedTime()<=0){
                return  new Resp<>().error(Resp.Status.PARAM_ERROR,"卡券领取后有效期为空");
            }
        }
        if(null == memberCoupon.getId()) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        MemberCoupon  coupon= memberCouponRepository.findOne(memberCoupon.getId());
        BeanUtil.copyProperties(memberCoupon,coupon);
        return Resp.success(memberCouponRepository.save(coupon),"保存成功");
    }
}
