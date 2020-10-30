package com.fzy.admin.fp.file.rich_text.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.rich_text.domain.RichTextInfo;
import com.fzy.admin.fp.file.rich_text.repository.RichTextInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.rich_text.domain.RichTextInfo;
import com.fzy.admin.fp.file.rich_text.repository.RichTextInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--11:47
 * @description md5Info 服务类
 */
@Slf4j
@Service
@Transactional
public class RichTextService implements BaseService<RichTextInfo> {

    @Resource
    private RichTextInfoRepository richTextInfoRepository;

    @Override
    public RichTextInfoRepository getRepository() {
        return richTextInfoRepository;
    }

    public Resp save(String info) {
        RichTextInfo richTextInfo = new RichTextInfo(info);
        richTextInfo = richTextInfoRepository.save(richTextInfo);
        return Resp.success(richTextInfo.getId(), "保存富文本内容成功!");
    }

    public Resp update(String id, String info) {
        RichTextInfo richTextInfo = richTextInfoRepository.findOne(id);
        if (richTextInfo == null) {
            richTextInfo = new RichTextInfo();
        }
        richTextInfo.setInfo(info);
        richTextInfo = richTextInfoRepository.save(richTextInfo);
        return Resp.success(richTextInfo.getId(), "修改富文本内容成功!");
    }

    public Resp queryRichTextInfo(String id) {
        if (ParamUtil.isBlank(id)) {
            return Resp.success(id, "");
        }
        RichTextInfo richTextInfo = richTextInfoRepository.findOne(id);
        if (ParamUtil.isBlank(richTextInfo)) {
            return Resp.success(id, "");
        }
        return Resp.success(richTextInfoRepository.findOne(id).getInfo(), "");
    }
}
