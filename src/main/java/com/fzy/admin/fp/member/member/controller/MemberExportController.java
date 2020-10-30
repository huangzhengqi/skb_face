package com.fzy.admin.fp.member.member.controller;

import cn.hutool.core.codec.Base64;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Created by zk on 2019-05-21 9:47
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/member/export")
public class MemberExportController extends BaseContent {
    @Resource
    private MemberService memberService;

    @GetMapping("/member_export")
    public void memberExport(String payload) {
        String payloadJson = Base64.decodeStr(payload);
        Map<String, String> payloadMap = JacksonUtil.toStringMap(payloadJson);
        String merchantId = payloadMap.get("merchantId");
        List<Member> memberList = memberService.findByMerchantId(merchantId);
        EasyPoiUtil.exportExcel(memberList, "会员信息", "会员", Member.class, "会员信息表.xls", response);
    }
}
