package com.fzy.admin.fp.member.member.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.repository.MemberLevelRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MemberLevelService extends Object implements BaseService<MemberLevel>{

    @Resource
    private MemberLevelRepository memberLevelRepository;

    public BaseRepository<MemberLevel> getRepository() { return this.memberLevelRepository; }

    public List<MemberLevel> findByMerchantId(String merchantId) {
        MemberLevel memberLevel = new MemberLevel();
        memberLevel.setMerchantId(merchantId);
        Specification specification = ConditionUtil.createSpecification(memberLevel);
        return findAll(specification);
    }

    public List<MemberLevel> findByMerchantIdOrderByMemberLimitAmountAsc(String merchantId){
      return  memberLevelRepository.findByMerchantIdOrderByMemberLimitAmountAsc(merchantId);
    }
}
