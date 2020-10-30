package com.fzy.admin.fp.invoice.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.invoice.domain.ElectronicBillingSetting;
import com.fzy.admin.fp.invoice.domain.TaxOfficeGoodsCode;
import com.fzy.admin.fp.invoice.dto.CreateBillingSettingDTO;
import com.fzy.admin.fp.invoice.dto.ElectronicBillingSettingDTO;
import com.fzy.admin.fp.invoice.dto.QueryGoodsCodeDTO;
import com.fzy.admin.fp.invoice.dto.UpdateBillingSettingDTO;
import com.fzy.admin.fp.invoice.repository.ElectronicBillingSettingRepository;
import com.fzy.admin.fp.invoice.repository.TaxOfficeGoodsCodeRepository;
import com.fzy.admin.fp.invoice.service.BillingService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("开票后台相关接口")
@RestController
@RequestMapping({"billing/backend/"})
public class BillingBackendController {
    @Autowired
    private BillingService billingService;
    @Autowired
    private ElectronicBillingSettingRepository electronicBillingSettingRepository;
    @Autowired
    private MchInfoService mchInfoService;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private TaxOfficeGoodsCodeRepository taxOfficeGoodsCodeRepository;

    public static final Integer ENABLED_FLAG = Integer.valueOf(1);


    @GetMapping({"/goods_codes"})
    @ApiOperation("查询开票类目")
    public Resp<List<TaxOfficeGoodsCode>> queryGoodsCode(@ApiParam QueryGoodsCodeDTO dto) {
        if (dto.getLevel() == null) {
            dto.setLevel(Integer.valueOf(0));
        }
        if (StringUtils.hasText(dto.getLikeName()) || StringUtils.hasText(dto.getName())) {
            dto.setLevel(null);
        }

        List<TaxOfficeGoodsCode> list = this.billingService.queryGoodsCode(dto);

        return Resp.success(list);
    }



    @PostMapping({"/create"})
    @ApiOperation("创建开票设置")
    public Resp<String> createBillingSetting(@UserId String merchantUserId, @ApiParam CreateBillingSettingDTO dto) {
        MchInfo mchInfo = getMchInfo(merchantUserId);
        if (!ENABLED_FLAG.equals(mchInfo.getIsOpenElectronicInvoice())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "开票设置未启用，请先启用");
        }

        dto.setMerchantId(mchInfo.getMerchantId());
        return this.billingService.createBillingSetting(dto);
    }



    @PutMapping({"/update"})
    @ApiOperation("更新开票设置")
    public Resp updateBillingSetting(@UserId String merchantUserId, @ApiParam UpdateBillingSettingDTO dto) {
        MchInfo mchInfo = getMchInfo(merchantUserId);
        if (!ENABLED_FLAG.equals(mchInfo.getIsOpenElectronicInvoice())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "开票设置未启用，请先启用");
        }
        dto.setMerchantId(mchInfo.getMerchantId());
        return this.billingService.updateBillingSetting(dto);
    }


    @GetMapping({"/"})
    @ApiOperation("获取开票设置")
    public Resp<ElectronicBillingSettingDTO> getBillingSetting(@UserId String merchantUserId) {
        Resp<ElectronicBillingSettingDTO> resp = new Resp<ElectronicBillingSettingDTO>();
        MerchantUser merchantUser = (MerchantUser)this.merchantUserService.findOne(merchantUserId);
        ElectronicBillingSetting electronicBillingSetting = this.electronicBillingSettingRepository.findByMerchantId(merchantUser.getMerchantId());
        if (electronicBillingSetting == null) {
            return Resp.success(null);
        }
        TaxOfficeGoodsCode taxOfficeGoodsCode = this.taxOfficeGoodsCodeRepository.findByGoodsCode(electronicBillingSetting.getGoodsCode());
        if (taxOfficeGoodsCode == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "开票设置关联的开票类目未存在");
        }

        ElectronicBillingSettingDTO dto = new ElectronicBillingSettingDTO();

        BeanUtils.copyProperties(electronicBillingSetting, dto);
        dto.setGoodsName(taxOfficeGoodsCode.getName());
        dto.setGoodsVatRate(taxOfficeGoodsCode.getVatRate());

        resp.common(Resp.Status.SUCCESS, null, dto);

        return resp;
    }

    @ApiOperation("查询开票设置启用状态")
    @GetMapping({"billing_enable_status"})
    public Resp<Boolean> billingSettingIsEnabled(@UserId String merchantUserId) {
        MchInfo mchInfo = getMchInfo(merchantUserId);

        return Resp.success(Boolean.valueOf(ENABLED_FLAG.equals(mchInfo.getIsOpenElectronicInvoice())));
    }

    @PutMapping({"/enable"})
    @ApiOperation("启用开票设置")
    public Resp enabledBillingSetting(@UserId String merchantUserId, @ApiParam Boolean enable) {
        MchInfo mchInfo = getMchInfo(merchantUserId);
        mchInfo.setIsOpenElectronicInvoice(Integer.valueOf(Boolean.FALSE.equals(enable) ? 0 : 1));
        this.mchInfoService.save(mchInfo);
        return Resp.success(null);
    }

    private MchInfo getMchInfo(String merchantUserId) {
        MerchantUser merchantUser = (MerchantUser)this.merchantUserService.findOne(merchantUserId);
        return this.mchInfoService.findByMerchantId(merchantUser.getMerchantId());
    }
}
