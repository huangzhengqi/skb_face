package com.fzy.admin.fp.advertise.group.controller;

import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.advertise.group.domain.GroupMerchantCompany;
import com.fzy.admin.fp.advertise.group.dto.GroupDTO;
import com.fzy.admin.fp.advertise.group.repository.GroupMerchantRepository;
import com.fzy.admin.fp.advertise.group.service.GroupService;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 广告新建群组controller
 */
@Api(value="GroupController", tags={"广告新建群组"})
@RestController
@RequestMapping(value="/advertise/group")
public class GroupController extends BaseContent {

    @Resource
    private GroupService groupService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private GroupMerchantRepository groupMerchantRepository;

    @Resource
    private CityRepository cityRepository;

    /**
     * 新建群组
     */
    @ApiOperation(value="新建群组", notes="新建群组",consumes = "")
    @PostMapping("/add")
    public Resp addGroup(@UserId String userId, GroupDTO groupDTO) {

        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Group advertiseGroup=new Group();

//        //查询当前代理是否开通了广告权限
//        User user=userService.findOne(userId);
//        Company userCompany=companyRepository.findOne(user.getCompanyId());
//        if (!userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
//            //判断代理是否开通的广告权限
//            if (userCompany.getAdvertiseType() == null || userCompany.getAdvertiseType().equals(Integer.valueOf(0))) {
//                return new Resp().error(Resp.Status.PARAM_ERROR, "没有投放广告的权限");
//            }
//        }

        if(ParamUtil.isBlank(groupDTO.getGroupName())){
            return new Resp().error(Resp.Status.PARAM_ERROR,"群组名称为空，请填写！");
        }

        if(groupService.getRepository().countAllByGroupNameAndDelFlag(groupDTO.getGroupName(),1) != 0){
            return new Resp().error(Resp.Status.PARAM_ERROR,"群组名称已存在，请重新填写！");
        }

        //获取查询条件的商户
        List<Map> merchantList=merchantService.getMerchant(serviceId, groupDTO, advertiseGroup);
        if (merchantList.size() == 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前条件没有商户，请重新筛选");
        }

        advertiseGroup.setServiceProviderId(serviceId);
        advertiseGroup.setCompanyId(serviceId);
//        advertiseGroup.setCompanyId(groupDTO.getCompanyId());
        advertiseGroup.setGroupName(groupDTO.getGroupName());
        advertiseGroup.setMerchantNumber(merchantList.size());
        Group group=groupService.save(advertiseGroup);//保存群组

        List list=new ArrayList();
        List<String> merchantIdList=new ArrayList<>();

        for (Object object : merchantList) {
            Map obj=(Map) object;
            String merchantId=obj.get("id").toString();
            merchantIdList.add(merchantId);
            GroupMerchantCompany groupMerchantCompany=new GroupMerchantCompany();
            groupMerchantCompany.setServiceProviderId(serviceId);
            groupMerchantCompany.setGroupId(group.getId());
            groupMerchantCompany.setMerchantId(merchantId);
            groupMerchantCompany.setCompanyId(groupDTO.getCompanyId());
            list.add(groupMerchantCompany);
        }

        Integer deviceNumber=equipmentService.getRepository().countByMerchantIdIn(merchantIdList);
        group.setDeviceNumber(deviceNumber);
        Integer alideviceType=equipmentService.getDeviceType(merchantIdList, 1);//支付宝
        group.setAliDeviceNumber(alideviceType);
        Integer wxdeviceType=equipmentService.getDeviceType(merchantIdList, 2);//微信
        group.setWxDeviceNumber(wxdeviceType);
        groupService.update(group);//更改群组
        groupMerchantRepository.save(list);

        return Resp.success(advertiseGroup, "添加成功");
    }


    /**
     * 查询新建群组
     */
    @ApiOperation(value="查询群组", notes="查询群组")
    @GetMapping("/query")
    public Resp<Page<Group>>  query(@UserId String userId, PageVo pageVo, String groupName) {
        Pageable pageable=PageUtil.initPage(pageVo);
        final String serviceId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
//        User user=userService.findOne(userId);
        Page<Group> groupPage = null;
        if(ParamUtil.isBlank(groupName)){
             groupPage=groupService.getRepository().findAllByServiceProviderIdAndDelFlagOrderByCreateTimeDesc(serviceId, CommonConstant.NORMAL_FLAG, pageable);
        }else {
             groupPage=groupService.getRepository().findAllByServiceProviderIdAndDelFlagAndGroupNameLikeOrderByCreateTimeDesc(serviceId, CommonConstant.NORMAL_FLAG,"%" + groupName + "%", pageable);
        }
        for(Group group:groupPage.getContent()){
            if(null !=group.getRegionName()){
                group.setRegionName(group.getRegionName().replace("[","").replace("]",""));
            }
        }
        return  Resp.success(groupPage);
    }

    /**
     * 查看商户
     */
    @ApiOperation(value="查看商户", notes="查看商户")
    @GetMapping("/get_merchant")
    public Resp<Map<String, Object>> getMerchant(String groupId) {
        HashMap map=new HashMap();
        List<Merchant> list=new ArrayList<>();
        Group group=groupService.findOne(groupId);
        List<GroupMerchantCompany> groupMerchantCompanyList=groupMerchantRepository.findAllByGroupId(groupId);
        List<City> cityList= cityRepository.findAll();
        List<String> ids=new ArrayList<>();
        for (GroupMerchantCompany groupMerchantCompany : groupMerchantCompanyList) {
            ids.add(groupMerchantCompany.getMerchantId());
        }
        List<Merchant> merchants=merchantService.findAll(ids);
        for(Merchant merchant:merchants){
            for(City city:cityList){
                if(merchant.getCity().equals(city.getId())){
                    merchant.setCityName(city.getCityName());
                    break;
                }
            }
            list.add(merchant);
        }
        map.put("groupName", group.getGroupName());
        map.put("size", group.getMerchantNumber());
        map.put("list", list);
        return Resp.success(map);
    }

    /**
     * 删除群组
     */
    @ApiOperation(value="删除群组", notes="删除群组",consumes = "")
    @PostMapping("/del")
    public Resp addGroup(@UserId String userId, @RequestBody List<String> ids) {
       groupService.getRepository().updateDelFlag(ids);
        return Resp.success("删除成功");
    }

}
