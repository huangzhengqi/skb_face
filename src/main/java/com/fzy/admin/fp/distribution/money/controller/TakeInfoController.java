package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.TakeInfo;
import com.fzy.admin.fp.distribution.money.dto.TakeInfoDTO;
import com.fzy.admin.fp.distribution.money.service.TakeInfoService;
import com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-19 09:55:22
 * @Desp 提现管理
 **/
@RestController
@RequestMapping("/dist/takeInfo")
@Api(value = "UserController", tags = {"分销-提现管理"})
public class TakeInfoController {
    @Resource
    private TakeInfoService takeInfoService;

    /**
     * 服务商查询请求
     * @param serviceProviderId
     * @param pageVo
     * @param takeInfoDTO
     * @return
     */
    @GetMapping("/query")
    @ApiOperation(value = "查询提现申请", notes = "查询提现申请")
    public Resp query(@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo,TakeInfoDTO takeInfoDTO){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<TakeInfoDetailVO> details =null;
        if(takeInfoDTO.getStartTime()!=null){
            details = takeInfoService.getRepository().getDetails(serviceProviderId,takeInfoDTO.getStartTime(),takeInfoDTO.getEndTime(), takeInfoDTO.getStatus(),takeInfoDTO.getName(), pageable);
        }else{
            details = takeInfoService.getRepository().getDetails(serviceProviderId, takeInfoDTO.getName(),takeInfoDTO.getStatus(),pageable);
        }
        return Resp.success(details);
    }

    @GetMapping("/total")
    @ApiOperation(value = "查询全部申请提现金额", notes = "查询全部申请提现金额")
    public Resp total(@TokenInfo(property="serviceProviderId")String serviceProviderId,TakeInfoDTO takeInfoDTO){
        takeInfoDTO.setServiceProviderId(serviceProviderId);
        TakeInfoDetailVO sum=null;
        if(takeInfoDTO.getStartTime()!=null){
            sum = takeInfoService.getRepository().getSum(serviceProviderId, takeInfoDTO.getStartTime(), takeInfoDTO.getEndTime(), takeInfoDTO.getStatus(), takeInfoDTO.getName());
        }else{
            sum = takeInfoService.getRepository().getSum(serviceProviderId, takeInfoDTO.getName(),takeInfoDTO.getStatus());
        }
        return Resp.success(sum.getTotalFee());
    }

    /**
     * 打款
     * @param serviceProviderId
     * @return
     */
    @PostMapping("/remit")
    @ApiOperation(value = "打款", notes = "打款")
    public Resp remit(@TokenInfo(property="serviceProviderId")String serviceProviderId,TakeInfoDTO takeInfoDTO){
        TakeInfo takeInfo = takeInfoService.getRepository().findByServiceProviderIdAndId(serviceProviderId, takeInfoDTO.getId());
        if(takeInfo==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登录");
        }
        if(takeInfoDTO.getStatus().equals("2")&& StringUtil.isEmpty(takeInfoDTO.getRemark())){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"请输入驳回理由");
        }
        takeInfo.setStatus(Integer.valueOf(takeInfoDTO.getStatus()));
        takeInfo.setRemark(takeInfoDTO.getRemark());
        takeInfoService.remit(takeInfo);
        return Resp.success("请求成功");
    }

}
