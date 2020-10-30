package com.fzy.admin.fp.invoice.service;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.invoice.domain.BillingRecord;
import com.fzy.admin.fp.invoice.domain.ElectronicBillingSetting;
import com.fzy.admin.fp.invoice.domain.Invoice;
import com.fzy.admin.fp.invoice.domain.TaxOfficeGoodsCode;
import com.fzy.admin.fp.invoice.dto.*;
import com.fzy.admin.fp.invoice.enmus.BillingStatus;
import com.fzy.admin.fp.invoice.enmus.InvoiceStatus;
import com.fzy.admin.fp.invoice.enmus.InvoiceType;
import com.fzy.admin.fp.invoice.enmus.InvoiceTypeCode;
import com.fzy.admin.fp.invoice.feign.FamilyInvoiceClient;
import com.fzy.admin.fp.invoice.feign.FamilyRequestBuilder;
import com.fzy.admin.fp.invoice.feign.FamilySellerClient;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyBaseBO;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyRequest;
import com.fzy.admin.fp.invoice.feign.jackson.FamilyResp;
import com.fzy.admin.fp.invoice.feign.req.*;
import com.fzy.admin.fp.invoice.feign.resp.*;
import com.fzy.admin.fp.invoice.repository.BillingRecordRepository;
import com.fzy.admin.fp.invoice.repository.ElectronicBillingSettingRepository;
import com.fzy.admin.fp.invoice.repository.InvoiceRepository;
import com.fzy.admin.fp.invoice.repository.TaxOfficeGoodsCodeRepository;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import org.apache.http.ParseException;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceImpl.class);


    @Autowired
    private TaxOfficeGoodsCodeRepository taxOfficeGoodsCodeRepository;


    @Autowired
    private ElectronicBillingSettingRepository electronicBillingSettingRepository;


    @Autowired
    private MerchantUserService merchantUserService;


    @Autowired
    private BillingRecordRepository billingRecordRepository;

    @Autowired
    private FamilyInvoiceClient familyInvoiceClient;

    @Autowired
    private FamilySellerClient familySellerClient;

    @Autowired
    private StoreService storeService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MchInfoService mchInfoService;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${facepay.invoice.channelId}")
    private String channelId;

    @Value("${facepay.invoice.defaultSalesperson}")
    private String salespersonMobile;

    @Autowired
    private Snowflake snowflake;

    final String formatPattern = "yyyyMMdd";


    public int batchSaveGoodsCode(List<GoodsItem> goodsItems) {
        if (goodsItems == null) {
            return 0;
        }

        List<TaxOfficeGoodsCode> goodsCodes = (List)goodsItems.stream().map(goodsItem -> { String goodsCode = goodsItem.getGoodsCode(); TaxOfficeGoodsCode entity = new TaxOfficeGoodsCode(); entity.setAvailableState(Boolean.valueOf("Y".equals(goodsItem.getAvailableState()))).setGoodsAbbreviation(goodsItem.getGoodsAbbreviation()).setCustomsItems(goodsItem.getCustomsItems()).setDescription(goodsItem.getDescription()).setGoodsCode(goodsCode).setParentCode(goodsItem.getParentCode()).setKeyword(goodsItem.getKeyword()).setVatPolicyBasis(goodsItem.getVatPolicyBasis()).setVatRate(goodsItem.getVatRate()).setVatSpecialManagement(goodsItem.getVatSpecialManagement()).setVatSpecialManagementCode(goodsItem.getVatSpecialManagementCode()).setVersion(goodsItem.getVersion()).setSaleTaxPolicyBasisCode(goodsItem.getSaleTaxPolicyBasisCode()).setSaleTaxPolicyBasis(goodsItem.getSaleTaxPolicyBasis()).setSaleTaxSpecialManagement(goodsItem.getSaleTaxSpecialManagement()).setStatisticalCoding(goodsItem.getStatisticalCoding()).setSummaryItem(Boolean.valueOf("Y".equals(goodsItem.getSummaryItem()))); entity.setName(goodsItem.getGoodsName()); try { if (StringUtils.hasText(goodsItem.getTransitionDeadline())) entity.setTransitionDeadline(DateUtils.parseDate(goodsItem.getTransitionDeadline(), new String[] { "yyyyMMdd" }));  if (StringUtils.hasText(goodsItem.getEnableTime())) entity.setEnableTime(DateUtils.parseDate(goodsItem.getEnableTime(), new String[] { "yyyyMMdd" }));  if (StringUtils.hasText(goodsItem.getUpdateDate())) entity.setUpdateTime(DateUtils.parseDate(goodsItem.getUpdateDate(), new String[] { "yyyyMMdd" }));  } catch (ParseException e) { e.printStackTrace(); return null; }  String newCode = goodsCode.replaceAll("00", ""); int level = newCode.length() / 2; entity.setCodeLevel(Integer.valueOf(level)); Date createTime = new Date(); entity.setCreateTime(createTime); entity.setUpdateTime(createTime); return entity; }).filter(Objects::nonNull).collect(Collectors.toList());

        List<TaxOfficeGoodsCode> result = this.taxOfficeGoodsCodeRepository.save(goodsCodes);

        return result.size();
    }




    public long countTaxOfficeGoodsTotal() { return this.taxOfficeGoodsCodeRepository.count(); }




    public List<TaxOfficeGoodsCode> queryGoodsCode(QueryGoodsCodeDTO dto) {
        return this.taxOfficeGoodsCodeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();


            if (StringUtils.hasText(dto.getName())) {
                predicate.getExpressions().add(criteriaBuilder
                        .equal(root.get("name"), dto.getName()));
            }

            if (StringUtils.hasText(dto.getLikeName())) {
                predicate.getExpressions().add(criteriaBuilder
                        .like(root.get("name"), "%" + dto.getLikeName() + "%"));

                predicate.getExpressions().add(criteriaBuilder
                        .equal(root.get("summaryItem"), Boolean.valueOf(false)));
            }


            if (StringUtils.hasText(dto.getParentCode())) {
                predicate.getExpressions().add(criteriaBuilder
                        .equal(root.get("parentCode"), dto.getParentCode()));
            }

            if (dto.getLevel() != null) {
                predicate.getExpressions().add(criteriaBuilder
                        .equal(root.get("codeLevel"), dto.getLevel()));
            }


            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("availableState"), Boolean.valueOf(true)));

            return predicate;
        });
    }


    public Resp<String> createBillingSetting(CreateBillingSettingDTO dto) {
        Resp<String> resp = new Resp<String>();

        assert dto.getMerchantId() != null;


        Merchant merchant = (Merchant)this.merchantService.findOne(dto.getMerchantId());
        if (merchant == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "未查询到该商户");
        }


        ElectronicBillingSetting electronicBillingSetting = this.electronicBillingSettingRepository.findByMerchantId(dto.getMerchantId());
        if (electronicBillingSetting != null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该商户已创建过开票设置");
        }


        String provinceId = merchant.getProvince();
        if (!StringUtils.hasText(provinceId)) {
            return resp.error(Resp.Status.PARAM_ERROR, "商户未设置所在省份");
        }

        Province province = (Province)this.provinceRepository.findOne(provinceId);
        if (province == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "商户未设置的省份信息有误");
        }

        MchInfo mchInfo = this.mchInfoService.findByMerchantId(merchant.getId());
        if (mchInfo == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "商户相关信息获取失败");
        }


        RegisterSellerRequest registerSellerRequest = new RegisterSellerRequest();
        registerSellerRequest.setAccountNo(merchant.getPhone());
        registerSellerRequest.setChannelId(this.channelId);
        registerSellerRequest.setSellerName(merchant.getName());

        registerSellerRequest.setProvinceCode(province.getProvinceCode());
        registerSellerRequest.setTaxpayerIdentificationNum(dto.getTaxpayerIdentificationNum());
        registerSellerRequest.setTel(merchant.getPhone());
        registerSellerRequest.setPhone(merchant.getPhone());
        registerSellerRequest.setRegisterAddress(merchant.getAddress());

        FamilyRequest<RegisterSellerRequest> sellerRequestFamilyRequest = FamilyRequestBuilder.newSellerMangerRequest(registerSellerRequest);
        FamilyResp<FamilyBaseBO> familyBaseBOFamilyResp = this.familySellerClient.registerSeller(sellerRequestFamilyRequest);
        if (log.isDebugEnabled()) {
            log.debug("注册数族商户账号{}", familyBaseBOFamilyResp);
        }
        if (!familyBaseBOFamilyResp.isSuccess()) {
            return resp.error(Resp.Status.PARAM_ERROR, ((FamilyBaseBO)familyBaseBOFamilyResp.getBody()).getReturnMessage());
        }


        TaxOfficeGoodsCode taxOfficeGoodsCode = this.taxOfficeGoodsCodeRepository.findByGoodsCode(dto.getGoodsCode());
        if (taxOfficeGoodsCode == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "未查询到该商品编码");
        }

        if (Boolean.TRUE.equals(taxOfficeGoodsCode.getSummaryItem())) {
            return resp.error(Resp.Status.PARAM_ERROR, "该根节点是汇总项,不可添加字节");
        }

        if (log.isDebugEnabled()) {
            log.debug("选中的税务局商品编码{}", taxOfficeGoodsCode);
        }


        SetCustomizeGoodsCodeRequest setCustomizeGoodsCodeRequest = new SetCustomizeGoodsCodeRequest();
        setCustomizeGoodsCodeRequest.setGoodsCodeName(dto.getName());
        setCustomizeGoodsCodeRequest.setAvailableState(taxOfficeGoodsCode.getVatRate());
        setCustomizeGoodsCodeRequest.setTaxRate(BigDecimal.valueOf((dto.getVatRate().intValue() / 100)));
        setAvailableTaxRate(taxOfficeGoodsCode, setCustomizeGoodsCodeRequest);
        setCustomizeGoodsCodeRequest.setGoodsCodeAbbreviation(taxOfficeGoodsCode.getGoodsAbbreviation());

        setCustomizeGoodsCodeRequest.setParentId(taxOfficeGoodsCode.getGoodsCode());


        String taxpayerIdentificationNum = dto.getTaxpayerIdentificationNum();
        TerminalBO terminalBO = findTerminal(taxpayerIdentificationNum);
        if (terminalBO != null) {
            setCustomizeGoodsCodeRequest.setBodySn(((TerminalBO.Terminal)terminalBO.getTerminalGroup().getGroup().get(0)).getBodySn());
        } else {
            setCustomizeGoodsCodeRequest.setBodySn(taxpayerIdentificationNum);
        }
        setCustomizeGoodsCodeRequest.setTaxpayerIdentificationNum(taxpayerIdentificationNum);
        setCustomizeGoodsCodeRequest.setAvailableState("Y");



        FamilyRequest<SetCustomizeGoodsCodeRequest> request = FamilyRequestBuilder.newSetCustomizeGoodsCodeRequestRequest(setCustomizeGoodsCodeRequest);
        FamilyResp<CustomizeGoodsCodeBO> familyResp = this.familyInvoiceClient.setCustomizeGoodsCode(request);
        if (log.isDebugEnabled()) {
            log.debug("置自定义商品 {}", resp);
        }
        CustomizeGoodsCodeBO customizeGoodsCodeBO = (CustomizeGoodsCodeBO)familyResp.getBody();
        if (!familyResp.isSuccess())
        {



            throw new BaseException(customizeGoodsCodeBO.getReturnMessage());
        }



        EnterpriseInfoSetting enterpriseInfoSetting = new EnterpriseInfoSetting();


        if (terminalBO != null) {
            enterpriseInfoSetting.setBodySn(((TerminalBO.Terminal)terminalBO.getTerminalGroup().getGroup().get(0)).getBodySn());
        } else {
            enterpriseInfoSetting.setBodySn(taxpayerIdentificationNum);
        }
        enterpriseInfoSetting.setBlankAccount(mchInfo.getAccountNumber());
        enterpriseInfoSetting.setBlankName(mchInfo.getBankName());
        enterpriseInfoSetting.setBusinessAddress(mchInfo.getAddress());
        enterpriseInfoSetting.setTelephone(mchInfo.getPhone());
        this.familyInvoiceClient.enterpriseInfoSetting(FamilyRequestBuilder.newEnterpriseInfoSetting(enterpriseInfoSetting));


        ElectronicBillingSetting entity = new ElectronicBillingSetting();
        BeanUtils.copyProperties(dto, entity);

        if (entity.getLowerLimit() == null) {
            entity.setLowerLimit(Integer.valueOf(-1));
        }
        if (entity.getUpperLimit() == null) {
            entity.setUpperLimit(Integer.valueOf(-1));
        }
        Date createTime = new Date();
        entity.setCreateTime(createTime);
        entity.setUpdateTime(createTime);
        entity.setDefaultSetting(Boolean.valueOf(true));
        entity.setGoodsCode(taxOfficeGoodsCode.getGoodsCode());
        entity.setFamilyGoodsCode(customizeGoodsCodeBO.getGoodsCode());
        entity.setMerchantId(dto.getMerchantId());

        this.electronicBillingSettingRepository.save(entity);

        if (entity.getId() == null)
        {


            return resp.error(Resp.Status.PARAM_ERROR, "创建开票设置失败");
        }


        return Resp.success(entity.getId(), null);
    }



    @Transactional
    public Resp updateBillingSetting(UpdateBillingSettingDTO dto) {
        Resp resp = new Resp();

        ElectronicBillingSetting entity = (ElectronicBillingSetting)this.electronicBillingSettingRepository.findOne(dto.getId());
        if (entity == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该开票设置不存在");
        }

        if (!entity.getMerchantId().equals(dto.getMerchantId())) {
            return resp.error(Resp.Status.PARAM_ERROR, "该开票设置不属于当前商户");
        }


        TaxOfficeGoodsCode taxOfficeGoodsCode = this.taxOfficeGoodsCodeRepository.findByGoodsCode(entity.getGoodsCode());
        if (taxOfficeGoodsCode == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "未查询到该商品编码");
        }

        if (dto.getUpperLimit() != null) {
            entity.setUpperLimit(dto.getUpperLimit());
        }
        if (dto.getLowerLimit() != null) {
            entity.setLowerLimit(dto.getLowerLimit());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getPreferentialPolicy() != null) {
            entity.setPreferentialPolicy(dto.getPreferentialPolicy());
        }
        if (dto.getVatRate() != null) {
            entity.setVatRate(dto.getVatRate());
        }

        if (StringUtils.hasText(dto.getPayee())) {
            entity.setPayee(dto.getPayee());
        }
        if (StringUtils.hasText(dto.getReviewer())) {
            entity.setReviewer(dto.getReviewer());
        }
        if (StringUtils.hasText(dto.getIssuer())) {
            entity.setIssuer(dto.getIssuer());
        }

        SetCustomizeGoodsCodeRequest setCustomizeGoodsCodeRequest = new SetCustomizeGoodsCodeRequest();
        setCustomizeGoodsCodeRequest.setGoodsCode(entity.getFamilyGoodsCode());
        setCustomizeGoodsCodeRequest.setGoodsCodeName(entity.getName());
        setCustomizeGoodsCodeRequest.setAvailableState(taxOfficeGoodsCode.getVatRate());
        setCustomizeGoodsCodeRequest.setTaxRate(BigDecimal.valueOf((entity.getVatRate().intValue() / 100)));
        setCustomizeGoodsCodeRequest.setGoodsCodeAbbreviation(taxOfficeGoodsCode.getGoodsAbbreviation());
        setCustomizeGoodsCodeRequest.setParentId(taxOfficeGoodsCode.getGoodsCode());

        String taxpayerIdentificationNum = entity.getTaxpayerIdentificationNum();
        TerminalBO terminalBO = findTerminal(taxpayerIdentificationNum);
        if (terminalBO != null) {
            setCustomizeGoodsCodeRequest.setBodySn(((TerminalBO.Terminal)terminalBO.getTerminalGroup().getGroup().get(0)).getBodySn());
        } else {
            setCustomizeGoodsCodeRequest.setBodySn(taxpayerIdentificationNum);
        }

        setAvailableTaxRate(taxOfficeGoodsCode, setCustomizeGoodsCodeRequest);
        setCustomizeGoodsCodeRequest.setTaxpayerIdentificationNum(taxpayerIdentificationNum);
        setCustomizeGoodsCodeRequest.setAvailableState("Y");
        FamilyRequest<SetCustomizeGoodsCodeRequest> request = FamilyRequestBuilder.newSetCustomizeGoodsCodeRequestRequest(setCustomizeGoodsCodeRequest);
        FamilyResp<CustomizeGoodsCodeBO> familyResp = this.familyInvoiceClient.setCustomizeGoodsCode(request);
        log.debug("置自定义商品 {}", familyResp);
        CustomizeGoodsCodeBO customizeGoodsCodeBO = (CustomizeGoodsCodeBO)familyResp.getBody();
        if (!familyResp.isSuccess())
        {
            return resp.error(Resp.Status.PARAM_ERROR, customizeGoodsCodeBO.getReturnMessage());
        }
        entity.setFamilyGoodsCode(customizeGoodsCodeBO.getGoodsCode());
        this.electronicBillingSettingRepository.save(entity);

        return Resp.success(null);
    }



    private void setAvailableTaxRate(TaxOfficeGoodsCode taxOfficeGoodsCode, SetCustomizeGoodsCodeRequest setCustomizeGoodsCodeRequest) {
        if (StringUtils.hasText(taxOfficeGoodsCode.getVatRate())) {
            String[] varRates = taxOfficeGoodsCode.getVatRate().split("、");





            String availableTaxRate = (String) Arrays.stream(varRates).filter(StringUtils::hasText).map(s -> s.replaceAll("%", "")).map(s -> BigDecimal.valueOf((Integer.parseInt(s) / 100))).map(BigDecimal::toString).collect(Collectors.joining("��"));
            setCustomizeGoodsCodeRequest.setAvailableTaxRate(availableTaxRate);
        }
    }








    public Resp<String> createInvoice(CreateInvoiceDTO dto) {
        Invoice entity = new Invoice();
        BeanUtils.copyProperties(dto, entity);


        this.invoiceRepository.save(entity);

        if (entity.getId() == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "创建发票抬头信息失败");
        }

        return Resp.success(entity.getId(), null);
    }


    public Resp updateInvoice(UpdateInvoiceDTO dto) {
        Invoice invoice = (Invoice)this.invoiceRepository.findOne(dto.getId());

        if (!invoice.getMemberId().equals(dto.getMemberId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "该发票信息不属于当前用户");
        }

        if (StringUtils.hasText(dto.getTitle())) {
            invoice.setTitle(dto.getTitle());
        }
        if (dto.getType() != null) {
            invoice.setType(dto.getType());
        }
        if (StringUtils.hasText(dto.getRecEmail())) {
            invoice.setRecEmail(dto.getRecEmail());
        }

        if (StringUtils.hasText(dto.getCompany())) {
            invoice.setCompany(dto.getCompany());
        } else {
            invoice.setCompany(null);
        }
        if (StringUtils.hasText(dto.getCompanyCode())) {
            invoice.setCompanyCode(dto.getCompanyCode());
        } else {
            invoice.setCompanyCode(null);
        }

        if (StringUtils.hasText(dto.getCompanyCode())) {
            invoice.setCompanyCode(dto.getCompanyCode());
        } else {
            invoice.setCompanyCode(null);
        }

        if (StringUtils.hasText(dto.getRegAddr())) {
            invoice.setRegAddr(dto.getRegAddr());
        } else {
            invoice.setRegAddr(null);
        }
        if (StringUtils.hasText(dto.getRegPhone())) {
            invoice.setRegPhone(dto.getRegPhone());
        } else {
            invoice.setRegPhone(null);
        }

        if (StringUtils.hasText(dto.getRecMobilePhone())) {
            invoice.setRecMobilePhone(dto.getRecMobilePhone());
        } else {
            invoice.setRecMobilePhone(null);
        }
        if (StringUtils.hasText(dto.getRecName())) {
            invoice.setRecName(dto.getRecName());
        } else {
            invoice.setRecName(null);
        }
        if (StringUtils.hasText(dto.getRegBaccount())) {
            invoice.setRegBaccount(dto.getRegBaccount());
        } else {
            invoice.setRegBaccount(null);
        }

        if (StringUtils.hasText(dto.getRegBname())) {
            invoice.setRegBname(dto.getRegBname());
        } else {
            invoice.setRegBname(null);
        }

        if (StringUtils.hasText(dto.getRecProvince())) {
            invoice.setRecProvince(dto.getRecProvince());
        } else {
            invoice.setRecProvince(null);
        }

        if (StringUtils.hasText(dto.getGotoAddr())) {
            invoice.setGotoAddr(dto.getGotoAddr());
        } else {
            invoice.setGotoAddr(null);
        }


        this.invoiceRepository.save(invoice);

        return Resp.success(null);
    }


    @Transactional
    public Resp billingByOrder(BillingByOrderDTO dto) {
        Resp resp = new Resp();

        String merchantId = dto.getMerchantId();
        Merchant merchant = (Merchant)this.merchantService.findOne(merchantId);
        if (merchant == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该商户不存在");
        }
        MchInfo mchInfo = this.mchInfoService.findByMerchantId(merchantId);
        if (mchInfo == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该商户配置信息不存在");
        }
        if (!Integer.valueOf(1).equals(mchInfo.getIsOpenElectronicInvoice())) {
            return resp.error(Resp.Status.PARAM_ERROR, "该商户未开启开票配置");
        }


        List<Order> orders = this.orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            predicate.getExpressions().add(criteriaBuilder
                    .and(new Predicate[] { root.get("id").in(dto.getOrderIds()) }));


            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("memberId"), dto.getMemberId()));

            predicate.getExpressions().add(criteriaBuilder
                    .equal(root.get("merchantId"), merchant.getId()));






            return predicate;
        });

        if (orders.isEmpty()) {
            return resp.error(Resp.Status.PARAM_ERROR, "获取开票订单失败");
        }
        long count = orders.stream().filter(order -> (order.getBillingStatus() != null)).count();
        if (count > 0L) {
            return resp.error(Resp.Status.PARAM_ERROR, "存在已申请开票的订单");
        }


        ElectronicBillingSetting electronicBillingSetting = this.electronicBillingSettingRepository.findByMerchantId(merchant.getId());
        if (electronicBillingSetting == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "未设置开票配置");
        }





        BigDecimal goodsPrice = (BigDecimal)orders.stream().map(order -> order.getTotalPrice()).reduce(BigDecimal::add).orElseGet(null);

        int goodsPriceByFen = goodsPrice.multiply(BigDecimal.valueOf(100L)).intValue();

        if (electronicBillingSetting.getLowerLimit().intValue() > 0 && goodsPriceByFen < electronicBillingSetting.getLowerLimit().intValue()) {
            return resp.error(Resp.Status.PARAM_ERROR, "开票金额小于允许开票的金额");
        }
        if (electronicBillingSetting.getUpperLimit().intValue() > 0 && goodsPriceByFen > electronicBillingSetting.getUpperLimit().intValue()) {
            return resp.error(Resp.Status.PARAM_ERROR, "开票金额大于允许开票的金额");
        }



        VATElectronicInvoiceRequest vatElectronicInvoiceRequest = new VATElectronicInvoiceRequest();
        vatElectronicInvoiceRequest.setRequestSn(String.valueOf(this.snowflake.nextId()));
        String taxpayerIdentificationNum = electronicBillingSetting.getTaxpayerIdentificationNum();
        vatElectronicInvoiceRequest.setSalesTaxpayerIdentificationNumber(taxpayerIdentificationNum);

        TerminalBO terminalBO = findTerminal(taxpayerIdentificationNum);
        if (terminalBO != null) {
            vatElectronicInvoiceRequest.setBodySn(((TerminalBO.Terminal)terminalBO.getTerminalGroup().getGroup().get(0)).getBodySn());
        }

        vatElectronicInvoiceRequest.setInvoiceTyeCode(InvoiceTypeCode.ELECTRONIC.getCode());
        vatElectronicInvoiceRequest.setBillingType(Integer.valueOf(0));
        vatElectronicInvoiceRequest.setUseType(Integer.valueOf(0));
        vatElectronicInvoiceRequest.setRemark(dto.getRemark());
        vatElectronicInvoiceRequest.setPayee(electronicBillingSetting.getPayee());
        vatElectronicInvoiceRequest.setReviewer(electronicBillingSetting.getReviewer());
        vatElectronicInvoiceRequest.setIssuer(electronicBillingSetting.getIssuer());
        if (StringUtils.hasText(dto.getRecMobilePhone())) {
            vatElectronicInvoiceRequest.setInvoicersContactInfo(dto.getRecMobilePhone());
        }

        if (StringUtils.hasText(dto.getRecEmail())) {
            vatElectronicInvoiceRequest.setInvoicersContactInfo(dto.getRecEmail());
        }


        VATElectronicInvoiceRequest.BillingItems billingItems = new VATElectronicInvoiceRequest.BillingItems();
        billingItems.setCount(Integer.valueOf(1));
        List<InvoiceItem> group = new ArrayList<InvoiceItem>();

        InvoiceItem invoiceItem = new InvoiceItem();
        BigDecimal taxRate = BigDecimal.valueOf(electronicBillingSetting.getVatRate().intValue()).divide(BigDecimal.valueOf(100L));

        int goodsNum = 1;






        BigDecimal divisor = BigDecimal.valueOf(1.0D + taxRate.doubleValue());
        BigDecimal notTaxGoodsPrice = goodsPrice.divide(divisor, 2, 4);
        BigDecimal amount = notTaxGoodsPrice.multiply(BigDecimal.valueOf(goodsNum));
        invoiceItem.setGoodsName(electronicBillingSetting.getName())
                .setOrderIndex(Integer.valueOf(1))
                .setInvoiceLineNature(Integer.valueOf(0))
                .setAmount(amount)
                .setTaxRate(taxRate)
                .setTax(amount.multiply(taxRate).setScale(2, 4))
                .setGoodsPrice(notTaxGoodsPrice)
                .setUnit("")
                .setGoodsNum(Integer.valueOf(goodsNum))
                .setGoodsCode(electronicBillingSetting.getGoodsCode())
                .setTaxpayerSelfCode(electronicBillingSetting.getFamilyGoodsCode())
                .setTaxRateIndication((electronicBillingSetting.getTaxExemptionType() == null) ? null : electronicBillingSetting.getTaxExemptionType().getCode().toString());
        group.add(invoiceItem);
        billingItems.setGroup(group);
        vatElectronicInvoiceRequest.setBillingItems(billingItems);
        vatElectronicInvoiceRequest.setPurchaseUnitName(dto.getTitle());
        if (!InvoiceType.PERSONAL.equals(dto.getInvoiceType())) {
            vatElectronicInvoiceRequest.setPurchaseUnitIdentificationNum(dto.getCompanyCode());
            vatElectronicInvoiceRequest.setPurchaseUnitBankAccount(dto.getRegBaccount());
            String purchaseUnitTel = null;
            if (StringUtils.hasText(dto.getRegAddr())) {
                purchaseUnitTel = dto.getRegAddr();
            }
            if (StringUtils.hasText(dto.getRecName())) {
                purchaseUnitTel = purchaseUnitTel + " " + dto.getRecName();
            }
            if (StringUtils.hasText(dto.getRecMobilePhone())) {
                purchaseUnitTel = purchaseUnitTel + " " + dto.getRecMobilePhone();
            }
            vatElectronicInvoiceRequest.setPurchaseUnitTel(purchaseUnitTel);
        }


        FamilyRequest<VATElectronicInvoiceRequest> request = FamilyRequestBuilder.newVATElectronicInvoiceRequest(vatElectronicInvoiceRequest);
        FamilyResp<InvoiceResultBO> familyResp = this.familyInvoiceClient.vATOrdinaryElectronicInvoice(request);
        if (!familyResp.isSuccess()) {
            InvoiceResultBO body = (InvoiceResultBO)familyResp.getBody();
            return resp.error(Resp.Status.PARAM_ERROR, (body != null) ? body.getReturnMessage() : "开票申请失败");
        }


        BillingRecord entity = new BillingRecord();
        BeanUtils.copyProperties(dto, entity);
        entity.setApplyTime(new Date());
        entity.setInvoiceSn(vatElectronicInvoiceRequest.getRequestSn());
        entity.setTotalAmount(invoiceItem.getAmount());
        entity.setTotalTax(invoiceItem.getTax());
        entity.setType(dto.getInvoiceType());
        entity.setTaxRate(invoiceItem.getTaxRate());
        entity.setTaxControlEquipmentNum(vatElectronicInvoiceRequest.getBodySn());
        entity.setOrderIds(JSON.toJSONString(dto.getOrderIds()));
        entity.setContent(invoiceItem.getGoodsName());
        this.billingRecordRepository.save(entity);
        if (entity.getId() == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "开票申请成功，开票记录保存失败");
        }


        orders.forEach(order ->
                order.setBillingStatus(BillingStatus.APPLY));

        this.orderRepository.save(orders);

        return Resp.success(null);
    }


    @Transactional
    public Resp billingCallback(String invoiceSn, InvoiceNoticeBO.InvoiceInfo invoiceInfo) {
        Resp resp = new Resp();


        BillingRecord billingRecord = this.billingRecordRepository.findByInvoiceSn(invoiceSn);
        if (billingRecord == null) {
            return resp.error(Resp.Status.PARAM_ERROR, "该开票记录不存在");
        }

        List<String> orderIds = (List)JSON.parseObject(billingRecord.getOrderIds(), (new TypeReference<List<String>>() {
        }).getType(), new Feature[0]);
        List<Order> orders = this.orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder
                    .and(new Predicate[] { root.get("id").in(orderIds.toArray(new String[0])) }));

            return conjunction;
        });

        orders.forEach(order ->
                order.setBillingStatus(BillingStatus.INVOICED));


        this.orderRepository.save(orders);


        billingRecord.setInvoiceStatus(InvoiceStatus.POSITIVE_SUCCESS);
        billingRecord.setDownloadUrl(invoiceInfo.getDownloadUrl());
        try {
            billingRecord.setBillingTime(DateUtils.parseDate(invoiceInfo.getBillingDate(), new String[] { "yyyyMMddHHmmdd" }));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String downloadUrl = billingRecord.getDownloadUrl();
        if (downloadUrl != null) {
            billingRecord.setDownloadUrl(downloadUrl.startsWith("http://") ? downloadUrl : ("http://" + downloadUrl));
        }

        this.billingRecordRepository.save(billingRecord);

        return Resp.success(null);
    }


    private TerminalBO findTerminal(String taxpayerIdentificationNum) {
        FindTerminalRequest findTerminalRequest = new FindTerminalRequest();
        findTerminalRequest.setTaxpayerIdentificationNum(taxpayerIdentificationNum);
        FamilyRequest<FindTerminalRequest> request = FamilyRequestBuilder.newFindTerminalRequest(findTerminalRequest);
        FamilyResp<TerminalBO> resp = this.familyInvoiceClient.findTerminal(request);
        log.debug("查询终端设备 {}", resp);
        if (!resp.isSuccess()) {
            return null;
        }

        return (TerminalBO)resp.getBody();
    }
}

