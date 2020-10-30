package com.fzy.admin.fp.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionDTO;
import com.fzy.admin.fp.auth.dto.DataStatisticsConditionRightDTO;
import com.fzy.admin.fp.auth.dto.DataTransactionDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.sdk.merchant.domain.*;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Created by zk on 2019-04-29 16:21
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/auth/running_account_new")
public class StatisticsNewController extends BaseContent {

    @Resource
    private CompanyService companyService;
    @Resource
    private OrderService orderService;

    /**
     * 我的信息
     */
    @GetMapping("/my")
    public Resp<MyDataVO> myData(String companyId) {
        log.info("我的信息");
        Company company = companyService.findOne(companyId);
        MyDataVO myDataVO = new MyDataVO();
        BeanUtil.copyProperties(company, myDataVO);
        return Resp.success(myDataVO);
    }

    /**
     * 数据概览
     */
    @GetMapping("/date_view")
    public Resp<DataViewVO> dataview(String companyId) {
        log.info("首页-数据概览");
        DataViewVO dataViewVO = companyService.dataview(companyId);
        return Resp.success(dataViewVO);
    }

    /**
     * app数据总览
     */
    @GetMapping("/app_data_view")
    @ApiOperation(value = "h5代理商 app首页-数据概览" ,notes = "h5代理商 app首页-数据概览")
    public Resp<DataViewAppVo> appDataView(String companyId){
        log.info("h5代理商 app首页-数据概览");
        DataViewAppVo appdataview = companyService.appdataview(companyId);
        return Resp.success(appdataview);
    }

    /**
     * 交易数据
     */
    @GetMapping("/date_transaction")
    public Resp<DataTransactionVO> dataTransaction(@UserId String userId, @TokenInfo(property = "level") Integer level, DataTransactionDTO dataTransactionDTO) {
        log.info("首页-交易数据");
        DataTransactionVO dataTransactionVO = companyService.dataTransaction(userId, level, dataTransactionDTO);
        return Resp.success(dataTransactionVO);
    }


    /**
     * 左侧图表
     *
     * @param condition
     * @return
     */
    @GetMapping("/data_statistics_left")
    public Resp<List<DataStatisticsLineDetailVO>> DataStatistics(DataStatisticsConditionDTO condition) {

        List<DataStatisticsLineDetailVO> dataStatisticsLineDetailVOS;
        if (condition.getType() == 5) {
            //佣金单处处理
            dataStatisticsLineDetailVOS = orderService.getLeftCommission(condition.getCompanyId(), condition.getStartTime(), condition.getEndTime());
        } else {
            //获取当前平台下所有的商户id
            List<String> merchantIds = companyService.findAllmerchantIds(condition.getCompanyId());
            dataStatisticsLineDetailVOS = orderService.getLeft(merchantIds, condition);
        }

        return Resp.success(dataStatisticsLineDetailVOS);
    }


    /**
     * 代理商app右侧图表
     *
     * @param condition
     * @return
     */
    @ApiOperation(value = "代理商app右侧图表",notes = "代理商app右侧图表")
    @GetMapping("/data_statistics_right")
    public Resp<List<DataStatisticsPieDetailVO>> DataStatisticsRight(DataStatisticsConditionRightDTO condition) {
        //获取当前平台下所有的商户id
        List<String> merchantIds = companyService.findAllmerchantIds(condition.getCompanyId());
        Company company = companyService.findOne(condition.getCompanyId());
        List<DataStatisticsPieDetailVO> dataStatisticsLineDetailVOS;
        // 一二三级代理商
        if (company.getType().equals(Company.Type.OPERATOR.getCode()) || company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode()) || company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            dataStatisticsLineDetailVOS = orderService.getAgentRight(merchantIds, condition);
        } else {
            //超级后台/服务商后台
            dataStatisticsLineDetailVOS = orderService.getRight(merchantIds, condition);
        }

        return Resp.success(dataStatisticsLineDetailVOS);
    }

    /**
     * 商家营收排行榜
     */
    @GetMapping("/merchant_data")
    public Resp<List<MerchantDataVo>> merchantData(DataTransactionDTO dataTransactionDTO,Integer payPriceType){
        List<MerchantDataVo> merchantDataVoList;

        //查询当前公司下所有商户
        Company company = companyService.findOne(dataTransactionDTO.getCompanyId());
        if(company == null){
            return Resp.success("当前用户下没有公司");
        }

        merchantDataVoList = companyService.getMerchantData(company,dataTransactionDTO.getStartTime(),dataTransactionDTO.getEndTime());
        if(merchantDataVoList.size() <= 0){
            return Resp.success("当前无商户数据");
        }

        if(payPriceType.equals(1)){
            merchantDataVoList.sort(Comparator.comparing(MerchantDataVo::getCountPayPrice));
        }else {
            merchantDataVoList.sort(Comparator.comparing(MerchantDataVo::getCountPayPrice).reversed());
        }
        return Resp.success(merchantDataVoList,"操作成功");
    }
}
