package com.fzy.admin.fp.member.credits.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;

import java.util.List;

/**
 * @author lb
 * @date 2019/5/17 14:19
 * @Description
 */
public interface CreditsInfoRepository extends BaseRepository<CreditsInfo> {

    List<CreditsInfo> findByMemberNumAndMerchantId(String memberNum, String merchantId);
}
