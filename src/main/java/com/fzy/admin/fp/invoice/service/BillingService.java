package com.fzy.admin.fp.invoice.service;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.invoice.domain.TaxOfficeGoodsCode;
import com.fzy.admin.fp.invoice.dto.*;
import com.fzy.admin.fp.invoice.feign.resp.GoodsItem;
import com.fzy.admin.fp.invoice.feign.resp.InvoiceNoticeBO;

import java.util.List;

public interface BillingService {

    int batchSaveGoodsCode(List<GoodsItem> paramList);

    long countTaxOfficeGoodsTotal();

    List<TaxOfficeGoodsCode> queryGoodsCode(QueryGoodsCodeDTO paramQueryGoodsCodeDTO);

    Resp<String> createBillingSetting(CreateBillingSettingDTO paramCreateBillingSettingDTO);

    Resp updateBillingSetting(UpdateBillingSettingDTO paramUpdateBillingSettingDTO);

    Resp<String> createInvoice(CreateInvoiceDTO paramCreateInvoiceDTO);

    Resp updateInvoice(UpdateInvoiceDTO paramUpdateInvoiceDTO);

    Resp billingByOrder(BillingByOrderDTO paramBillingByOrderDTO);

    Resp billingCallback(String paramString, InvoiceNoticeBO.InvoiceInfo paramInvoiceInfo);
}
