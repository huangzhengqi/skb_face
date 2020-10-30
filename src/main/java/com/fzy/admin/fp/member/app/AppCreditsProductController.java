package com.fzy.admin.fp.member.app;

import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.service.CreditsProductService;
import com.fzy.admin.fp.member.credits.utils.CorrectionService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsProduct;
import com.fzy.admin.fp.member.credits.service.CreditsProductService;
import com.fzy.admin.fp.member.credits.utils.CorrectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/23 15:15
 * @Description
 */

@RestController
@Slf4j
@RequestMapping("/member/credits_product/app")
public class AppCreditsProductController extends BaseController<CreditsProduct> {

    @Resource
    private CreditsProductService creditsProductService;

    @Resource
    private CorrectionService correctionService;


    @Override
    public BaseService<CreditsProduct> getService() {
        return creditsProductService;
    }

    @GetMapping(value = "/list_re")
    public Resp getProductList(@TokenInfo(property = "merchantId") String merchantId,
                               CreditsProduct creditsProduct, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误! ");
        }
        //获取该商户的所有活动并修改状
        List<CreditsProduct> creditsProducts = creditsProductService.findByMerchantId(merchantId);
        //更新数据状态
        correctionService.correction(creditsProducts);
        //二次查询正确状态数据并返回
        creditsProduct.setMerchantId(merchantId);
        Page<CreditsProduct> page1 = creditsProductService.list(creditsProduct, pageVo);

        return Resp.success(page1);
    }

    @GetMapping(value = "/search")
    public Resp<CreditsProduct> search(String id) {
        return Resp.success(creditsProductService.search(id));
    }

    @PostMapping(value = "/save_re")
    public Resp reSave(@TokenInfo(property = "merchantId") String merchantId, @Valid CreditsProduct creditsProduct) {

        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误! ");
        }
        creditsProduct.setMerchantId(merchantId);
        creditsProductService.save(creditsProduct);
        return Resp.success("添加成功");
    }

}
