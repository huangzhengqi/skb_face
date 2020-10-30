package com.fzy.admin.fp.advertise.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.advertise.domain.Advertise;
import com.fzy.admin.fp.advertise.domain.AdvertiseTarget;
import com.fzy.admin.fp.advertise.domain.AdvertiseViewLog;
import com.fzy.admin.fp.advertise.dto.*;
import com.fzy.admin.fp.advertise.service.AdvertiseService;
import com.fzy.admin.fp.advertise.service.AdvertiseTargetService;
import com.fzy.admin.fp.advertise.service.AdvertiseViewLogService;
import com.fzy.admin.fp.advertise.vo.AdvertisePageVO;
import com.fzy.admin.fp.advertise.vo.AdvertiseViewListVO;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.utils.DateUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/7/1 16:04
 * @Description
 */
@RestController
@RequestMapping(value = "/advertise/advertise")
@Api(value = "AdvertiseController", tags = {"管理后台接口"})
public class AdvertiseController {

    @Resource
    private AdvertiseService advertiseService;
    @Resource
    private AdvertiseTargetService advertiseTargetService;

    @Resource
    private AdvertiseViewLogService advertiseViewLogService;
    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private CityRepository cityRepository;

    @ApiOperation(value = "新增广告", notes = "新增广告")
    @PostMapping("/save")
    @Transactional(rollbackOn = RuntimeException.class)
    public Resp<String> save(@RequestBody AdvertiseAddDTO advertiseAddDTO,
                             @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {

        if(advertiseAddDTO.getBeginTime() == null || advertiseAddDTO.getBeginTime().equals("")){
            throw new BaseException("有效时间不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if(advertiseAddDTO.getEndTime() == null || advertiseAddDTO.getEndTime().equals("")){
            throw new BaseException("有效时间不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        Date endTime=DateUtil.getEndOfDay(advertiseAddDTO.getEndTime());
        try {
            Advertise advertise = new Advertise();
            BeanUtil.copyProperties(advertiseAddDTO, advertise);
            advertise.setEndTime(endTime);
            advertise.setServiceProviderId(serviceProviderId);
            advertise.setStatus(Integer.valueOf(1));
            advertise.setCreateTime(new Date());
            this.advertiseService.save(advertise);

            if(advertiseAddDTO.getTargetType().equals(Integer.valueOf(1)) && advertiseAddDTO.getTargetRange().equals(Integer.valueOf(17))){
                //指定商户
                if (advertiseAddDTO.getMetType() != null) {
                    merchantRepository.findAll();
                    for (String metType : advertiseAddDTO.getMetType()) {
                        AdvertiseTarget advertiseTarget = new AdvertiseTarget();
                        BeanUtil.copyProperties(advertiseAddDTO, advertiseTarget);
                        advertiseTarget.setAdvertiseId(advertise.getId());
                        //advertiseTarget.setTargetId(targetId);
                        advertiseTargetService.save(advertiseTarget);
                    }
                }
            }else if (advertiseAddDTO.getTargetType().equals(Integer.valueOf(1)) && advertiseAddDTO.getTargetRange().equals(Integer.valueOf(4))) {
                //指定商户
                if (advertiseAddDTO.getTargetIds() != null) {
                    for (String targetId : advertiseAddDTO.getTargetIds()) {
                        AdvertiseTarget advertiseTarget = new AdvertiseTarget();
                        BeanUtil.copyProperties(advertiseAddDTO, advertiseTarget);
                        advertiseTarget.setAdvertiseId(advertise.getId());
                        advertiseTarget.setTargetId(targetId);
                        advertiseTarget.setType(Integer.valueOf(2));
                        advertiseTargetService.save(advertiseTarget);
                    }
                }
            } else if (advertiseAddDTO.getTargetType().equals(Integer.valueOf(1)) && advertiseAddDTO.getTargetRange().equals(Integer.valueOf(5))) {
                //指定城市
                if (advertiseAddDTO.getCityIds() != null) {
                    for (String cityId : advertiseAddDTO.getCityIds()) {
                        AdvertiseTarget advertiseTarget = new AdvertiseTarget();
                        BeanUtil.copyProperties(advertiseAddDTO, advertiseTarget);
                        advertiseTarget.setAdvertiseId(advertise.getId());
                        advertiseTarget.setCityIds(cityId);
                        advertiseTarget.setType(Integer.valueOf(1));
                        advertiseTargetService.save(advertiseTarget);
                    }
                }
            }

            return Resp.success("添加成功");
        } catch ( Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "添加失败");
        }

    }


    @ApiOperation(value = "删除广告", notes = "删除广告")
    @DeleteMapping(value = "/delete")
    @Transactional
    public Resp<String> delete(@RequestBody AdvertiseDelDTO advertiseDelDTO) {
        List<Advertise> list = advertiseService.findAll(advertiseDelDTO.getIds());
        for (Advertise e : list) {
            advertiseService.delete(e.getId());
            advertiseTargetService.deleteByAdvertiseId(e.getId());
        }
        return Resp.success("删除成功");
    }

    @ApiOperation(value = "广告列表", notes = "广告列表")
    @GetMapping(value = "/list")
    public Resp<Page<AdvertisePageVO>> list(AdvertiseListDTO advertiseListDTO,@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        PageVo pageVo = new PageVo();
        BeanUtil.copyProperties(advertiseListDTO, pageVo);
        Page<AdvertisePageVO> page = advertiseService.getPage(advertiseListDTO, pageVo,serviceProviderId);
        return Resp.success(page);
    }

    @ApiOperation(value = "禁用/启用广告", notes = "禁用/启用广告")
    @PutMapping(value = "/isable")
    public Resp<String> isable(@RequestBody AdvertiseIsableDTO advertiseIsableDTO) {
        List<Advertise> list = advertiseService.findAll(advertiseIsableDTO.getIds());
        for (Advertise e : list) {
            e.setStatus(advertiseIsableDTO.getStatus());
            advertiseService.save(e);
        }
        return Resp.success("修改成功！");
    }

    @ApiOperation(value = "获取广告 app/小程序/设备", notes = "获取广告 app/小程序/设备")
    @GetMapping({"/view"})
    public Resp<List<Advertise>> viewAdv(@RequestParam("fromRange") @ApiParam("5商户app首页广告 6客户小程序 7（小程序）客户付款完页面 8（设备）启动页 9（设备）收银页 10（设备）支付成功页 11app收款后广告 12（设备）非会员支付页 13收押金页成功 14退押金成功") Integer fromRange) {
        String merchantId = TokenUtils.getMerchantId();
        AdvertiseViewDTO advertiseViewDTO = new AdvertiseViewDTO();
        advertiseViewDTO.setFromRange(fromRange);
        advertiseViewDTO.setMerchantId(merchantId);
        return this.advertiseService.findAdv(advertiseViewDTO);
    }

    @ApiOperation(value = "获取广告 h5", notes = "获取广告 h5")
    @GetMapping(value = "/view/h5")
    public Resp<List<Advertise>> viewAdvH5(AdvertiseViewDTO advertiseViewDTO) {
        return advertiseService.findAdv(advertiseViewDTO);
    }

    @ApiOperation(value = "查看广告 app/小程序", notes = "查看广告 app/小程序")
    @GetMapping({"/click"})
    public void clickAdv(@ApiParam("广告id") @RequestParam("advertiseId") String advertiseId, @ApiParam("6客户小程序 7（小程序）客户付款完页面 8（设备）启动页 9（设备）收银页 10（设备）支付成功页 11app收款后广告 12（设备）非会员支付页 13收押金页成功 14退押金成功") @RequestParam("fromRange") Integer fromRange) {
        String merchantId = TokenUtils.getMerchantId();
        AdvertiseViewLog advertiseViewLog = new AdvertiseViewLog();
        advertiseViewLog.setAdvertiseId(advertiseId);
        advertiseViewLog.setFromRange(fromRange);
        advertiseViewLog.setMerchantId(merchantId);
        advertiseViewLog.setStatus(Integer.valueOf(2));
        this.advertiseViewLogService.save(advertiseViewLog);
    }

    @ApiOperation(value = "查询广告 app/小程序", notes = "查询广告 app/小程序")
    @GetMapping({"/click/h5"})
    public void clickAdvH5(AdvertiseClickDTO advertiseViewDTO) {
        AdvertiseViewLog advertiseViewLog = new AdvertiseViewLog();
        advertiseViewLog.setAdvertiseId(advertiseViewDTO.getAdvertiseId());
        advertiseViewLog.setFromRange(advertiseViewDTO.getFromRange());
        advertiseViewLog.setMerchantId(advertiseViewDTO.getMerchantId());
        advertiseViewLog.setStatus(Integer.valueOf(2));
        this.advertiseViewLogService.save(advertiseViewLog);
    }

    @ApiOperation(value = "点击/曝光列表", notes = "点击/曝光列表")
    @GetMapping(value = "/view/list")
    public Resp<Page<AdvertiseViewListVO>> viewlist(AdvertiseViewListDTO advertiseViewListDTO) {
        PageVo pageVo = new PageVo();
        BeanUtil.copyProperties(advertiseViewListDTO, pageVo);
        Page<AdvertiseViewListVO> page = advertiseService.viewList(advertiseViewListDTO, pageVo);
        return Resp.success(page);
    }

    @ApiOperation(value = "城市列表", notes = "城市列表")
    @GetMapping(value = "/city/list")
    public Resp<List<String>> citylist(String id) {
        List<String> cityIds = advertiseTargetService.findCitysByAdvId(id);
        List<String> cityNames = cityRepository.findAllByIdIn(cityIds);
        return Resp.success(cityNames);
    }
}
