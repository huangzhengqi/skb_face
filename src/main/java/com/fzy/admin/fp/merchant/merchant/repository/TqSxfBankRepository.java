package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.TqSxfBank;

import java.util.List;

public interface TqSxfBankRepository extends BaseRepository<TqSxfBank> {

    List<TqSxfBank> findByUnitedBankNameLike(String unitedBankName);

    TqSxfBank findByUnitedBankName(String unitedBankName);
}
