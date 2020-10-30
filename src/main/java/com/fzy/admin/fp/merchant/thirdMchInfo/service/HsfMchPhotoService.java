package com.fzy.admin.fp.merchant.thirdMchInfo.service;

import com.fzy.admin.fp.merchant.thirdMchInfo.repository.HsfMchPhotoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchPhoto;
import com.fzy.admin.fp.merchant.thirdMchInfo.repository.HsfMchPhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:56 2019/6/27
 * @ Description: 慧闪付图片进件业务层
 **/
@Slf4j
@Service
@Transactional
public class HsfMchPhotoService implements BaseService<HsfMchPhoto> {

    @Resource
    private HsfMchPhotoRepository hsfMchPhotoRepository;

    @Override
    public HsfMchPhotoRepository getRepository() {
        return hsfMchPhotoRepository;
    }


}
