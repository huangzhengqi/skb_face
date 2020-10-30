package com.fzy.admin.fp.member.credits.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;
import com.fzy.admin.fp.member.credits.service.ExchangeRecordService;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/22 11:46
 * @Description
 */
@Slf4j
@RestController
@RequestMapping(value = "/member/export")
public class CreditsExportController extends BaseContent {

    @Resource
    private ExchangeRecordService exchangeRecordService;

    @GetMapping(value = "/exchange_record_export")
    public void exchangeRecordExport(@TokenInfo(property = "merchantId") String merchantId) {
        List<ExchangeRecord> exchangeRecords = exchangeRecordService.findByMerchantId(merchantId);
        log.info("获取到的对象：{}" + exchangeRecords);
        EasyPoiUtil.exportExcel(exchangeRecords, "兑换记录信息", "兑换记录",
                ExchangeRecord.class, "兑换记录表.xls", response);

    }

}
