package com.fzy.admin.fp.goods.controller;

import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.MemberRule;
import com.fzy.admin.fp.goods.service.MemberRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/merchant/member/rule")
@Api(value = "MemberRuleController",tags = "满减规则控制层")
public class MemberRuleController extends BaseController<MemberRule>{

        @Autowired
        private MemberRuleService memberRuleService;

        @Override
        public MemberRuleService getService() {
            return memberRuleService;
        }

        @ApiOperation(value = "获取分页", notes = "获取分页")
        @GetMapping({""})
        public Resp<Page<MemberRule>> getPage(PageVo pageVo, MemberRule memberRule) {
            String merchantId = TokenUtils.getMerchantId();
            memberRule.setMerchantId(merchantId);
            Pageable pageable = PageUtil.initPage(pageVo);
            Specification specification = ConditionUtil.createSpecification(memberRule);
            Page<MemberRule> page = this.memberRuleService.findAll(specification, pageable);
            return Resp.success(page);
        }

        @ApiOperation(value = "保存或更新", notes = "保存或更新")
        @PostMapping({""})
        public Resp saveRule(@RequestBody MemberRule entity) {
            String merchantId = TokenUtils.getMerchantId();
            entity.setMerchantId(merchantId);
            this.memberRuleService.save(entity);
            return Resp.success("操作成功");
        }

}
