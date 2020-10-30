package com.fzy.admin.fp.member.rule.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.enumeration.EnumUtils;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.member.rule.vo.SmallProgramStoreRecordDetailVo;
import com.fzy.admin.fp.member.rule.vo.SmallProgramStoreRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 17:20 2019/5/21
 * @ Description:
 **/
@RestController
@Slf4j
@RequestMapping("/member/stored_recored/applet")
public class StoreRecoredManagementController extends BaseContent {


    @Resource
    private StoredRecoredService storedRecoredService;

    /*
     * @author drj
     * @date 2019-05-21 21:37
     * @Description :会员获取储值记录
     */
    @GetMapping("/find_by_member_id")
    public Resp findByMemberId(PageVo pageVo, @UserId String memberId, Integer status) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<StoredRecored> page = null;
        //如果status为3查所有
        if (3 == status) {
            Integer[] tradeTypes = new Integer[4];
            tradeTypes[0] = 1;
            tradeTypes[1] = 2;
            tradeTypes[2] = 3;
            tradeTypes[3] = 4;
            page = storedRecoredService.getRepository().findByMemberIdAndTradeTypeIn(memberId, tradeTypes, pageable);
        }
        //如果status为1,查所有增加记录(充值,退款,导入)
        if (1 == status) {
            Integer[] tradeTypes = new Integer[3];
            tradeTypes[0] = 1;
            tradeTypes[1] = 3;
            tradeTypes[2] = 4;
            page = storedRecoredService.getRepository().findByMemberIdAndTradeTypeIn(memberId, tradeTypes, pageable);
        }
        //如果status为2,查所有减少记录(消费)
        if (2 == status) {
            Integer[] tradeTypes = new Integer[1];
            tradeTypes[0] = 2;
            page = storedRecoredService.getRepository().findByMemberIdAndTradeTypeIn(memberId, tradeTypes, pageable);

        }
        //StoredRecored转对应的VO
        List<StoredRecored> storedRecoredList = page.getContent();
        List<SmallProgramStoreRecordVo> adminStoreRecordVos = storedRecoredList.stream()
                .map(e -> new SmallProgramStoreRecordVo(e.getId(), e.getCreateTime(), e.getTradingMoney(), EnumUtils.findByCode(e.getTradeType(), StoredRecored.TradeType.class)))
                .collect(Collectors.toList());
        //如果是消费,钱变为负数
        for (SmallProgramStoreRecordVo adminStoreRecordVo : adminStoreRecordVos) {
            if ("消费".equals(adminStoreRecordVo.getTradeTypeText())) {
                adminStoreRecordVo.setTradingMoney(adminStoreRecordVo.getTradingMoney().multiply(new BigDecimal("-1")));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("content", adminStoreRecordVos);
        return Resp.success(map);
    }


    /*
     * @author drj
     * @date 2019-05-21 21:37
     * @Description ：查看储值详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        StoredRecored storedRecored = storedRecoredService.findOne(id);
        if (ParamUtil.isBlank(storedRecored)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        SmallProgramStoreRecordDetailVo smallProgramStoreRecordDetailVo = new SmallProgramStoreRecordDetailVo();
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(storedRecored, smallProgramStoreRecordDetailVo, copyOptions);
        smallProgramStoreRecordDetailVo.setTradeTypeText(EnumUtils.findByCode(storedRecored.getTradeType(), StoredRecored.TradeType.class));
        if ("消费".equals(smallProgramStoreRecordDetailVo.getTradeTypeText())) {
            smallProgramStoreRecordDetailVo.setTradingMoney(smallProgramStoreRecordDetailVo.getTradingMoney().multiply(new BigDecimal("-1")));
        }
        return Resp.success(smallProgramStoreRecordDetailVo);
    }
}
