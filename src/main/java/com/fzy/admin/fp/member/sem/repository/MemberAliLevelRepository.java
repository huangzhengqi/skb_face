package com.fzy.admin.fp.member.sem.repository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;

public interface MemberAliLevelRepository extends BaseRepository<MemberAliLevel>{

    MemberAliLevel findByName(String name);

    MemberAliLevel findByNameAndIdNotIn(String name,String id);
}
