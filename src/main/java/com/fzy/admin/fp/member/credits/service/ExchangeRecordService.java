package com.fzy.admin.fp.member.credits.service;


import com.fzy.admin.fp.member.credits.repository.ExchangeRecordRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.credits.domain.ExchangeRecord;
import com.fzy.admin.fp.member.credits.repository.ExchangeRecordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/17 10:30
 * @Description
 */
@Service
public class ExchangeRecordService implements BaseService<ExchangeRecord> {

    @Resource
    private ExchangeRecordRepository exchangeRecordRepository;

    @Override
    public BaseRepository<ExchangeRecord> getRepository() {
        return exchangeRecordRepository;
    }

    public List<ExchangeRecord> findRecords(String merchantId, String memberId, String productId) {
        return exchangeRecordRepository.findByMerchantIdAndMemberIdAndProductId(merchantId, memberId, productId);
    }

    public List<ExchangeRecord> findByMerchantId(String merchantId) {
        return exchangeRecordRepository.findByMerchantId(merchantId);
    }


    public ExchangeRecord findByMerchantIdAndStatusAndGoodCodes(String merchantId, Integer status, String goodCodes) {
        return exchangeRecordRepository.findByMerchantIdAndStatusAndGoodCodes(merchantId, status, goodCodes);
    }

    public Integer countByProductIdAndStatus(String productId,Integer status){
        return exchangeRecordRepository.countByProductIdAndStatus(productId,status);
    }
}
