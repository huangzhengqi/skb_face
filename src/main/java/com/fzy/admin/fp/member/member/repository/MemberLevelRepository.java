package com.fzy.admin.fp.member.member.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberLevelRepository extends BaseRepository<MemberLevel> {

    List<MemberLevel> findByMerchantIdOrderByMemberLimitAmountAsc(String merchantId);
}
