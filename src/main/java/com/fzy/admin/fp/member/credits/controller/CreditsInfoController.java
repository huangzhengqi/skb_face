package com.fzy.admin.fp.member.credits.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.credits.domain.CreditsInfo;
import com.fzy.admin.fp.member.credits.dto.CreditsInfoDTO;
import com.fzy.admin.fp.member.credits.service.CreditsInfoService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lb
 * @date 2019/5/17 13:56
 * @Description 积分明细
 */
@RestController
@RequestMapping(value = "/member/credits_info")
public class CreditsInfoController extends BaseController<CreditsInfo> {

    @Resource
    private CreditsInfoService creditsInfoService;

    @Resource
    private MemberService memberService;

    @Override
    public BaseService<CreditsInfo> getService() {
        return creditsInfoService;
    }

    @GetMapping(value = "/person_info")
    public Resp findCreditsInfo(String memberNum, @TokenInfo(property = "merchantId") String merchantId, PageVo pageVo) {
        if (memberNum == null || merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, Resp.Status.PARAM_ERROR.getMsg());
        }
        Page<CreditsInfo> creditsInfos = creditsInfoService.findByMemberNumAndMerchantId(memberNum, merchantId, pageVo);
        return Resp.success(creditsInfos);
    }

    @GetMapping(value = "/person_info_detail")
    public Resp findCreditsInfoDetail(@TokenInfo(property = "merchantId") String merchantId,
                                      CreditsInfo creditsInfo, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, Resp.Status.PARAM_ERROR.getMsg());
        }
        System.out.println("编号：" + creditsInfo.getTradeNum());
        creditsInfo.setMerchantId(merchantId);
        Page<CreditsInfo> page = creditsInfoService.list(creditsInfo, pageVo);
        return Resp.success(page);
    }

    //获取当前会员的剩余积分
    @GetMapping(value = "/scores")
    public Resp personScores(String memberNum, @TokenInfo(property = "merchantId") String merchantId) {
        if (memberNum == null || merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, Resp.Status.PARAM_ERROR.getMsg());
        }
        Integer a = creditsInfoService.personScores(memberNum, merchantId);
        if (a.equals(-1)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, Resp.Status.PARAM_ERROR.getMsg());
        }
        return Resp.success(a);
    }

    @GetMapping(value = "/person_Info")
    public Resp getPersonInfo(String memberId, CreditsInfo creditsInfo, PageVo pageVo) {
        Member member = memberService.findOne(memberId);
        if (member == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员不存在");
        }
        Integer scores = member.getScores();
        creditsInfo.setMemberNum(member.getMemberNum());
        Page<CreditsInfo> creditsInfos = creditsInfoService.list(creditsInfo, pageVo);

        List<CreditsInfo> creditsInfoList = creditsInfos.getContent();
        List<CreditsInfoDTO> creditsInfoDTOList = new ArrayList<>();
        for (CreditsInfo creditsInfo1 : creditsInfoList) {
            CreditsInfoDTO creditsInfoDTO = new CreditsInfoDTO();
            creditsInfoDTO.setTradeTime(creditsInfo1.getTradeTime());
            creditsInfoDTO.setAvaCredits(creditsInfo1.getAvaCredits());
            creditsInfoDTO.setTradeScores(creditsInfo1.getTradeScores());
            if (creditsInfo1.getStoreName() == null) {
                creditsInfoDTO.setOperator("");
            } else {
                creditsInfoDTO.setOperator(creditsInfo1.getStoreName());
            }
            if (creditsInfo1.getInfoType().equals(CreditsInfo.Trade.CONSUM_SCORE.getCode())) {
                creditsInfoDTO.setCreditsType(CreditsInfo.Trade.CONSUM_SCORE.getMessage());
            } else if (creditsInfo1.getInfoType().equals(CreditsInfo.Trade.RECHARGE_GIFTS.getCode())) {
                creditsInfoDTO.setCreditsType(CreditsInfo.Trade.RECHARGE_GIFTS.getMessage());
            } else if (creditsInfo1.getInfoType().equals(CreditsInfo.Trade.CONSUM_CONSUM.getCode())) {
                creditsInfoDTO.setCreditsType(CreditsInfo.Trade.CONSUM_CONSUM.getMessage());
            } else if (creditsInfo1.getInfoType().equals(CreditsInfo.Trade.REFUND.getCode())) {
                creditsInfoDTO.setCreditsType(CreditsInfo.Trade.REFUND.getMessage());
            } else if (creditsInfo1.getInfoType().equals(CreditsInfo.Trade.CARD_GIVEN.getCode())) {
                creditsInfoDTO.setCreditsType(CreditsInfo.Trade.CARD_GIVEN.getMessage());
            }
            creditsInfoDTOList.add(creditsInfoDTO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("scores", scores);
        map.put("creditsInfoDTOList", creditsInfoDTOList);
        map.put("totalElements", creditsInfos.getTotalElements());
        return Resp.success(map);
    }


}
