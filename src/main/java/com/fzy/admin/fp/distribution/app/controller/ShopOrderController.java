package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.order.domain.OrderGoods;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.dto.ShopOrderDTO;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-2 17:09:00
 * @Desp 订单
 **/

@RestController
@RequestMapping("/dist/app/order")
@Api(value = "AppOrderController", tags = {"分销-app订单管理"})
public class ShopOrderController extends BaseContent {
    @Resource
    private ShopOrderService shopOrderService;

    @GetMapping("/query")
    @ApiOperation(value = "app-我的订单", notes = "app-我的订单")
    public Resp query(@UserId String userId, ShopOrderDTO shopOrderDTO, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<ShopOrder> specification = new Specification<ShopOrder>() {
            @Override
            public Predicate toPredicate(Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("userId"), userId));
                predicates.add(cb.equal(root.get("delFlag"), CommonConstant.NORMAL_FLAG));
                if(shopOrderDTO.getStatus()!=null){
                    predicates.add(cb.equal(root.get("status").as(Integer.class), shopOrderDTO.getStatus()));
                }
                /*Join<ShopOrder, OrderGoods> oderJoin = root.join("orderGoods", JoinType.LEFT);
                //两张表关联查询
                predicates.add(cb.equal(oderJoin.get("delFlag"), 1));*/
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Page<ShopOrder> all = shopOrderService.findAll(specification, pageable);
        return Resp.success(all);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "app-订单详情", notes = "app-订单详情")
    public Resp detail(@UserId String userId, String id){
        Specification<ShopOrder> specification = new Specification<ShopOrder>() {
            @Override
            public Predicate toPredicate(Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("userId"), userId));
                predicates.add(cb.equal(root.get("id"), id));
                Join<ShopOrder, OrderGoods> oderJoin = root.join("orderGoods", JoinType.LEFT);
                //两张表关联查询
                predicates.add(cb.equal(oderJoin.get("delFlag"), 1));
                Predicate[] pre = new Predicate[predicates.size()]; 
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        return Resp.success(shopOrderService.findOne(specification));
    }

    @PostMapping("/remove")
    @ApiOperation(value = "app-取消订单", notes = "app-取消订单")
    public Resp remove(@UserId String userId,String id){
        ShopOrder shopOrder = shopOrderService.getRepository().findByUserIdAndId(userId, id);
        if(shopOrder==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        shopOrder.setDelFlag(CommonConstant.DEL_FLAG);
        shopOrderService.update(shopOrder);
        return Resp.success("取消成功");
    }

    @PostMapping("/refund")
    @ApiOperation(value = "app-申请退款", notes = "app-申请退款")
    public Resp refund(@UserId String userId,String id,String refundReason){
        ShopOrder shopOrder = shopOrderService.getRepository().findByUserIdAndId(userId, id);
        if(shopOrder==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        shopOrder.setStatus(4);
        shopOrder.setRefundReason(refundReason);
        shopOrder.setApplyRefundTime(new Date());
        shopOrderService.update(shopOrder);
        return Resp.success("取消成功");
    }

    @PostMapping("/finish")
    @ApiOperation(value = "app-确认收货", notes = "app-确认收货")
    public Resp finish(@UserId String userId,String id){
        ShopOrder shopOrder = shopOrderService.getRepository().findByUserIdAndId(userId, id);
        if(shopOrder==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        shopOrder.setStatus(3);
        shopOrder.setFinishTime(new Date());
        shopOrderService.update(shopOrder);
        return Resp.success("确认收货成功");
    }

}
