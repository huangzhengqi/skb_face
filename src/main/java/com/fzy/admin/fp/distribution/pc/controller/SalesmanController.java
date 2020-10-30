package com.fzy.admin.fp.distribution.pc.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.dto.DistUserDTO;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.app.vo.DistUserVO;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.pc.dto.SalesmanDTO;
import com.fzy.admin.fp.distribution.pc.vo.TeamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
@RestController
@RequestMapping("/dist/salesman")
@Api(value = "SalesmanController", tags = {"分销-招商管理"})
public class SalesmanController extends BaseContent {
    @Resource
    private DistUserService distUserService;

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private WalletService walletService;

    @PostMapping("/add")
    @ApiOperation(value = "添加业务员", notes = "添加业务员")
    public Resp addDistUser(@TokenInfo(property = "serviceProviderId") String serviceProviderId, DistUserDTO distUserDTO) {
        if (StringUtil.isEmpty(distUserDTO.getUserName())) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "手机号不能为空");
        }
        DistUser distUser = distUserService.getRepository().findByUserName(distUserDTO.getUserName());
        if (distUser != null) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "手机号已存在");
        }
        distUser = new DistUser();
        //邀请码长度
        int inviteLen = 4;
        BeanUtils.copyProperties(distUserDTO, distUser);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        distUser.setServiceProviderId(serviceId);
        distUser.setUserName(distUserDTO.getUserName());
        distUser.setStatus(0);
        distUser.setGrade(4);
        distUser.setPassword(BCrypt.hashpw("123456"));
        distUser.setServiceProviderId(serviceId);
        distUser.setHeadImg(CommonConstant.HEAD_IMG);
        distUser.setParentId("");
        distUser.setSex(0);
        DistUser saveUser = distUserService.save(distUser);
        String id = saveUser.getId();
        String inviteNum = id.substring(id.length() - inviteLen, id.length());
        //不为空则代表邀请码已存在,则需要邀请码长度+1
        while (distUserService.getRepository().findByInviteNumAndServiceProviderId(inviteNum, serviceProviderId) != null) {
            ++inviteLen;
            inviteNum = id.substring(id.length() - inviteLen, id.length());
        }
        distUser.setInviteNum(inviteNum);
        distUserService.update(distUser);
        Wallet wallet = new Wallet();
        wallet.setUserId(distUser.getId());
        wallet.setServiceProviderId(serviceProviderId);
        wallet.setBalance(new BigDecimal("0"));
        wallet.setBonus(new BigDecimal("0"));
        wallet.setTake(new BigDecimal("0"));
        walletService.save(wallet);
        return Resp.success("新增成功默认密码为123456");
    }

    @GetMapping("/query")
    @ApiModelProperty(value = "查询招商列表", notes = "查询招商列表")
    public Resp query(@TokenInfo(property = "serviceProviderId") String serviceProviderId, PageVo pageVo, DistUserDTO distUserDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        DistUser model = new DistUser();
        model.setServiceProviderId(serviceProviderId);
        model.setName(distUserDTO.getName());
        model.setGrade(4);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<DistUser> page = distUserService.findAll(specification, pageable);
        Map<String, Object> result = new HashMap();
        List<DistUserVO> distUserVOList = new ArrayList<>();
        for (DistUser distUser : page.getContent()) {
            DistUserVO distUserVO = new DistUserVO();
            BeanUtil.copyProperties(distUser, distUserVO);
            List<DistUser> distUserList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(distUser.getInviteNum() + "/%", serviceProviderId);
            List<DistUser> firstList = distUserList.stream().filter(s -> (s.getParentId().split("/").length == 1) && s.getGrade() != 0).collect(Collectors.toList());
            List<DistUser> total = distUserList.stream().filter(s -> s.getGrade() != 0).collect(Collectors.toList());
            distUserVO.setInviteSize(distUserList.size());
            distUserVO.setFirstNum(firstList.size());
            distUserVO.setTeamSize(total.size());
            if (distUserDTO.getStartTime() != null) {
                distUserList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderIdAndCreateTimeBetween(distUserVO.getInviteNum() + "%", serviceProviderId, distUserDTO.getStartTime(), distUserDTO.getEndTime());
            }
            List<DistUser> collect = distUserList.stream().filter(s -> (s.getParentId().split("/").length <= 6) && s.getGrade() != 0).collect(Collectors.toList());
            distUserVO.setFee(0.0);
            //代理费
            for (DistUser data : collect) {
                DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
                if (distOrder != null) {
                    distUserVO.setFee(distOrder.getPrice().doubleValue() + distUserVO.getFee());
                }
            }
            distUserVOList.add(distUserVO);
        }
        List<DistOrder> orderList = distOrderService.getRepository().findAllByServiceProviderId(serviceProviderId);
        BigDecimal totalFee = orderList.stream().map(DistOrder::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        result.put("totalFee", totalFee);
        result.put("content", distUserVOList);
        result.put("size", page.getSize());
        result.put("totalElements", page.getTotalElements());
        result.put("totalPages", page.getTotalPages());
        return Resp.success(result);
    }

    @GetMapping("/detail")
    @ApiModelProperty(value = "查询招商详情", notes = "查询招商详情")
    public Resp detail(@TokenInfo(property = "serviceProviderId") String serviceProviderId, PageVo pageVo, SalesmanDTO salesmanDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        DistUser salesman = distUserService.getRepository().findByServiceProviderIdAndId(serviceProviderId, salesmanDTO.getId());
        if (salesman == null) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "参数错误，请重新登录");
        }
        Specification<DistUser> specification = new Specification<DistUser>() {
            @Override
            public Predicate toPredicate(Root<DistUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.and(cb.like(cb.lower(root.get("parentId")), salesman.getInviteNum() + "/%")));
                if (StringUtil.isNotEmpty(salesmanDTO.getStartTime())) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("payTime").as(String.class), salesmanDTO.getStartTime()));
                }
                if (StringUtil.isNotEmpty(salesmanDTO.getEndTime())) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("payTime").as(String.class), salesmanDTO.getEndTime()));
                }
                if (salesmanDTO.getType() != null) {
                    predicates.add(cb.equal(root.get("grade").as(Integer.class), salesmanDTO.getType()));
                }
                if (salesmanDTO.getRank() != null) {
                    predicates.add(cb.equal(root.get("level").as(Integer.class), salesmanDTO.getRank()));
                }
                Predicate[] ps = new Predicate[predicates.size()];
                Predicate predicateWhere = cb.and(predicates.toArray(ps));

                if (salesmanDTO.getName() != null) {
                    List<Predicate> listPermission = new ArrayList<>();
                    listPermission.add(cb.like(root.get("name"), salesmanDTO.getName() + "%"));
                    listPermission.add(cb.like(root.get("userName"), salesmanDTO.getName() + "%"));
                    Predicate[] predicatesPermissionArr = new Predicate[listPermission.size()];
                    Predicate predicatesPermission = cb.or(listPermission.toArray(predicatesPermissionArr));
                    return query.where(predicateWhere, predicatesPermission).getRestriction();
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<DistUser> all = distUserService.findAll(specification, pageable);

        List<DistUserVO> distUserVOList = new ArrayList<>();

        for (DistUser data : all.getContent()) {
            DistUserVO distUserVO = new DistUserVO();
            BeanUtil.copyProperties(data, distUserVO);
            String[] split = data.getParentId().split("/");
            distUserVO.setRank(split.length);
            String grade = data.getParentId() + data.getInviteNum() + "/";//用户自己的邀请码
            List<DistUser> userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade + "%", data.getServiceProviderId());
            distUserVO.setTeamSize(userList.size());
            distUserVOList.add(distUserVO);
        }
        Map<String, Object> result = new HashMap();
        result.put("content", distUserVOList);
        result.put("totalPages", all.getTotalPages());
        result.put("totalElements", all.getTotalElements());
        result.put("size", all.getSize());
        return Resp.success(result);
    }


    @GetMapping("/team")
    @ApiModelProperty(value = "团队概述", notes = "团队概述")
    public Resp team(@TokenInfo(property = "serviceProviderId") String serviceProviderId, PageVo pageVo, SalesmanDTO salesmanDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<DistUser> specification = new Specification<DistUser>() {
            @Override
            public Predicate toPredicate(Root<DistUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.equal(root.get("level"), 1));
                predicates.add(cb.lessThan(root.get("grade"), 4));
                predicates.add(cb.greaterThan(root.get("grade"), 0));
                if (StringUtil.isNotEmpty(salesmanDTO.getStartTime())) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("payTime").as(String.class), salesmanDTO.getStartTime()));
                }
                if (StringUtil.isNotEmpty(salesmanDTO.getEndTime())) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("payTime").as(String.class), salesmanDTO.getEndTime()));
                }
                if (StringUtil.isNotEmpty(salesmanDTO.getName())) {
                    predicates.add(cb.like(root.get("name"), salesmanDTO.getName() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<DistUser> all = distUserService.findAll(specification, pageable);
        List<TeamDTO> teamDTOList = new ArrayList<>();
        for (DistUser data : all.getContent()) {
            TeamDTO teamDTO = new TeamDTO();
            String parentInviteNum = data.getParentId().replace("/", "");
            DistUser distUser = distUserService.getRepository().findByInviteNumAndServiceProviderId(parentInviteNum, data.getServiceProviderId());
            teamDTO.setName(data.getName());
            teamDTO.setSalesman(distUser.getName());
            teamDTO.setPayTime(data.getPayTime());
            teamDTO.setHeadImg(data.getHeadImg());
            List<DistUser> distUserList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(data.getParentId() + data.getInviteNum() + "/%", data.getServiceProviderId());
            teamDTO.setTeamSize(distUserList.size());
            teamDTO.setSecondNum(distUserList.stream().filter(s -> s.getLevel() == 2 && s.getGrade() > 0).collect(Collectors.toList()).size());
            teamDTO.setThirdNum(distUserList.stream().filter(s -> s.getLevel() == 3 && s.getGrade() > 0).collect(Collectors.toList()).size());
            teamDTO.setFourthNum(distUserList.stream().filter(s -> s.getLevel() == 4 && s.getGrade() > 0).collect(Collectors.toList()).size());
            teamDTO.setFifthNum(distUserList.stream().filter(s -> s.getLevel() == 5 && s.getGrade() > 0).collect(Collectors.toList()).size());
            teamDTO.setSixthNum(distUserList.stream().filter(s -> s.getLevel() == 6 && s.getGrade() > 0).collect(Collectors.toList()).size());
            teamDTOList.add(teamDTO);
        }
        //查询团队总人数
        Specification<DistUser> specification1 = new Specification<DistUser>() {
            @Override
            public Predicate toPredicate(Root<DistUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.lessThanOrEqualTo(root.get("grade"), 3));
                predicates.add(cb.greaterThanOrEqualTo(root.get("grade"), 1));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Map<String, Object> map = new HashMap<>();
        map.put("content", teamDTOList);
        map.put("size", all.getSize());//团长人数
        map.put("totalElements", all.getTotalElements());
        map.put("totalPages", all.getTotalPages());
        map.put("totalSize", distUserService.findAll(specification1).size());//团队总人数
        return Resp.success(map);
    }

}
