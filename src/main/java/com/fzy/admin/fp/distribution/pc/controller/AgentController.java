package com.fzy.admin.fp.distribution.pc.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.dto.DistUserDTO;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.app.vo.DistUserVO;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.dto.CostsDTO;
import com.fzy.admin.fp.distribution.money.service.CostsService;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.pc.dto.OperationDTO;
import com.fzy.admin.fp.distribution.pc.vo.OperationPage;
import com.fzy.admin.fp.distribution.pc.vo.OperationVO;
import com.fzy.admin.fp.distribution.utils.DistUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author yy
 * @Date 2019-11-20 09:17:35
 * @Desp 代理管理
 **/
@RestController
@RequestMapping("/dist/pc/agent")
@Api(value = "AgentController", tags = {"分销-代理管理"})
public class AgentController extends BaseContent {
    @Resource
    private DistUserService distUserService;

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private CostsService costsService;




    @GetMapping("/query")
    @ApiOperation(value = "代理管理", notes = "代理列表")
    public Resp<Page<DistUserVO>> find(@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo, DistUserDTO distUserDTO){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<DistUserVO> details =null;
        if(distUserDTO.getStartTime()!=null){
            details = distUserService.getRepository().getDetailsList(serviceProviderId, distUserDTO.getStartTime(),distUserDTO.getEndTime(),distUserDTO.getName(), distUserDTO.getStatus(), distUserDTO.getGrade(), pageable);
        }else{
            details = distUserService.getRepository().getDetailsList(serviceProviderId, distUserDTO.getName(), distUserDTO.getStatus(), distUserDTO.getGrade(), pageable);
        }
        for(DistUserVO distUserVO:details.getContent()){
            DistUser distUser = distUserService.getRepository().findOne(distUserVO.getId());
            DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(distUserVO.getId(), distUserVO.getGrade(), 1);
            //代理费
            if(distOrder != null&&distOrder.getPrice()!=null){
                distUserVO.setFee(distOrder.getPrice().doubleValue());
            }
            String parentId = distUser.getParentId();
            String[] split = parentId.split("/");
            int length = split.length;

            if(StringUtil.isNotEmpty(distUser.getInviteNum())){//查询自己的下级

                String grade = distUser.getParentId() + distUser.getInviteNum() + "/";//用户自己的邀请码
                // 该邀请码发展出来的所有下级
                List<DistUser> userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade+"%", distUser.getServiceProviderId());

                // 根据所有下级筛选出一级
                 List<DistUser> firstLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+1&&s.getGrade()!=0).collect(Collectors.toList());

                // 根据所有下级筛选出二级
                List<DistUser> secondLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+2&&s.getGrade()!=0).collect(Collectors.toList());

                //一级&二级的人数
                //
                distUserVO.setFirstNum(firstLevel.size());
                distUserVO.setSecondNum(secondLevel.size());
                distUserVO.setTeamTotalNum(userList.size());
            }

            if(StringUtil.isNotEmpty(split[length - 1])){
                DistUser parent = distUserService.getRepository().findByInviteNumAndServiceProviderId(split[length - 1], serviceProviderId);
                distUserVO.setInviteName(parent!=null?parent.getName():"");
            }

            //查询直邀人数
            Integer directCount = distUserService.getRepository().countByOneLevelId(distUserVO.getId());
            distUserVO.setDirectNum(directCount);

            //团队人数
            Integer teamNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderId(distUser.getInviteNum() + "/%", serviceProviderId);
            distUserVO.setTeamSize(teamNum);

        }
        return Resp.success(details);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public Resp detail(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id){
        DistUserVO distUserVO = distUserService.getRepository().getDetails(serviceProviderId,id);
        if(distUserVO==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登陆");
        }
        if(StringUtil.isNotEmpty(distUserVO.getParentId())){
            String parentInvite = DistUtil.getParentInvite(distUserVO.getParentId());
            //查询邀请人的信息
            DistUser parent = distUserService.getRepository().findByInviteNumAndServiceProviderId(parentInvite,serviceProviderId);
            distUserVO.setInviteName(parent.getName());
            distUserVO.setGrade(parent.getGrade());
        }
        //查询自己的下级人数
        if(StringUtil.isNotEmpty(distUserVO.getInviteNum())){
            String grade = distUserVO.getParentId() + distUserVO.getInviteNum() + "/%";//用户自己的邀请码
            Integer directCount = distUserService.getRepository().countByOneLevelId(id);
            Integer indirectCount= distUserService.getRepository().countByTwoLevelId(id);
            Integer teamSize = distUserService.getRepository().countByParentIdLikeAndServiceProviderId(grade,serviceProviderId);
            Integer agentNum = distUserService.getRepository().countByParentIdLikeAndGradeGreaterThanAndServiceProviderId(grade, 0,serviceProviderId);
            Integer directAgentNum = distUserService.getRepository().countByOneLevelIdAndGrade(id,Integer.valueOf(1));
            distUserVO.setDirectNum(directCount);
            distUserVO.setIndirectNum(indirectCount);
            distUserVO.setTeamSize(teamSize);
            distUserVO.setAgentNum(agentNum);
            distUserVO.setDirectAgentNum(directAgentNum);
        }
        return Resp.success(distUserVO);
    }

    /*@GetMapping("/detail")
    @ApiOperation(value = "代理管理", notes = "详情")
    public Resp detail(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id){
        DistUserVO distUserVO = distUserService.getRepository().getDetails(serviceProviderId, id);
        if(distUserVO==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登陆");
        }
        String parentInvite = DistUtil.getParentInvite(distUserVO.getParentId());
        //查询邀请人的信息
        DistUser parent = distUserService.getRepository().findByInviteNumAndServiceProviderId(parentInvite, serviceProviderId);
        distUserVO.setInviteName(parent.getName());
        distUserVO.setGrade(parent.getGrade());
        //查询代理费用
        DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(distUserVO.getId(), distUserVO.getGrade(), 1);
        if(distOrder!=null){
            distUserVO.setFee(distOrder.getPrice().doubleValue());
        }

        //查询自己的下级人数
        if(StringUtil.isNotEmpty(distUserVO.getInviteNum())){
            String parentId = distUserVO.getParentId();
            String[] split = parentId.split("/");
            int length = split.length;

            String grade = distUserVO.getParentId() + distUserVO.getInviteNum() + "/";//用户自己的邀请码
            List<DistUser> userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade+"%", serviceProviderId);

            //一级
            List<DistUser> firstLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+1&&s.getGrade()!=0).collect(Collectors.toList());

            //二级
            List<DistUser> secondLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+2&&s.getGrade()!=0).collect(Collectors.toList());

            //一级&二级的人数
            distUserVO.setFirstNum(firstLevel.size());
            distUserVO.setSecondNum(secondLevel.size());
        }

        return Resp.success(distUserVO);
    }*/

    /**
     * @param serviceProviderId
     * @param id
     * @return
     */
    @PostMapping("/set")
    @ApiOperation(value = "禁用/启动用户", notes = "禁用/启动用户")
    public Resp set(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id){
        DistUser distUser = distUserService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if(distUser==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登陆");
        }
        distUser.setStatus(distUser.getStatus()==0?1:0);
        distUserService.update(distUser);
        return Resp.success("设置成功");
    }

    @PostMapping("/add_operation")
    @ApiOperation(value = "添加运营中心账号", notes = "添加运营中心账号")
    public Resp addOperation (@TokenInfo(property="serviceProviderId")String serviceProviderId,@Valid OperationDTO operationDTO){
        String password="123456";
        DistUser distUser=new DistUser();
        distUser.setUserName(operationDTO.getPhone());
        distUser.setServiceProviderId(serviceProviderId);
        distUser.setParentId("");
        distUser.setLevel(0);
        distUser.setGrade(2);
        distUser.setCity("未知");
        distUser.setHeadImg(CommonConstant.HEAD_IMG);
        distUser.setPassword(BCrypt.hashpw(password));
        distUser.setSex(0);
        distUser.setStatus(0);
        distUser.setName(operationDTO.getName());
        distUserService.saveUser(distUser);
        return Resp.success("新增成功");
    }

    @GetMapping("/query_operation")
    @ApiOperation(value = "查询运营中心账号", notes = "查询运营中心账号")
    public Resp<OperationPage> queryOperation (@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<DistUser> distUserPage = distUserService.getRepository().findAllByServiceProviderIdAndGrade(serviceProviderId, 2, pageable);
        List<OperationVO> operationVOList=new ArrayList<>();
        for(DistUser distUser:distUserPage.getContent()){
            OperationVO operationVO=new OperationVO();
            BeanUtil.copyProperties(distUser,operationVO);
            Integer teamNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderId(distUser.getInviteNum() + "/%", serviceProviderId);
            operationVO.setNum(teamNum);
            operationVO.setPhone(distUser.getUserName());
            operationVOList.add(operationVO);
        }
        OperationPage operationPage=new OperationPage();
        operationPage.setTotalElements(distUserPage.getTotalElements());
        operationPage.setTotalPages(distUserPage.getTotalPages());
        operationPage.setOperationVOList(operationVOList);
        return Resp.success(operationPage);
    }


    @GetMapping("/performance")
    @ApiOperation(value = "团队业绩", notes = "团队业绩")
    public Resp performance(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id,DistUserDTO distUserDTO,Integer type, PageVo pageVo){
        DistUser distUser = distUserService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        PageVo pageVo1=new PageVo();
        pageVo1.setPageSize(3);
        pageVo1.setPageNumber(1);
        CostsDTO entity=new CostsDTO();
        entity.setServiceProviderId(serviceProviderId);
        Pageable pageable1 = PageUtil.initPage(pageVo1);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Costs> page = costsService.findAll(specification, pageable1);
        int firstSize=0;
        int secondSize=0;
        BigDecimal firstFee=new BigDecimal("0");
        BigDecimal secondFee=new BigDecimal("0");
        Map<String,Object> map=new HashMap();
        Costs first=new Costs();
        Costs second=new Costs();
        Costs third=new Costs();
        for(Costs costs:page.getContent()){
            if(distUser.getGrade()==costs.getType()){
                map.put("firstRate",costs.getFirstCommissions());//一级提成比例
                map.put("secondRate",costs.getSecondCommissions());//二级提成比例
            }
            switch(costs.getType()){
                case 1:
                    first=costs;
                    break;
                case 2:
                    second=costs;
                    break;
                case 3:
                    third=costs;
                    break;
            }
        }
        //List<DistUserVO> firstLevelVo=new ArrayList<>();

        //List<DistUserVO> secondLevelVo=new ArrayList<>();

        List<DistUserVO> levelVo=new ArrayList<>();
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<DistUser> distUserPage =null;

        //查询自己的下级人数
        if(StringUtil.isNotEmpty(distUser.getInviteNum())){
            String parentId = distUser.getParentId();
            String[] split = parentId.split("/");
            int length = split.length;

            String grade = distUser.getParentId() + distUser.getInviteNum() + "/%";//用户自己的邀请码
            List<DistUser> allDistUser = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade , serviceProviderId);
            List<DistUser> collect = allDistUser.stream().filter(s -> (s.getParentId().split("/").length == length + 1) && s.getGrade() != 0).collect(Collectors.toList());
            for(DistUser data:collect){
                DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
                if(distOrder!=null){
                    firstFee=firstFee.add(distOrder.getPrice());
                }
            }
            firstSize=collect.size();


            collect = allDistUser.stream().filter(s -> (s.getParentId().split("/").length == length + 2) && s.getGrade() != 0).collect(Collectors.toList());
            for(DistUser data:collect){
                DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
                if(distOrder!=null){
                    secondFee=secondFee.add(distOrder.getPrice());
                }
            }
            secondSize=collect.size();

            if(distUserDTO.getStartTime()!=null){
                distUserPage = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderIdAndCreateTimeBetween(grade+"%", serviceProviderId,distUserDTO.getStartTime(),distUserDTO.getEndTime(),pageable);
            }else{
                distUserPage = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade+"%", serviceProviderId,pageable);
            }
            List<DistUser> userList=distUserPage.getContent();

            if(userList!=null){
                if(type==null){
                    userList = userList.stream().filter(s->(s.getParentId().split("/").length==length+1||s.getParentId().split("/").length==length+2)&&s.getGrade()!=0).collect(Collectors.toList());
                }else if(type==1){
                    userList = userList.stream().filter(s->(s.getParentId().split("/").length==length+1)&&s.getGrade()!=0).collect(Collectors.toList());
                }else if(type==2){
                    userList = userList.stream().filter(s->(s.getParentId().split("/").length==length+2)&&s.getGrade()!=0).collect(Collectors.toList());
                }

                for(DistUser data:userList){
                    DistUserVO distUserVO=new DistUserVO();
                    BeanUtil.copyProperties(data, distUserVO);
                    Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(data.getGrade(), data.getServiceProviderId());
                    DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
                    distUserVO.setGradeName(costs.getName());
                    if(distOrder!=null){
                        distUserVO.setFee(distOrder.getPrice().doubleValue());
                    }else{
                        distUserVO.setFee(0.0);
                    }
                    if(data.getParentId().split("/").length==length+1){
                        distUserVO.setRank(1);
                    }else if(data.getParentId().split("/").length==length+2){
                        distUserVO.setRank(2);
                    }
                    switch(data.getGrade()){
                        case 1:
                            if(distUserVO.getRank()==1){
                                distUserVO.setRate(first.getFirstCommissions());
                            }else{
                                distUserVO.setRate(first.getSecondCommissions());
                            }
                        case 2:
                            if(distUserVO.getRank()==1){
                                distUserVO.setRate(second.getFirstCommissions());
                            }else{
                                distUserVO.setRate(second.getSecondCommissions());
                            }
                        case 3:
                            if(distUserVO.getRank()==1){
                                distUserVO.setRate(third.getFirstCommissions());
                            }else{
                                distUserVO.setRate(third.getSecondCommissions());
                            }
                    }
                    levelVo.add(distUserVO);
                }

                //firstFee = levelVo.stream().filter(s->s.getParentId().split("/").length==length+1&&s.getGrade()!=0).collect(Collectors.summingDouble(DistUserVO::getFee));

                //secondFee = levelVo.stream().filter(s->s.getParentId().split("/").length==length+2&&s.getGrade()!=0).collect(Collectors.summingDouble(DistUserVO::getFee));

                /*//一级
                List<DistUser> firstLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+1&&s.getGrade()!=0).collect(Collectors.toList());

                BeanUtil.copyProperties(firstLevel,firstLevelVo);
                firstLevelVo=setInfo(firstLevelVo,first,second,third,1);
                firstFee = firstLevelVo.stream().collect(Collectors.summingDouble(DistUserVO::getFee));

                //二级
                List<DistUser> secondLevel = userList.stream().filter(s->s.getParentId().split("/").length==length+2&&s.getGrade()!=0).collect(Collectors.toList());
                BeanUtil.copyProperties(secondLevel,secondLevelVo);
                secondLevelVo=setInfo(secondLevelVo,first,second,third,2);
                secondFee = firstLevelVo.stream().collect(Collectors.summingDouble(DistUserVO::getFee));

                firstSize=firstLevelVo.size();
                secondSize=secondLevelVo.size();*/
            }
        }
        map.put("firstSize",firstSize);//一级代理总数
        map.put("secondSize",secondSize);//二级代理总数
        //map.put("firstLevelVo",firstLevelVo);//一级代理费用
        //map.put("secondLevelVo",secondLevelVo);//二级代理费用

        if(distUserPage!=null){
            map.put("totalElements",distUserPage.getTotalElements());
            map.put("totalPages",distUserPage.getTotalPages());
            map.put("size",distUserPage.getSize());
        }else {
            map.put("totalElements",0);
            map.put("totalPages",0);
            map.put("size",0);
        }

        map.put("levelVo",levelVo);//代理列表
        map.put("firstFee",firstFee);//一级代理费用
        map.put("secondFee",secondFee);//二级代理费用
        return Resp.success(map);
    }



    /**
     * 得到代理里面的信息
     * @param list
     * @param first
     * @param second
     * @param third
     * @param grade
     * @return
     */
    private List<DistUserVO> setInfo(List<DistUserVO> list,Costs first,Costs second,Costs third,Integer grade){
        for(DistUserVO data:list){
            DistUser one = distUserService.findOne(data.getId());
            Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(one.getGrade(), one.getServiceProviderId());
            DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
            data.setGradeName(costs.getName());
            data.setFee(distOrder.getPrice().doubleValue());
            switch(data.getGrade()){
                case 1:
                    if(grade==1){
                        data.setRate(first.getFirstCommissions());
                    }else{
                        data.setRate(first.getSecondCommissions());
                    }
                case 2:
                    if(grade==1){
                        data.setRate(second.getFirstCommissions());
                    }else{
                        data.setRate(second.getSecondCommissions());
                    }
                case 3:
                    if(grade==1){
                        data.setRate(third.getFirstCommissions());
                    }else{
                        data.setRate(third.getSecondCommissions());
                    }
            }
        }
        return list;
    }
}
