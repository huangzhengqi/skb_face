package com.fzy.admin.fp.member.credits.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.credits.domain.CreditsProduct;

import java.util.List;

/**
 * @author lb
 * @date 2019/5/15 16:07
 * @Description
 */
public interface CreditsProductRepository extends BaseRepository<CreditsProduct> {

    List<CreditsProduct> findByMerchantId(String merchantId);

}
