package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.YunHorn;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:33 2019/7/1
 * @ Description: 云喇叭DAO
 **/

public interface YunHornRepository extends BaseRepository<YunHorn> {

    YunHorn findByServiceProviderId(String companyId);
}
