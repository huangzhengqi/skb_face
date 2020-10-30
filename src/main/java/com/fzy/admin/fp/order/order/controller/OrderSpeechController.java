package com.fzy.admin.fp.order.order.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.service.SpeechOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @Author ：drj.
 * @Date  ：Created in 15:34 2019/6/19
 * @Description: 订单语音接口
 **/
@RestController
@RequestMapping("/order/order")
public class OrderSpeechController extends BaseContent {

    @Resource
    private SpeechOrderService speechOrderService;

//    @GetMapping("/training/rotation")
//    public Resp rotation(@TokenInfo(property = "merchantId") String merchantId, String storeId, String beginTime, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
//        return Resp.success(speechOrderService.speechInfos(merchantId, storeId, beginTime, serviceProviderId));
//    }

    @GetMapping({"/training/rotation"})
    public Resp rotation(@TokenInfo(property = "merchantId") String merchantId, String storeId, String beginTime, @RequestAttribute("serviceProviderId") String serviceProviderId){
        return Resp.success(this.speechOrderService.speechInfos(merchantId, storeId, beginTime, serviceProviderId));
    }

    @ApiOperation(value = "获取支付成功订单语音播报",notes = "获取支付成功订单语音播报")
    @GetMapping("/training/order")
    public Resp trainingOrder(@RequestAttribute("serviceProviderId") String serviceProviderId ,String orderNumber){
        return Resp.success(this.speechOrderService.trainingOrder(serviceProviderId,orderNumber));
    }
}
