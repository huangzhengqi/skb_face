package com.fzy.admin.fp.merchant.management.feignImpl;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.domain.SnConfig;
import com.fzy.admin.fp.merchant.merchant.repository.SnConfigRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.domain.SnConfigVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Created by wtl on 2019-03-14 10:13
 * @description
 */
@Service
public class MerchantBusinessServiceFeignImpl implements MerchantBusinessService {


    @Resource
    private MerchantService merchantService;
    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;

    @Resource
    private SnConfigRepository snConfigRepository;


    @Override
    public List<MerchantVO> findByCompanyIds(String[] ids) {
        List<Merchant> merchants;
        if (ParamUtil.isBlank(ids)) {
            merchants = new ArrayList<>();
        } else {
            merchants = merchantService.getRepository().findByCompanyIdIn(ids);
        }

        return merchants.stream().map(merchant -> {
            MerchantVO merchantVO = new MerchantVO();
            BeanUtils.copyProperties(merchant, merchantVO);
            return merchantVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MerchantVO> findByAgentId(String id) {
        List<Merchant> merchants;
        merchants = merchantService.getRepository().findByManagerIdAndTypeOrderByCreateTimeDesc(id,1);

        return merchants.stream().map(merchant -> {
            MerchantVO merchantVO = new MerchantVO();
            BeanUtils.copyProperties(merchant, merchantVO);
            return merchantVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer findByCompanyId(String companyId) {
        return merchantService.getRepository().countByCompanyIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG);
    }

    @Override
    public MerchantVO findByMerchantId(String id) {
        Merchant merchant = merchantService.findOne(id);
        if (merchant == null) {
            return null;
        }
        MerchantVO merchantVO = new MerchantVO();
        BeanUtils.copyProperties(merchant, merchantVO);
        return merchantVO;
    }

    //获取业务员对应的商户selectItem
    @Override
    public List<MerchantSelectItem> findByManagerIdSelectItem(String managerId) {
        return merchantService.getRepository().findByManagerIdSelectItem(managerId);
    }

    @Override
    public boolean changeManagerId(String[] merchantIds, String managerId) {
        List<Merchant> merchantList = merchantService.getRepository().findAll(Arrays.asList(merchantIds));
        for (Merchant merchant : merchantList) {
            merchant.setManagerId(managerId);
        }
        merchantService.save(merchantList);
        return true;
    }

    @Override
    public AppletConfigVO findByAppId(String appId) {
        final MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByAppId(appId);
        if (ParamUtil.isBlank(merchantAppletConfig)) {
            return null;
        }
        return new AppletConfigVO(merchantAppletConfig.getMerchantId(), merchantAppletConfig.getAppKey(), merchantAppletConfig.getAppId());
    }

    @Override
    public AppletConfigVO findAppletByMerchantId(String merchantId) {
        final MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(merchantId);
        if (ParamUtil.isBlank(merchantAppletConfig)) {
            return null;
        }
        return new AppletConfigVO(merchantAppletConfig.getMerchantId(), merchantAppletConfig.getAppKey(), merchantAppletConfig.getAppId());
    }

    @Override
    public SnConfigVO findBySn(String sn) {
        SnConfig snConfig = snConfigRepository.findBySnAndDelFlag(sn, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(snConfig)) {
            return null;
        }
        // 获取商户
        Merchant merchant = merchantService.findOne(snConfig.getMerchantId());
        SnConfigVO snConfigVO = new SnConfigVO();
        snConfigVO.setMid(merchant.getId());
        snConfigVO.setAppKey(merchant.getAppKey());
        snConfigVO.setFlag(snConfig.getFlag());
        return snConfigVO;
    }

    @Override
    public void editSnConfig(String sn, Integer flag) {
        SnConfig snConfig = snConfigRepository.findBySnAndDelFlag(sn, CommonConstant.NORMAL_FLAG);
        snConfig.setFlag(flag);
        snConfigRepository.save(snConfig);
    }
}
