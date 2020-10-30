package com.fzy.admin.fp.member.sem.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.sem.domain.MemberCreditsRuler;

import java.util.List;


public interface MemberCreditsRulerRepository extends BaseRepository<MemberCreditsRuler> {
    List<MemberCreditsRuler> findByMerchantId(String merchantId);
}
