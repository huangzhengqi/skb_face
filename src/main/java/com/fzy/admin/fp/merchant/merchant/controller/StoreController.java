package com.fzy.admin.fp.merchant.merchant.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.YunlabaUtil;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.FeiyuConfig;
import com.fzy.admin.fp.merchant.merchant.domain.HuaBeiConfig;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.dto.HuaBeiSettingDTO;
import com.fzy.admin.fp.merchant.merchant.dto.UpdateCategoryDTO;
import com.fzy.admin.fp.merchant.merchant.repository.FeiYuConfigRepository;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 门店控制层
 */
@Slf4j
@RestController
@RequestMapping("/merchant/store")
@Api(value="StoreController", tags={"门店相关"})
public class StoreController extends BaseController<Store> {

    @Resource
    private StoreService storeService;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private FeiYuConfigRepository feiYuConfigRepository;

    @Override
    public StoreService getService() {
        return storeService;
    }

    @ApiOperation(value = "获取门店列表",notes = "获取门店列表")
    @GetMapping("/list_rewrite")
    public Resp listRewrite(Store entity, PageVo pageVo, @UserId String userId) {

        return Resp.success(storeService.listRewrite(entity, pageVo, userId));
    }

    @ApiOperation(value = "添加门店",notes = "添加门店")
    @PostMapping("/save_rewrite")
    public Resp saveRewrite(@Valid Store entity, @UserId String userId) {
        return Resp.success(storeService.saveRewrite(entity, userId));
    }

    @ApiOperation(value = "查看门店详情",notes = "查看门店详情")
    @GetMapping("/find_one")
    public Resp<Store> findOne(String id) {

        Store store=storeService.findOne(id);
//        try {
//            Integer.parseInt(store.getProvince());
//            Province province=provinceRepository.findOne(store.getProvince());
//            log.info("==province" + province);
//            store.setProvince(province.getProvinceName());
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Integer.parseInt(store.getCity());
//            City city=cityRepository.findOne(store.getCity());
//            log.info("==city" + city);
//            store.setCity(city.getCityName());
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
        return Resp.success(store);
    }


    @ApiOperation(value = "获取商户对应的门店下拉框",notes = "获取商户对应的门店下拉框")
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(@UserId String userId) {
        MerchantUser user=merchantUserService.findOne(userId);
        return Resp.success(storeService.getRepository().selectItem(user.getMerchantId()));
    }

    /**
     * 查找店铺
     *
     * @param merchantId
     * @param store
     * @param pageVo
     * @return
     */
    @GetMapping(value="/find_stores")
    public Resp findByStores(@TokenInfo(property="merchantId") String merchantId, Store store, PageVo pageVo) {
        store.setMerchantId(merchantId);
        return list(store, pageVo);
    }

    /**
     * 云喇叭测试
     *
     * @param
     * @return
     */
    @GetMapping(value="/voice_test")
    @ApiOperation(value="云喇叭测试", notes="云喇叭测试")
    public Resp voiceTest(@RequestParam(value="deviceId") @ApiParam(value="设备编号") String deviceId) {
        FeiyuConfig feiyuConfig=feiYuConfigRepository.findByDeviceIdAndStatus(deviceId, 1);
        if (feiyuConfig == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前设备已被禁用");
        }
        return YunlabaUtil.sendVoice(deviceId, "100", "1", feiyuConfig.getIsSuffix());
    }

    /**
     * 获取云喇叭配置信息
     *
     * @param storeId
     * @return
     */
    @ApiOperation(value="获取云喇叭配置信息", notes="获取云喇叭配置信息")
    @GetMapping(value="/voice")
    public Resp<List<FeiyuConfig>> getConfig(@RequestParam(value="storeId") @ApiParam(value="门店id") String storeId) {
        List<FeiyuConfig> feiyuConfigs=feiYuConfigRepository.findAllByStoreId(storeId);
        return Resp.success(feiyuConfigs, "");
    }

    @ApiOperation(value="配置云喇叭信息", notes="配置云喇叭信息")
    @PostMapping(value="/voice")
    public Resp<String> setConfig(@RequestBody FeiyuConfig feiyuConfig) {
        try {
            if (!StringUtils.isEmpty(feiyuConfig.getId()) && feiYuConfigRepository.findByIdNotAndDeviceId(feiyuConfig.getId(), feiyuConfig.getDeviceId()) != null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "当前设备已被使用");
            }
            if (StringUtils.isEmpty(feiyuConfig.getId()) && feiYuConfigRepository.findByDeviceId(feiyuConfig.getDeviceId()) != null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "当前设备已被使用");
            }
            if (StringUtils.isEmpty(feiyuConfig.getId())) {
                //第一次配置需要先绑定
                Resp resp=YunlabaUtil.bind(feiyuConfig.getDeviceId(), "1", feiyuConfig.getStoreId());
                if (!resp.getCode().equals(Resp.Status.SUCCESS.getCode())) {
                    return resp;
                }
            }
            //设置支付后广告
            if (!StringUtils.isEmpty(feiyuConfig.getAdvStr())) {
                Resp respAdv=YunlabaUtil.modify(feiyuConfig.getDeviceId(), feiyuConfig.getAdvStr(), "2");
                if (!respAdv.getCode().equals(Resp.Status.SUCCESS.getCode())) {
                    return respAdv;
                }
            }
            feiYuConfigRepository.save(feiyuConfig);
            return Resp.success("", "绑定成功");
        } catch (Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "绑定失败");
        }

    }

    /**
     * 获取云喇叭配置信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value="删除配置", notes="删除配置")
    @DeleteMapping(value="/voice/{id}")
    public Resp<String> deleteConfig(@ApiParam(name="id", value="id") @PathVariable("id") String id) {
        try {
            FeiyuConfig feiyuConfig=feiYuConfigRepository.findOne(id);
            //先解绑 强制解绑
            Resp resp=YunlabaUtil.bind(feiyuConfig.getDeviceId(), "4", feiyuConfig.getStoreId());
            if (!resp.getCode().equals(Resp.Status.SUCCESS.getCode())) {
                return resp;
            }
            feiYuConfigRepository.delete(id);
            return Resp.success("", "解绑成功");
        } catch (Exception e) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR, "解绑失败");
        }

    }

    @ApiOperation(value="设置行业", notes="设置行业")
    @PostMapping(value="/category")
    public Resp category(@RequestBody UpdateCategoryDTO updateCategoryDTO) {
        Store store=storeService.findOne(updateCategoryDTO.getId());
        if (ParamUtil.isBlank(store)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "门店不存在");
        }
        store.setIndustryCategory(updateCategoryDTO.getIndustryCategory());

        return Resp.success(storeService.update(store));
    }


    @ApiOperation(value = "花呗分期设置", notes = "花呗分期设置")
    @PostMapping({"/huabei/set"})
    @ApiResponse(code = 200,message = "OK",response = HuaBeiConfig.class)
    public Resp<HuaBeiConfig> huabeiConfig(@RequestBody HuaBeiSettingDTO huaBeiSettingDTO) {
        return storeService.huabeiConfig(huaBeiSettingDTO);
    }

    @ApiOperation(value = "获取花呗分期设置", notes = "获取花呗分期设置")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "equipmentId",dataType = "String",value = "设备id")})
    @ApiResponse(code = 200,message = "OK",response = HuaBeiConfig.class)
    @GetMapping({"/huabei"})
    public Resp<HuaBeiConfig> getConfig1(String equipmentId) {
        return storeService.getConfig1(equipmentId);
    }
}
