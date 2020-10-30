package com.fzy.admin.fp.wx.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;
import com.fzy.admin.fp.wx.repository.WxRedPackDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/9/10 14:41
 * @Version 1.0
 * @description 返佣红包明细业务层
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class WxRedPackDetailService implements BaseService<WxRedPackDetail> {

    @Resource
    private WxRedPackDetailRepository wxRedPackDetailRepository;

    @Resource
    private MerchantService merchantService;

    @PersistenceContext
    public EntityManager em;

    @Override
    public WxRedPackDetailRepository getRepository() {
        return wxRedPackDetailRepository;
    }

    public WxRedPackDetail createWxRedPackDetail(Merchant merchant, String mchBillno, String serviceProviderId) {
        WxRedPackDetail wxRedPackDetail = new WxRedPackDetail();
        wxRedPackDetail.setMerchantId(merchant.getId());
        wxRedPackDetail.setReOpenid(merchant.getOpenId());
        wxRedPackDetail.setServiceProviderId(serviceProviderId);
        wxRedPackDetail.setMchBillno(mchBillno);
        return wxRedPackDetail;
    }

    public void updateWxRedPackDetailAndMerchant(Merchant merchant, BigDecimal actualMoney, WxRedPackDetail wxRedPackDetail, String description, Integer returnType) {
        wxRedPackDetail.setDescription(description);
        wxRedPackDetail.setReturnType(returnType);
        wxRedPackDetail.setTotalAmount(actualMoney);
        wxRedPackDetailRepository.saveAndFlush(wxRedPackDetail);

        //待结算金额 + 返佣金额
        merchant.setWaitRebate(merchant.getWaitRebate() == null ? new BigDecimal("0") : merchant.getWaitRebate().add(actualMoney));
        merchantService.update(merchant);
    }

    public Map<String, String> findAmountReturnedAndActualCollection(String merchantId) {
        Map map = new HashMap();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT IFNULL(sum(total_amount),0) AS totalAmount from wx_red_pack_detail WHERE merchant_id = :merchantId and return_type != 0 ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        for (Object obj : list) {
            Map row = (Map) obj;
            map.put("totalAmount", row.get("totalAmount").toString());
        }

        StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT IFNULL(sum(total_amount),0) AS totalAmount2 from wx_red_pack_detail WHERE merchant_id = :merchantId and `status` is not null");
        query = em.createNativeQuery(sb2.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        list = query.getResultList();
        for (Object obj : list) {
            Map row = (Map) obj;
            map.put("totalAmount2", row.get("totalAmount2").toString());
        }
        return map;
    }

    public Map findTotal(Map<String,Object> hashMap ,String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT IFNULL(sum(total_amount),0) AS totalAmount from wx_red_pack_detail WHERE service_provider_id = :serviceProviderId and return_type != 0 ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("serviceProviderId", id);
        List<Map> list = query.getResultList();
        for (Object obj : list) {
            Map row = (Map) obj;
            hashMap.put("totaL1", row.get("totalAmount").toString());
        }

        StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT IFNULL(sum(total_amount),0) AS totalAmount2 from wx_red_pack_detail WHERE service_provider_id = :serviceProviderId and `status` is not null");
        query = em.createNativeQuery(sb2.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("serviceProviderId", id);
        list = query.getResultList();
        for (Object obj : list) {
            Map row = (Map) obj;
            hashMap.put("totaL2", row.get("totalAmount2").toString());
        }
        return hashMap;
    }
}
