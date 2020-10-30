package com.fzy.admin.fp.member.member.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.member.domain.MemberReceiveCard;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:25 2019/5/31
 * @ Description:
 **/

public interface MemberReceiveCardRepository extends BaseRepository<MemberReceiveCard> {

    Integer countByMemberId(String memberId);
}
