package com.fzy.admin.fp.distribution.shop.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.repository.GoodsPropertyRepository;
import com.fzy.admin.fp.distribution.shop.domain.Address;
import com.fzy.admin.fp.distribution.shop.repository.AddressRepository;
import com.fzy.admin.fp.distribution.shop.repository.DistGoodsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-2 14:02:04
 * @Desp
 **/
@Service
public class AddressService implements BaseService<Address> {

    @Resource
    private AddressRepository addressRepository;

    public AddressRepository getRepository() {
        return addressRepository;
    }

    /**
     * 修改地址
     * @param address
     */
    public void updateAddress(Address address){
        if(address.getStatus()!=null&&address.getStatus()==1){
            //将之前的默认地址取消默认
            Address byUserIdAndStatus = addressRepository.findByUserIdAndStatus(address.getUserId(), 1);
            if(byUserIdAndStatus!=null&&byUserIdAndStatus.getId()!=address.getId()){
                byUserIdAndStatus.setStatus(0);
                update(byUserIdAndStatus);
            }
        }
        update(address);
    }

    /**
     * 添加地址
     * @param address
     */
    public void saveAddress(Address address){
        if(address.getStatus()!=null&&address.getStatus()==1){
            //将之前的默认地址取消默认
            Address byUserIdAndStatus = addressRepository.findByUserIdAndStatus(address.getUserId(), 1);
            if(byUserIdAndStatus!=null){
                byUserIdAndStatus.setStatus(0);
                update(byUserIdAndStatus);
            }
        }
        save(address);
    }
}
