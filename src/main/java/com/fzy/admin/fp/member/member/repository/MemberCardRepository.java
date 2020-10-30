package com.fzy.admin.fp.member.member.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.member.domain.MemberCard;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:25 2019/5/31
 * @ Description:
 **/

public interface MemberCardRepository extends BaseRepository<MemberCard> {

    /**
     * 根据商户id查询会员卡
     * @param merchantId
     * @return
     */
    MemberCard findByMerchantId(String merchantId);

    /**
     * 根据微信卡券id查询会员卡
     * @param cardId
     * @return
     */
    MemberCard findByCardId(String cardId);
}
