package com.fzy.admin.fp.member.sem.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.sem.domain.MemberAli;

public interface MemberAliRepository extends BaseRepository<MemberAli>{

    /**
     * 支付宝会员
     * @return
     */
    MemberAli findByBuyerIdAndDelFlag(String buyerId, Integer delFlag);


    /**
     * 按商户标识支付宝会员ID查找
     * @param merchantId
     * @param buyerId
     * @param delFlag
     * @return
     */
    MemberAli findByMerchantIdAndBuyerIdAndDelFlag(String merchantId,String buyerId,Integer delFlag);


    /**
     * 检测会员卡号是否重复
     * @param memberNum
     * @param normalFlag
     * @return
     */
    int countByMemberNumAndDelFlag(String memberNum, Integer normalFlag);

}
