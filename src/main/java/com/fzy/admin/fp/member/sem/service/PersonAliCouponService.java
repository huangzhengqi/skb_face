package com.fzy.admin.fp.member.sem.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.sem.domain.PersonAliCoupon;
import com.fzy.admin.fp.member.sem.repository.PersonAliCouponRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonAliCouponService implements BaseService<PersonAliCoupon>{

    @Resource
    private PersonAliCouponRepository personAliCouponRepository;

    @Override
    public BaseRepository<PersonAliCoupon> getRepository() {
        return personAliCouponRepository;
    }

    List<PersonAliCoupon> findByCouponId(String merchantId,String memberId,String couponId){
        return new ArrayList<PersonAliCoupon>();
    }
}
