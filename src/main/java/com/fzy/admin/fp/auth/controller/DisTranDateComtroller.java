package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.DisOrderDTO;
import com.fzy.admin.fp.auth.dto.EquipmenSearchDTO;
import com.fzy.admin.fp.auth.dto.EquipmentDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.DistribuService;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.auth.vo.DisTranDataVO;
import com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO;
import com.fzy.admin.fp.auth.vo.EquipmentVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.PageResult;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.merchant.app.dto.MyMerchantDTO;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.service.OrderService;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author fyz123
 * @create 2020/7/23 8:50
 * @Description: 服务商后台交易数据
 */
@Slf4j
@RestController
@RequestMapping("/auth/disTran")
@Api(value = "DisTranDateComtroller", tags = "服务商后台-交易数据相关")
public class DisTranDateComtroller extends BaseController {

    @Resource
    private DistribuService distribuService;

    @Override
    public DistribuService getService() {
        return distribuService;
    }

    @Resource
    private CompanyService companyService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private StoreService storeService;

    @PersistenceContext
    public EntityManager em;

    @Resource
    private OrderService orderService;

    @Resource
    private DistUserService distUserService;

    @Resource
    private UserService userService;

    @Resource
    private EquipmentService equipmentService;

    @GetMapping("/order_list")
    @ApiOperation("交易数据列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "storeId", dataType = "String", value = "门店ID"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", value = "门店/商户"),
            @ApiImplicitParam(paramType = "query", name = "beginTime", dataType = "String", value = "开始日期"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "String", value = "结束日期")})
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response = DisTranDataVO.class)})
    public Resp list(@TokenInfo(property = "companyId") String companyId, String name, String beginTime, String endTime, PageVo pageVo) {
        Company company = companyService.findOne(companyId);
        //查询服务商下的所有商户
        List<Merchant> merchants = merchantService.getRepository().findByServiceProviderId(company.getId());
        List<String> merchantIds = merchants.stream().map(Merchant::getId).distinct().filter(i -> !StringUtil.isEmpty(i)).collect(Collectors.toList());
        //根据商户查询门店
        List<Store> stores = storeService.getRepository().findByMerchantIdIn(merchantIds);
        List<String> storeIds = stores.stream().map(Store::getId).distinct().filter(i -> !StringUtil.isEmpty(i)).collect(Collectors.toList());
        PageResult result = disTranList(storeIds, name, beginTime, endTime, pageVo);
        //根据门店查询订单信息
        return Resp.success(result, "获取列表");
    }

    @GetMapping("/details")
    @ApiOperation("交易数据详情")
    public Resp<Map<String, Object>> details(@TokenInfo(property = "companyId") String companyId, DisOrderDTO disOrderDTO, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<AdvertiseDevice> specification = new Specification<AdvertiseDevice>() {
            @Override
            public Predicate toPredicate(Root<AdvertiseDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("storeId"), disOrderDTO.getStoreId()));
                if (null != disOrderDTO.getOrderName()) {
                    Predicate pre1 = cb.like(root.get("orderNumber"), "%" + disOrderDTO.getOrderName() + "%");
                    Predicate pre2 = cb.like(root.get("userName"), "%" + disOrderDTO.getOrderName() + "%");
                    predicates.add(cb.or(pre1, pre2));
                }
                if (StringUtil.isNotEmpty(disOrderDTO.getStatus())) {
                    predicates.add(cb.equal(root.get("status"), disOrderDTO.getStatus()));
                }
                //
                if (StringUtil.isNotEmpty(disOrderDTO.getPayType())) {
                    predicates.add(cb.equal(root.get("payType"), disOrderDTO.getPayType()));
                }
                if (StringUtil.isNotEmpty(disOrderDTO.getPayWay())) {
                    predicates.add(cb.equal(root.get("payWay"), disOrderDTO.getPayWay()));
                }
                if (null != disOrderDTO.getPayWay() && StringUtil.isNotEmpty(disOrderDTO.getPayWay())) {
                    predicates.add(cb.equal(root.get("payWay"), disOrderDTO.getPayWay()));
                }
                if (null != disOrderDTO.getBeginTime() && StringUtil.isNotEmpty(disOrderDTO.getPayWay())) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("payTime"), disOrderDTO.getBeginTime()));
                }
                if (null != disOrderDTO.getEndTime() && StringUtil.isNotEmpty(disOrderDTO.getPayWay())) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("payTime"), disOrderDTO.getEndTime()));
                }
                predicates.add(cb.equal(root.get("delFlag"), "1"));
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Page<Order> page = this.orderService.getRepository().findAll(specification, pageable);
        //查询订单总金额,退款金额，客户付款，优惠
        List<Order> list = this.orderService.getRepository().findAll(specification);
        //订单总金额
        List<BigDecimal> totalPrices = list.stream().filter(dto -> "2".equals(dto.getStatus().toString()) || "6".equals(dto.getStatus().toString())).map(dto -> dto.getTotalPrice()).collect(Collectors.toList());
        BigDecimal totalPrice = totalPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        //退款金额
        List<BigDecimal> refundPayPrices = list.stream().map(dto -> dto.getRefundPayPrice()).collect(Collectors.toList());
        BigDecimal refundPayPrice = refundPayPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        //客户付款
        List<BigDecimal> actPayPrices = list.stream().filter(order -> order.getStatus().toString().equals("2")).map(dto -> dto.getActPayPrice()).collect(Collectors.toList());
        BigDecimal actPayPrice = actPayPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        //优惠
        List<BigDecimal> disCountPrices = list.stream().map(dto -> dto.getDisCountPrice()).collect(Collectors.toList());
        BigDecimal disCountPrice = disCountPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> map = new HashMap<>();
        map.put("totalPrice", totalPrice);
        map.put("refundPayPrice", refundPayPrice);
        map.put("actPayPrice", actPayPrice);
        map.put("disCountPrice", disCountPrice);
        map.put("page", page);
        return Resp.success(map, "获取列表");
    }

    /**
     * 根据门店ID查询交易数据
     *
     * @param storeIds
     * @param name
     * @param beginTime
     * @param endTime
     * @param pageVo
     * @return
     */
    public PageResult disTranList(List<String> storeIds, String name, String beginTime, String endTime, PageVo pageVo) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ms.`name` AS storeName,m.`name` AS merchantName,ms.id,count( 1 ) AS orderCount,SUM( act_pay_price ) AS orderActPayPrice," +
                " od.store_id AS storeId,m.id AS merchantId ");
        sb.append(" FROM lysj_order_order od LEFT JOIN lysj_merchant_store ms ON od.store_id = ms.id ");
        sb.append(" LEFT JOIN lysj_merchant_merchant m ON ms.merchant_id = m.id");
        sb.append(" WHERE store_id IN ( :storeId ) ");
        if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime)) {
            sb.append(" AND pay_time >= :beginTime");
            sb.append(" AND pay_time <= :endTime");
        }
        if (StringUtil.isNotEmpty(name)) {
            sb.append(" AND (ms.`name` like '%" + name + "%'OR m.`name` like '%" + name + "%')");
        }
        //状态支付成功，部分退款成功
        sb.append(" AND od.`status` IN ( '2', '6' ) GROUP BY od.store_id");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("storeId", storeIds);
        if (StringUtil.isNotEmpty(beginTime)) {
            query.setParameter("beginTime", beginTime);
        }
        if (StringUtil.isNotEmpty(endTime)) {
            query.setParameter("endTime", endTime);
        }
        PageResult pageResult = new PageResult();
        //总数
        pageResult.setTotal(query.getResultList().size());
        //分页
        int startIndex = pageVo.getPageSize() * (pageVo.getPageNumber() - 1);
        query.setFirstResult(startIndex);
        query.setMaxResults(pageVo.getPageSize());


        //获取列表分页
        List list = query.getResultList();
        //查询刷脸金额,有效的交易金额状态 2，6，支付成功，部分退款成功
        List<DisTranDataVO> disTranDataVOS = new ArrayList<>();
        for (Object obj : list) {
            DisTranDataVO disTranDataVO = new DisTranDataVO();
            Map row = (Map) obj;
            StringBuilder builder = new StringBuilder();
            builder.append(" SELECT count( 1 ) faceNum,IFNULL( SUM( act_pay_price ), 0 ) AS facePrice,lr.face2Num ");
            builder.append(" FROM lysj_order_order od,(SELECT count( 1 ) face2Num FROM lysj_order_order od  ");
            builder.append(" WHERE store_id = :storeId AND pay_type = '3' AND act_pay_price >= 2   ");
            if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime)) {
                builder.append(" AND pay_time >=?1  AND pay_time <= ?2 ");
            }
            builder.append(" AND `status` in ('2','6')) lr WHERE  ");
            builder.append(" store_id = :storeId AND pay_type = '3' and `status` in ('2','6') ");
            if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime)) {
                builder.append(" AND pay_time >= ?1 AND  pay_time <= ?2 ");
            }
            builder.append(" GROUP BY lr.face2Num ");
            Query nativeQuery = em.createNativeQuery(builder.toString());
            nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            nativeQuery.setParameter("storeId", row.get("storeId"));
            if (StringUtil.isNotEmpty(beginTime) && StringUtil.isNotEmpty(endTime)) {
                nativeQuery.setParameter(1, beginTime);
                nativeQuery.setParameter(2, endTime);
            }
            List rows = nativeQuery.getResultList();
            if (rows.size() == 0) {
                disTranDataVO.setFaceNum("0");
                disTranDataVO.setFace2Num("0");
                disTranDataVO.setFacePrice("0");
            }
            for (Object object : rows) {
                Map map = (Map) object;
                disTranDataVO.setFaceNum(map.get("faceNum").toString() == null ? "0" : map.get("faceNum").toString());
                disTranDataVO.setFace2Num(map.get("face2Num").toString() == null ? "0" : map.get("face2Num").toString());
                disTranDataVO.setFacePrice(map.get("facePrice").toString() == null ? "0" : map.get("facePrice").toString());
            }
            disTranDataVO.setStoreId(row.get("storeId").toString());
            disTranDataVO.setMerchantId(row.get("merchantId").toString());
            disTranDataVO.setOrderActPayPrice(row.get("orderActPayPrice").toString());
            disTranDataVO.setOrderCount(row.get("orderCount").toString());
            disTranDataVO.setStoreName(row.get("storeName").toString());
            disTranDataVO.setMerchantName(row.get("merchantName").toString());
            disTranDataVOS.add(disTranDataVO);
            //根据商户ID查询业务员，普通，分销
            Merchant merchant = merchantService.getRepository().getOne(row.get("merchantId").toString());
            if (null != merchant.getType()) {
                //分销
                if ("1".equals(merchant.getType().toString())) {
                    DistUser distUser = distUserService.getRepository().getOne(merchant.getManagerId());
                    disTranDataVO.setManagerName(distUser.getName());
                }
                //普通
                if ("0".equals(merchant.getType().toString())) {
                    User user = userService.getRepository().findOne(merchant.getManagerId());
                    disTranDataVO.setManagerName(user.getName());
                }
            }
        }
        pageResult.setRows(disTranDataVOS);
        return pageResult;
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(DisOrderDTO disOrderDTO, HttpServletResponse httpServletResponse) throws IOException {
        Specification<AdvertiseDevice> specification = new Specification<AdvertiseDevice>() {
            @Override
            public Predicate toPredicate(Root<AdvertiseDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("storeId"), disOrderDTO.getStoreId()));
                if (null != disOrderDTO.getOrderName()) {
                    predicates.add(cb.like(root.get("orderNumber"), "%" + disOrderDTO.getOrderName() + "%"));
                    predicates.add(cb.like(root.get("userName"), "%" + disOrderDTO.getOrderName() + "%"));
                }
                if (StringUtil.isNotEmpty(disOrderDTO.getStatus())) {
                    predicates.add(cb.equal(root.get("status"), disOrderDTO.getStatus()));
                }
                if (StringUtil.isNotEmpty(disOrderDTO.getPayType())) {
                    predicates.add(cb.equal(root.get("payType"), disOrderDTO.getPayType()));
                }
                if (StringUtil.isNotEmpty(disOrderDTO.getPayWay())) {
                    predicates.add(cb.equal(root.get("payWay"), disOrderDTO.getPayWay()));
                }
                if (null != disOrderDTO.getBeginTime()) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("payTime"), disOrderDTO.getBeginTime()));
                }
                if (null != disOrderDTO.getEndTime()) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("payTime"), disOrderDTO.getEndTime()));
                }
                predicates.add(cb.equal(root.get("delFlag"), "1"));
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        List<Order> list = this.orderService.getRepository().findAll(specification);
        List<EquipmentDetailPageVO> detailPageVOS = new ArrayList<>();
        for (Order order : list) {
            EquipmentDetailPageVO pageVO = new EquipmentDetailPageVO(order.getId(), order.getOrderNumber(), order.getStoreName(), order.getUserName(),
                    order.getTotalPrice(), order.getActPayPrice(), order.getDisCountPrice(), order.getStatus(), order.getPayWay(), order.getPayChannel(),
                    order.getCreateTime(), order.getPayType());
            detailPageVOS.add(pageVO);
        }
        EasyPoiUtil.exportExcel(detailPageVOS, "交易数据", "sheetName", EquipmentDetailPageVO.class, "fileNameb.xls", httpServletResponse);

    }

}
