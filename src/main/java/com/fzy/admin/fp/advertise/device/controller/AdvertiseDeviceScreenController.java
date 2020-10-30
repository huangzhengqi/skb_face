package com.fzy.admin.fp.advertise.device.controller;

import com.fzy.admin.fp.advertise.device.domian.*;
import com.fzy.admin.fp.advertise.device.service.*;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="AdvertiseDeviceScreenController", tags={"查询设备首页屏幕广告"})
@RestController
@RequestMapping(value="/advertise/device/screen")
public class AdvertiseDeviceScreenController extends BaseContent {

    @Resource
    private AdvertiseDeviceHomeService advertiseDeviceHomeService;

    @Resource
    private AdvertiseDeviceMidService advertiseDeviceMidService;

    @Resource
    private AdvertiseDeviceSuccessService advertiseDeviceSuccessService;

    @Resource
    private AdvertiseDeviceViewLogService advertiseDeviceViewLogService;

    @Resource
    private AdvertiseDeviceService advertiseDeviceService;

    /**
     * 查询当前商户设备屏幕广告广告
     */

    @ApiOperation(value="查询设备商户屏幕广告", notes="查询设备商户屏幕广告")
    @GetMapping("/query")
    public Resp<Map<String,Object>> queryDeviceAdvertise(String merchantId, Integer type) {

        Map map=new HashMap();
        List list=new ArrayList();
        //查询首屏广告
        if (type.equals(Integer.valueOf(0))) {
            List<AdvertiseDeviceHome> deviceAdvertiseList=advertiseDeviceHomeService.getRepository().findAllByMerchantId(merchantId);

            for (AdvertiseDeviceHome advertiseDeviceHome : deviceAdvertiseList) {
                AdvertiseDevice deviceAdvertise=advertiseDeviceService.getRepository().findByIdAndStatus(advertiseDeviceHome.getAdvertiseDeviceId(), 1);
                list.add(deviceAdvertise);

                //记录广告投放曝光
                AdvertiseDeviceViewLog advertiseDeviceViewLog=new AdvertiseDeviceViewLog();
                advertiseDeviceViewLog.setAdvertiseDeviceId(deviceAdvertise.getId());
                advertiseDeviceViewLog.setTargetRange(deviceAdvertise.getTargetRange());
                advertiseDeviceViewLog.setMerchantId(advertiseDeviceHome.getMerchantId());
                advertiseDeviceViewLog.setStatus(Integer.valueOf(1));
                this.advertiseDeviceViewLogService.save(advertiseDeviceViewLog);
            }

            //如果商户没有广告则使用默认的
            if (list == null || list.size() == 0) {

            }

            map.put("list", list);
            //查询支付页广告
        } else if (type.equals(Integer.valueOf(1))) {
            List<AdvertiseDeviceMid> advertiseDeviceMids=advertiseDeviceMidService.getRepository().findAllByMerchantId(merchantId);

            for (AdvertiseDeviceMid advertiseDeviceMid : advertiseDeviceMids) {
                AdvertiseDevice deviceAdvertise=advertiseDeviceService.getRepository().findByIdAndStatus(advertiseDeviceMid.getAdvertiseDeviceId(), 1);
                list.add(deviceAdvertise);

                //记录广告投放曝光
                AdvertiseDeviceViewLog advertiseDeviceViewLog=new AdvertiseDeviceViewLog();
                advertiseDeviceViewLog.setAdvertiseDeviceId(deviceAdvertise.getId());
                advertiseDeviceViewLog.setTargetRange(deviceAdvertise.getTargetRange());
                advertiseDeviceViewLog.setMerchantId(advertiseDeviceMid.getMerchantId());
                advertiseDeviceViewLog.setStatus(Integer.valueOf(1));
                this.advertiseDeviceViewLogService.save(advertiseDeviceViewLog);
            }

            map.put("list", list);
        } else {
            List<AdvertiseDeviceSuccess> advertiseSuccessList=advertiseDeviceSuccessService.getRepository().findAllByMerchantId(merchantId);

            for (AdvertiseDeviceSuccess advertiseDeviceSuccess : advertiseSuccessList) {
                AdvertiseDevice deviceAdvertise=advertiseDeviceService.getRepository().findByIdAndStatus(advertiseDeviceSuccess.getAdvertiseDeviceId(), 1);
                list.add(deviceAdvertise);

                //记录广告投放曝光
                AdvertiseDeviceViewLog advertiseDeviceViewLog=new AdvertiseDeviceViewLog();
                advertiseDeviceViewLog.setAdvertiseDeviceId(deviceAdvertise.getId());
                advertiseDeviceViewLog.setTargetRange(deviceAdvertise.getTargetRange());
                advertiseDeviceViewLog.setMerchantId(advertiseDeviceSuccess.getMerchantId());
                advertiseDeviceViewLog.setStatus(Integer.valueOf(1));
                this.advertiseDeviceViewLogService.save(advertiseDeviceViewLog);
            }

            map.put("list", advertiseSuccessList);
        }
        return Resp.success(map);
    }
}
