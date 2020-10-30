package com.fzy.admin.fp.auth.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.StatisticsCountData;
import com.fzy.admin.fp.auth.repository.StatisticsCountDataRepository;
import com.fzy.admin.fp.auth.vo.CountDataVO;
import com.fzy.admin.fp.auth.vo.StatisticsCountDataVo;
import com.fzy.admin.fp.common.util.RedisUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.service.CommissionDayService;
import com.fzy.admin.fp.order.order.service.CommissionService;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.order.domain.OrderVo;
import com.fzy.admin.fp.sdk.order.feign.OrderServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MerchantService merchantService;

    @Resource
    private MerchantRepository merchantRepository;


    @Autowired
    private CommissionService commissionService;


    @Resource
    private OrderService orderService;

    @Autowired
    private CommissionDayService commissionDayService;

    @Resource
    private MerchantBusinessService merchantBusinessService;


    @Resource
    private OrderServiceFeign orderServiceFeign;


    @Resource
    private StatisticsCountDataRepository statisticsCountDataRepository;


    @Resource
    private CompanyService companyService;


    /**
     * 分销代理查询佣金统计
     * @param conditionStartTime
     * @param conditionEndTime
     * @return
     */
    public StatisticsCountDataVo getAgentCacheCountDataAndOrderVoList(String id, String conditionStartTime, String conditionEndTime) {

        StatisticsCountDataVo statisticsCountDataVo = new StatisticsCountDataVo();

        //当前业务员旗下的商户
        List<MerchantVO> merchantVOs = this.merchantBusinessService.findByAgentId(id);

        DateTime startTime = DateUtil.beginOfDay(DateUtil.parse(conditionStartTime));
        DateTime endTime = DateUtil.endOfDay(DateUtil.parse(conditionEndTime));



        List<String> tempMerchantIds = new ArrayList<String>();
        merchantVOs.forEach(merchantVO ->
                tempMerchantIds.add(merchantVO.getId()));

        String[] merchantIds = (String[])tempMerchantIds.toArray(new String[tempMerchantIds.size()]);

        //查询出订单
        //List<OrderVo> orderVoList = findByMerchantIdsAsc(merchantIds, startTime.toString(), endTime.toString());
        List<OrderVo> orderVoList = findByDistMerchantIdsAsc(merchantIds, startTime.toString(), endTime.toString());


        CountDataVO countDataVO = new CountDataVO();


        countDataVO.setOrderNum(Integer.valueOf(orderVoList.size()));



        for (OrderVo orderVo : orderVoList) {
            countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(orderVo.getTotalPrice()));

            countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));

            countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(orderVo.getDisCountPrice()));

            if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus()) || OrderVo.Status.REFUNDTOTAL
                    .getCode().equals(orderVo.getStatus())) {
                countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
                countDataVO.setRefundNum(Integer.valueOf(countDataVO.getRefundNum().intValue() + 1));
            }
        }
        //结算佣金
      List<CommissionDay> commissionDaysList = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(id, startTime, endTime,1);
      BigDecimal commission = commissionDaysList.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
//        List<Commission> commissionList = commissionService.getRepository().findAllByDirectIdAndCreateTimeBetweenAndType(id, startTime, endTime, 1);
//        BigDecimal commission = commissionList.stream().map(Commission::getDirectCommission).reduce(BigDecimal.ZERO, BigDecimal::add);

        countDataVO.setCommissionAmount(commission);

        countDataVO.setValidDealAmount(countDataVO.getOrderAmount().subtract(countDataVO.getRefundAmount()));

        statisticsCountDataVo.setCompanyId(id);
        statisticsCountDataVo.setSaveDay(conditionStartTime);

        BeanUtils.copyProperties(countDataVO, statisticsCountDataVo);

        return statisticsCountDataVo;
    }


    public Map<String, Object> getToday(Company company) {

        String companyId = company.getId();

        String merkey = companyId + "_merchant_all";
        List<String> tempMerchantIds = getMerchantIdCache(merkey, String.class);

        if (tempMerchantIds == null || 0 == tempMerchantIds.size()) {
            tempMerchantIds = getOneCompany(company);
        }


        //获取当前日期
        String toDay = DateUtil.today();

        //获取当前时间
        DateTime dateTime = DateUtil.parseDateTime(DateUtil.now());
        //当前时间多少个小时
        int currentHour = dateTime.getField(DateField.HOUR_OF_DAY);


        int beforeHour = currentHour - 1;


        CountDataVO countDataVOMain = new CountDataVO();


        List<OrderVo> orderVoListMain = new ArrayList<OrderVo>();


        List<OrderVo> currentTimeOrderVoList = null;
        if (tempMerchantIds.size() > 0) {
            currentTimeOrderVoList = getOrderByCurrentTime(tempMerchantIds, toDay, currentHour);
        } else {
            log.info("该代理商（服务商）下无店铺数据，代理商（服务商）名称：{}", company.getName());
        }


        String key = companyId + "_" + toDay + "_orderVoList_" + beforeHour;
        List<OrderVo> beforeTimeOrderVoList = getArr(key, OrderVo.class);


        if (beforeTimeOrderVoList == null || 0 == beforeTimeOrderVoList.size()) {
            if (tempMerchantIds.size() > 0) {
                beforeTimeOrderVoList = getOrderBeforeTime(tempMerchantIds, toDay, beforeHour);
            } else {
                log.info("该代理商（服务商）下无店铺数据，代理商（服务商）名称：{}", company.getName());
            }
        }

        if (currentTimeOrderVoList != null && currentTimeOrderVoList.size() > 0) {
            orderVoListMain.addAll(currentTimeOrderVoList);
        }
        if (beforeTimeOrderVoList != null && beforeTimeOrderVoList.size() > 0) {
            orderVoListMain.addAll(beforeTimeOrderVoList);
        }





        countDataVOMain.setOrderNum(Integer.valueOf(orderVoListMain.size()));
        for (OrderVo orderVo : orderVoListMain) {



            countDataVOMain.setOrderAmount(countDataVOMain.getOrderAmount().add(orderVo.getTotalPrice()));

            countDataVOMain.setCustomerPaidAmount(countDataVOMain.getCustomerPaidAmount().add(orderVo.getActPayPrice()));

            countDataVOMain.setDiscountAmount(countDataVOMain.getDiscountAmount().add(orderVo.getDisCountPrice()));

            if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus()) || OrderVo.Status.REFUNDTOTAL
                    .getCode().equals(orderVo.getStatus())) {


                countDataVOMain.setRefundAmount(countDataVOMain.getRefundAmount().add(orderVo.getRefundPayPrice()));
                countDataVOMain.setRefundNum(Integer.valueOf(countDataVOMain.getRefundNum().intValue() + 1));
            }
        }



        BigDecimal currentCommission = getCompanyCurrentCommission(company, toDay, currentHour);

        String commissionKey = companyId + "_" + toDay + "_commission_" + beforeHour;
        BigDecimal beforeCommission = getBigNum(commissionKey, BigDecimal.class);
        if (beforeCommission == null || beforeCommission.compareTo(BigDecimal.ZERO) == 0) {
            beforeCommission = getCompanyBeforeCommission(company, toDay, beforeHour);
        }

        countDataVOMain.setCommissionAmount(countDataVOMain.getCommissionAmount().add(currentCommission));
        countDataVOMain.setCommissionAmount(countDataVOMain.getCommissionAmount().add(beforeCommission));


        countDataVOMain.setValidDealAmount(countDataVOMain.getOrderAmount().subtract(countDataVOMain.getRefundAmount()));


        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("countData", countDataVOMain);
        resultMap.put("orderVoList", orderVoListMain);

        return resultMap;
    }

    public BigDecimal getCompanyBeforeCommission(Company company, String toDay, int hour) {
        List<Commission> commissionList = null;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByOemIdBeforeTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);

            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getOemCommission());
            }

            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.OPERATOR.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByFirstIdBeforeTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
            }
            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionBySecondIdBeforeTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByThirdIdBeforeTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getThirdCommission());
            }
            return ComissionMoney;
        }
        return new BigDecimal(0);
    }

    public BigDecimal getBigNum(String key, Class<BigDecimal> c) {
        String str = RedisUtil.get(key);

        return (BigDecimal) JSONObject.parseObject(str, c);
    }

    public BigDecimal getCompanyCurrentCommission(Company company, String toDay, int hour) {
        List<Commission> commissionList = null;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByOemIdCurrentTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);

            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getOemCommission());
            }

            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.OPERATOR.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByFirstIdCurrentTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
            }
            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionBySecondIdCurrentTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        }  if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {

            commissionList = this.commissionService.getRepository().getCommissionByThirdIdCurrentTime(company.getId(), toDay, hour);

            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getThirdCommission());
            }
            return ComissionMoney;
        }
        return new BigDecimal(0);
    }

    public List<OrderVo> getOrderBeforeTime(List<String> merchantIds, String toDay, int hour) {
        String merchantTemp = JSONArray.toJSONString(merchantIds);
        List<Order> orderList = this.orderService.getRepository().findOrderByBeforeCurrentTime(merchantTemp, toDay, hour);
        return setName(orderList);
    }

    public List<OrderVo> getArr(String key, Class<OrderVo> c) {
        String str = RedisUtil.get(key);

        return JSONArray.parseArray(str, c);
    }

    public List<OrderVo> getOrderByCurrentTime(List<String> merchantIds, String toDay, int hour) {
        String merchantTemp = JSONArray.toJSONString(merchantIds);
        List<Order> orderList = this.orderService.getRepository().findOrderByCurrentTime(merchantTemp, toDay, hour);
        return setName(orderList);
    }

    public List<String> getOneCompany(Company company) {
        String companyId = company.getId();


        List<String> companyIds = new ArrayList<String>();


        this.companyService.getCompanyByParentId(company, companyIds);


        List<MerchantVO> merchantVOs = this.merchantBusinessService.findByCompanyIds((String[])companyIds.toArray(new String[companyIds.size()]));

        List<String> tempMerchantIds = new ArrayList<String>();
        merchantVOs.forEach(merchantVO ->
                tempMerchantIds.add(merchantVO.getId()));



        String key = companyId + "_merchant_all";
//        setMerchantIdCache(key, tempMerchantIds, 86400); 一天
        setMerchantIdCache(key, tempMerchantIds, 3600);

        return tempMerchantIds;
    }

    public void setMerchantIdCache(String key, List<String> merchantIds, int timeOut) {
        String json = JSONArray.toJSONString(merchantIds);
        RedisUtil.set(key, json, Integer.valueOf(timeOut));
    }

    public List<String> getMerchantIdCache(String key, Class<String> c) {
        String json = RedisUtil.get(key);
        return JSONArray.parseArray(json, c);
    }

    private List<OrderVo> setName(List<Order> orderList) {
        return (List)orderList.stream().filter(order -> !Order.PayWay.MEMBERCARD.getCode().equals(order.getPayWay()))
                .map(order -> {
                    OrderVo orderVo = new OrderVo();


                    Merchant merchant = (Merchant)this.merchantRepository.findOne(order.getMerchantId());

                    if(merchant.getCompanyId() != null){
                        Company company = (Company)this.companyService.findOne(merchant.getCompanyId());
                        setOrderVoCompanyName(orderVo, company);
                    }
                    orderVo.setMerchantName(merchant.getName());


                    BeanUtils.copyProperties(order, orderVo);
                    return orderVo;
                }).collect(Collectors.toList());
    }

    private void setOrderVoCompanyName(OrderVo orderVo, Company company) {
        if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {

            orderVo.setThirdAgentName(company.getName());

            Company disCompany = (Company)this.companyService.findOne(company.getParentId());
            setOrderVoCompanyName(orderVo, disCompany);
        } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {

            orderVo.setSubAgentName(company.getName());

            Company opeCompany = (Company)this.companyService.findOne(company.getParentId());
            setOrderVoCompanyName(orderVo, opeCompany);
        } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {

            orderVo.setAgentName(company.getName());
        }
    }
    
    public void setData(CountDataVO countDataVOMain, CountDataVO countDataVOTemp) {
        countDataVOMain.setOrderAmount(countDataVOMain.getOrderAmount().add(countDataVOTemp.getOrderAmount()));

        countDataVOMain.setOrderNum(Integer.valueOf(countDataVOMain.getOrderNum().intValue() + countDataVOTemp.getOrderNum().intValue()));

        countDataVOMain.setRefundAmount(countDataVOMain.getRefundAmount().add(countDataVOTemp.getRefundAmount()));

        countDataVOMain.setRefundNum(Integer.valueOf(countDataVOMain.getRefundNum().intValue() + countDataVOTemp.getRefundNum().intValue()));

        countDataVOMain.setValidDealAmount(countDataVOMain.getValidDealAmount().add(countDataVOTemp.getValidDealAmount()));

        countDataVOMain.setCommissionAmount(countDataVOMain.getCommissionAmount().add(countDataVOTemp.getCommissionAmount()));

        countDataVOMain.setCustomerPaidAmount(countDataVOMain.getCustomerPaidAmount().add(countDataVOTemp.getCustomerPaidAmount()));

        countDataVOMain.setDiscountAmount(countDataVOMain.getDiscountAmount().add(countDataVOTemp.getDiscountAmount()));
    }

    public CountDataVO getObj(String key, Class<CountDataVO> c) {
        String str = RedisUtil.get(key);

        return (CountDataVO)JSONObject.parseObject(str, c);
    }

    public StatisticsCountData getStatisticsCountDataBy(String companyId, String saveTime){
        return this.statisticsCountDataRepository.findByCompanyIdAndSaveDay(companyId, saveTime);
    }

    public void setCacheCountDataAndOrderVoList(Company company, String conditionStartTime, String conditionEndTime) {
        String companyId = company.getId();

        //公司集合id
        List<String> companyIds = new ArrayList<String>();


        this.companyService.getCompanyByParentId(company, companyIds);

        //当前公司旗下的商户
        List<MerchantVO> merchantVOs = this.merchantBusinessService.findByCompanyIds((String[])companyIds.toArray(new String[companyIds.size()]));

        DateTime startTime = DateUtil.beginOfDay(DateUtil.parse(conditionStartTime));
        DateTime endTime = DateUtil.endOfDay(DateUtil.parse(conditionEndTime));



        List<String> tempMerchantIds = new ArrayList<String>();
        merchantVOs.forEach(merchantVO ->
                tempMerchantIds.add(merchantVO.getId()));

        String[] merchantIds = (String[])tempMerchantIds.toArray(new String[tempMerchantIds.size()]);

        //查询出订单
        List<OrderVo> orderVoList = findByMerchantIdsAsc(merchantIds, startTime.toString(), endTime.toString());


        CountDataVO countDataVO = new CountDataVO();


        countDataVO.setOrderNum(Integer.valueOf(orderVoList.size()));



        for (OrderVo orderVo : orderVoList) {



            countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(orderVo.getTotalPrice()));

            countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));

            countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(orderVo.getDisCountPrice()));

            if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus()) || OrderVo.Status.REFUNDTOTAL
                    .getCode().equals(orderVo.getStatus())) {


                countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
                countDataVO.setRefundNum(Integer.valueOf(countDataVO.getRefundNum().intValue() + 1));
            }
        }

        //结算佣金
        BigDecimal commission = this.companyService.getLeftCommission(company, startTime.toString(), endTime.toString());

        countDataVO.setCommissionAmount(commission);

        countDataVO.setValidDealAmount(countDataVO.getOrderAmount().subtract(countDataVO.getRefundAmount()));



        StatisticsCountData statisticsCountData = this.statisticsCountDataRepository.findByCompanyIdAndSaveDay(companyId, conditionStartTime);
        if (statisticsCountData == null) {

            statisticsCountData = new StatisticsCountData();
            statisticsCountData.setCompanyId(companyId);
            statisticsCountData.setSaveDay(conditionStartTime);
        }


        BeanUtils.copyProperties(countDataVO, statisticsCountData);

        this.statisticsCountDataRepository.save(statisticsCountData);


        String countDataKey = companyId + "_" + conditionStartTime + "_countData";
        setObjWithTime(countDataKey, countDataVO, 86400);


        String orderVoListKey = companyId + "_" + conditionStartTime + "_orderVoList";
        setArrWithTime(orderVoListKey, orderVoList, 86400*7);
    }

    public void setArrWithTime(String key, List<OrderVo> list, int timeout) {
        String json = JSONArray.toJSONString(list);

        RedisUtil.set(key, json, Integer.valueOf(timeout));
    }

    public void setObjWithTime(String key, CountDataVO object, int timeout) {
        String json = JSONObject.toJSONString(object);

        RedisUtil.set(key, json, Integer.valueOf(timeout));
    }

    public List<OrderVo> findByMerchantIdsAsc(String[] ids, String startTime, String endTime) {
        DateTime dateTime1 = DateUtil.beginOfDay(DateUtil.parse(startTime));
        DateTime dateTime2 = DateUtil.endOfDay(DateUtil.parse(endTime));
        List<Order> orderList = this.orderService.findByMerchantIdsAsc(ids, dateTime1, dateTime2);
        return setName(orderList);
    }

    /**
     * 分销分润
     * @param ids
     * @param startTime
     * @param endTime
     * @return
     */
    public List<OrderVo> findByDistMerchantIdsAsc(String[] ids, String startTime, String endTime) {
        DateTime dateTime1 = DateUtil.beginOfDay(DateUtil.parse(startTime));
        DateTime dateTime2 = DateUtil.endOfDay(DateUtil.parse(endTime));
        List<Order> orderList = this.orderService.findByMerchantIdsAsc(ids, dateTime1, dateTime2);
        return setName(orderList);
    }

    public StatisticsCountDataVo getCacheCountDataAndOrderVoList(Company company, String conditionStartTime, String conditionEndTime) {
        CountDataVO countDataVO = new CountDataVO();
        DateTime startTime = DateUtil.beginOfDay(DateUtil.parse(conditionStartTime));
        DateTime endTime = DateUtil.endOfDay(DateUtil.parse(conditionEndTime));
        StatisticsCountDataVo statisticsCountDataVo = new StatisticsCountDataVo();
        String companyId = company.getId();
        //公司集合id
        List<String> companyIds = new ArrayList<String>();
        //服务商
        if(company.getType().equals(Company.Type.PROVIDERS.getCode())){
            //获取每天交易记录
            merchantService.getTransactionAmountNewDay(company.getId(),countDataVO,startTime.toString(),endTime.toString());
        }else if(!company.getType().equals(Company.Type.PROVIDERS.getCode()) || !company.getType().equals(Company.Type.ADMIN.getCode())){
            this.companyService.getCompanyByParentId(company, companyIds);
            //当前公司旗下的商户
            List<MerchantVO> merchantVOs = this.merchantBusinessService.findByCompanyIds((String[])companyIds.toArray(new String[companyIds.size()]));
            List<String> tempMerchantIds = new ArrayList<String>();
            merchantVOs.forEach(merchantVO ->
                    tempMerchantIds.add(merchantVO.getId()));
            String[] merchantIds = (String[])tempMerchantIds.toArray(new String[tempMerchantIds.size()]);
            //查询出订单
            List<OrderVo> orderVoList = findByMerchantIdsAsc(merchantIds, startTime.toString(), endTime.toString());
            countDataVO.setOrderNum(Integer.valueOf(orderVoList.size()));
            for (OrderVo orderVo : orderVoList) {
                countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(orderVo.getTotalPrice()));
                countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));
                countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(orderVo.getDisCountPrice()));
                if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus()) || OrderVo.Status.REFUNDTOTAL
                        .getCode().equals(orderVo.getStatus())) {
                    countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
                    countDataVO.setRefundNum(Integer.valueOf(countDataVO.getRefundNum().intValue() + 1));
                }
            }
        }
        //结算佣金
        BigDecimal commission = this.companyService.getLeftCommission(company, startTime.toString(), endTime.toString());
        countDataVO.setCommissionAmount(commission);
        countDataVO.setValidDealAmount(countDataVO.getOrderAmount().subtract(countDataVO.getRefundAmount()));
        statisticsCountDataVo.setCompanyId(companyId);
        String[] split = conditionStartTime.split(" ");
        statisticsCountDataVo.setSaveDay(split[0]);
        BeanUtils.copyProperties(countDataVO, statisticsCountDataVo);
        return statisticsCountDataVo;
    }

    /**
     * 计算公司下单个商户的每日佣金记录
     * @param company
     * @param conditionStartTime
     * @param conditionEndTime
     * @return
     */
    public StatisticsCountDataVo getMerchantDataAndOrderVoList(Company company,String merchantId, String conditionStartTime, String conditionEndTime) {

        StatisticsCountDataVo statisticsCountDataVo = new StatisticsCountDataVo();

        String companyId = company.getId();

        //公司集合id
        List<String> companyIds = new ArrayList<String>();


        this.companyService.getCompanyByParentId(company, companyIds);

        DateTime startTime = DateUtil.beginOfDay(DateUtil.parse(conditionStartTime));
        DateTime endTime = DateUtil.endOfDay(DateUtil.parse(conditionEndTime));



        String[] merchantIds = new String[1];
        merchantIds[0] = merchantId;


        //查询出订单
        List<OrderVo> orderVoList = findByMerchantIdsAsc(merchantIds, startTime.toString(), endTime.toString());


        CountDataVO countDataVO = new CountDataVO();


        countDataVO.setOrderNum(Integer.valueOf(orderVoList.size()));



        for (OrderVo orderVo : orderVoList) {



            countDataVO.setOrderAmount(countDataVO.getOrderAmount().add(orderVo.getTotalPrice()));

            countDataVO.setCustomerPaidAmount(countDataVO.getCustomerPaidAmount().add(orderVo.getActPayPrice()));

            countDataVO.setDiscountAmount(countDataVO.getDiscountAmount().add(orderVo.getDisCountPrice()));

            if (OrderVo.Status.REFUNDPART.getCode().equals(orderVo.getStatus()) || OrderVo.Status.REFUNDTOTAL
                    .getCode().equals(orderVo.getStatus())) {


                countDataVO.setRefundAmount(countDataVO.getRefundAmount().add(orderVo.getRefundPayPrice()));
                countDataVO.setRefundNum(Integer.valueOf(countDataVO.getRefundNum().intValue() + 1));
            }
        }

        //结算公司下单个商户佣金
        BigDecimal commission = this.companyService.getMerchantLeftCommission(company,merchantId, startTime, endTime);

        countDataVO.setCommissionAmount(commission);

        countDataVO.setValidDealAmount(countDataVO.getOrderAmount().subtract(countDataVO.getRefundAmount()));

        statisticsCountDataVo.setCompanyId(companyId);
        statisticsCountDataVo.setSaveDay(conditionStartTime);

        BeanUtils.copyProperties(countDataVO, statisticsCountDataVo);

        return statisticsCountDataVo;
    }
}
