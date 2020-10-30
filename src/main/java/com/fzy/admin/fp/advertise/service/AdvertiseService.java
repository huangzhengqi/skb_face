package com.fzy.admin.fp.advertise.service;

import com.fzy.admin.fp.advertise.repository.AdvertiseRepository;
import com.fzy.admin.fp.advertise.domain.Advertise;
import com.fzy.admin.fp.advertise.domain.AdvertiseTarget;
import com.fzy.admin.fp.advertise.domain.AdvertiseViewLog;
import com.fzy.admin.fp.advertise.dto.AdvertiseListDTO;
import com.fzy.admin.fp.advertise.dto.AdvertiseViewDTO;
import com.fzy.admin.fp.advertise.dto.AdvertiseViewListDTO;
import com.fzy.admin.fp.advertise.repository.AdvertiseRepository;
import com.fzy.admin.fp.advertise.vo.AdvertisePageVO;
import com.fzy.admin.fp.advertise.vo.AdvertiseViewListVO;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/7/1 16:14
 * @Description
 */
@Service
public class AdvertiseService implements BaseService<Advertise> {

    @Resource
    private AdvertiseRepository advertiseRepository;
    @Resource
    private MerchantService merchantService;
    @Resource
    private AdvertiseTargetService advertiseTargetService;
    @Resource
    private AdvertiseViewLogService advertiseViewLogService;

    @Resource
    private CompanyRepository companyRepository;

    @Override
    public BaseRepository<Advertise> getRepository() {
        return advertiseRepository;
    }


    /**
     * 获取广告列表
     *
     * @param advertiseViewDTO
     * @return
     */

    public Resp<List<Advertise>> findAdv(AdvertiseViewDTO advertiseViewDTO) {
        List<Advertise> list = new ArrayList<>();
        Merchant merchant = (Merchant) this.merchantService.findOne(advertiseViewDTO.getMerchantId());
        if (merchant == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "商户不存在");
        }
        if (advertiseViewDTO.getFromRange().equals(Integer.valueOf(5))) {
            List<String> merchantIds = new ArrayList<String>();
            merchantIds.add(advertiseViewDTO.getMerchantId());
            List<AdvertiseTarget> merchantAd = this.advertiseTargetService.findAllByTargetIdIn(merchantIds);
            List<AdvertiseTarget> cityAdv = this.advertiseTargetService.findAllByCityIds(merchant.getCity());
            List<AdvertiseTarget> allAdv = new ArrayList<AdvertiseTarget>();
            allAdv.addAll(merchantAd);
            allAdv.addAll(cityAdv);
            List<String> advIds = (List) allAdv.stream().map(AdvertiseTarget::getAdvertiseId).collect(Collectors.toList());
            List<Advertise> allAdvertises = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(1));
            advIds.addAll((Collection) allAdvertises.stream().map(BaseEntity::getId).collect(Collectors.toList()));
            List<Advertise> oemAdvertises = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdAndTargetRangeOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), merchant.getServiceProviderId(), Integer.valueOf(16));
            advIds.addAll((Collection) oemAdvertises.stream().map(BaseEntity::getId).collect(Collectors.toList()));
            List<String> companyIds = this.merchantService.findSuperiorIds(merchant);
            List<Company> companies = this.companyRepository.findByIdIn(companyIds);
            for (Company company : companies) {
                if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
                    List<Advertise> advertises = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdAndTargetRangeOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), merchant.getServiceProviderId(), Integer.valueOf(2));
                    advIds.addAll((Collection) advertises.stream().map(BaseEntity::getId).collect(Collectors.toList()));
                }
                if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
                    List<Advertise> advertises = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdAndTargetRangeOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), merchant.getServiceProviderId(), Integer.valueOf(3));
                    advIds.addAll((Collection) advertises.stream().map(BaseEntity::getId).collect(Collectors.toList()));
                }
                if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
                    List<Advertise> advertises = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdAndTargetRangeOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), merchant.getServiceProviderId(), Integer.valueOf(15));
                    advIds.addAll((Collection) advertises.stream().map(BaseEntity::getId).collect(Collectors.toList()));
                }
            }
            list = this.advertiseRepository.findTop4ByIdInAndBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdOrderByBeginTimeDesc(advIds, new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), merchant.getServiceProviderId());
            if (list.size() == 0) {
                list = this.advertiseRepository.findTop4ByIdInAndStatusAndTypeAndServiceProviderIdOrderByBeginTimeDesc(advIds, Integer.valueOf(1), Integer.valueOf(1), merchant.getServiceProviderId());
            }
        } else {
            //如果是支付宝设备则查视频类
            if (advertiseViewDTO.getStatus() != null && advertiseViewDTO.getStatus() == 0) {
                list = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTargetRangeAndServiceProviderIdOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), advertiseViewDTO.getFromRange(), merchant.getServiceProviderId());
            } else {
                if(advertiseViewDTO.getDeviceType() != null){
                    //获取支付宝广告和全部广告
                    if (advertiseViewDTO.getDeviceType().equals(Integer.valueOf(1))) {
                        List<Integer> deviceTypeList = new ArrayList<>();
                        deviceTypeList.add(Integer.valueOf(0));
                        deviceTypeList.add(Integer.valueOf(1));
                        list = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeAndServiceProviderIdAndDeviceTypeInOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), advertiseViewDTO.getFromRange(), merchant.getServiceProviderId(), deviceTypeList);

                        //获取微信广告和全部广告
                    } else if (advertiseViewDTO.getDeviceType().equals(Integer.valueOf(2))) {
                        List<Integer> deviceTypeList = new ArrayList<>();
                        deviceTypeList.add(Integer.valueOf(0));
                        deviceTypeList.add(Integer.valueOf(2));
                        list = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeAndServiceProviderIdAndDeviceTypeInOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), advertiseViewDTO.getFromRange(), merchant.getServiceProviderId(), deviceTypeList);
                    }
//                list = this.advertiseRepository.findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeAndServiceProviderIdOrderByBeginTimeDesc(new Date(), new Date(), Integer.valueOf(1), Integer.valueOf(0), advertiseViewDTO.getFromRange(), merchant.getServiceProviderId());
                }
            }
        }
        if (list.size() == 0) {
            List<Integer> targetRange = new ArrayList<Integer>();
            targetRange.add(advertiseViewDTO.getFromRange());
            if (!advertiseViewDTO.getFromRange().equals(Integer.valueOf(5))) {
                list = this.advertiseRepository.findTop4ByStatusAndTypeAndTargetRangeInAndServiceProviderIdOrderByBeginTimeDesc(Integer.valueOf(1), Integer.valueOf(1), targetRange, merchant.getServiceProviderId());
            }
        }
        //记录曝光次数
        for (Advertise advertise : list) {
            AdvertiseViewLog advertiseViewLog = new AdvertiseViewLog();
            advertiseViewLog.setAdvertiseId(advertise.getId());
            advertiseViewLog.setFromRange(advertiseViewDTO.getFromRange());
            advertiseViewLog.setMerchantId(advertiseViewDTO.getMerchantId());
            advertiseViewLog.setStatus(Integer.valueOf(1));
            this.advertiseViewLogService.save(advertiseViewLog);
        }
        return Resp.success(list);
    }

    /*public Resp<List<Advertise>> findAdv(AdvertiseViewDTO advertiseViewDTO) {
        Merchant merchant = merchantService.findOne(advertiseViewDTO.getMerchantId());
        if (merchant == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户不存在");
        }
        //获取商户所有上级单位id
        List<String> companyIds = merchantService.findSuperiorIds(merchant);
        //通过上级id和城市查询当前商户需要的广告
        List<AdvertiseTarget> advertiseTargets = advertiseTargetService.findAllByTargetIdIn(companyIds);
        List<AdvertiseTarget> cityAdv = advertiseTargetService.findAllByCityIds(merchant.getCity());
        List<AdvertiseTarget> allAdv = new ArrayList<>();
        allAdv.addAll(advertiseTargets);
        allAdv.addAll(cityAdv);
        //获取广告id
        List<String> advIds = allAdv.stream().map(AdvertiseTarget::getAdvertiseId).collect(Collectors.toList());
        List<Integer> range = new ArrayList<>();
        if (advertiseViewDTO.getFromRange().equals(5)) {
            range.add(1);
            range.add(2);
            range.add(3);
            range.add(4);
            range.add(5);
        } else {
            range.add(advertiseViewDTO.getFromRange());
        }
        List<Advertise> list = advertiseRepository.findTop4ByIdInAndBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTargetRangeInAndServiceProviderIdOrderByBeginTimeDesc(advIds,new Date(),new Date(),1,range,merchant.getServiceProviderId());

        //记录曝光次数
        for (Advertise advertise : list) {
            AdvertiseViewLog advertiseViewLog = new AdvertiseViewLog();
            advertiseViewLog.setAdvertiseId(advertise.getId());
            advertiseViewLog.setFromRange(advertiseViewDTO.getFromRange());
            advertiseViewLog.setMerchantId(advertiseViewDTO.getMerchantId());
            advertiseViewLog.setStatus(1);
            advertiseViewLogService.save(advertiseViewLog);
        }

        return Resp.success(list);
    }
*/
    public Page<AdvertiseViewListVO> viewList(AdvertiseViewListDTO advertiseViewListDTO, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (StringUtils.isEmpty(advertiseViewListDTO.getBeginTime())) {
            return advertiseRepository.viewList(advertiseViewListDTO.getId(), advertiseViewListDTO.getStatus(), pageable);
        } else {
            return advertiseRepository.viewList(advertiseViewListDTO.getId(), advertiseViewListDTO.getBeginTime(), advertiseViewListDTO.getEndTime(), advertiseViewListDTO.getStatus(), pageable);
        }
    }

    /**
     * 获取广告列表
     *
     * @param advertiseListDTO
     * @param pageVo
     * @return
     */
    public Page<AdvertisePageVO> getPage(AdvertiseListDTO advertiseListDTO, PageVo pageVo, String serviceId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<AdvertisePageVO> page;
        if (StringUtils.isEmpty(advertiseListDTO.getStatus())) {
            page = advertiseRepository.getPage(advertiseListDTO.getTitle(), serviceId, pageable);
        } else {
            page = advertiseRepository.getPage(advertiseListDTO.getTitle(), serviceId, advertiseListDTO.getStatus(), pageable);
        }
        for (AdvertisePageVO advertisePageVO : page.getContent()) {
            Integer clickNum = advertiseViewLogService.findClickNumByAdvId(advertisePageVO.getId());
            Integer exposureNum = advertiseViewLogService.findExposureNumByAdvId(advertisePageVO.getId());
            advertisePageVO.setClickNum(clickNum);
            advertisePageVO.setExposureNum(exposureNum);
        }
        return page;
    }

    public List<String> getMerchantType(String companyId) {
        return null;
    }
}
