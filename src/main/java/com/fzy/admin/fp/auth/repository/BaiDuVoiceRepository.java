package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.BaiDuVoice;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:33 2019/7/1
 * @ Description: 百度语音DAO
 **/

public interface BaiDuVoiceRepository extends BaseRepository<BaiDuVoice> {

    BaiDuVoice findByServiceProviderId(String companyId);
}
