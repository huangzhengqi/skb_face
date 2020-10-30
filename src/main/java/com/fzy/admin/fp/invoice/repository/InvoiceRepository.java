package com.fzy.admin.fp.invoice.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.invoice.domain.Invoice;

import java.util.List;

public interface InvoiceRepository extends BaseRepository<Invoice> {
    List<Invoice> findByMemberId(String paramString);
}
