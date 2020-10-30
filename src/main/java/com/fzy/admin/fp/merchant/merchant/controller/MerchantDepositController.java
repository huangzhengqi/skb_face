package com.fzy.admin.fp.merchant.merchant.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRepository;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.dto.DepositEditDTO;
import com.fzy.admin.fp.merchant.merchant.dto.DepositPageDTO;
import com.fzy.admin.fp.merchant.merchant.dto.DepositRefundDTO;
import com.fzy.admin.fp.merchant.merchant.service.DepositService;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.vo.UnFreezeVO;
import com.fzy.admin.fp.pay.pay.domain.Deposit;
import com.fzy.admin.fp.sdk.pay.domain.AliUnFreezeParam;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;


@RestController
@RequestMapping({"/merchant/deposit"})
@Api(value = "MerchantDepositController", tags = {"押金"})
public class MerchantDepositController {
    private static final Logger log = LoggerFactory.getLogger(MerchantDepositController.class);

    @Resource
    DepositService depositService;

    @Resource
    MerchantUserRepository merchantUserRepository;

    @Resource
    OrderRepository orderRepository;

    @GetMapping({"/list"})
    public Resp<Page<Deposit>> getPage(DepositPageDTO depositPageDTO, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        depositPageDTO.setMerchantId(TokenUtils.getMerchantId());
        Page<Deposit> page = this.depositService.getPage(pageable, depositPageDTO);

        return Resp.success(page);
    }

    @PutMapping({""})
    @ApiOperation("修改押金备注")
    public Resp edit(@RequestBody DepositEditDTO depositEditDTO) {

        Deposit deposit = depositService.findOne(depositEditDTO.getId());
        if(deposit == null || ParamUtil.isBlank(deposit)){
            throw new BaseException("当前押金不存在",Resp.Status.PARAM_ERROR.getCode());
        }
        deposit.setRemark(depositEditDTO.getRemark());
        depositService.update(deposit);
        return Resp.success("修改成功");
    }

    @ApiOperation(value = "退押金", notes = "退押金")
    @PostMapping({"/refund"})
    public Resp<UnFreezeVO> refund(@RequestBody DepositRefundDTO depositRefundDTO) {

        Deposit deposit = depositService.findOne(depositRefundDTO.getDepositId());
        if(deposit == null || ParamUtil.isBlank(deposit)){
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前用户没有押金");
        }

        MerchantUser user = (MerchantUser)this.merchantUserRepository.findOne(TokenUtils.getUserId());
        if (user == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
        }

        try {
           return this.depositService.refund(deposit,user,depositRefundDTO);
        }catch (Exception e){
            return (new Resp()).error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
    }

}
