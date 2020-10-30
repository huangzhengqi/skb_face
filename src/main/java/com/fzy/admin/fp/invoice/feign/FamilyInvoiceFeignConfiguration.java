package com.fzy.admin.fp.invoice.feign;

import com.fzy.admin.fp.invoice.sign.AuthenticationSignHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

public class FamilyInvoiceFeignConfiguration implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FamilyInvoiceFeignConfiguration.class);


    @Autowired
    private AuthenticationSignHelper authenticationSignHelper;


    public void apply(RequestTemplate template) {
        String body = new String(template.request().body(), StandardCharsets.UTF_8);
        log.info("请求的xml报文11111111{}", body);

        this.authenticationSignHelper.setHeaders(template);
    }
}
