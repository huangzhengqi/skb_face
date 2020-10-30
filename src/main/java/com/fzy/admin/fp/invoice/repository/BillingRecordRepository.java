package com.fzy.admin.fp.invoice.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.invoice.domain.BillingRecord;

public interface BillingRecordRepository extends BaseRepository<BillingRecord> {
    BillingRecord findByInvoiceSn(String paramString);

    BillingRecord findByIdAndMemberId(String paramString1, String paramString2);
}

