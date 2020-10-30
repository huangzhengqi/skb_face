package com.fzy.admin.fp.invoice.feign;

import com.fzy.admin.fp.invoice.feign.jackson.FamilyRequest;
import com.fzy.admin.fp.invoice.feign.req.*;

public final class FamilyRequestBuilder {

    public static FamilyRequest<RegisterSellerRequest> newSellerMangerRequest(RegisterSellerRequest request) { return newRequest("KHXXTS", request); }

    public static FamilyRequest<FindSellerRequest> newSellerQueryRequest(FindSellerRequest request) { return newRequest("MERCHANTSEARCH", request); }

    public static FamilyRequest<QueryGoodsRequest> newQueryGoodsNumberRequest(QueryGoodsRequest request) { return newRequest("SSBMQQ", request); }

    public static FamilyRequest<SetCustomizeGoodsCodeRequest> newSetCustomizeGoodsCodeRequestRequest(SetCustomizeGoodsCodeRequest request) { return newRequest("SPBMSZ", request); }

    public static FamilyRequest<QueryCustomizeGoodsCodeRequest> newQueryCustomizeGoodsCodeRequest(QueryCustomizeGoodsCodeRequest request) { return newRequest("SPBM", request); }

    public static FamilyRequest<DeleteCustomizeGoodsCodeRequest> newDeleteCustomizeGoodsCodeRequestRequest(DeleteCustomizeGoodsCodeRequest request) { return newRequest("SPBMSC", request); }










    public static FamilyRequest<FindTerminalRequest> newFindTerminalRequest(FindTerminalRequest request) { return newRequest("TERMINALMANAGE", request); }











    public static FamilyRequest<VATElectronicInvoiceRequest> newVATElectronicInvoiceRequest(VATElectronicInvoiceRequest request) { return newRequest("FPKJ", request); }










    public static FamilyRequest<EnterpriseInfoSetting> newEnterpriseInfoSetting(EnterpriseInfoSetting request) { return newRequest("QYXXSZ", request); }












    public static <T> FamilyRequest<T> newRequest(String code, T request) { return new FamilyRequest(code, request); }
}
