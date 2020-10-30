package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistAdvertise;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.distribution.app.dto.AppAdvertiseDTO;
import com.fzy.admin.fp.distribution.app.service.AppDistAdvertiseService;
import com.fzy.admin.fp.distribution.app.utils.DateUtil;
import com.fzy.admin.fp.distribution.app.vo.WindowStateVo;
import com.fzy.admin.fp.distribution.article.vo.ArticlePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/dist/app/advertise")
@Api(value="AppDistAdvertiseController", tags="分销app广告")
public class AppDistAdvertiseController extends BaseContent {

    @Resource
    private AppDistAdvertiseService appDistAdvertiseService;


    @ApiOperation(value="新增分销广告", notes="新增分销广告")
    @PostMapping("/save")
    @Transactional(rollbackOn=RuntimeException.class)
    public Resp save(@RequestBody AppAdvertiseDTO appAdvertiseDTO, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {

        try {

            //当前启动页时间是否存在广告
            if (appAdvertiseDTO.getTargetRange().equals(Integer.valueOf(0))) {
                DistAdvertise distAdvertise1=appDistAdvertiseService.getRepository().findByGetDistAdvertise(serviceProviderId, appAdvertiseDTO.getTargetRange(), appAdvertiseDTO.getBeginTime(), appAdvertiseDTO.getEndTime());
                if (!ParamUtil.isBlank(distAdvertise1)) {
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                    String beginTime=formatter.format(distAdvertise1.getBeginTime());
                    String endTime=formatter.format(distAdvertise1.getEndTime());
                    return new Resp().error(Resp.Status.PARAM_ERROR, beginTime + "-" + endTime + " 该时间段内存在广告,请重新选择时间");
                }
            }
            DistAdvertise distAdvertise=new DistAdvertise();
            BeanUtil.copyProperties(appAdvertiseDTO, distAdvertise);
            distAdvertise.setServiceProviderId(serviceProviderId);
            distAdvertise.setStatus(Integer.valueOf(1));
            distAdvertise.setCreateTime(new Date());

            long nowTime=new Date().getTime();//当前时间
            long beginTime=distAdvertise.getBeginTime().getTime();//投放时间
            long endTime=distAdvertise.getEndTime().getTime();//结束时间
            int type=DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            distAdvertise.setType(Integer.valueOf(type));

            Date endTimeDate=DateUtil.getEndOfDay(distAdvertise.getEndTime());
            distAdvertise.setEndTime(endTimeDate);

            //查询广告表有多少条数据
            int count=appDistAdvertiseService.findCountAdvertise(serviceProviderId, appAdvertiseDTO.getTargetRange());
            distAdvertise.setWeight(count + 1);

            appDistAdvertiseService.save(distAdvertise);
            return Resp.success("添加banner成功");

        } catch (Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "添加失败");
        }
    }

    @ApiOperation(value="编辑分销广告", notes="编辑分销广告")
    @PutMapping("/update")
    @Transactional(rollbackOn=RuntimeException.class)
    public Resp update(@RequestBody AppAdvertiseDTO appAdvertiseDTO, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {

        try {

            DistAdvertise distAdvertise=appDistAdvertiseService.findOne(appAdvertiseDTO.getId());
            //当前启动页时间是否存在广告
            if (appAdvertiseDTO.getTargetRange().equals(Integer.valueOf(0))) {
                if (DateUtils.isSameDay(appAdvertiseDTO.getBeginTime(), distAdvertise.getBeginTime()) == false || DateUtils.isSameDay(appAdvertiseDTO.getEndTime(), distAdvertise.getEndTime()) == false) {
                    DistAdvertise distAdvertise1=appDistAdvertiseService.getRepository().findByGetDistAdvertise(serviceProviderId, appAdvertiseDTO.getTargetRange(), appAdvertiseDTO.getBeginTime(), appAdvertiseDTO.getEndTime());
                    if (!ParamUtil.isBlank(distAdvertise1)) {
                        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
                        String beginTime=formatter1.format(distAdvertise1.getBeginTime());
                        String endTime=formatter1.format(distAdvertise1.getEndTime());
                        return new Resp().error(Resp.Status.PARAM_ERROR, beginTime + "-" + endTime + " 该时间段内存在广告,请重新选择时间");
                    }
                }
            }

            if (!distAdvertise.getWeight().equals(appAdvertiseDTO.getWeight())) {
                if (!ParamUtil.isBlank(appAdvertiseDTO.getWeight())) {
                    //查询广告表有多少条数据
                    if (appDistAdvertiseService.getRepository().countByServiceProviderIdAndWeightAndTargetRange(serviceProviderId, appAdvertiseDTO.getWeight(), appAdvertiseDTO.getTargetRange()) != 0) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "该排序数值已存在，不可重复！");
                    }
                }
            }
            BeanUtil.copyProperties(appAdvertiseDTO, distAdvertise);

            long nowTime=new Date().getTime();//当前时间
            long beginTime=distAdvertise.getBeginTime().getTime();//投放时间
            long endTime=distAdvertise.getEndTime().getTime();//结束时间
            int type=DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            distAdvertise.setType(Integer.valueOf(type));

            Date endTimeDate=DateUtil.getEndOfDay(distAdvertise.getEndTime());
            distAdvertise.setEndTime(endTimeDate);
            appDistAdvertiseService.update(distAdvertise);
            return Resp.success("修改banner成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Resp().error(Resp.Status.PARAM_ERROR, "修改失败");
        }
    }

    /**
     * 删除分销app广告
     */
    @ApiOperation(value="删除分销广告", notes="删除分销广告")
    @DeleteMapping("/delete")
    public Resp delete(String id) {

        DistAdvertise distAdvertise=appDistAdvertiseService.findOne(id);
        if (ParamUtil.isBlank(distAdvertise)) {
            Resp.success("广告表不存在");
        }

        appDistAdvertiseService.delete(distAdvertise);
        return Resp.success("删除成功");
    }

    /**
     * 分销广告列表
     */
    @ApiOperation(value="分销广告列表", notes="分销广告列表")
    @GetMapping("/list")
    public Resp list(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, PageVo pageVo) throws Exception {

        Pageable pageable=PageUtil.initPage(pageVo);
        Specification<DistAdvertise> specification=new Specification<DistAdvertise>() {
            @Override
            public Predicate toPredicate(Root<DistAdvertise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                Predicate[] pre=new Predicate[predicates.size()];
                Predicate Pre_And=cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };

        Map map=new HashMap();
        ArticlePageVO articlePageVO=new ArticlePageVO();
        Page<DistAdvertise> all=appDistAdvertiseService.findAll(specification, pageable);
        articlePageVO.setTotalElements(all.getTotalElements());
        articlePageVO.setTotalPages(all.getTotalPages());
        map.put("totalElements", articlePageVO.getTotalElements());
        map.put("totalPages", articlePageVO.getTotalPages());

        List<DistAdvertise> content=all.getContent();
        if (content.size() <= 0 || content == null) {
            map.put("all", content);
            return Resp.success(map);
        }

        List list=new ArrayList();
        for (DistAdvertise distAdvertise : content) {

            long beginTime=distAdvertise.getBeginTime().getTime();//投放开始时间
            long endTime=distAdvertise.getEndTime().getTime();//投放结束时间
            long nowTime=new Date().getTime();//当前时间
            int type=DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            if (Integer.valueOf(type) != distAdvertise.getType()) {
                distAdvertise.setType(type);
                appDistAdvertiseService.update(distAdvertise);//修改状态
            }
            list.add(distAdvertise);
        }
        //按照优先级排序
        list.sort(Comparator.comparing(DistAdvertise::getWeight).reversed());
        map.put("all", list);

        return Resp.success(map);
    }


    /**
     * 首页投放广告
     */
    @ApiOperation(value="首页投放广告", notes="首页投放广告")
    @GetMapping("/home/advertise")
    public Resp homeAdvertise(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId, Integer targetRange) {

        PageVo pageVo=new PageVo();
        pageVo.setPageNumber(0);
        pageVo.setPageSize(4);
        pageVo.setPageSort("weight");
        pageVo.setPageOrder("desc");
        Pageable pageable=PageUtil.initPage(pageVo);

        Specification<DistAdvertise> specification=new Specification<DistAdvertise>() {
            @Override
            public Predicate toPredicate(Root<DistAdvertise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.equal(root.get("status"), DistAdvertise.Status.DISABLE.getCode()));
                predicates.add(cb.equal(root.get("type"), DistAdvertise.Type.INLAUNCH.getCode()));
                predicates.add(cb.equal(root.get("targetRange"), targetRange));
                Predicate[] pre=new Predicate[predicates.size()];
                Predicate Pre_And=cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<DistAdvertise> all=appDistAdvertiseService.findAll(specification, pageable);

        List<DistAdvertise> content=all.getContent();
        if (content.size() <= 0 || content == null) {
            return Resp.success(content);
        }
        return Resp.success(content);
    }


    /**
     * 修改状态
     */
    @PutMapping("/update_status")
    @ApiOperation(value="修改状态", notes="修改状态")
    public Resp updateStatus(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid WindowStateVo windowStateVo) {

        DistAdvertise distAdvertise=appDistAdvertiseService.getRepository().findByServiceProviderIdAndId(serviceProviderId, windowStateVo.getId());
        if (distAdvertise == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        distAdvertise.setStatus(windowStateVo.getStatus());
        if (windowStateVo.getStatus().equals(DistWindow.Status.ENABLED)) {
            distAdvertise.setType(0);
        } else {
            distAdvertise.setType(1);
        }
        return Resp.success(appDistAdvertiseService.update(distAdvertise), "修改成功");
    }
}
