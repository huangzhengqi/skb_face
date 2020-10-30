package com.fzy.admin.fp.member.sem.service;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberCreditsRuler;
import com.fzy.admin.fp.member.sem.repository.MemberCreditsRulerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberCreditsRulerService implements BaseService<MemberCreditsRuler>{

    @Resource
    private MemberCreditsRulerRepository memberCreditsRulerRepository;


    @Override
    public BaseRepository<MemberCreditsRuler> getRepository() {
        return memberCreditsRulerRepository;
    }

    public Resp<MemberCreditsRuler> findByMerchantId(String merchantId){
        if(StringUtils.isBlank(merchantId)) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        List<MemberCreditsRuler> memberCreditsRuler = memberCreditsRulerRepository.findByMerchantId(merchantId);
        if(memberCreditsRuler.size() > 0 && !memberCreditsRuler.isEmpty()) {
            return Resp.success(memberCreditsRuler.get(0));
        }
        return Resp.success(new MemberCreditsRuler());
    }


    public Resp<MemberCreditsRuler> saveCreditsRuler(MemberCreditsRuler memberCreditsRuler){
        if(StringUtils.isBlank(memberCreditsRuler.getMerchantId())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        if(null == memberCreditsRuler.getIsTrue()) {
            memberCreditsRuler.setIsTrue(0);
        }
        return Resp.success(memberCreditsRulerRepository.save(memberCreditsRuler),"保存成功");
    }
}
