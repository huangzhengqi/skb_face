package com.fzy.admin.fp.distribution.order.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.repository.ShopOrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-2 17:07:27
 * @Desp
 **/
@Service
public class ShopOrderService implements BaseService<ShopOrder> {

    @Resource
    private ShopOrderRepository shopOrderRepository;

    public ShopOrderRepository getRepository() {
        return shopOrderRepository;
    }


    /**
     * 退款
     */
    public void refund(){


    }
}
