package com.fzy.admin.fp.member.member.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.RelationUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member/member/level")
public class MemberLevelController extends BaseController<MemberLevel> {

    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private MemberService memberService;


    public BaseService<MemberLevel> getService() { return this.memberLevelService; }

    @PostMapping({"/save_rewrite"})
    public Resp saveRewrite(@Valid @RequestBody List<MemberLevel> memberLevels, @TokenInfo(property = "merchantId") String merchantId) {
        log.info("入参==={}", memberLevels);
        memberLevels.forEach(memberLevel -> {
            memberLevel.setMerchantId(merchantId);
            save(memberLevel);
        });
        MemberLevel level =  memberLevelService.findOne("1");
        if(null == level) {
            level = this.getBasicLevel();
            save(level);
        }
        return Resp.success("新增会员等级成功");
    }

    public MemberLevel getBasicLevel(){
        MemberLevel  level = new MemberLevel();
        level.setId("1");
        level.setName("普通会员");
        level.setDiscount(BigDecimal.valueOf(1));
        level.setRightExplain("普通会员无权益");
        level.setGiftScore(0);
        level.setMemberLimitAmount(BigDecimal.valueOf(0));
        level.setGiftMoney(BigDecimal.valueOf(0));
        return level;
    }


    @PostMapping({"/save_single"})
    public Resp saveSingle(@Valid @RequestBody MemberLevel memberLevel,@TokenInfo(property = "merchantId") String merchantId) {
        log.info("入参====={}",memberLevel);
        if(null == merchantId) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        memberLevel.setMerchantId(merchantId);
        save(memberLevel);
        MemberLevel level =  memberLevelService.findOne("1");
        if(null == level) {
            level = this.getBasicLevel();
            save(level);
        }
        return Resp.success("新增会员等级成功");
    }


    public Resp list(MemberLevel entity, PageVo pageVo) {
        entity.setMerchantId(TokenUtils.getMerchantId());
        return super.list(entity, pageVo);
    }

    public Resp delete(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            if (isMemberLevelUsed(ids[i])) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员等级已在使用中,无法删除");
            }
        }

        return super.delete(ids);
    }

    public Resp update(@RequestBody MemberLevel entity) {
        log.info("===参数{}", entity);
        if ("普通会员".equals(entity.getName())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员等级名称不可为[普通会员]");
        }
        if (isMemberLevelUsed(entity.getId())) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员等级已在使用中,无法编辑");
        }

        MemberLevel level = (MemberLevel)this.memberLevelService.findOne(entity.getId());
        String merchantId = TokenUtils.getMerchantId();
        List<MemberLevel> levels = this.memberLevelService.findByMerchantId(merchantId);
        if (!level.getName().equals(entity.getName())) {
            List<String> repeatNames = new ArrayList<String>();
            List<String> names = new ArrayList<String>();
            names.add(entity.getName());
            levels.forEach(memberLevel -> getRepeatNames(memberLevel, names, repeatNames));
            if (repeatNames.size() != 0) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员等级名称不可重复");
            }
        }
        if (level.getMemberLimitAmount().compareTo(entity.getMemberLimitAmount()) != 0) {
            List<BigDecimal> repeatAmounts = new ArrayList<BigDecimal>();
            List<BigDecimal> amounts = new ArrayList<BigDecimal>();
            amounts.add(entity.getMemberLimitAmount());
            levels.forEach(memberLevel -> getRepeatAmounts(memberLevel, amounts, repeatAmounts));
            if (repeatAmounts.size() != 0) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员充值金额不可重复");
            }
        }
        return super.update(entity);
    }

    @PostMapping({"/name_valid"})
    public Resp nameValid(@RequestBody List<String> names, @TokenInfo(property = "merchantId") String merchantId) {
        List<String> repeatNames = new ArrayList<String>();
        List<MemberLevel> levels = this.memberLevelService.findByMerchantId(merchantId);
        levels.forEach(memberLevel -> getRepeatNames(memberLevel, names, repeatNames));
        return Resp.success(repeatNames);
    }

    @PostMapping({"/amount_valid"})
    public Resp amountValid(@RequestBody List<BigDecimal> amounts) {
        String merchantId = TokenUtils.getMerchantId();
        List<BigDecimal> repeatAmounts = new ArrayList<BigDecimal>();
        List<MemberLevel> levels = this.memberLevelService.findByMerchantId(merchantId);
        levels.forEach(memberLevel -> getRepeatAmounts(memberLevel, amounts, repeatAmounts));
        return Resp.success(repeatAmounts);
    }

    private void getRepeatAmounts(MemberLevel memberLevel, List<BigDecimal> amounts, List<BigDecimal> repeatAmounts) {
        amounts.forEach(amount -> {
            if (amount.compareTo(memberLevel.getMemberLimitAmount()) == 0) {
                repeatAmounts.add(amount);
                return;
            }
        });
    }

    private void getRepeatNames(MemberLevel memberLevel, List<String> names, List<String> repeatNames) {
        names.forEach(name -> {
            if (name.equals(memberLevel.getName())) {
                repeatNames.add(name);
                return;
            }
        });
    }

    private boolean isMemberLevelUsed(String memberLevelId) {
        boolean isMemberLevelUsed = false;
        List<Member> memberList = this.memberService.getRepository().findByMemberLevelIdAndDelFlag(memberLevelId, CommonConstant.NORMAL_FLAG);
        if (memberList.size() > 0) {
            isMemberLevelUsed = true;
        }
        return isMemberLevelUsed;
    }
}
