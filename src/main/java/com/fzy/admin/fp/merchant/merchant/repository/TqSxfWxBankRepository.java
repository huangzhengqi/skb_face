package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.TqSxfWxBank;

/**
 * 天阙随行付微信银行关联
 */
public interface TqSxfWxBankRepository extends BaseRepository<TqSxfWxBank> {

    TqSxfWxBank findByTqSxfBankName(String tqSxfBankName);
}
