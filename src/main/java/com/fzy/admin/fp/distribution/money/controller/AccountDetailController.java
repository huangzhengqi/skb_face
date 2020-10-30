package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.dto.AccountDetailDTO;
import com.fzy.admin.fp.distribution.money.service.AccountDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-22 16:55:00
 * @Desp 账户明细
 **/
@RestController
@RequestMapping("/dist/accountDetail")
@Api(value = "AccountDetailController", tags = {"分销-账户明细"})
public class AccountDetailController extends BaseContent {

    @Resource
    private AccountDetailService accountDetailService;



    @GetMapping(value = "/query")
    @ApiOperation(value = "账户明细", notes = "账户明细")
    public Resp query(PageVo pageVo,AccountDetailDTO accountDetailDTO){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<AccountDetail> page =null;
        String status=(accountDetailDTO.getStatus()==null?"":accountDetailDTO.getStatus() )+ "%";
        if(accountDetailDTO.getStartTime()!=null){
            page = accountDetailService.getRepository().findAllByUserIdAndCreateTimeBetweenAndStatusLike(accountDetailDTO.getUserId(),accountDetailDTO.getStartTime(),accountDetailDTO.getEndTime(),status, pageable);
        }else {
            page = accountDetailService.getRepository().findAllByUserIdAndStatusLike(accountDetailDTO.getUserId(),status, pageable);
        }
        return Resp.success(page);
    }
}
