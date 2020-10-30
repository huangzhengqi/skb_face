package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.dto.DisMchInfoDTO;
import com.fzy.admin.fp.merchant.merchant.dto.MchInfoDTO;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.vo.DisMchInfoVO;
import com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO;
import com.fzy.admin.fp.pay.pay.service.TqSxfPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:35 2019/4/30
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/mch_info")
@Api(value="MchInfoController", tags={"商户进件相关"})
public class MchInfoController extends BaseController<MchInfo> {

    @Resource
    private MchInfoService mchInfoService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private TqSxfPayService tqSxfPayService;


    @Override
    public MchInfoService getService() {
        return mchInfoService;
    }


    @GetMapping("/list_rewrite")
    public Resp listRewrite(String companyId,Integer status, PageVo pageVo) {

        return Resp.success(mchInfoService.listRewrite(companyId, status,pageVo));
    }

    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(mchInfoService.findOne(id));
    }

    @GetMapping("/find_by_fuwushang")
    @ApiOperation(value = "服务商进件列表 新",notes = "服务商进件列表 ")
    public Resp<Page<MchInfoVO>> findByFuwushangNew(MchInfoDTO mchInfoDTO, PageVo pageVo,
                                                    @TokenInfo(property = "companyId") String companyId) {
        return Resp.success(mchInfoService.findByFuwushangNew(mchInfoDTO, pageVo,companyId),"");
    }

    @GetMapping("/find_by_fenxiaoshang")
    @ApiOperation(value = "分销商进件列表",notes = "分销商进件列表 ")
    public Resp<Page<DisMchInfoVO>> findByFenXiaoNew(DisMchInfoDTO disMchInfoDTO, PageVo pageVo,
                                                     @TokenInfo(property = "companyId") String userId) {
        return Resp.success(mchInfoService.findByFenXiaoNew(disMchInfoDTO, pageVo,userId),"");
    }

    @GetMapping("/sign")
    @ApiOperation(value = "签约商户",notes = "签约商户")
    public Resp<String> sign(String id) {
        try {
            MchInfo mchInfo = mchInfoService.findOne(id);
            mchInfo.setStatus(3);
            mchInfoService.save(mchInfo);
            Merchant merchant = merchantService.findOne(mchInfo.getMerchantId());
            if (merchant != null) {
                merchant.setStatus(1);
                merchantService.save(merchant);
            }
            return Resp.success("","签约成功");
        } catch (Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "未知错误");
        }

    }

    @GetMapping("/change_status")
    @ApiOperation(value = "签约商户",notes = "签约商户")
    public Resp<String> changeStatus(String id,Integer status,String remark) {
        try {
                MchInfo mchInfo = mchInfoService.findOne(id);
                mchInfo.setStatus(status);
                mchInfo.setRemark(remark);
                mchInfoService.save(mchInfo);
                Merchant merchant = merchantService.findOne(mchInfo.getMerchantId());
                if (merchant != null && status.equals(3)) {
                    merchant.setStatus(1);
                    merchantService.save(merchant);
                }

            return Resp.success("","操作成功");

        } catch (Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "未知错误");
        }
    }

    @ApiOperation(value = "配置商户账号",notes = "商户设置")
    @PostMapping("/merchantSetup")
    public Resp merchantSetup(String mno) throws Exception {
        return Resp.success(tqSxfPayService.merchantSetup(mno));
    }


    @ApiOperation(value = "结算查询接口",notes = "结算查询接口")
    @GetMapping("/query/settlement")
    public Resp querySettlement(String mno,String queryTime) throws Exception {
        return tqSxfPayService.querySettlement(mno,queryTime);
    }
}
