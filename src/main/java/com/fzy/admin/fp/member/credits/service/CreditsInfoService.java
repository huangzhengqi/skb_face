package com.fzy.admin.fp.member.credits.service;


import com.fzy.admin.fp.member.credits.repository.CreditsInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.repository.CreditsInfoRepository;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lb
 * @date 2019/5/17 14:18
 * @Description
 */
@Service
@Slf4j
public class CreditsInfoService implements BaseService<CreditsInfo> {

    @Resource
    private CreditsInfoRepository creditsInfoRepository;

    @Resource
    private MemberRepository memberRepository;

    @Override
    public BaseRepository<CreditsInfo> getRepository() {
        return creditsInfoRepository;
    }

    public Page<CreditsInfo> findByMemberNumAndMerchantId(String memberNum, String merchantId, PageVo pageVo) {
        CreditsInfo creditsInfo = new CreditsInfo();
        creditsInfo.setMemberNum(memberNum);
        creditsInfo.setMerchantId(merchantId);
        return list(creditsInfo, pageVo);
    }

    public Integer personScores(String memberNum, String merchantId) {
        Member member = memberRepository.findByMerchantIdAndMemberNum(merchantId, memberNum);
        log.info("获取到的会员信息为：{}" + member);
        if (member == null) {
            return -1;
        }
        return member.getScores();
    }
}
