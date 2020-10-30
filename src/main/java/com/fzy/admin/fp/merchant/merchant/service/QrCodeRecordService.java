package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.merchant.merchant.repository.QrCodeRecordRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.merchant.domain.QrCodeRecord;
import com.fzy.admin.fp.merchant.merchant.repository.QrCodeRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:34 2019/4/30
 * @ Description: 批量二维码服务层
 **/
@Slf4j
@Service
@Transactional
public class QrCodeRecordService implements BaseService<QrCodeRecord> {

    @Resource
    private QrCodeRecordRepository qrCodeRecordRepository;


    @Override
    public QrCodeRecordRepository getRepository() {
        return qrCodeRecordRepository;
    }
}
