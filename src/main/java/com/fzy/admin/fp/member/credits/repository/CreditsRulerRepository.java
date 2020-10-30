package com.fzy.admin.fp.member.credits.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.credits.domain.CreditsRuler;

/**
 * @author lb
 * @date 2019/5/14 11:40
 * @Description
 */
public interface CreditsRulerRepository extends BaseRepository<CreditsRuler> {

    public CreditsRuler findByMerchantId(String merchantId);
}
