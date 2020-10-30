package com.fzy.admin.fp.distribution.commission.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.commission.domain.CommissionTotal;
import com.fzy.admin.fp.distribution.commission.dto.CommissionTotalDTO;
import com.fzy.admin.fp.distribution.commission.service.CommissionTotalService;
import com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.service.CommissionDayService;
import com.fzy.admin.fp.order.order.vo.CommissionDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @Author yy
 * @Date 2019-12-26 17:37:04
 * @Desp
 **/

@RestController
@RequestMapping("/dist/commission")
@Api(value = "DistCommissionController", tags = {"分销-流水分润"})
public class DistCommissionController extends BaseContent{

    @Resource
    private CommissionTotalService commissionTotalService;

    @Resource
    private CommissionDayService commissionDayService;


    @GetMapping("/query")
    @ApiOperation(value = "流水分润", notes = "流水分润")
    public Resp queryCommission(CommissionTotalDTO CommissionTotalDTO, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<CommissionTotal> specification = new Specification<CommissionTotal>() {
            @Override
            public Predicate toPredicate(Root<CommissionTotal> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //日期查询
                if(CommissionTotalDTO.getStartTime()!=null&&CommissionTotalDTO.getEndTime()!=null){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), CommissionTotalDTO.getStartTime()));
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), CommissionTotalDTO.getEndTime()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<CommissionTotal> page = commissionTotalService.findAll(specification, pageable);
        CommissionTotalVO commissionTotal = commissionTotalService.getRepository().getCommissionTotal();
        Map<String,Object> result=new HashMap<>();
        result.put("page",page);
        result.put("commissionTotal",commissionTotal);
        return Resp.success(result);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "流水分润详情", notes = "流水分润详情")
    public Resp detail(CommissionTotalDTO CommissionTotalDTO, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<CommissionDayVO> page = commissionDayService.getRepository().getPage(CommissionTotalDTO.getStartTime(), pageable);
        CommissionTotalVO commissionTotal = commissionDayService.getRepository().getCommissionTotal(CommissionTotalDTO.getStartTime());
        Map<String,Object> result=new HashMap<>();
        result.put("page",page);
        result.put("commissionTotal",commissionTotal);
        return Resp.success(result);
    }

    @GetMapping("/commission")
    @ApiOperation(value = "用户详情下的流水分润", notes = "用户详情下的流水分润")
    public Resp query(CommissionTotalDTO CommissionTotalDTO, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        // 分页工具类
        Page<CommissionDay> page=null;
        if(CommissionTotalDTO.getStartTime()!=null&&CommissionTotalDTO.getEndTime()!=null){
            // 根据 开始时间 结束时间 类型 用户编号查询所有数据
            page= commissionDayService.getRepository().findAllByCreateTimeBetweenAndTypeAndCompanyId(pageable,CommissionTotalDTO.getStartTime(),CommissionTotalDTO.getEndTime(),1, CommissionTotalDTO.getUserId());
        }else {
            // 根据 类型  用户编号 查询所有数据
            page = commissionDayService.getRepository().findAllByTypeAndCompanyId(pageable, 1, CommissionTotalDTO.getUserId());
        }
        CommissionTotalVO commissionTotal = commissionDayService.getRepository().getCommissionTotalByCompanyId(CommissionTotalDTO.getUserId());
        Map<String,Object> result=new HashMap<>();
        result.put("page",page);
        result.put("commissionTotal",commissionTotal);
        return Resp.success(result);
    }
}
