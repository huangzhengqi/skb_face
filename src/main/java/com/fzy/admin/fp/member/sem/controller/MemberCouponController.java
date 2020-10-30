package com.fzy.admin.fp.member.sem.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.jpa.RelationUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import com.fzy.admin.fp.member.sem.domain.MemberCoupon;
import com.fzy.admin.fp.member.sem.dto.MemberCouponDTO;
import com.fzy.admin.fp.member.sem.service.MemberCouponService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Api(value = "MemberCouponController",tags = {"支付宝优惠券相关"})
@RequestMapping("/member/sem/coupon")
@RestController
@Slf4j
public class MemberCouponController {

    @Resource
    private MemberCouponService memberCouponService;

    @PersistenceContext
    private EntityManager em;

    @Resource
    private StoreService storeService;

    @ApiOperation(value = "保存优惠券",notes = "保存优惠券")
    @PostMapping("/save")
    public Resp saveCoupon(MemberCoupon memberCoupon){
        return memberCouponService.saveCoupon(memberCoupon);
    }

    @ApiOperation(value = "更新优惠劵",notes = "更新优惠劵")
    @PostMapping("/update")
    public Resp updateCoupon(@Valid MemberCoupon memberCoupon) {
        return memberCouponService.updateCoupon(memberCoupon);
    }

    @ApiOperation(value="删除优惠劵",notes = "删除优惠劵")
    @PostMapping("/delete")
    public Resp deleteCoupon(String[] ids) {
        List<MemberCoupon> list = memberCouponService.findAll(ids);
        for (MemberCoupon coupon : list) {
            if (coupon == null) {
                continue;
            }
            RelationUtil.delRelation(coupon, em, true);
            coupon.setDelFlag(CommonConstant.DEL_FLAG);
        }
        memberCouponService.save(list);
        return Resp.success("删除成功");
    }

    @ApiOperation(value = "优惠券列表",notes = "优惠券列表")
    @GetMapping("/list")
    public Resp list(MemberCouponDTO memberCouponDTO, PageVo pageVo) {
        memberCouponDTO.setDelFlag(CommonConstant.NORMAL_FLAG);
        return Resp.success(memberCouponService.list(memberCouponDTO,pageVo));
    }

    @ApiOperation(value="查询优惠劵",notes = "查询优惠劵")
    @GetMapping("/findOne")
    public Resp findOne(String couponId) {
        return Resp.success(memberCouponService.findOne(couponId));
    }

    @ApiOperation(value = "查找店铺",notes = "查找店铺")
    @GetMapping(value = "/findStore")
    public Resp findByStores(Store store, PageVo pageVo) {
        return Resp.success(storeService.list(store,pageVo));
    }
}
