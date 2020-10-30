package com.fzy.admin.fp.advertise.device.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice;
import com.fzy.admin.fp.advertise.device.dto.AdvertiseDeviceDTO;
import com.fzy.admin.fp.advertise.device.dto.AdvertiseDeviceListDTO;
import com.fzy.admin.fp.advertise.device.dto.AdvertiseDeviceViewListDTO;
import com.fzy.admin.fp.advertise.device.service.AdvertiseDeviceService;
import com.fzy.admin.fp.advertise.device.service.AdvertiseDeviceViewLogService;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceViewListVO;
import com.fzy.admin.fp.advertise.dto.AdvertiseIsableDTO;
import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.advertise.group.repository.GroupRepository;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value="AdvertiseDeviceController", tags={"设备广告后台管理接口"})
@RestController
@RequestMapping(value="/advertise/device")
public class AdvertiseDeviceController extends BaseContent {

    @Resource
    private AdvertiseDeviceService advertiseDeviceService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private AdvertiseDeviceViewLogService advertiseDeviceViewLogService;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private CityRepository cityRepository;

    /**
     * 新增投放计划
     *
     * @param advertiseDeviceDTO
     * @return
     */
    @ApiOperation(value="新增投放计划", notes="新增投放计划")
    @PostMapping(value="/add")
    public Resp<String> addDeviceAdvertise(@UserId String userId, AdvertiseDeviceDTO advertiseDeviceDTO) throws Exception {
        //查询当前代理是否开通了广告权限
        User user=userService.findOne(userId);
        Company userCompany=companyService.findOne(user.getCompanyId());
        if (!userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
            //判断代理是否开通的广告权限
            if (userCompany.getAdvertiseType() == null || userCompany.getAdvertiseType().equals(Integer.valueOf(0))) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "没有投放广告的权限");
            }
        }
        if (advertiseDeviceDTO.getName() == null || advertiseDeviceDTO.getName().equals("")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "计划名称不能为空");
        }
        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        //添加投放计划
        Resp resp=advertiseDeviceService.addDeviceAdvertise(serviceId, advertiseDeviceDTO);
        if (resp.getObj().equals(false)) {
            return Resp.success("当前时段商户的广告位已经超过了");
        }
        return Resp.success("添加成功");
    }


    /**
     * 预估
     */
    @ApiOperation(value="预估", notes="预估")
    @GetMapping(value="/estimate")
    public Resp<Integer> getEstimate(String gruopId) {
        return Resp.success(advertiseDeviceService.getEstimate(gruopId));
    }

    /**
     * 计划列表
     */
    @ApiOperation(value="计划列表", notes="计划列表")
    @GetMapping(value="/get_list")
    public Resp<Page<AdvertiseDevice>> getList(PageVo pageVo, AdvertiseDeviceListDTO advertiseDeviceListDTO, @UserId String userId) {
        Pageable pageable=PageUtil.initPage(pageVo);
        User user=userService.findOne(userId);
        Specification<AdvertiseDevice> specification=new Specification<AdvertiseDevice>() {
            @Override
            public Predicate toPredicate(Root<AdvertiseDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                //cb.接条件（大于、等于、小于等）
                //predicates.add(cb.equal(root.get("companyId"), user.getCompanyId()));
                predicates.add(cb.equal(root.get("serviceProviderId"), user.getServiceProviderId()));
                if (advertiseDeviceListDTO.getTitle() != null) {
                    predicates.add(cb.like(root.get("name"), "%" + advertiseDeviceListDTO.getTitle() + "%"));
                }
                if (advertiseDeviceListDTO.getStatus() != null && 0!=advertiseDeviceListDTO.getStatus()) {
                    predicates.add(cb.equal(root.get("status"), advertiseDeviceListDTO.getStatus()));
                }
                if (advertiseDeviceListDTO.getType() != null && 0!=advertiseDeviceListDTO.getType()) {
                    predicates.add(cb.equal(root.get("type"), advertiseDeviceListDTO.getType()));
                }
                predicates.add(cb.equal(root.get("delFlag"),"1"));
                Predicate[] pre=new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Page<AdvertiseDevice> all=advertiseDeviceService.getRepository().findAll(specification, pageable);
        final String serviceProviderId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        for (AdvertiseDevice advertiseDevice : all) {
            Integer clickNum=advertiseDeviceViewLogService.findClickNumByAdvId(advertiseDevice.getId());
            Integer exposureNum=advertiseDeviceViewLogService.findExposureNumByAdvId(advertiseDevice.getId());
            advertiseDevice.setClickNum(clickNum);
            advertiseDevice.setExposureNum(exposureNum);
            //预计投放查询商户 广告类型 1指定商户群 2全部商户 3指定商户
            if("1".equals(advertiseDevice.getTargetType().toString())){
                Integer merchantNum=advertiseDeviceService.getEstimate(advertiseDevice.getGroupId());
                advertiseDevice.setExpectedName(merchantNum.toString()+"商户");
            }else if("3".equals(advertiseDevice.getTargetType().toString())){
                    //指定商户
                    String [] ids=advertiseDevice.getMerchantIds().split(",");
                    List<Merchant> list=merchantService.getRepository().findByIdInAndServiceProviderId(ids,serviceProviderId);
                    advertiseDevice.setExpectedName(list.size()+"商户");
            }else {
                advertiseDevice.setExpectedName("全部商户");
            }
        }
        return Resp.success(all);
    }


    @ApiOperation(value="点击/曝光列表", notes="点击/曝光列表")
    @GetMapping(value="/view/list")
    public Resp<Page<AdvertiseDeviceViewListVO>> viewlist(AdvertiseDeviceViewListDTO advertiseDeviceViewListDTO) {
        PageVo pageVo=new PageVo();
        BeanUtil.copyProperties(advertiseDeviceViewListDTO, pageVo);
        Page<AdvertiseDeviceViewListVO> page=advertiseDeviceService.viewList(advertiseDeviceViewListDTO, pageVo);
        return Resp.success(page);
    }


    @ApiOperation(value="删除广告", notes="删除广告")
    @DeleteMapping(value="/delete")
    public Resp<String> delete(@UserId String userId,@RequestBody  List<String> ids) {
        List<AdvertiseDevice> list=advertiseDeviceService.findAll(ids);
        advertiseDeviceService.getRepository().updateDelFlag(ids);
        return Resp.success("删除成功");
    }

    @ApiOperation(value="暂停/恢复广告", notes="暂停/恢复广告")
    @PutMapping(value="/isable")
    public Resp<String> isable(@RequestBody AdvertiseIsableDTO advertiseIsableDTO) {
        List<AdvertiseDevice> list=advertiseDeviceService.findAll(advertiseIsableDTO.getIds());
        //当前时间
        Date currentdate=new Date();
        for (AdvertiseDevice e : list) {
            //默认广告
            if("2".equals(e.getType().toString())){
                if("0".equals(advertiseIsableDTO.getStatus().toString())){
                    e.setStatus(Integer.valueOf(5));
                    e.setAdvertiseStatus(Integer.valueOf(0));
                }else{
                    e.setStatus(Integer.valueOf(2));
                    e.setAdvertiseStatus(Integer.valueOf(0));
                }
            }else{
                //暂停
                if("0".equals(advertiseIsableDTO.getStatus().toString())){
                    e.setStatus(Integer.valueOf(5));
                    e.setAdvertiseStatus(Integer.valueOf(0));
                }else{
                    e.setAdvertiseStatus(Integer.valueOf(1));
                    //投放中
                    int beginTime=currentdate.compareTo(e.getBeginTime());
                    int endTime=currentdate.compareTo(e.getEndTime());
                    if(1==beginTime && endTime == -1){
                        e.setStatus(Integer.valueOf(2));
                    }
                    //已结束
                    if(1==endTime){
                        e.setStatus(Integer.valueOf(3));
                    }
                    //待投放 当前时间小于开始时间
                    if(-1==beginTime){
                        e.setStatus(Integer.valueOf(1));
                    }
                }
            }
            //判断时间是待投放，还是投放中
            advertiseDeviceService.save(e);
        }
        return Resp.success("修改成功！");
    }


    @ApiOperation(value="预计投放", notes="预计投放")
    @GetMapping("/expected_delivery")
    public Resp expectedDelivery(String advertiseId) {
        List<Merchant> merchants=advertiseDeviceService.expectedDelivery(advertiseId);
        return Resp.success(merchants);
    }

    @ApiOperation(value="选择群组", notes="选择群组")
    @GetMapping("/get_group_list")
    public Resp<List<Group>> getGroupList(@UserId String userId) {
        User user=userService.findOne(userId);
        if (ParamUtil.isBlank(user)) {
            return Resp.success("当前用户不存在");
        }
        return Resp.success(advertiseDeviceService.getGroupList(user));
    }


    @ApiOperation(value="指定商户", notes="指定商户")
    @GetMapping("/designation_merchant")
    public Resp<Page<Merchant>> designationMerchant(Merchant entity, PageVo pageVo, @TokenInfo(property="companyId") String companyId) {
        Page<Merchant> page=null;
        //获取服务商id
        final String serviceProviderId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        entity.setServiceProviderId(serviceProviderId);
        //判断当前登录用户对应角色信息  如果是服务商就查询全部商户 （包括分销）
        Company company=companyService.findOne(companyId);
        //服务商查询
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            page=merchantService.getPageService(entity, pageVo, companyId);
        } else {
            page=merchantService.getPage(entity, pageVo, companyId);
        }
        List<City> cityList= cityRepository.findAll();
        for(Merchant merchant:page.getContent()){
            for(City city:cityList){
                if(merchant.getCity().equals(city.getId())){
                    merchant.setCityName(city.getCityName());
                    break;
                }
            }
        }
        return Resp.success(page);
    }

    @ApiOperation(value="获取单个广告", notes="获取单个广告")
    @GetMapping("/get_advertise_one")
    public Resp<AdvertiseDevice> getAdvertiseOne(String id) {
        return Resp.success(advertiseDeviceService.findOne(id));
    }


    @ApiOperation(value="编辑根据ID查找", notes="编辑根据ID查找")
    @PutMapping("/get_advertise")
    public Resp<AdvertiseDevice> updateAdvertise(String id) {
        AdvertiseDevice advertiseDevice=advertiseDeviceService.findOne(id);
        //投放对象，广告类型 1指定商户群 2全部商户 3指定商户
        if("1".equals(advertiseDevice.getTargetType().toString())){
            Group group=  groupRepository.getOne(advertiseDevice.getGroupId());
            advertiseDevice.setGroupName(group.getGroupName());
        }
        if("3".equals(advertiseDevice.getTargetType().toString())){
            String [] ids=advertiseDevice.getMerchantIds().split(",");
            List<String> list = java.util.Arrays.asList(ids);
            List<Merchant> merchants= merchantService.findAll(list);
            advertiseDevice.setMerchants(merchants);
        }
        return Resp.success(advertiseDevice, "查找成功");
    }

    @ApiOperation(value="编辑广告", notes="编辑广告")
    @PutMapping("/update_advertise")
    public Resp<AdvertiseDevice> update(AdvertiseDevice model) {
        return Resp.success(advertiseDeviceService.update(model), "修改成功");
    }

}
