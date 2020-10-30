package com.fzy.admin.fp.member.sem.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.sem.domain.MemberCardTemplate;
import org.springframework.data.jpa.repository.Query;

public interface MemberCardTemplateRepository  extends BaseRepository<MemberCardTemplate> {

    MemberCardTemplate findByTempateId( String templateId);

    MemberCardTemplate findByMerchantIdAndDelFlag(String merchantId,Integer delFlag);

    @Query(value = "select * from lysj_member_card_template where id = (select max(id)as maxid from lysj_member_card_template where merchant_id = ?1 and del_flag = 1)",nativeQuery = true)
    MemberCardTemplate findMaxId(String merchantId);

}
