package com.fzy.admin.fp.invoice.feign;

import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyRequest;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.req.FindSellerRequest;
import com.fzy.admin.fp.invoice.feign.req.RegisterSellerRequest;
import com.fzy.admin.fp.invoice.feign.resp.SellerBO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "familySellerClient", url = "${facepay.invoice.sellerApi}", configuration = {FamilyInvoiceFeignConfiguration.class})
public interface FamilySellerClient {
    @RequestMapping(value = {"merchant/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<FamilyBaseBO> registerSeller(FamilyRequest<RegisterSellerRequest> paramFamilyRequest);

    @RequestMapping(value = {"merchant/business"}, consumes = {"application/xml"}, produces = {"application/xml"},method = RequestMethod.POST)
    FamilyResp<SellerBO> findSeller(FamilyRequest<FindSellerRequest> paramFamilyRequest);
}
