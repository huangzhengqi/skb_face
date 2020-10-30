package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.dto.MchBlankQrCodeDTO;
import com.fzy.admin.fp.merchant.merchant.service.CollectionEquipmentService;
import com.fzy.admin.fp.merchant.merchant.service.MchBlankQrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author hzq
 * @Date 2020/10/22 14:26
 * @Version 1.0
 * @description 收款设备
 */
@Slf4j
@RestController
@RequestMapping("/merchant/collection/equipment")
@Api(value = "CollectionEquipmentController", tags = {"收款设备相关"})
public class CollectionEquipmentController {

    @Resource
    private CollectionEquipmentService collectionEquipmentService;
    @Resource
    private MchBlankQrCodeService mchBlankQrCodeService;

    @ApiOperation(value = "收款设备列表", notes = "收款设备列表")
    @GetMapping("/list")
    public Resp collectionEquipment(@UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            throw new BaseException("token错误", Resp.Status.TOKEN_ERROR.getCode());
        }
        return collectionEquipmentService.list(userId);
    }

    @ApiOperation(value = "所属门店", notes = "所属门店")
    @GetMapping("/get/store")
    public Resp getStore(@UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            throw new BaseException("token错误", Resp.Status.TOKEN_ERROR.getCode());
        }
        return collectionEquipmentService.getStore(userId);
    }

    @ApiOperation(value = "所属收银员", notes = "所属收银员")
    @GetMapping("/get/userId")
    public Resp getUserId(String storeId) {
        if (ParamUtil.isBlank(storeId)) {
            throw new BaseException("storeId不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        return collectionEquipmentService.getUserId(storeId);
    }

    @ApiOperation(value = "商户添加收款码贴牌", notes = "商户添加收款码贴牌")
    @PostMapping("/add/merchant/qrcode")
    public Resp addMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        return Resp.success(mchBlankQrCodeService.addMerchantQrcode(mchBlankQrCodeDTO));
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "二维码id")})
    @ApiOperation(value = "二维码解绑", notes = "二维码解绑")
    @PostMapping("/untie/merchant/qrcode")
    public Resp untieMerchantQrcode(String id) {
        return Resp.success(mchBlankQrCodeService.untieMerchantQrcode(id));
    }
}
