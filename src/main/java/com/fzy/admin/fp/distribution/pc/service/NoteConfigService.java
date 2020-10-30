package com.fzy.admin.fp.distribution.pc.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.domain.NoteConfig;
import com.fzy.admin.fp.distribution.pc.repository.NoteConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-17 18:26:30
 * @Desp
 **/
@Service
public class NoteConfigService implements BaseService<NoteConfig> {

    @Resource
    private NoteConfigRepository noteConfigRepository;

    public NoteConfigRepository getRepository() {
        return noteConfigRepository;
    }
}
