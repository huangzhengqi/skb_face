package com.fzy.admin.fp.member.sem.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.jpa.RelationUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import com.fzy.admin.fp.member.sem.service.MemberAliLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Slf4j
@Api(value = "MemberAliLevelController",tags = {"支付宝会员等级"})
@RestController
@RequestMapping("/member/sem/memberLevel")
public class MemberAliLevelController {


    @PersistenceContext
    private EntityManager em;

    @Resource
    private MemberAliLevelService memberAliLevelService;

    @ApiOperation(value="会员等级列表",notes = "会员等级列表")
    @GetMapping("/list")
    public Resp findList(@TokenInfo(property = "merchantId") String merchantId,PageVo pageVo){
        if(null == merchantId) {
            return  new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        MemberAliLevel memberAliLevel = new MemberAliLevel();
        memberAliLevel.setMerchantId(merchantId);
        memberAliLevel.setDelFlag(CommonConstant.NORMAL_FLAG);
        return Resp.success(memberAliLevelService.list(memberAliLevel,pageVo));
    }

    @ApiOperation(value="会员等级保存或更新")
    @PostMapping("/save")
    public Resp saveOrUpdate(@RequestBody MemberAliLevel aliLevel,@TokenInfo(property = "merchantId") String merchantId) {
        if(null == merchantId) {
            return  new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        aliLevel.setMerchantId(merchantId);
        return  memberAliLevelService.saveOrUpdate(aliLevel);
    }


    @ApiOperation(value = "会员等级删除")
    @PostMapping("/delete")
    public Resp delete(String[] ids){
        List<MemberAliLevel> list = memberAliLevelService.findAll(ids);
        for (MemberAliLevel level : list) {
            if (level == null) {
                continue;
            }
            RelationUtil.delRelation(level, em, true);
            level.setDelFlag(CommonConstant.DEL_FLAG);
        }
        memberAliLevelService.save(list);
        return Resp.success("删除成功");
    }
}
