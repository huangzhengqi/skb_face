package com.fzy.admin.fp.merchant.bcrmApp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:05 2019/6/1
 * @ Description: APP商户进件
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/merchant_info/bcrm_app")
public class BcrmAppMerchantInfoController extends BaseContent {

    @Resource
    private MchInfoService mchInfoService;

    /*
     * @author drj
     * @date 2019-06-01 11:09
     * @Description :获取进件列表
     */
    @GetMapping("/list")
    public Resp listRewrite(@TokenInfo(property = "companyId") String companyId,Integer status, PageVo pageVo) {

        return Resp.success(mchInfoService.listRewrite(companyId,status, pageVo));
    }

    /*
     * @author drj
     * @date 2019-06-01 11:10
     * @Description :查看进件列表详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(mchInfoService.findOne(id));
    }


    /*
     * @author drj
     * @date 2019-06-01 11:10
     * @Description ：修改进件
     */
    @PostMapping("/update")
    public Resp update(MchInfo model) {
        MchInfo mchInfo = mchInfoService.findOne(model.getId());
        if (mchInfo == null) {
            throw new BaseException("进件不存在", Resp.Status.PARAM_ERROR.getCode());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(model, mchInfo, copyOptions);
        mchInfoService.save(mchInfo);
        return Resp.success("修改成功");
    }
}
