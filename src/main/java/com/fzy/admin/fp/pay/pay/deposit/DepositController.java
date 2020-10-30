package com.fzy.admin.fp.pay.pay.deposit;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.repository.EquipmentRepository;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.service.DepositService;
import com.fzy.admin.fp.order.order.vo.UnFreezeVO;
import com.fzy.admin.fp.pay.pay.domain.Deposit;
import com.fzy.admin.fp.pay.pay.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping({"/pay/pay/deposit"})
@Api(value = "DepositController", tags = {"押金相关接口"})
public class DepositController {
    private static final Logger log = LoggerFactory.getLogger(DepositController.class);

    @Resource
    DepositService depositService;

    @Resource
    EquipmentRepository equipmentRepository;


    @ApiOperation(value = "页面按钮", notes = "页面按钮 1收银  2收银+押金 3收银+结算 4收银+押金+结算")
    @GetMapping({"/button"})
    public Resp<Integer> button(String equpmentId) {
        Equipment equipment = (Equipment)this.equipmentRepository.findOne(equpmentId);
        if (equipment == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "设备不存在");
        }
        return Resp.success(equipment.getMode());
    }




    @ApiOperation(value = "微信收押金", notes = "微信收押金")
    @PostMapping({"/wx/face_pay"})
    public Resp<DepositDTO> facepay(@RequestBody FacePayDTO facePayDTO) {
        try {
            PayDepositDTO payDepositDTO = new PayDepositDTO();
            BeanUtil.copyProperties(facePayDTO, payDepositDTO);
            payDepositDTO.setType(Deposit.TYPE.WX.getCode());
            return this.depositService.deposit(payDepositDTO);
        } catch (Exception e) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "支付宝收押金", notes = "支付宝收押金")
    @PostMapping("/ali/frezz")
    public Resp<DepositDTO> frezz(@RequestBody AliCollectDTO model) {
        try {
            PayDepositDTO payDepositDTO = new PayDepositDTO();
            BeanUtil.copyProperties(model, payDepositDTO);
            payDepositDTO.setType(Deposit.TYPE.ALI.getCode());
            return this.depositService.deposit(payDepositDTO);
        } catch (Exception e) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "支付宝退押金", notes = "支付宝退押金")
    @PostMapping({"/ali/unfrezz"})
    public Resp<UnFreezeVO> unfrezz(@RequestBody AliRefundDTO model) {
        try {
            RefundDepositDTO refundDepositDTO = new RefundDepositDTO();
            BeanUtil.copyProperties(model, refundDepositDTO);
            refundDepositDTO.setType(Deposit.TYPE.ALI.getCode());
            return this.depositService.refundDeposit(refundDepositDTO);
        } catch (Exception e) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
    }



    @ApiOperation(value = "微信退押金", notes = "微信退押金")
    @PostMapping({"/wx/refund"})
    public Resp<UnFreezeVO> refund(@RequestBody RefundDTO refundDTO) {
        try {
            RefundDepositDTO refundDepositDTO = new RefundDepositDTO();
            BeanUtil.copyProperties(refundDTO, refundDepositDTO);
            refundDepositDTO.setType(Deposit.TYPE.WX.getCode());
            return this.depositService.refundDeposit(refundDepositDTO);
        } catch (Exception e) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, e.getMessage());
        }
    }
}

