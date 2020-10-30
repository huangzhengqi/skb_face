package com.fzy.admin.fp.member.coupon.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lb
 * @date 2019/5/24 9:24
 * @Description
 */
public interface CouponRepository extends BaseRepository<Coupon> {

    List<Coupon> findByCardTypeAndMerchantIdAndDelFlag(Integer type, String merchantId, Integer del);

    List<Coupon> findByCardTypeAndTypeAndMerchantIdAndDelFlag(Integer cardType,Integer type, String merchantId, Integer del);

    List<Coupon> findByMerchantIdAndNameAndDelFlag(String merchantId, String name, Integer del);

    //绑卡赠送的卡券
    List<Coupon> findByMerchantIdAndCardTypeAndDelFlag(String merchantId, Integer type, Integer del);

    Page<Coupon> findByMerchantIdAndCardTypeAndDelFlagAndActStatusIn
            (String merchantId, Integer type, Integer del, Integer act1, Integer act2, Pageable pageable);

    /**
     * 根据卡券id查卡券
     * @param cardId
     * @return
     */
    Coupon findByCardId(String cardId);


}
