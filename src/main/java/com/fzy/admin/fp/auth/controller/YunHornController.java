package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.YunHorn;
import com.fzy.admin.fp.auth.service.YunHornService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:09 2019/6/4
 * @ Description: 云喇叭控制层
 **/
@Slf4j
@RestController
@RequestMapping("/auth/yun_horn")
public class YunHornController extends BaseController<YunHorn> {

    @Resource
    private YunHornService yunHornService;

    @Override
    public YunHornService getService() {
        return yunHornService;
    }

    @GetMapping("/get_info")
    public Resp listRewrite(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String companyId) {
        List<YunHorn> yunHorns = new ArrayList<>();
        YunHorn yunHorn = yunHornService.getRepository().findByServiceProviderId(companyId);
        //若云喇叭为空,则创建一个
        if (ParamUtil.isBlank(yunHorn)) {
            yunHorn = new YunHorn();
            yunHorn.setServiceProviderId(companyId);
            yunHorn.setHornSerial("");
            yunHorn.setHornToken("");
            yunHornService.save(yunHorn);
        }
        yunHorns.add(yunHorn);
        Map<String, Object> map = new HashMap<>();
        map.put("content", yunHorns);
        map.put("totalElements", 1);
        map.put("totalPages", 1);
        return Resp.success(map);
    }

}
