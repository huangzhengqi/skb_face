package com.fzy.admin.fp.merchant.bcrmApp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:21 2019/6/1
 * @ Description: APP商户管理
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/merchant/bcrm_app")
public class BcrmAppMerchantController extends BaseContent {

    @Resource
    private MerchantService merchantService;

    @Resource
    private MerchantUserService merchantUserService;


    @PostMapping("/save")
    public Resp save(@Valid Merchant model, @TokenInfo(property = "companyId") String companyId, @UserId String userId, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        model.setCompanyId(companyId);
        model.setManagerId(userId);
        model.setType(0);
        model.setServiceProviderId((String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        return Resp.success(merchantService.saveRewrite(model, serviceProviderId));
    }


    @GetMapping("/detail")
    public Resp detail(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        return Resp.success(merchantService.findOne(merchantId));
    }


    @GetMapping("/list")
    public Resp list(@TokenInfo(property = "companyId") String companyId, Merchant model, PageVo pageVo, String userType, Integer channel,@UserId String userId) {
        model.setCompanyId(companyId);
        return Resp.success(merchantService.findByCompanyId(model, pageVo, userType, channel,userId));
    }


    @PostMapping("/update")
    @Transactional
    public Resp update(Merchant model) {
        Merchant merchant = merchantService.findOne(model.getId());
        if (merchant == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(model, merchant, copyOptions);
        //对实体类中的@validation注解进行校验
        BindingResult bindingResult = Validation.valid(merchant);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
        merchantService.save(merchant);
        //查询该商户对应登录用户信息
        MerchantUser merchantUser = merchantUserService.findByUsername(model.getPhone());
        merchantUser.setName(model.getName());
        merchantUserService.save(merchantUser);
        return Resp.success("修改成功");
    }
}
