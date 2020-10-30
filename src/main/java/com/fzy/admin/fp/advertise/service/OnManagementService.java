package com.fzy.admin.fp.advertise.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.advertise.repository.*;
import com.fzy.admin.fp.advertise.domain.*;
import com.fzy.admin.fp.advertise.repository.*;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRepository;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/7/3 16:06
 * @Description
 */
@Service
public class OnManagementService implements BaseService<OnManagement> {

    @Resource
    private OnManagementRepository onManagementRepository;

    @Resource
    private StrategicManagementRepository strategicManagementRepository;

    @Resource
    private AdvertiseManagementRepository advertiseManagementRepository;

    @Resource
    private StrategicManagementService strategicManagementService;

    @Resource
    private StrategicDistributorsRepository strategicDistributorsRepository;

    @Resource
    private RegionCityRepository regionCityRepository;

    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private StoreRepository storeRepository;

    @Resource
    private MerchantUserRepository merchantUserRepository;

    @Override
    public BaseRepository<OnManagement> getRepository() {
        return onManagementRepository;
    }

    //保存单个
    public void saveOne(OnManagement onManagement) {
        //预设
        onManagement.setCostMoney(0.00);
        onManagement.setShowNumber(0);
        onManagement.setClickNumber(0);
        onManagement.setRunNumber(0);
        onManagement.setClickRate(0.00);
        onManagement.setStatus(2);
        //把广告和策略转化为不可删除状态
        updateNow(onManagement);

        onManagementRepository.save(onManagement);
    }

    //查找单个信息
    public OnManagement findOne(String id) {
        OnManagement onManagement = onManagementRepository.findOne(id);
        AdvertiseManagement advertiseManagement = advertiseManagementRepository.findOne(onManagement.getAdvertiseId());
        StrategicManagement strategicManagement = strategicManagementRepository.findOne(onManagement.getStrategicId());
        onManagement.setAdvertiseManagement(advertiseManagement);
        onManagement.setStrategicManagement(strategicManagement);
        return onManagement;
    }

    //修改单个投放信息
    public void updateOne(OnManagement onManagement) {

        OnManagement onManagement1 = onManagementRepository.findOne(onManagement.getId());

        if (onManagement1.getStatus().equals(OnManagement.Status.OVER_USER.getCode())) {
            throw new BaseException("该投放已经结束无法修改");
        }

        if (onManagement1.getStatus().equals(OnManagement.Status.NO_USE.getCode())) {

            //修改之前的绑定的广告和策略状态
            updatePast(onManagement);

            //修改当前最新绑定广告和策略的状态
            updateNow(onManagement);
        }
        if (onManagement1.getStatus().equals(OnManagement.Status.USE.getCode())) {
            if (!onManagement1.getStartTime().equals(onManagement.getStartTime())) {
                throw new BaseException("已经投放无法修改初始投放时间");
            }
            if (!onManagement.getAdvertiseId().equals(onManagement1.getAdvertiseId())) {
                throw new BaseException("已经投放无法修改广告");
            }
            if (!onManagement.getStrategicId().equals(onManagement1.getStrategicId())) {
                throw new BaseException("已经投放无法修改策略");
            }
        }
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(true);
        BeanUtil.copyProperties(onManagement, onManagement1, copyOptions);
        onManagementRepository.save(onManagement1);
    }

    //删除单个投放
    public void deleteOne(String id) {
        OnManagement onManagement = onManagementRepository.findOne(id);
        if (onManagement.getStatus().equals(OnManagement.Status.USE.getCode())) {
            throw new BaseException("正在投放无法删除");
        } else if (onManagement.getStatus().equals(OnManagement.Status.OVER_USER.getCode())) {
            throw new BaseException("投放结束无法删除");
        }
        //修改关联的状态
        updateNow(onManagement);

        onManagementRepository.delete(id);
    }


    //投放操作
    public void onUse(String id) {
        OnManagement onManagement = onManagementRepository.findOne(id);
        onManagement.setStatus(OnManagement.Status.USE.getCode());
        onManagement.setStartTime(new Date());
        onManagementRepository.save(onManagement);
    }

    //结束投放操作
    public void endUse(String id) {
        OnManagement onManagement = onManagementRepository.findOne(id);
        onManagement.setStatus(OnManagement.Status.OVER_USER.getCode());
        onManagement.setEndTime(new Date());
        onManagementRepository.save(onManagement);
    }


    //修改过去投放关联的状态
    public void updatePast(OnManagement onManagement) {

        //未修改前的投放
        OnManagement onManagement1 = onManagementRepository.findOne(onManagement.getId());
        //查询投放中是否还有其他使用了该广告和策略
        String advertiseId = onManagement1.getAdvertiseId();//过去的广告id
        String strategicId = onManagement1.getStrategicId();//过去的策略id
        List<OnManagement> onManagements = onManagementRepository.findByAdvertiseId(advertiseId);
        List<OnManagement> onManagementList = onManagementRepository.findByStrategicId(strategicId);
        if (onManagements.size() == 1) {
            //把广告修改为可以删除的状态
            AdvertiseManagement advertiseManagement = advertiseManagementRepository.findOne(advertiseId);
            advertiseManagement.setSourceType(AdvertiseManagement.SourceType.NO_USE.getCode());
            advertiseManagementRepository.save(advertiseManagement);
        }
        if (onManagementList.size() == 1) {
            //把策略修改为可以删除的状态
            StrategicManagement strategicManagement = strategicManagementRepository.findOne(strategicId);
            strategicManagement.setSourceType(StrategicManagement.SourceType.NO_USE.getCode());
            strategicManagementRepository.save(strategicManagement);
        }

    }

    //修改现在关联广告和策略的状态
    public void updateNow(OnManagement onManagement) {
        String advertiseId = onManagement.getAdvertiseId();
        String strategicId = onManagement.getStrategicId();
        //把广告和策略转化为不可删除状态
        AdvertiseManagement advertiseManagement = advertiseManagementRepository.findOne(advertiseId);
        if (advertiseManagement.getSourceType().equals(AdvertiseManagement.SourceType.NO_USE.getCode())) {
            advertiseManagement.setSourceType(AdvertiseManagement.SourceType.USE.getCode());
            advertiseManagementRepository.save(advertiseManagement);
        }
        StrategicManagement strategicManagement = strategicManagementRepository.findOne(strategicId);
        if (strategicManagement.getSourceType().equals(StrategicManagement.SourceType.NO_USE.getCode())) {
            strategicManagement.setSourceType(StrategicManagement.SourceType.USE.getCode());
            strategicManagementRepository.save(strategicManagement);
        }
    }

    //处理计费的业务逻辑
    public void updateByCost(Integer status, OnManagement onManagement) {
        String advertiseId = onManagement.getAdvertiseId();
        AdvertiseManagement advertiseManagement = advertiseManagementRepository.findOne(advertiseId);
        if (status.equals(1)) {
            //主表直跳计数计费
            int num = onManagement.getRunNumber();
            num += 1;
            onManagement.setRunNumber(num);
            double b = onManagement.getRunCost();
            double cost = onManagement.getCostMoney();
            cost += b;
            onManagement.setCostMoney(cost);
            onManagementRepository.save(onManagement);
        } else if (status.equals(2)) {
            //主表展示计费
            int showNumber = onManagement.getShowNumber();
            int a = showNumber / 1000;
            showNumber += 1;
            int b = showNumber / 1000;
            onManagement.setShowNumber(showNumber);
            if (a != b) {
                double showCost = onManagement.getShowCost();
                double cost = onManagement.getCostMoney();
                cost += showCost;
                onManagement.setCostMoney(cost);
            }
            if (advertiseManagement.getType().equals(AdvertiseManagement.Type.CPC.getCode())) {
                double clickRate = 0.00;
                if (!onManagement.getShowNumber().equals(0)) {
                    clickRate = onManagement.getClickNumber() / (double) onManagement.getShowNumber();
                }
                onManagement.setClickRate(clickRate);
            }
            onManagementRepository.save(onManagement);
        } else if (status.equals(3)) {
            //主表点击计费
            int clickNumber = onManagement.getClickNumber();
            clickNumber += 1;
            onManagement.setClickNumber(clickNumber);
            double clickCost = onManagement.getClickCost();
            double cost = onManagement.getCostMoney();
            cost += clickCost;
            onManagement.setCostMoney(cost);
            if (advertiseManagement.getType().equals(AdvertiseManagement.Type.CPC.getCode())) {
                double clickRate = 0.00;
                if (!onManagement.getShowNumber().equals(0)) {
                    clickRate = onManagement.getClickNumber() / (double) onManagement.getShowNumber();
                }
                System.out.println("clickRate" + clickRate);
                onManagement.setClickRate(clickRate);
            }
            onManagementRepository.save(onManagement);
        }
    }


    //挑选符合条件的投放广告列表
    public List<OnManagement> getSelect(Integer appType, String merchantId, String storeId) {
        int status = OnManagement.Status.USE.getCode();//投放当中的广告
        if (appType == null) {
            throw new BaseException("请指定支付后还是app类型");
        }
        //找出可以投放的信息
        List<OnManagement> onManagementList = onManagementRepository.findByStatusAndAppType(status, appType);
        System.out.println(onManagementList);
        //获取当前正在使用的策略Id匹配当前时间条件规则
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String weekDay = sdf.format(date);
        List<OnManagement> onManagements = new ArrayList<>();//未条件匹配的投放
        for (OnManagement onManagement : onManagementList) {
            String strategicId = onManagement.getStrategicId();
            StrategicManagement sm = strategicManagementService.findOne(strategicId);
            String weekDayNums = sm.getWeekDay();
            String[] day = weekDayNums.split(",");
            if (sm.getTimeRange().equals(StrategicManagement.TimeRange.SOME.getCode())) {
                boolean is = false;
                for (String num : day) {
                    if (weekDay.equals(num)) {
                        //星期几规则已匹配还需匹配时间段
                        List<StrategicTime> strategicTimesmList = sm.getStrategicTimeList();
                        for (StrategicTime st : strategicTimesmList) {
                            String startTime = st.getStartTime();
                            String endTime = st.getEndTime();
                            SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
                            String hours = sd.format(date);
                            try {
                                Date hour = sd.parse(hours);
                                Date start = sd.parse(startTime);
                                Date end = sd.parse(endTime);
                                if (hour.after(start) && hour.before(end)) {
                                    onManagements.add(onManagement);//匹配时间段添入投放集合
                                    is = true;
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (is) {
                        break;
                    }
                }
            } else {
                for (String num : day) {
                    if (weekDay.equals(num)) {
                        onManagements.add(onManagement);//当前星期几匹配时间规则
                        break;
                    }
                }
            }
        }
        //再次经过投放取得广告,此时投放只匹配时间段
        List<OnManagement> onManagements1 = new ArrayList<>();
        //开始匹配渠道商规则
        for (OnManagement onManagement : onManagements) {
            String strategicId = onManagement.getStrategicId();
            StrategicManagement sm = strategicManagementService.findOne(strategicId);
            if (sm.getOperatorRange().equals(StrategicManagement.Range.SOME.getCode())) {
                List<StrategicDistributors> sd = strategicDistributorsRepository
                        .findByDistributorsIdAndStrategicId(merchantId, strategicId);
                if (!sd.isEmpty()) {
                    onManagements1.add(onManagement);
                }
            } else {
                onManagements1.add(onManagement);
            }

        }
        //已经匹配商户范围规则
        System.out.println(onManagements1);
        List<OnManagement> onManagements2 = new ArrayList<>();
        //三重匹配地域规则
        for (OnManagement onManagement : onManagements1) {
            String strategicId = onManagement.getStrategicId();
            StrategicManagement sm = strategicManagementService.findOne(strategicId);
            if (sm.getRegionType().equals(StrategicManagement.RegionType.SOME.getCode())) {
                //获取匹配城市
                List<RegionCity> regionCities = regionCityRepository.findByStrategicId(strategicId);
                //如果没有门店ID默认商户城市地址
                List<String> cities = new ArrayList<>();
                for (RegionCity regionCity : regionCities) {
                    String cityName = regionCity.getCityName();
                    cities.add(cityName);
                }
                if (storeId == null || storeId.isEmpty()) {
                    Merchant merchant = merchantRepository.findOne(merchantId);
                    String cityName = merchant.getCity();
                    if (cities.contains(cityName)) {
                        onManagements2.add(onManagement);
                    }
                } else {
                    String cityName = storeRepository.findOne(storeId).getCity();
                    if (cities.contains(cityName)) {
                        onManagements2.add(onManagement);
                    }
                }
            } else {
                onManagements2.add(onManagement);
            }
        }
        for (OnManagement onManagement : onManagements2) {
            String advertiseId = onManagement.getAdvertiseId();
            onManagement.setAdvertiseManagement(advertiseManagementRepository.findOne(advertiseId));
        }
        System.out.println(onManagements2);
        return onManagements2;
    }

    //PC端交换ID
    public String exchangeId(String userId) {
        return merchantUserRepository.findOne(userId).getMerchantId();
    }


}
