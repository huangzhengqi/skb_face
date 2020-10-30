package com.fzy.admin.fp.member.app.controller;

import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.app.dto.AppStoreRecordDto;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:56 2019/6/12
 * @ Description: APP查询会员消费记录
 **/
@RestController
@RequestMapping("/member/store_record/app")
public class AppStoreRecordController extends BaseContent {

    @Resource
    private StoredRecoredService storedRecoredService;

    //会员所有消费记录
    @GetMapping("/consume/all/list")
    public Resp storedList(@TokenInfo(property = "merchantId") String merchantId, PageVo pageVo, AppStoreRecordDto appStoreRecordDto) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (ParamUtil.isBlank(appStoreRecordDto.getPayWay())) {
            appStoreRecordDto.setPayWay("");
        }
        if (ParamUtil.isBlank(appStoreRecordDto.getPayStatus())) {
            appStoreRecordDto.setPayStatus("");
        }
        if (ParamUtil.isBlank(appStoreRecordDto.getStoreId())) {
            appStoreRecordDto.setStoreId("");
        }
        if (ParamUtil.isBlank(appStoreRecordDto.getMemberId())) {
            appStoreRecordDto.setMemberId("");
        }
        return Resp.success(storedRecoredService.getRepository().findByAppStoreRecordDto(pageable, merchantId, appStoreRecordDto.getStoreId()
                , appStoreRecordDto.getPayStatus(), appStoreRecordDto.getPayWay(), appStoreRecordDto.getMemberId(), DateUtil.beginOfDay(appStoreRecordDto.getStartTime()).toJdkDate(), DateUtil.endOfDay(appStoreRecordDto.getEndTime()).toJdkDate()));
    }

    //会员消费记录详情
    @GetMapping("/consume/detail")
    public Resp storedDetail(String id) {
        return Resp.success(storedRecoredService.findOne(id));
    }


    //单个会员消费记录
    @GetMapping("consume/customer/list")
    public Resp consumeList(PageVo pageVo, AppStoreRecordDto appStoreRecordDto) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (ParamUtil.isBlank(appStoreRecordDto.getPayWay())) {
            appStoreRecordDto.setPayWay("");
        }
        if (ParamUtil.isBlank(appStoreRecordDto.getPayStatus())) {
            appStoreRecordDto.setPayStatus("");
        }
        if (ParamUtil.isBlank(appStoreRecordDto.getStoreId())) {
            appStoreRecordDto.setStoreId("");
        }
        return Resp.success(storedRecoredService.getRepository().findByAppStoreRecordDto(pageable, "", appStoreRecordDto.getStoreId()
                , appStoreRecordDto.getPayStatus(), appStoreRecordDto.getPayWay(), appStoreRecordDto.getMemberId(), DateUtil.beginOfDay(appStoreRecordDto.getStartTime()).toJdkDate(), DateUtil.endOfDay(appStoreRecordDto.getEndTime()).toJdkDate()));
    }

}
