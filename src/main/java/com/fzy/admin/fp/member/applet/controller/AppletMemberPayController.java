package com.fzy.admin.fp.member.applet.controller;

import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.member.applet.dto.AppletMemberPayDTO;
import com.fzy.admin.fp.member.applet.dto.AppletOrderDTO;
import com.fzy.admin.fp.member.applet.service.AppletMemberPayService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.pc.vo.PayResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-06-17 9:35
 * @description
 */
@RestController
@RequestMapping("/member/pay/applet")
@Api(value = "AppletMemberPayController", tags = "小程序会员卡支付接口")
public class AppletMemberPayController extends BaseContent {

    @Resource
    private AppletMemberPayService appletMemberPayService;

    @ApiOperation(value = "生成会员付款码", notes = "生成会员付款码")
    @PostMapping("/create_code")
    public Resp createAuthCode(@UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员参数错误");
        }
        Member member = appletMemberPayService.memberService.findOne(userId);
        if (ParamUtil.isBlank(member)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员参数错误");
        }
        if (!CommonConstant.NORMAL_FLAG.equals(member.getDelFlag())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员已被禁用");
        }
        String authCode = "";
        while (true) {
            // 会员付款码以"MPC-"开头
            authCode = "MPC-" + RandomUtil.randomNumbers(18);
            String s = MemberConstant.MEMBER_PAY_CODE_CACHE.get(authCode);
            if (ParamUtil.isBlank(s)) {
                break;
            }
        }
        // 存到缓存
        MemberConstant.MEMBER_PAY_CODE_CACHE.put(authCode, member.getMemberNum());
        return Resp.success(authCode, "生成会员付款码");
    }

    @ApiOperation(value = "小程序充值", notes = "小程序充值")
    @GetMapping({"/store_money"})
    public Resp storeMoney(@UserId String id, AppletMemberPayDTO appletMemberPayDTO) throws Exception {
        return Resp.success(this.appletMemberPayService.storeMoney(id, appletMemberPayDTO), "小程序充值预下单");
    }

    @ApiOperation(value = "设备会员充值", notes = "设备会员充值")
    @ApiResponse(code = 200,message = "OK",response = PayResult.class)
    @GetMapping({"/store_money_member"})
    public Resp storeMoneyMember(String memberId, AppletMemberPayDTO appletMemberPayDTO, OrderDto orderDto) throws Exception {
        return this.appletMemberPayService.storeMoneyMember(memberId, appletMemberPayDTO, orderDto);
    }

    @GetMapping({"/order"})
    public Resp order(@UserId String id, AppletOrderDTO appletOrderDTO) throws Exception {
        return Resp.success(this.appletMemberPayService.order(id, appletOrderDTO), "小程序扫码点餐");
    }
}
