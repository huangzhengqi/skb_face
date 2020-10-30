package com.fzy.admin.fp.wx.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.wx.domain.WxRewardDetail;
import com.fzy.admin.fp.wx.repository.WxRewardDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/10/9 15:33
 * @Version 1.0
 * @description 奖励红包明细业务层
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class WxRewardDetailService implements BaseService<WxRewardDetail> {

    @Resource
    private WxRewardDetailRepository wxRewardDetailRepository;

    @PersistenceContext
    public EntityManager em;


    @Override
    public WxRewardDetailRepository getRepository() {
        return wxRewardDetailRepository;
    }

    public WxRewardDetail createWxRedPackDetail(Merchant merchant, String mchBillno, String serviceProviderId) {
        WxRewardDetail wxRewardDetail = new WxRewardDetail();
        wxRewardDetail.setMerchantId(merchant.getId());
        wxRewardDetail.setReOpenid(merchant.getOpenId());
        wxRewardDetail.setServiceProviderId(serviceProviderId);
        wxRewardDetail.setMchBillno(mchBillno);
        wxRewardDetail.setRewardPrice(merchant.getRewardPrice());
        return wxRewardDetail;
    }

    public void updateWxRedPackDetailAndMerchant(WxRewardDetail wxRewardDetail,String description, Integer returnType) {
        wxRewardDetail.setDescription(description);
        wxRewardDetail.setReturnType(returnType);
        wxRewardDetailRepository.saveAndFlush(wxRewardDetail);
    }

    public Map<String,String> findAmountReturnedAndActualCollection(String merchantId) {
        Map map = new HashMap();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT IFNULL(sum(reward_price),0) AS totalAmount2 from wx_reward_detail WHERE merchant_id = :merchantId and `status` is not null");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        for (Object obj : list) {
            Map row = (Map) obj;
            map.put("totalAmount2", row.get("totalAmount2").toString());
        }
        return map;
    }
}
