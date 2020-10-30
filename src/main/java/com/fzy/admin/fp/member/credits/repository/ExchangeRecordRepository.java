package com.fzy.admin.fp.member.credits.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;

import java.util.List;

/**
 * @author lb
 * @date 2019/5/17 10:29
 * @Description
 */
public interface ExchangeRecordRepository extends BaseRepository<ExchangeRecord> {

    //查找指定会员兑换商品的记录
    List<ExchangeRecord> findByMerchantIdAndMemberIdAndProductId(String merchantId, String memberId, String productId);

    List<ExchangeRecord> findByMerchantId(String merchantId);

    //我的提货码个数 状态为0未提货即可
    Integer countByMerchantIdAndMemberIdAndStatus(String merchantId, String memberId, Integer status);

    ExchangeRecord findByMerchantIdAndStatusAndGoodCodes(String merchantId, Integer status, String goodCodes);

    Integer countByProductIdAndStatus(String productId,Integer status);
}
