package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
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
 * @Date 2020/10/22 14:29
 * @Version 1.0
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class CollectionEquipmentService {

    @Resource
    private MerchantUserService merchantUserService;
    @Resource
    private StoreService storeService;
    @PersistenceContext
    public EntityManager em;

    /**
     * 收款设备列表
     * @param userId
     */
    public Resp list(String userId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        if(!merchantUser.getUserType().equals(MerchantUser.UserType.MERCHANT.getCode())){
            return Resp.success("数据为空");
        }
        //微信刷脸设备
        List<Map> maps0 = equipmentList(merchantUser.getMerchantId());
        //二维码贴牌
        List<Map> maps1 = mchBlankQrCodeList(merchantUser.getMerchantId());
        //云喇叭
        List<Map> maps2 = feiyuConfigList(merchantUser.getMerchantId());

        Map<String,List<Map>> map = new HashMap<>();
        map.put("maps0",maps0);
        map.put("maps1",maps1);
        map.put("maps2",maps2);
        return Resp.success(map);
    }

    private List<Map> feiyuConfigList(String merchantId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id,b.`name` AS storeName from feiyu_config a left join lysj_merchant_store b on a.store_id = b.id left join lysj_merchant_merchant c on b.merchant_id = c.id WHERE c.id = :merchantId ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        return list;
    }

    private List<Map> mchBlankQrCodeList(String merchantId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id AS id , b.name AS storeName, a.qr_code_id AS qrCodeId from merchant_blank_qr_code a left join lysj_merchant_store b on a.store_id = b.id WHERE a.merchant_id = :merchantId ORDER BY a.create_time DESC");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        return list;
    }

    private List<Map> equipmentList(String merchantId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id, a.device_id AS deviceId ,b.`name` AS storeName , a.device_type AS deviceType from equpmient a left join lysj_merchant_store b on a.store_id = b.id WHERE a.merchant_id = :merchantId ORDER BY a.create_time DESC ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchantId);
        List<Map> list = query.getResultList();
        return list;
    }

    public Resp getStore(String userId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        if(!merchantUser.getUserType().equals(MerchantUser.UserType.MERCHANT.getCode())){
            return Resp.success("数据为空");
        }
        return Resp.success(storeService.getRepository().findByMerchantId(merchantUser.getMerchantId()));
    }

    public Resp getUserId(String storeId) {
       return  Resp.success(merchantUserService.getRepository().findByStoreId(storeId));
    }
}
