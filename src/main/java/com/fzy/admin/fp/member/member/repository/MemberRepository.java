package com.fzy.admin.fp.member.member.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.member.domain.Member;

import java.util.Date;
import java.util.List;

/**
 * @author Created by zk on 2019-05-14 10:29
 * @description
 */
public interface MemberRepository extends BaseRepository<Member> {

    //根据手机号查找会员
    Member findByPhoneAndMerchantIdAndDelFlag(String phone, String merchantId, Integer delFlag);

    int countByMerchantIdAndPhoneAndDelFlag(String merchantId, String phone, Integer delFlag);

    int countByMemberNumAndMerchantIdAndDelFlag(String memberNum, String merchantId, Integer delFlag);

    Member findByMerchantIdAndMemberNum(String merchantId, String memberNum);
    Member findByMerchantIdAndPhone(String merchantId, String phone);

    long countByMerchantIdAndAndCreateTimeBetween(String merchantId, Date begin, Date end);

    long countByMerchantIdAndAndCreateTimeBefore(String merchantId, Date date);

    long countByMerchantId(String merchantId);

    List<Member> findByMerchantIdOrderByCreateTimeDesc(String merchantId);

    List<Member> findByMerchantIdAndCreateTimeBetween(String merchantId, Date begin, Date end);

    Member findByOpenIdAndDelFlag(String openId, Integer delFlag);

    int countByMerchantIdAndPhone(String merchantId, String phone);

    List<Member> findByPhoneLikeOrMemberNumLikeOrderByLastPayDateDescCreateTimeDesc(String phone, String memberNum);


    int countByMemberNumAndDelFlag(String memberNum, Integer normalFlag);

    List<Member> findByMerchantIdAndLastPayDateBetween(String merchantId, Date begin, Date end);

    /**
     * @author Created by wtl on 2019/6/26 22:43
     * @Description 根据公众号openid获取会员，判断扫码用户是否已经是会员
     */
    Member findByOfficialOpenIdAndMerchantIdAndDelFlag(String officialOpenId, String merchantId, Integer delFlag);

    List<Member> findByMemberLevelIdAndDelFlag(String paramString, Integer paramInteger);

    Member findByMerchantIdAndOpenIdAndDelFlag(String paramString1, String paramString2, Integer paramInteger);

    Member findByMerchantIdAndBuyerIdAndDelFlag(String paramString1, String paramString2, Integer paramInteger);

    Member findByBuyerIdAndMerchantId(String paramString1, String paramString2);

    Member findByOpenIdAndMerchantId(String paramString1, String paramString2);

}
