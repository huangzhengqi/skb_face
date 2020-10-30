package com.fzy.admin.fp.invoice.feign;

import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.resp.AccessTokenBO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "familyAuthenticationClient", url = "${facepay.invoice.api}")
public interface FamilyAuthenticationClient {
    @RequestMapping(value = {"/invoice/token"},consumes = {"application/xml"}, produces = {"application/x-www-form-urlencoded"},method = RequestMethod.POST)
    FamilyResp<AccessTokenBO> getAccessToken(@RequestParam("appId") String paramString1, @RequestParam("appSecret") String paramString2);
}
