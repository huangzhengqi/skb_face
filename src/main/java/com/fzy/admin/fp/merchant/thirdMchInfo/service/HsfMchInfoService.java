package com.fzy.admin.fp.merchant.thirdMchInfo.service;

import com.fzy.admin.fp.merchant.thirdMchInfo.repository.HsfMchInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchInfo;
import com.fzy.admin.fp.merchant.thirdMchInfo.repository.HsfMchInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:54 2019/6/25
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class HsfMchInfoService implements BaseService<HsfMchInfo> {

    @Resource
    private HsfMchInfoRepository hsfMchInfoRepository;

    @Override
    public HsfMchInfoRepository getRepository() {
        return hsfMchInfoRepository;
    }


    public Map<String, Object> hsfMap() {

        List<HsfMchInfo> hsfMchInfoList = hsfMchInfoRepository.findAll();
        //list转map
        return hsfMchInfoList.stream().collect(Collectors.toMap(HsfMchInfo::getMerchantId, HsfMchInfo::getStatus));
    }
}
