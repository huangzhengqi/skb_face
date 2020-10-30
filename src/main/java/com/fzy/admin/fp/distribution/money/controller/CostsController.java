package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.dto.CostsDTO;
import com.fzy.admin.fp.distribution.money.service.CostsService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@RestController
@RequestMapping("/dist/costs")
@Api(value = "UserController", tags = {"分销-代理商提成分配"})
public class CostsController extends BaseContent {

    @Resource
    private CostsService costsService;

    @Resource
    private DistUserService distUserService;

    /**
     * 查询费率设置
     * @return
     */
    @GetMapping(value = "/re_list")
    @ApiOperation(value = "分销设置", notes = "分销提成查询列表")
    public Resp re_list(CostsDTO entity, PageVo pageVo, @TokenInfo(property = "serviceProviderId") String serviceProviderId) {
        entity.setServiceProviderId(serviceProviderId);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Costs> page = costsService.findAll(specification, pageable);
        if(page.getContent()==null||page.getContent().size()==0){
            List<Costs> costsList=new ArrayList<>();
            Costs costs =new Costs();
            costs.setFirstCommissions(22);
            costs.setSecondCommissions(18);
            costs.setPrice(new BigDecimal("5800"));
            costs.setServiceProviderId(serviceProviderId);
            costs.setRemark("无");
            costs.setName("普通代理");
            costs.setType(1);
            costsList.add(costs);

            costs =new Costs();
            costs.setFirstCommissions(30);
            costs.setSecondCommissions(25);
            costs.setPrice(new BigDecimal("8800"));
            costs.setServiceProviderId(serviceProviderId);
            costs.setRemark("送一台蜻蜓设备");
            costs.setName("VIP代理");
            costs.setType(2);
            costsList.add(costs);

            costs =new Costs();
            costs.setFirstCommissions(35);
            costs.setSecondCommissions(30);
            costs.setPrice(new BigDecimal("12800"));
            costs.setServiceProviderId(serviceProviderId);
            costs.setRemark("送一台蜻蜓设备");
            costs.setName("合伙人");
            costs.setType(3);
            costsList.add(costs);


            costsService.getRepository().save(costsList);
            page = costsService.findAll(specification, pageable);
        }
        return Resp.success(page);
    }


    /**
     * 查询费率设置
     * @return
     */
    @GetMapping(value = "/find")
    @ApiOperation(value = "分销设置", notes = "分销提成查询")
    public Resp find(@UserId String userId) {
        DistUser one = distUserService.findOne(userId);
        Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(one.getGrade(), one.getServiceProviderId());
        return Resp.success(costs);
    }



    /**
     * 添加费率设置
     * @param serviceProviderId
     * @param costsDTO
     * @return
     */
    @PostMapping("/set")
    @ApiOperation(value = "分销设置", notes = "分销提成设置")
    public Resp set(@TokenInfo(property = "serviceProviderId")String serviceProviderId, CostsDTO costsDTO){
        Costs costs = costsService.getRepository().findByIdAndServiceProviderId(costsDTO.getId(),serviceProviderId);
        if(costs==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误，请重新登录");
        }
        BeanWrap.copyProperties(costsDTO,costs);
        costsService.update(costs);
        return Resp.success("修改成功");
    }





}
