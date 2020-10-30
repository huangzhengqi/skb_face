package com.fzy.admin.fp.member.rule.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.dto.StoredAmountAndRecordDto;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ：drj.
 * @Date  ：Created in 16:45 2019/5/14
 * @Description: 储值明细控制层
 **/
@Slf4j
@RestController
@RequestMapping("/member/stored_recored")
public class StoredRecoredController extends BaseController<StoredRecored> {

    @Resource
    private StoredRecoredService storedRecoredService;

    @Override
    public StoredRecoredService getService() {
        return storedRecoredService;
    }

    @ApiOperation(value = "根据商户id查询储值记录",notes = "根据商户id查询储值记录")
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(StoredAmountAndRecordDto model, PageVo pageVo, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRecoredService.findByMerchantId(model, pageVo, merchantId));
    }

    @ApiOperation(value = "储值报表信息",notes = "储值报表信息")
    @GetMapping("/search_stored_amount")
    public Resp searchStoredAmount(StoredAmountAndRecordDto model, @TokenInfo(property = "merchantId") String merchantId) {
        return Resp.success(storedRecoredService.searchStoredAmount(model, merchantId));
    }


}
