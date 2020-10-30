package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.Rate;
import com.fzy.admin.fp.distribution.money.dto.RateDTO;
import com.fzy.admin.fp.distribution.money.service.RateService;
import com.fzy.assist.wraps.BeanWrap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-11-16 11:04:10
 **/
@RestController
@RequestMapping("/dist/rate")
@Api(value = "UserController", tags = {"分销-费率分润"})
public class RateController {
    @Resource
    private RateService rateService;

    /**
     * 查询费率设置
     * @return
     */
    @GetMapping(value = "/re_list")
    @ApiOperation(value = "分销设置", notes = "费率分润查询")
    public Resp re_list(PageVo pageVo, @TokenInfo(property = "serviceProviderId") String serviceProviderId) {
        Rate entity=new Rate();
        entity.setServiceProviderId(serviceProviderId);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Rate> page = rateService.findAll(specification, pageable);
        if(page.getContent()==null||page.getContent().size()==0){
            List<Rate> rateList=new ArrayList<>();
            Rate rate =new Rate();
            rate.setPushPrice(5);
            rate.setFirstFixedRate(2);
            rate.setSecondFixedRate(2);
            rate.setServiceProviderId(serviceProviderId);
            rate.setRemark("无");
            rate.setName("普通代理");
            rate.setType(1);
            rateList.add(rate);

            rate =new Rate();
            rate.setPushPrice(7);
            rate.setFirstFixedRate(2);
            rate.setSecondFixedRate(2);
            rate.setServiceProviderId(serviceProviderId);
            rate.setRemark("送一台蜻蜓设备");
            rate.setName("VIP代理");
            rate.setType(2);
            rateList.add(rate);

            rate =new Rate();
            rate.setPushPrice(9);
            rate.setFirstFixedRate(2);
            rate.setSecondFixedRate(2);
            rate.setServiceProviderId(serviceProviderId);
            rate.setRemark("送一台蜻蜓设备");
            rate.setName("合伙人");
            rate.setType(3);
            rateList.add(rate);
            rateService.getRepository().save(rateList);
            page = rateService.findAll(specification, pageable);
        }
        return Resp.success(page);
    }

    /**
     * 添加费率设置
     * @param serviceProviderId
     * @param rateDTO
     * @return
     */
    @PostMapping("/set")
    @ApiOperation(value = "分销设置", notes = "费率分润设置")
    public Resp set(@TokenInfo(property = "serviceProviderId")String serviceProviderId, RateDTO rateDTO){
        Rate rate = rateService.getRepository().findByIdAndServiceProviderId(rateDTO.getId(),serviceProviderId);
        if(rate==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请重新登录");
        }
        BeanWrap.copyProperties(rateDTO,rate);
        rateService.update(rate);
        return Resp.success("修改成功");
    }


}

