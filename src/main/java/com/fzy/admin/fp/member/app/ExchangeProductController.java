package com.fzy.admin.fp.member.app;


import com.fzy.admin.fp.member.credits.domain.ExchangeProduct;
import com.fzy.admin.fp.member.credits.service.ExchangeProductService;
import com.fzy.admin.fp.member.credits.service.ExchangeRecordService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.ExchangeProduct;
import com.fzy.admin.fp.member.credits.service.ExchangeProductService;
import com.fzy.admin.fp.member.credits.service.ExchangeRecordService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lb
 * @date 2019/5/20 9:11
 * @Description 兑换商品
 */

@RestController
@RequestMapping(value = "/member/exchange_product/app")
public class ExchangeProductController {

    @Resource
    private ExchangeProductService exchangeProductService;

    @Resource
    private StoreServiceFeign storeServiceFeign;

    @Resource
    private ExchangeRecordService exchangeRecordService;

    @PostMapping(value = "/exchange")
    public Resp changeProduct(@Valid ExchangeProduct exchangeProduct, BindingResult bindingResult,
                              @TokenInfo(property = "merchantId") String merchantId
            , @RequestParam(value = "app", required = false) Integer app, @RequestParam(value = "storeId", required = false) String storeId) {
        if (bindingResult.hasErrors()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        exchangeProduct.setMerchantId(merchantId);
        int apps = 0;
        if (app != null) {
            apps = app;
        }
        String storeName;
        if (storeId == null) {
            MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(merchantId);
            storeId = merchantDefaultStore.getStoreId();
            storeName = merchantDefaultStore.getStoreName();
        } else {
            storeName = storeServiceFeign.findStore(storeId);
            if (storeName == null) {
                MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(merchantId);
                storeId = merchantDefaultStore.getStoreId();
                storeName = merchantDefaultStore.getStoreName();
            }
        }
        return exchangeProductService.change(exchangeProduct, apps, storeId, storeName);
    }

    @PostMapping(value = "/check_status")
    public Resp updateStatus(@TokenInfo(property = "merchantId") String merchantId, String code) {
        return exchangeProductService.update(merchantId, code);
    }

}
