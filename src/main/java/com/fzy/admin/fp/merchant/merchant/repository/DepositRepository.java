package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.pay.pay.domain.Deposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface DepositRepository extends BaseRepository<Deposit> {
    @Query("from Deposit de where de.createTime>= ?1 and de.createTime<=?2 and de.merchantId=?3")
    Page<Deposit> getPage(Date paramDate1, Date paramDate2, String paramString, Pageable paramPageable);

    @Query("from Deposit de where de.createTime>= ?1 and de.createTime<=?2 and de.status=?3 and de.merchantId=?4")
    Page<Deposit> getPage(Date paramDate1, Date paramDate2, Integer paramInteger, String paramString, Pageable paramPageable);

    Deposit findByOrderId(String paramString);

    Deposit findTop1ByOfficeUserIdAndMerchantIdAndStatusOrderByCreateTimeDesc(String paramString1, String paramString2, Integer paramInteger);
}

