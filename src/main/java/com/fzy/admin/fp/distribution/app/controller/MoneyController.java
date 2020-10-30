package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.app.vo.DistUserVO;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.domain.TakeInfo;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.dto.AccountDetailDTO;
import com.fzy.admin.fp.distribution.money.dto.TakeInfoDTO;
import com.fzy.admin.fp.distribution.money.service.AccountDetailService;
import com.fzy.admin.fp.distribution.money.service.CostsService;
import com.fzy.admin.fp.distribution.money.service.TakeInfoService;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author yy
 * @Date 2019-11-19 16:50:40
 * @Desp 我的提成
 **/

@RestController
@RequestMapping("/dist/app/commission")
@Api(value = "UserController", tags = {"分销-我的提成"})
public class MoneyController extends BaseContent {

    @Resource
    private TakeInfoService takeInfoService;

    @Resource
    private DistUserService distUserService;

    @Resource
    private CostsService costsService;

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private WalletService walletService;

    @Resource
    private AccountDetailService accountDetailService;

    @Resource
    private SystemSetupService systemSetupService;

    @GetMapping(value = "/re_list")
    @ApiOperation(value = "账户明细", notes = "账户明细")
    public Resp query(PageVo pageVo, AccountDetailDTO accountDetailDTO, @UserId String userId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<AccountDetail> specification = new Specification<AccountDetail>() {
            @Override
            public Predicate toPredicate(Root<AccountDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                //cb.接条件（大于、等于、小于等）
                predicates.add(cb.equal(root.get("userId"), userId));
                if (accountDetailDTO.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), accountDetailDTO.getStatus()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
        Page<AccountDetail> page = accountDetailService.getRepository().findAll(specification, pageable);
        Wallet wallet = walletService.getRepository().findByUserId(userId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("wallet", wallet);
        return Resp.success(result);
    }

    @PostMapping("/apply")
    @ApiOperation(value = "申请提现", notes = "申请提现")
    public Resp applyTake(@UserId String userId, @Valid TakeInfoDTO takeInfoDTO) {
        int length = (takeInfoDTO.getMoney() + "").length() - (takeInfoDTO.getMoney() + "").indexOf(".") - 1;
        if (length > 2) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "金额至多保留两位小数");
        }
        DistUser distUser = distUserService.findOne(userId);
        SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(distUser.getServiceProviderId());
        if (takeInfoDTO.getMoney() < systemSetup.getMinMoney().doubleValue()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "提现金额不得少于" + systemSetup.getMinMoney() + "元");
        }
        return Resp.success(takeInfoService.applyTake(userId, takeInfoDTO.getMoney(), takeInfoDTO.getAccount(), takeInfoDTO.getName(), takeInfoDTO.getType(), distUser.getServiceProviderId()));
    }

    @GetMapping("/query")
    @ApiOperation(value = "用户查询提现详情", notes = "用户查询提现详情")
    public Resp userQuery(@UserId String userId, PageVo pageVo) {
        TakeInfo takeInfo = new TakeInfo();
        takeInfo.setUserId(userId);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(takeInfo);
        Page<TakeInfo> page = this.takeInfoService.findAll(specification, pageable);
        return Resp.success(page);
    }

    @GetMapping("/commission")
    @ApiModelProperty(value = "提成统计", notes = "提成统计")
    public Resp commission(@UserId String userId) {
        Map result = new HashMap();
        DistUser distUser = distUserService.getRepository().findOne(userId);
        Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(distUser.getGrade(), distUser.getServiceProviderId());
        if (StringUtil.isEmpty(distUser.getInviteNum())) {
            return Resp.success(null);
        }
        String parentId = distUser.getParentId();
        String[] split = parentId.split("/");
        int length = split.length;

        String grade = distUser.getParentId() + distUser.getInviteNum() + "/";
        List<DistUser> distUserList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade + "%", distUser.getServiceProviderId());
        distUserList = distUserList.stream().filter(s -> (s.getParentId().split("/").length == length + 1 || s.getParentId().split("/").length == length + 2) && s.getGrade() != 0).collect(Collectors.toList());

        List<DistUserVO> distUserVOList = new ArrayList<>();
        for (DistUser data : distUserList) {
            Costs cost = costsService.getRepository().findByTypeAndServiceProviderId(data.getGrade(), data.getServiceProviderId());
            DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
            DistUserVO distUserVO = new DistUserVO();
            distUserVO.setName(data.getName());
            distUserVO.setPayTime(data.getPayTime());
            distUserVO.setGradeName(cost.getName());
            distUserVO.setFee(distOrder.getPrice().doubleValue());
            if (data.getParentId().split("/").length == length + 1) {
                distUserVO.setRank(1);
            } else {
                distUserVO.setRank(2);
            }
            distUserVOList.add(distUserVO);
        }
        result.put("costs", costs);
        result.put("distUserVOList", distUserVOList);
        return Resp.success(result);
    }
}
