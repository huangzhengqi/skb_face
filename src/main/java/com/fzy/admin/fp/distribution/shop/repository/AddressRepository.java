package com.fzy.admin.fp.distribution.shop.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.shop.domain.Address;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressRepository extends BaseRepository<Address> {
    Page<Address> findAllByUserId(String userId, Pageable pageable);

    List<Address> findAllByUserId(String userId);

    Address findByUserIdAndId(String userId,String id);


    Address findByUserIdAndStatus(String userId,Integer id);
}
