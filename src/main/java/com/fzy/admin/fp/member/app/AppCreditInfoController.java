package com.fzy.admin.fp.member.app;

import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/6/18 15:01
 * @Description 商户APP积分明细
 */
@RestController
@RequestMapping(value = "/member/credits_info/app")
public class AppCreditInfoController extends BaseController<CreditsInfo> {

    @Resource
    private CreditsInfoService creditsInfoService;

    @Override
    public BaseService<CreditsInfo> getService() {
        return creditsInfoService;
    }

    /**
     * 积分详细信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/find_detail")
    public Resp getCreditsInfo(String id) {

        return Resp.success(creditsInfoService.findOne(id));
    }

    @GetMapping(value = "/find_list")
    public Resp getCreditsInfos(@TokenInfo(property = "merchantId") String merchantId, CreditsInfo creditsInfo, PageVo pageVo) {

        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户错误");
        }
        creditsInfo.setMerchantId(merchantId);
        Page<CreditsInfo> page = creditsInfoService.list(creditsInfo, pageVo);

        return Resp.success(page);
    }

}
