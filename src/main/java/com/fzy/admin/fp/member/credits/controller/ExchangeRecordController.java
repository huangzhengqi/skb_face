package com.fzy.admin.fp.member.credits.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;
import com.fzy.admin.fp.member.credits.service.ExchangeRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/5/17 10:12
 * @Description 商品兑换记录
 */
@RestController
@RequestMapping(value = "/member/change_record")
public class ExchangeRecordController extends BaseController<ExchangeRecord> {

    @Resource
    private ExchangeRecordService exchangeRecordService;

    @Override
    public BaseService<ExchangeRecord> getService() {
        return exchangeRecordService;
    }

    @GetMapping(value = "/list_re")
    public Resp findAll(@TokenInfo(property = "merchantId") String merchantId,
                        ExchangeRecord exchangeRecord, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误!");
        }
        exchangeRecord.setMerchantId(merchantId);
        Page<ExchangeRecord> page = exchangeRecordService.list(exchangeRecord, pageVo);
        return Resp.success(page);

    }

    @GetMapping(value = "/my_code")
    public Resp myCode(@TokenInfo(property = "merchantId") String merchantId, ExchangeRecord exchangeRecord, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "token错误!");
        } else if (exchangeRecord.getMemberId() == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "个人资料错误!");
        }
        exchangeRecord.setMerchantId(merchantId);
        Page<ExchangeRecord> page = exchangeRecordService.list(exchangeRecord, pageVo);
        return Resp.success(page);
    }

    //小程序扫码兑换接口

    @PostMapping(value = "/check_status")
    public Resp updateStatus(@TokenInfo(property = "merchantId") String merchantId, String code) {

        ExchangeRecord exchangeRecord = exchangeRecordService
                .findByMerchantIdAndStatusAndGoodCodes(merchantId, ExchangeRecord.Status.NO_CHANGE.getCode(), code);
        if (exchangeRecord == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "找不到该提货码");
        }
        exchangeRecord.setStatus(ExchangeRecord.Status.CHANGE.getCode());
        exchangeRecordService.update(exchangeRecord);
        return Resp.success("提货成功!");
    }


}
