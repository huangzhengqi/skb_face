package com.fzy.admin.fp.member.member.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.dto.MemberCardDTO;
import com.fzy.admin.fp.member.member.service.MemberCardService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author ：drj.
 * @Date  ：Created in 14:29 2019/5/31
 * @Description:  商户后台会员卡控制层
 **/
@Slf4j
@RestController
@RequestMapping("/member/member/card")
@Api(value = "MemberCardController", tags = {"商户后台会员卡控制层"})
public class MemberCardController extends BaseController<MemberCard> {

    @Resource
    private MemberCardService memberCardService;

    @Override
    public MemberCardService getService() {
        return memberCardService;
    }

    @ApiOperation(value = "商户后台创建会员卡", notes = "商户后台创建会员卡")
    @PostMapping("/create")
    public Resp createCard(@UserId String userId, MemberCardDTO memberCardDTO) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token传值有误");
        }
        return memberCardService.createCard(userId,memberCardDTO);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查询会员卡详情",notes = "查询会员卡详情")
    public Resp detail(@TokenInfo(property = "merchantId") String merchantId) {
        return memberCardService.detail(merchantId);
    }


    @ApiOperation(value = "普通一键激活会员卡", notes = "普通一键激活会员卡")
    @PostMapping("/activateuser/form")
    public Resp activateuserform(@UserId String userId, String cardId) {
        return memberCardService.activateuserform(userId, cardId);
    }

    @ApiOperation(value = "创建二维码接口",notes = "创建二维码接口")
    @PostMapping("/qrcode/create")
    public Resp cardQrcodeCreate(@TokenInfo(property = "merchantId") String merchantId){
        return memberCardService.cardQrcodeCreate(merchantId);
    }

    @ApiOperation(value = "设置测试白名单",notes = "设置测试白名单")
    @PostMapping("/testwhitelist/set")
    public Resp testwhitelistSet(@TokenInfo(property = "merchantId") String merchantId,String openId){
        return memberCardService.testwhitelistSet(merchantId,openId);
    }
}
