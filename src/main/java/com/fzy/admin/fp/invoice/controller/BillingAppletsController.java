package com.fzy.admin.fp.invoice.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.invoice.domain.BillingRecord;
import com.fzy.admin.fp.invoice.domain.ElectronicBillingSetting;
import com.fzy.admin.fp.invoice.domain.Invoice;
import com.fzy.admin.fp.invoice.dto.BillingByOrderDTO;
import com.fzy.admin.fp.invoice.dto.CreateInvoiceDTO;
import com.fzy.admin.fp.invoice.dto.QueryConsumerDetailsDTO;
import com.fzy.admin.fp.invoice.dto.UpdateInvoiceDTO;
import com.fzy.admin.fp.invoice.repository.BillingRecordRepository;
import com.fzy.admin.fp.invoice.repository.ElectronicBillingSettingRepository;
import com.fzy.admin.fp.invoice.repository.InvoiceRepository;
import com.fzy.admin.fp.invoice.service.BillingService;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("开票小程序相关")
@RestController
@RequestMapping({"billing/applets/"})
public class BillingAppletsController {

    private static final Logger log = LoggerFactory.getLogger(BillingAppletsController.class);



    @Autowired
    private BillingService billingService;


    @Autowired
    private InvoiceRepository invoiceRepository;


    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private BillingRecordRepository billingRecordRepository;


    @Autowired
    private ElectronicBillingSettingRepository electronicBillingSettingRepository;

    @Autowired
    private MchInfoService mchInfoService;

    @Autowired
    private StoreService storeService;


    @ApiOperation("查询消费明细")
    @GetMapping({"orders"})
    public Resp<List<Order>> queryConsumerDetails(@UserId String memberId, @TokenInfo(property = "merchantId") String merchantId, @ApiParam QueryConsumerDetailsDTO dto) {
        Page<Order> page = this.orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("memberId"), memberId));

            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("merchantId"), merchantId));


            if (dto.getMaxTotalPrice() != null) {
                predicate.getExpressions().add(criteriaBuilder
                        .lessThanOrEqualTo(root.get("totalPrice"), BigDecimal.valueOf(dto.getMaxTotalPrice().intValue())
                                .divide(BigDecimal.valueOf(100L), 2, 4)));
            }


            if (dto.getMinTotalPrice() != null) {
                predicate.getExpressions().add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get("totalPrice"), BigDecimal.valueOf(dto.getMinTotalPrice().intValue())
                                .divide(BigDecimal.valueOf(100L), 2, 4)));
            }


            if (dto.getStoreIds() != null && dto.getStoreIds().length > 0) {
                predicate.getExpressions().add(criteriaBuilder
                        .and(new Predicate[] { root.get("storeId").in(dto.getStoreIds()) }));
            }

            if (dto.getOrderIds() != null && dto.getOrderIds().length > 0) {
                predicate.getExpressions().add(criteriaBuilder
                        .and(new Predicate[] { root.get("id").in(dto.getOrderIds()) }));
            }



            if (dto.getMaxCreateDate() != null) {
                predicate.getExpressions().add(criteriaBuilder
                        .lessThanOrEqualTo(root.get("createTime"), dto.getMaxCreateDate()));
            }

            if (dto.getMinCreateDate() != null) {
                predicate.getExpressions().add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get("createTime"), dto.getMinCreateDate()));
            }


            if (Boolean.TRUE.equals(dto.getInvoiced())) {
                predicate.getExpressions().add(criteriaBuilder
                        .isNotNull(root.get("billingStatus")));
            }

            if (Boolean.FALSE.equals(dto.getInvoiced())) {
                predicate.getExpressions().add(criteriaBuilder
                        .isNull(root.get("billingStatus")));
            }


            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("status"), Order.Status.SUCCESSPAY.getCode()));


            return predicate;

        },PageUtil.initPage(dto));


        return Resp.success(page.getContent());
    }



    @ApiOperation("查询发票抬头列表")
    @GetMapping({"invoices"})
    public Resp<Invoice[]> queryInvoice(@UserId String memberId) {
        List<Invoice> list = this.invoiceRepository.findByMemberId(memberId);

        return Resp.success(list.toArray(new Invoice[0]), null);
    }


    @ApiOperation("创建发票抬头")
    @PostMapping({"crate"})
    public Resp<String> createInvoice(@UserId String memberId, @ApiParam("dto") CreateInvoiceDTO dto) {
        dto.setMemberId(memberId);
        return this.billingService.createInvoice(dto);
    }


    @ApiOperation("更改发票抬头")
    @PutMapping({"update"})
    public Resp updateInvoice(@UserId String memberId, @ApiParam("dto") UpdateInvoiceDTO dto) {
        dto.setMemberId(memberId);
        return this.billingService.updateInvoice(dto);
    }




    @ApiOperation("通过订单开票")
    @PostMapping({"billing_by_order"})
    public Resp billingByOrder(@UserId String memberId, @TokenInfo(property = "merchantId") String merchantId, @ApiParam BillingByOrderDTO dto) {
        dto.setMemberId(memberId);
        dto.setMerchantId(merchantId);
        return this.billingService.billingByOrder(dto);
    }




    @ApiOperation("查询开票历史")
    @GetMapping({"billing_records"})
    public Resp<List<BillingRecord>> getBillingRecords(@UserId final String memberId, @TokenInfo(property = "merchantId") final String merchantId, PageVo pageVo) {
        Page<BillingRecord> page = this.billingRecordRepository.findAll(new Specification()
        {
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate conjunction = criteriaBuilder.conjunction();

                conjunction.getExpressions().add(criteriaBuilder
                        .equal(root.get("memberId"), memberId));

                conjunction.getExpressions().add(criteriaBuilder
                        .equal(root.get("merchantId"), merchantId));



                return conjunction;
            }
        },PageUtil.initPage(pageVo));

        return Resp.success(page.getContent());
    }


    @ApiOperation("查询开票记录详情")
    @GetMapping({"billing_records/{id}"})
    public Resp<BillingRecord> getBillingRecord(@UserId String memberId, @PathVariable String id) {
        BillingRecord billingRecord = this.billingRecordRepository.findByIdAndMemberId(id, memberId);
        if (billingRecord == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "未查询到该发票详情");
        }

        return Resp.success(billingRecord, null);
    }


    @GetMapping({"/billing_setting"})
    @ApiOperation("获取开票设置")
    public Resp<ElectronicBillingSetting> getBillingSetting(@TokenInfo(property = "merchantId") String merchantId) {
        MchInfo mchInfo = this.mchInfoService.findByMerchantId(merchantId);
        if (!BillingBackendController.ENABLED_FLAG.equals(mchInfo.getIsOpenElectronicInvoice())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "开票配置未启用");
        }

        Resp resp = new Resp();
        ElectronicBillingSetting electronicBillingSetting = this.electronicBillingSettingRepository.findByMerchantId(merchantId);
        if (electronicBillingSetting == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "开票设置未存在");
        }

        resp.common(Resp.Status.SUCCESS, null, electronicBillingSetting);

        return resp;
    }


    @GetMapping({"/stores"})
    @ApiOperation("获取商户下的门店列表")
    public Resp<Store[]> getStores(@TokenInfo(property = "merchantId") String merchantId) {
        List<Store> list = this.storeService.getRepository().findByMerchantIdAndStatus(merchantId, Store.Status.ENABLE.getCode());
        return Resp.success(list.toArray(new Store[0]));
    }
}
