package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.sem.domain.MemberCreditsInfo;
import com.fzy.admin.fp.member.sem.repository.MemberCreditsInfoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberCreditsInfoService implements BaseService<MemberCreditsInfo>{


    @Resource
    private MemberCreditsInfoRepository memberCreditInfoRepository;


    @Override
    public BaseRepository<MemberCreditsInfo> getRepository() {
        return memberCreditInfoRepository;
    }

}
