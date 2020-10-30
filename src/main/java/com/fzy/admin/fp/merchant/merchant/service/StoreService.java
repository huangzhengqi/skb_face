package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.repository.EquipmentRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.HuaBeiConfig;
import com.fzy.admin.fp.merchant.merchant.dto.HuaBeiSettingDTO;
import com.fzy.admin.fp.merchant.merchant.repository.HuabeiConfigRepository;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 门店服务层
 */
@Slf4j
@Service
@Transactional
public class StoreService implements BaseService<Store> {

    @Resource
    private StoreRepository storeRepository;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private HuabeiConfigRepository huabeiConfigRepository;

    @Resource
    private EquipmentRepository equipmentRepository;


    @Override
    public StoreRepository getRepository() {
        return storeRepository;
    }

    public String saveRewrite(Store entity, String userId) {

        //获取当前登录用户的商户id
        MerchantUser user = merchantUserService.findOne(userId);
        entity.setMerchantId(user.getMerchantId());
        entity.setStoreFlag(Store.StoreFlag.NORMAL.getCode());
        storeRepository.save(entity);
        return "保存成功";
    }


    public Map<String, Object> listRewrite(Store entity, PageVo pageVo, String userId) {
        Map<String, Object> map;
        //获取当前登录用户的用户类型
        MerchantUser user = merchantUserService.findOne(userId);
        //如果当前登录不是商户
        if (!MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            Store store = storeRepository.findOne(user.getStoreId());
            List<Store> storeList = new LinkedList<>();
            storeList.add(store);
            map = mapInit(1, 1, storeList);
            return map;
        }
        //如果为商户
        entity.setMerchantId(user.getMerchantId());
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Store> page = storeRepository.findAll(specification, pageable);
        map = mapInit(page.getTotalPages(), page.getTotalElements(), page.getContent());
        return map;
    }

    public Integer countStore(String id) {
        return storeRepository.countByMerchantId(id);
    }



    //查看门店详情
    @Override
    public Store findOne(String id) {
        if (ParamUtil.isBlank(id)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        Integer storeUserNum = merchantUserService.getRepository().countByStoreIdAndStatusAndDelFlag(id, User.Status.ENABLE.getCode(), CommonConstant.NORMAL_FLAG);
        Store store = storeRepository.findOne(id);
        store.setStoreUserNum(storeUserNum);
        return store;
    }


    //查看门店下所有员工数


    private Map<String, Object> mapInit(Integer totalPages, long totalElements, Object content) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", totalPages);
        map.put("totalElements", totalElements);
        map.put("content", content);
        return map;
    }

    public Resp<HuaBeiConfig> huabeiConfig(HuaBeiSettingDTO huaBeiSettingDTO) {
        Equipment equipment = equipmentRepository.findOne(huaBeiSettingDTO.getEquipmentId());
        if(ParamUtil.isBlank(equipment)){
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"当前设备不存在,请联系客服");
        }
        HuaBeiConfig huaBeiConfig = huabeiConfigRepository.findByStoreIdAndEquipmentId(equipment.getStoreId(), equipment.getId());
        if(ParamUtil.isBlank(huaBeiConfig)){
            huaBeiConfig = new HuaBeiConfig();
            huaBeiConfig.setMerchantId(equipment.getMerchantId());
            huaBeiConfig.setEquipmentId(huaBeiSettingDTO.getEquipmentId());
            huaBeiConfig.setStoreId(equipment.getStoreId());
            huaBeiConfig.setStatus(huaBeiSettingDTO.getStatus());
            huaBeiConfig.setInterest(huaBeiSettingDTO.getInterest());
        }else {
            huaBeiConfig.setStatus(huaBeiSettingDTO.getStatus());
            huaBeiConfig.setInterest(huaBeiSettingDTO.getInterest());
        }
        return Resp.success(huabeiConfigRepository.save(huaBeiConfig),"设置成功");
    }

    public Resp<HuaBeiConfig> getConfig1(String equipmentId) {
        Equipment equipment = equipmentRepository.findOne(equipmentId);
        if(ParamUtil.isBlank(equipment)){
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"当前设备不存在,请联系客服");
        }
        return Resp.success(huabeiConfigRepository.findByStoreIdAndEquipmentId(equipment.getStoreId(),equipmentId),"查询成功");
    }
}