package com.fzy.admin.fp.sdk.merchant.feign;


import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.domain.SnConfigVO;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.domain.SnConfigVO;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:58 2019/4/25
 * @ Description:
 **/
public interface MerchantBusinessService {

    List<MerchantVO> findByAgentId(String ids);


    List<MerchantVO> findByCompanyIds(String[] ids);

    //查询一级代理商或者二级代理商底下是否存在商户
    Integer findByCompanyId(String companyId);

    MerchantVO findByMerchantId(String id);

    //查询一级代理商或者二级代理商底下商户SelectItem
    List<MerchantSelectItem> findByManagerIdSelectItem(String managerId);

    boolean changeManagerId(String[] merchantIds, String managerId);

    AppletConfigVO findByAppId(String appId);

    // 根据商户id获取商户的小程序配置
    AppletConfigVO findAppletByMerchantId(String merchantId);

    // 根据sn号获取sn配置
    SnConfigVO findBySn(String sn);

    // 修改sn配置的状态
    void editSnConfig(String sn, Integer flag);


}
