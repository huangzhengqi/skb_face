package com.fzy.admin.fp.distribution.shop.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.pc.service.GoodsPropertyService;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;
import com.fzy.admin.fp.distribution.shop.repository.DistGoodsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-11-30 10:01:47
 * @Desp
 **/
@Service
public class DistGoodsService implements BaseService<DistGoods> {

    @Resource
    private DistGoodsRepository distGoodsRepository;

    @Resource
    private GoodsPropertyService goodsPropertyService;

    @Resource
    private ShopCartService shopCartService;

    public DistGoodsRepository getRepository() {
        return distGoodsRepository;
    }

    @Transactional
    public void update(DistGoods distGoods,String[] propertyName,String[] property){
        update(distGoods);
        goodsPropertyService.getRepository().deleteByGoodsId(distGoods.getId());
        List<GoodsProperty> goodsPropertyList=new ArrayList<>();
        for(int i=0;i<propertyName.length;i++){
            GoodsProperty goodsProperty=new GoodsProperty();
            goodsProperty.setName(propertyName[i]);
            goodsProperty.setStatus(0);
            goodsProperty.setProperty(property[i]);
            goodsProperty.setGoodsId(distGoods.getId());
            goodsProperty.setServiceProviderId(distGoods.getServiceProviderId());
            goodsPropertyList.add(goodsProperty);
        }
        goodsPropertyService.save(goodsPropertyList);
    }


    /*@Transactional
    public void save(DistGoods distGoods,String[] propertyName,String[] property){
        distGoods=save(distGoods);
        List<GoodsProperty> goodsPropertyList=new ArrayList<>();
        for(int i=0;i<propertyName.length;i++){
            GoodsProperty goodsProperty=new GoodsProperty();
            goodsProperty.setName(propertyName[i]);
            goodsProperty.setStatus(0);
            goodsProperty.setProperty(property[i]);
            goodsProperty.setGoodsId(distGoods.getId());
            goodsProperty.setServiceProviderId(distGoods.getServiceProviderId());
            goodsPropertyList.add(goodsProperty);
        }
        goodsPropertyService.save(goodsPropertyList);
    }*/



    @Transactional
    public void deleteById(String id){
        goodsPropertyService.getRepository().deleteByGoodsId(id);//删除商品属性
        shopCartService.getRepository().deleteByGoodsId(id);//删除购物车里的商品信息
        delete(id);//删除商品
    }
}
