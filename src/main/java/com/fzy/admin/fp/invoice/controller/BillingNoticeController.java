package com.fzy.admin.fp.invoice.controller;



import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyNoticeResponseBO;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.resp.InvoiceNoticeBO;
import com.fzy.admin.fp.invoice.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/"})
public class BillingNoticeController
{
    private static final Logger log = LoggerFactory.getLogger(BillingNoticeController.class);





    @Autowired
    private BillingService billingService;






    @RequestMapping(value = {"invoice_notify"}, produces = {"application/xml"})
    @ResponseBody
    public FamilyResp<FamilyNoticeResponseBO> resultNotice(@RequestBody FamilyResp<InvoiceNoticeBO> notifyData) {
        if (notifyData == null || !notifyData.isSuccess()) {
            return null;
        }

        log.info("数组开票通知   通知参数=====> {}", notifyData);
        InvoiceNoticeBO body = (InvoiceNoticeBO)notifyData.getBody();

        Resp resp = this.billingService.billingCallback(body.getInvoiceSn(), (InvoiceNoticeBO.InvoiceInfo)body.getInvoiceInfos().get(0));
        log.info("开票通知回调{}", resp);
        boolean isSuccess = Resp.Status.SUCCESS.getCode().equals(resp.getCode());


        return outResponseMessage(isSuccess, body.getInvoiceSn());
    }



    private FamilyResp<FamilyNoticeResponseBO> outResponseMessage(boolean success, String invoiceSn) {
        FamilyResp<FamilyNoticeResponseBO> resp = new FamilyResp<FamilyNoticeResponseBO>();
        resp.setId("FPTZ");
        FamilyNoticeResponseBO body = new FamilyNoticeResponseBO();
        body.setReturnCode(success ? "0" : "100002");
        body.setInvoiceSn(invoiceSn);
        resp.setBody(body);

        return resp;
    }
}
