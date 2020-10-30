package com.fzy.admin.fp.advertise.controller;

import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.advertise.domain.CostMoney;
import com.fzy.admin.fp.advertise.domain.OnManagement;
import com.fzy.admin.fp.advertise.dto.OnManagementDTO;
import com.fzy.admin.fp.advertise.dto.PCDTO;
import com.fzy.admin.fp.advertise.service.CostMoneyService;
import com.fzy.admin.fp.advertise.service.OnManagementService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/7/3 16:04
 * @Description
 */

@RestController
@RequestMapping(value = "/advertise/on_management")
public class OnManagementController extends BaseController<OnManagement> {

    @Resource
    private OnManagementService onManagementService;

    @Resource
    private CostMoneyService costMoneyService;


    @Override
    public BaseService<OnManagement> getService() {
        return onManagementService;
    }

    @PostMapping(value = "/save_re")
    public Resp saveOne(@Valid OnManagement onManagement) {
        onManagementService.saveOne(onManagement);
        return Resp.success("新建成功");
    }

    @GetMapping(value = "/find_one")
    public Resp finOne(String id) {
        return Resp.success(onManagementService.findOne(id));
    }

    @PostMapping(value = "/update_re")
    public Resp updateOne(OnManagement onManagement) {
        onManagementService.updateOne(onManagement);
        return Resp.success("修改成功");
    }

    @PostMapping(value = "/delete_re")
    public Resp deleteOne(String id) {
        onManagementService.deleteOne(id);
        return Resp.success("删除成功");
    }

    @PostMapping(value = "/run")
    public Resp runStatus(String id) {
        onManagementService.onUse(id);
        return Resp.success("投放成功");
    }

    @PostMapping(value = "/end")
    public Resp endStatus(String id) {
        onManagementService.endUse(id);
        return Resp.success("结束投放成功");
    }

    //统计点击展示花费
    @PostMapping(value = "/cost")
    @Transactional
    public Resp costMoney(String id, Integer status) {
        OnManagement onManagement = onManagementService.findOne(id);
        //1直接跳转 2展示 3点击
        if (status == null) {
            throw new BaseException("计费模式不能为空");
        } else if (onManagement == null) {
            throw new BaseException("没有找到该投放Id");
        }
        onManagementService.updateByCost(status, onManagement);
        costMoneyService.updateOneByDay(id, status);

        return Resp.success("花费更新成功");
    }


    //数据分析
    @GetMapping(value = "/analysis")
    public Resp analysis(String onId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date time1, @DateTimeFormat(pattern = "yyyy-MM-dd") Date time2) {
        OnManagement onManagement = onManagementService.findOne(onId);
        if (onManagement == null || time1 == null || time2 == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "缺少分析所需要的数据");
        }
        Map<String, Object> map = new HashMap<>();
        time1 = DateUtil.beginOfDay(time1);
        time2 = DateUtil.endOfDay(time2);
        List<CostMoney> costMoneyList = costMoneyService.findByOnIdAndCreateTime(onId, time1, time2);
        map.put("details", costMoneyList);
        OnManagement onManagement1 = new OnManagement();//返回数据
        if (onManagement.getAdvertiseType().equals(OnManagement.AdvertiseType.CPC.getCode())) {
            int showNumber = 0;
            int clickNumber = 0;
            for (CostMoney costMoney : costMoneyList) {
                showNumber += costMoney.getShowNumber();
                clickNumber += costMoney.getClickNumber();
            }
            double b = 0.00;
            if (showNumber != 0) {
                b = (double) clickNumber / showNumber;
            }
            onManagement1.setShowNumber(showNumber);
            onManagement1.setClickNumber(clickNumber);
            onManagement1.setClickRate(b);
            onManagement1.setClickCost(onManagement.getClickCost());
            onManagement1.setShowCost(onManagement.getShowCost());
            double showCost = 0;
            double clickCost = 0;
            int a = showNumber / 1000;
            int c = clickNumber;
            showCost = onManagement.getShowCost() * a;
            clickCost = onManagement.getClickCost() * c;
            onManagement1.setCostMoney(showCost + clickCost);
            onManagement1.setAdvertiseType(OnManagement.AdvertiseType.CPC.getCode());
        } else if (onManagement.getAdvertiseType().equals(OnManagement.AdvertiseType.CPM.getCode())) {
            int showNumber = 0;
            for (CostMoney costMoney : costMoneyList) {
                showNumber += costMoney.getShowNumber();
            }
            int a = showNumber / 1000;
            double b = onManagement.getShowCost();
            onManagement1.setCostMoney(a * b);
            onManagement1.setShowCost(onManagement.getShowCost());
            onManagement1.setShowNumber(onManagement.getShowNumber());
            onManagement1.setAdvertiseType(OnManagement.AdvertiseType.CPM.getCode());
        } else if (onManagement.getAdvertiseType().equals(OnManagement.AdvertiseType.CPV.getCode())) {
            int runNumber = 0;
            for (CostMoney costMoney : costMoneyList) {
                runNumber += costMoney.getRunNumber();
            }
            double a = onManagement.getRunCost();
            onManagement1.setRunCost(onManagement.getRunCost());
            onManagement1.setRunNumber(runNumber);
            onManagement1.setCostMoney(a * runNumber);
            onManagement1.setAdvertiseType(OnManagement.AdvertiseType.CPV.getCode());
        }
        map.put("look", onManagement1);
        return Resp.success(map);
    }


    //拿取符合当前条件的广告投放列表
    @GetMapping(value = "/select")
    public Resp getSelect(String merchantId, String storeId, String userId) {
        if (merchantId.isEmpty() && userId.isEmpty()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请必须传入一个参数");
        }
        if (merchantId.isEmpty()) {
            merchantId = onManagementService.exchangeId(userId);
        }
        //1支付后广告
        List<OnManagement> onManagements = onManagementService.getSelect(1, merchantId, storeId);
        List<PCDTO> pcdtos = onManagements.stream()
                .map(e -> new PCDTO(e.getId(), e.getAdvertiseManagement().getImageId()
                        , e.getAdvertiseManagement().getImageLink(), e.getAdvertiseType())).collect(Collectors.toList());
        return Resp.success(pcdtos);
    }

    @GetMapping(value = "/select_app")
    public Resp getSelectApp(String storeId, @TokenInfo(property = "merchantId") String merchantId) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请提供商户ID获取广告");
        }
        //2 app广告
        List<OnManagement> onManagements = onManagementService.getSelect(2, merchantId, storeId);
        List<OnManagementDTO> dtos = onManagements.stream()
                .map(e -> new OnManagementDTO(e.getId()
                        , e.getAdvertiseManagement().getImageId()
                        , e.getAdvertiseManagement().getImageLink()))
                .collect(Collectors.toList());
        return Resp.success(dtos);
    }


}
