package com.fzy.admin.fp.file.rich_text.controller;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.rich_text.domain.RichTextInfo;
import com.fzy.admin.fp.file.rich_text.service.RichTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ZhangWenJian
 * @data 2019/1/4--17:12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/fms/rich_text")
public class RichTextInfoController extends BaseController<RichTextInfo> {
    @Resource
    private RichTextService richTextService;

    @Override
    public BaseService<RichTextInfo> getService() {
        return richTextService;
    }

    @Override
    public Resp list(RichTextInfo richTextInfo, PageVo pageVo) {
        return null;
    }

    ;

    @Override
    public Resp save(RichTextInfo richTextInfo) {
        return null;
    }

    @PostMapping("/save_rewrite")
    public Resp test(String info) {
        return richTextService.save(info);
    }

    @PostMapping("/update_rewrite")
    public Resp test(String id, String info) {
        return richTextService.update(id, info);
    }


    @GetMapping("/query")
    public Resp queryRichTextInfo(String id) {
        return richTextService.queryRichTextInfo(id);
    }


}
