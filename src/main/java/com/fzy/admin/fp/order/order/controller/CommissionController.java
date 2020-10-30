package com.fzy.admin.fp.order.order.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.ThreadPoolUtil;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.order.order.repository.CommissionDayRepository;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.ThreadPoolUtil;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.dto.CreateCommissionDTO;
import com.fzy.admin.fp.order.order.dto.PageSearchDTO;
import com.fzy.admin.fp.order.order.dto.SettleDTO;
import com.fzy.admin.fp.order.order.repository.CommissionDayRepository;
import com.fzy.admin.fp.order.order.service.CommissionService;
import com.fzy.admin.fp.order.order.vo.CommissionSummaryVO;
import com.fzy.admin.fp.order.order.vo.SummaryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单控制层
 */
@Slf4j
@RestController
@RequestMapping("/order/commission")
@Api(value = "CommissionController", tags = {"佣金相关接口"})
public class CommissionController {
    @Resource
    private CommissionService commissionService;
    @Resource
    private CommissionDayRepository commissionDayRepository;

    @Resource
    protected HttpServletResponse response;

    /**
     * 数据统计
     *
     * @param companyId
     * @return
     */
    @ApiOperation(value = "数据统计", notes = "数据统计")
    @GetMapping("/summary")
    public Resp<CommissionSummaryVO> summary(@TokenInfo(property = "companyId") String companyId) {

        CommissionSummaryVO commissionSummaryVO = commissionService.summary(companyId);
        return Resp.success(commissionSummaryVO, "");

    }


    /**
     * 下级结算数据统计
     *
     * @param companyId
     * @return
     */
    @ApiOperation(value = "下级结算数据统计", notes = "下级结算数据统计")
    @GetMapping("/commission")
    public Resp<SummaryVO> commission(@TokenInfo(property = "companyId") String companyId) {

        SummaryVO summaryVO = commissionService.commission(companyId);
        return Resp.success(summaryVO, "");

    }

    /**
     * 结算清单
     *
     * @param
     * @return
     */
    @ApiOperation(value = "结算清单", notes = "结算清单")
    @GetMapping("/list")
    public Resp<Page<CommissionDay>> summaryList(PageVo pageVo, @TokenInfo(property = "companyId") String companyId, PageSearchDTO pageSearchDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<CommissionDay> page = commissionService.getPage(companyId, pageable, pageSearchDTO);
        return Resp.success(page, "");
    }

    /**
     * 结算清单导出
     * @param pageVo
     * @param companyId
     * @param pageSearchDTO
     * @return
     */
    @GetMapping("/export/summary")
    public Resp exportSummary(String companyId, PageVo pageVo, PageSearchDTO pageSearchDTO ,String[] ids){

        try {
            Pageable pageable = PageUtil.initPage(pageVo);
            List<CommissionDay> list = commissionService.getList(companyId, pageable, pageSearchDTO, ids);
            EasyPoiUtil.exportExcel(list, "结算清单", "结算", CommissionDay.class, "结算清单信息表.xls", response);
            return Resp.success("导出成功");
        }catch (Exception e){
            log.info(e.getMessage());
            throw new BaseException("导出失败", Resp.Status.PARAM_ERROR.getCode());
        }

    }


    /**
     * 结算
     *
     * @param
     * @return
     */
    @ApiOperation(value = "结算", notes = "结算")
    @PostMapping("/settle")
    public Resp<String> settle(@RequestBody SettleDTO settleDTO) {
        for (String id : settleDTO.getId()) {
            CommissionDay commissionDay = commissionDayRepository.findOne(id);
            commissionDay.setStatus(1);
            commissionDay.setVoucherUrl(settleDTO.getVoucherurl());
            commissionDay.setSettleTime(new Date());
            commissionDayRepository.save(commissionDay);
        }

        return Resp.success("结算成功");

    }


    @ApiOperation(value = "重新生成佣金记录", notes = "补充之前的的佣金记录")
    @PostMapping("/cretecomission")
    public Resp<String> reCreate(@RequestBody CreateCommissionDTO createCommissionDTO) {
        try {
            ThreadPoolUtil.getPool().execute(new Thread(() -> commissionService.reCreate(createCommissionDTO)));
            return Resp.success("", "后台正在生成中");
        } catch (Exception e) {
            commissionService.changeCreateStatus(createCommissionDTO.getId());
            return Resp.success("", "生成失败");
        }
    }

    @ApiOperation(value = "获取佣金生成结果", notes = "获取佣金生成结果")
    @PostMapping("/cretecomission/status")
    public Resp<Integer> commissionStatus(String id) {
        return  commissionService.commissionStatus(id);
    }




}
