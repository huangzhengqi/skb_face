package com.fzy.admin.fp.advertise.device.service;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceHome;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceMid;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceSuccess;
import com.fzy.admin.fp.advertise.device.dto.AdvertiseDeviceDTO;
import com.fzy.admin.fp.advertise.device.dto.AdvertiseDeviceViewListDTO;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceHomeRepository;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceMidRepository;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceRepository;
import com.fzy.admin.fp.advertise.device.repository.AdvertiseDeviceSuccessRepository;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceVO;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceViewListVO;
import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.advertise.group.domain.GroupMerchantCompany;
import com.fzy.admin.fp.advertise.group.repository.GroupMerchantRepository;
import com.fzy.admin.fp.advertise.group.repository.GroupRepository;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.utils.DateUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AdvertiseDeviceService implements BaseService<AdvertiseDevice> {

    @Resource
    private AdvertiseDeviceRepository advertiseDeviceRepository;

    @Resource
    private GroupMerchantRepository groupMerchantRepository;

    @Resource
    private AdvertiseDeviceHomeRepository advertiseDeviceHomeRepository;

    @Resource
    private AdvertiseDeviceMidRepository advertiseDeviceMidRepository;

    @Resource
    private AdvertiseDeviceSuccessRepository advertiseDeviceSuccessRepository;

    @Resource
    private AdvertiseDeviceViewLogService advertiseDeviceViewLogService;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private CompanyService companyService;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private CityRepository cityRepository;

    @Override
    public AdvertiseDeviceRepository getRepository() {
        return advertiseDeviceRepository;
    }

    /**
     * 首屏
     *
     * @param deviceAdvertise
     * @param advertiseDeviceDTO
     */
    public boolean setDeviceAdvertiseHome(String serviceId,AdvertiseDevice deviceAdvertise, AdvertiseDeviceDTO advertiseDeviceDTO) {

        //指定商户群  判断投放对象类型
        if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(1))){
            //查询群组关联的商户表
            List<GroupMerchantCompany> groupMerchantCompanyList=groupMerchantRepository.findAllByGroupId(advertiseDeviceDTO.getGroupId());
            for (GroupMerchantCompany groupMerchantCompany : groupMerchantCompanyList) {

                List<AdvertiseDeviceVO> advertiseDeviceVOS=advertiseDeviceHomeRepository.getAdvertiseDeviceHome(groupMerchantCompany.getMerchantId(), advertiseDeviceDTO.getBeginTime(), advertiseDeviceDTO.getEndTime());
                if (advertiseDeviceVOS.size() >= 4) {
                    log.info("当前时段商户广告位已经超过了4位");
                    return false;
                }

                AdvertiseDeviceHome advertiseDeviceHome=new AdvertiseDeviceHome();
                advertiseDeviceHome.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceHome.setGroupId(advertiseDeviceDTO.getGroupId());
                advertiseDeviceHome.setMerchantId(groupMerchantCompany.getMerchantId());
                advertiseDeviceHome.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceHome);
                advertiseDeviceHomeRepository.save(advertiseDeviceHome);
            }

            return true;

         //全部商户
        }else if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(2))){
            List<String> merchantIds = companyService.findAllmerchantIds(serviceId);
            for (String merchantId : merchantIds){
                AdvertiseDeviceHome advertiseDeviceHome=new AdvertiseDeviceHome();
                advertiseDeviceHome.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceHome.setMerchantId(merchantId);
                advertiseDeviceHome.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceHome);
                advertiseDeviceHomeRepository.save(advertiseDeviceHome);
            }

            return true;

         //指定商户
        }else {
            String merchantIds=advertiseDeviceDTO.getMerchantIds();
            String[] split=merchantIds.split(",");
            for (int i = 0; i<split.length ; i++){
                AdvertiseDeviceHome advertiseDeviceHome=new AdvertiseDeviceHome();
                advertiseDeviceHome.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceHome.setMerchantId(split[i]);
                advertiseDeviceHome.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceHome);
                advertiseDeviceHomeRepository.save(advertiseDeviceHome);
            }
        }
        return true;

    }

    /**
     * 支付页
     *
     * @param deviceAdvertise
     * @param advertiseDeviceDTO
     */
    public boolean setDeviceAdvertiseMid(String serviceId,AdvertiseDevice deviceAdvertise, AdvertiseDeviceDTO advertiseDeviceDTO) {

        //指定商户群  判断投放对象类型
        if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(1))){
            //查询群组关联的商户表
            List<GroupMerchantCompany> groupMerchantCompanyList=groupMerchantRepository.findAllByGroupId(advertiseDeviceDTO.getGroupId());
            for (GroupMerchantCompany groupMerchantCompany : groupMerchantCompanyList) {
                List<AdvertiseDeviceVO>  advertiseDeviceVOS=advertiseDeviceMidRepository.getAdvertiseDeviceMid(groupMerchantCompany.getMerchantId(), advertiseDeviceDTO.getBeginTime(), advertiseDeviceDTO.getEndTime());
                int count=advertiseDeviceVOS.size();
                if (count >= 3) {
                    log.info("当前时段商户广告位已经超过了3位");
                    return false;
                }
                AdvertiseDeviceMid advertiseDeviceMid=new AdvertiseDeviceMid();
                advertiseDeviceMid.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceMid.setGroupId(advertiseDeviceDTO.getGroupId());
                advertiseDeviceMid.setMerchantId(groupMerchantCompany.getMerchantId());
                advertiseDeviceMid.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceMid);
                advertiseDeviceMidRepository.save(advertiseDeviceMid);
            }
            return true;
         //全部商户
        }else if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(2))){
            List<String> merchantIds = companyService.findAllmerchantIds(serviceId);
            for (String merchantId : merchantIds){
                AdvertiseDeviceMid advertiseDeviceMid=new AdvertiseDeviceMid();
                advertiseDeviceMid.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceMid.setMerchantId(merchantId);
                advertiseDeviceMid.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceMid);
                advertiseDeviceMidRepository.save(advertiseDeviceMid);
            }
            return true;
         //指定商户
        }else {
            String merchantIds=advertiseDeviceDTO.getMerchantIds();
            String[] split=merchantIds.split(",");
            for (int i = 0; i<split.length ; i++){
                AdvertiseDeviceMid advertiseDeviceMid=new AdvertiseDeviceMid();
                advertiseDeviceMid.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceMid.setMerchantId(split[i]);
                advertiseDeviceMid.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceMid);
                advertiseDeviceMidRepository.save(advertiseDeviceMid);
            }
        }
        return true;
    }

    /**
     * 支付成功页
     *
     * @param deviceAdvertise
     * @param advertiseDeviceDTO
     */
    public boolean setDeviceAdvertiseSuccess(String serviceId,AdvertiseDevice deviceAdvertise, AdvertiseDeviceDTO advertiseDeviceDTO) {
        //指定商户群  判断投放对象类型
        if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(1))){
            //查询群组关联的商户表
            List<GroupMerchantCompany> groupMerchantCompanyList=groupMerchantRepository.findAllByGroupId(advertiseDeviceDTO.getGroupId());
            for (GroupMerchantCompany groupMerchantCompany : groupMerchantCompanyList) {
                List<AdvertiseDeviceVO> advertiseDeviceVOS=advertiseDeviceSuccessRepository.getAdvertiseDeviceSuccess(groupMerchantCompany.getMerchantId(), advertiseDeviceDTO.getBeginTime(), advertiseDeviceDTO.getEndTime());
                if (advertiseDeviceVOS.size() >= 1) {
                    log.info("当前时段商户广告位已经超过了1位");
                    return false;
                }
                AdvertiseDeviceSuccess advertiseDeviceSuccess=new AdvertiseDeviceSuccess();
                advertiseDeviceSuccess.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceSuccess.setGroupId(advertiseDeviceDTO.getGroupId());
                advertiseDeviceSuccess.setMerchantId(groupMerchantCompany.getMerchantId());
                advertiseDeviceSuccess.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceSuccess);
                advertiseDeviceSuccessRepository.save(advertiseDeviceSuccess);
            }
            return true;

         //全部商户
        }else if(advertiseDeviceDTO.getTargetType().equals(Integer.valueOf(2))){
            List<String> merchantIds = companyService.findAllmerchantIds(serviceId);
            for (String merchantId : merchantIds){
                AdvertiseDeviceSuccess advertiseDeviceSuccess=new AdvertiseDeviceSuccess();
                advertiseDeviceSuccess.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceSuccess.setMerchantId(merchantId);
                advertiseDeviceSuccess.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceSuccess);
                advertiseDeviceSuccessRepository.save(advertiseDeviceSuccess);
            }
            return true;
         //指定商户
        }else {
            String merchantIds=advertiseDeviceDTO.getMerchantIds();
            String[] split=merchantIds.split(",");
            for (int i = 0; i<split.length ; i++){
                AdvertiseDeviceSuccess advertiseDeviceSuccess=new AdvertiseDeviceSuccess();
                advertiseDeviceSuccess.setServiceProviderId(deviceAdvertise.getServiceProviderId());
                advertiseDeviceSuccess.setMerchantId(split[i]);
                advertiseDeviceSuccess.setAdvertiseDeviceId(deviceAdvertise.getId());
                BeanUtil.copyProperties(deviceAdvertise, advertiseDeviceSuccess);
                advertiseDeviceSuccessRepository.save(advertiseDeviceSuccess);
            }
        }
        return true;
    }

    @Transactional
    public Resp addDeviceAdvertise(String serviceId, AdvertiseDeviceDTO advertiseDeviceDTO) throws Exception {
        AdvertiseDevice deviceAdvertise=new AdvertiseDevice();
        BeanUtil.copyProperties(advertiseDeviceDTO, deviceAdvertise);
        deviceAdvertise.setServiceProviderId(serviceId);
        //默认广告
        deviceAdvertise.setDeviceType(advertiseDeviceDTO.getDeviceType());
        if(advertiseDeviceDTO.getType().equals(Integer.valueOf(2))){
            deviceAdvertise.setStatus(Integer.valueOf(2));
        }
        deviceAdvertise=advertiseDeviceRepository.save(deviceAdvertise);
        //不是默认广告
        if(advertiseDeviceDTO.getType().equals(Integer.valueOf(1))){
            //设置投放状态
            //当前时间
            Date nowDate = new Date();
            long nowTime=nowDate.getTime();
            //投放时间
            long beginTime = advertiseDeviceDTO.getBeginTime().getTime();
            //结束时间
            long endTime = advertiseDeviceDTO.getEndTime().getTime();
            int status = DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            deviceAdvertise.setStatus(Integer.valueOf(status));
            //投放首屏
            if (advertiseDeviceDTO.getTargetRange().equals(Integer.valueOf(0))) {
                boolean b=setDeviceAdvertiseHome(serviceId,deviceAdvertise, advertiseDeviceDTO);
                if (b == false) {
                    advertiseDeviceRepository.delete(deviceAdvertise.getId());
                    return Resp.success(false);
                }
                //投放收银页面
            } else if (advertiseDeviceDTO.getTargetRange().equals(Integer.valueOf(1))) {
                boolean b=setDeviceAdvertiseMid(serviceId,deviceAdvertise, advertiseDeviceDTO);
                if (b == false) {
                    advertiseDeviceRepository.delete(deviceAdvertise.getId());
                    return Resp.success(false);
                }
                //支付成功页
            } else {
                boolean b=setDeviceAdvertiseSuccess(serviceId,deviceAdvertise, advertiseDeviceDTO);
                if (b == false) {
                    advertiseDeviceRepository.delete(deviceAdvertise.getId());
                    return Resp.success(false);
                }
            }
        }
        return Resp.success(true);
    }

    /**
     * 预估
     *
     * @param gruopId
     */
    public Integer getEstimate(String gruopId) {
        List<GroupMerchantCompany> groupMerchantCompanies=groupMerchantRepository.findAllByGroupId(gruopId);
        return groupMerchantCompanies.size();
    }

    public Page<AdvertiseDeviceViewListVO> viewList(AdvertiseDeviceViewListDTO advertiseDeviceViewListDTO, PageVo pageVo) {
        Pageable pageable=PageUtil.initPage(pageVo);
        if (StringUtils.isEmpty(advertiseDeviceViewListDTO.getBeginTime())) {
            return advertiseDeviceRepository.viewList(advertiseDeviceViewListDTO.getId(), advertiseDeviceViewListDTO.getStatus(), pageable);
        } else {
            return advertiseDeviceRepository.viewList(advertiseDeviceViewListDTO.getId(), advertiseDeviceViewListDTO.getBeginTime(), advertiseDeviceViewListDTO.getEndTime(), advertiseDeviceViewListDTO.getStatus(), pageable);
        }
    }

    @Transactional
    public void deleteHome(String id) {
        advertiseDeviceHomeRepository.deleteByIds(id);
        delete(id);
    }

    @Transactional
    public void deleteMid(String id) {
        advertiseDeviceMidRepository.deleteByIds(id);
        delete(id);
    }

    @Transactional
    public void deleteSuccess(String id) {
        advertiseDeviceSuccessRepository.deleteByIds(id);
        delete(id);
    }

    /**
     * 预计投放群组
     * @param advertiseId
     */
    public  List<Merchant> expectedDelivery(String advertiseId) {
        //当前广告
        AdvertiseDevice advertiseDevice=advertiseDeviceRepository.findOne(advertiseId);
        List<Merchant> list=new ArrayList<>();
        //如果是指定商户，根据商户查询
        if("3".equals(advertiseDevice.getTargetType())){
            String [] ids=advertiseDevice.getMerchantIds().split(",");
            List<String> stringList = java.util.Arrays.asList(ids);
            list=merchantRepository.findByIdIn(stringList);
        }else {
            //根据广告查询群组
            Group group=groupRepository.findOne(advertiseDevice.getGroupId());
            //根据群组查询群组有多少商户
            List<GroupMerchantCompany> allByGroupId=groupMerchantRepository.findAllByGroupId(group.getId());
            List<String> ids=new ArrayList<>();
            for(GroupMerchantCompany groupMerchantCompany:allByGroupId){
                ids.add(groupMerchantCompany.getMerchantId());
            }
            list=merchantRepository.findByIdIn(ids);
        }
        List<City> cityList= cityRepository.findAll();
        for(Merchant merchant:list){
            for(City city:cityList){
                if(merchant.getCity().equals(city.getId())){
                    merchant.setCityName(city.getCityName());
                    break;
                }
            }
        }
        return list;
    }

    public List<Group> getGroupList(User user) {
       return groupRepository.findAllByServiceProviderIdAndCompanyIdAndDelFlagOrderByCreateTimeDesc(user.getServiceProviderId(), user.getCompanyId(),1);
    }
}
