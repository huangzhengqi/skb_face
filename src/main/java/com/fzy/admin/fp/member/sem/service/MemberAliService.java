package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.member.sem.domain.MemberAli;
import com.fzy.admin.fp.member.sem.repository.MemberAliRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberAliService implements BaseService<MemberAli>{

    @Resource
    private MemberAliRepository memberAliRepository;

    @Override
    public BaseRepository<MemberAli> getRepository() {
        return memberAliRepository;
    }

    public MemberAli findByBuyerId(String buyserId) {
      return  memberAliRepository.findByBuyerIdAndDelFlag(buyserId, CommonConstant.NORMAL_FLAG);
    }

    public MemberAli findByMerchantIdAndBuyerIdAndDelFlag(String merchantId,String buyserId){
        return memberAliRepository.findByMerchantIdAndBuyerIdAndDelFlag(merchantId,buyserId,CommonConstant.NORMAL_FLAG);
    }

    public Boolean countByMemberNum(String memberNum){
        return memberAliRepository.countByMemberNumAndDelFlag(memberNum,CommonConstant.NORMAL_FLAG) > 0;
    }

}
