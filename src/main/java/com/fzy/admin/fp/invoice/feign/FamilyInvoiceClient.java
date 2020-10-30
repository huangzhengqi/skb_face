package com.fzy.admin.fp.invoice.feign;

import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyRequest;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.req.*;
import com.fzy.admin.fp.invoice.feign.resp.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "familyInvoiceClient", url = "${facepay.invoice.api}", configuration = {FamilyInvoiceFeignConfiguration.class})
public interface FamilyInvoiceClient {
    public static final String INVOICE_REQUEST_URI = "invoice/business";

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<GoodsCodeBO> queryGoodsCode(FamilyRequest<QueryGoodsRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<CustomizeGoodsCodeBO> setCustomizeGoodsCode(FamilyRequest<SetCustomizeGoodsCodeRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<CustomizeGoodsCodeBO> deleteCustomizeGoodsCode(FamilyRequest<DeleteCustomizeGoodsCodeRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<CustomizeGoodQueryResultBO> queryCustomizeGoodsCode(FamilyRequest<QueryCustomizeGoodsCodeRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.GET)
    FamilyResp<TerminalBO> findTerminal(FamilyRequest<FindTerminalRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.GET)
    FamilyResp<InvoiceQueryResultBO> queryInvoice(FamilyRequest<QueryInvoiceRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<InvoiceResultBO> vATOrdinaryElectronicInvoice(FamilyRequest<VATElectronicInvoiceRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<FamilyBaseBO> invoiceHeaderCompletion(FamilyRequest<InvoiceHeaderCompletionRequest> paramFamilyRequest);

    @RequestMapping(value = {"invoice/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<FamilyBaseBO> enterpriseInfoSetting(FamilyRequest<EnterpriseInfoSetting> paramFamilyRequest);
}
