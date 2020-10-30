package com.fzy.admin.fp.invoice.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.invoice.domain.ElectronicBillingSetting;

public interface ElectronicBillingSettingRepository extends BaseRepository<ElectronicBillingSetting> {
    ElectronicBillingSetting findByMerchantId(String paramString);
}
