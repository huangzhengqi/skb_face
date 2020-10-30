package com.fzy.admin.fp.member.sem.service;


import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberAliLevel;
import com.fzy.admin.fp.member.sem.repository.MemberAliLevelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

@Service
public class MemberAliLevelService implements BaseService<MemberAliLevel>{

    @Resource
    private MemberAliLevelRepository memberAliLevelRepository;

    @Override
    public BaseRepository<MemberAliLevel> getRepository() {
        return memberAliLevelRepository;
    }

    public Resp saveOrUpdate(MemberAliLevel memberAliLevel) {
        if(StringUtils.isBlank(memberAliLevel.getName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"等级名称为空");
        }
        if(StringUtils.isBlank(memberAliLevel.getMerchantId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }

        if (null != memberAliLevel.getId()) {
            memberAliLevel.setId(memberAliLevel.getId());
            MemberAliLevel  level =  memberAliLevelRepository.findByNameAndIdNotIn(memberAliLevel.getName(),memberAliLevel.getId());
            if(null != level) {
                return new Resp().error(Resp.Status.PARAM_ERROR,"会员等级名称重复");
            }
        }else {
            MemberAliLevel  level  = memberAliLevelRepository.findByName(memberAliLevel.getName());
            if(null != level) {
                return new Resp().error(Resp.Status.PARAM_ERROR,"会员等级名称重复");
            }
            if(null == memberAliLevel.getDiscountType()) {
                memberAliLevel.setDiscountType(2);
            }
            if(null == memberAliLevel.getGiftType()) {
                memberAliLevel.setGiftType(0);
            }
        }
        return Resp.success(memberAliLevelRepository.save(memberAliLevel));
    }
}
