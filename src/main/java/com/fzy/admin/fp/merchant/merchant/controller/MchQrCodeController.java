package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchQrCode;
import com.fzy.admin.fp.merchant.merchant.service.MchQrCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:40 2019/5/9
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/mch_qr_code")
public class MchQrCodeController extends BaseController<MchQrCode> {

    @Resource
    private MchQrCodeService mchQrCodeService;


    @Override
    public MchQrCodeService getService() {
        return mchQrCodeService;
    }


    @GetMapping("/list_rewrite")
    public Resp listRewrite(MchQrCode entity, PageVo pageVo, @UserId String userId) {
        return Resp.success(mchQrCodeService.listRewrite(entity, pageVo, userId), "操作成功");
    }


    @PostMapping("/save_rewrite")
    public Resp saveRewrite(MchQrCode entity, @UserId String userId) {
        return Resp.success(mchQrCodeService.saveRewrite(entity, userId), "添加成功");
    }


    @PostMapping("/update_rewrite")
    public Resp updateRewrite(MchQrCode entity, @UserId String userId) {
        return Resp.success(mchQrCodeService.updateRewrite(entity, userId), "修改成功");
    }

    /*
     * @author drj
     * @date 2019-05-23 10:32
     * @Description :禁用或启用
     */
    @PostMapping("/update_status")
    public Resp updateStatus(String id, Integer status) {
        return Resp.success(mchQrCodeService.updateStatus(id, status));
    }
}
