package com.fzy.admin.fp.member.member.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import com.fzy.admin.fp.member.rule.service.StoredRecoredService;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-05-14 10:32
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/member/member")
@Api(value = "MemberController",tags = {"会员模块控制层"})
public class MemberController extends BaseController<Member> {
    @Resource
    private MemberService memberService;
    @Resource
    private MerchantUserFeign merchantUserFeign;
    @Resource
    private StoredRecoredService storedRecoredService;
    @Resource
    private PersonCouponService personCouponService;

    @Override
    public BaseService<Member> getService() {
        return memberService;
    }


    @GetMapping("/member_list")
    public Resp<Page<Member>> list(@TokenInfo(property = "merchantId") String merchantId, Member entity, PageVo pageVo) {
        entity.setMerchantId(merchantId);
        log.info("--------------------------------------merchantID{}",merchantId);
        entity.setBuyerId("isNull");
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Member> page = memberService.findAll(ConditionUtil.createSpecification(entity),pageable);
        log.info("----------------------------{}",page.getContent());

        return Resp.success(page);
    }

    @GetMapping("/member_ali_list")
    public Resp<Page<Member>> aliList(@TokenInfo(property = "merchantId") String merchantId, Member entity, PageVo pageVo){
        entity.setMerchantId(merchantId);
        log.info("--------------------------------------merchantID{}",merchantId);
        entity.setOpenId("isNull");
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Member> page = memberService.findAll(ConditionUtil.createSpecification(entity),pageable);
        log.info("----------------------------{}",page.getContent());
        return Resp.success(page);
    }


    @GetMapping("/member_detail")
    public Resp memberDetail(@UserId String id) {
        Member member = memberService.findOne(id);
        final List<PersonCoupon> personCouponList = personCouponService.findAll(member.getMerchantId(), member.getId());
        final Map<Integer, Long> map = personCouponList.stream().collect(Collectors.groupingBy(PersonCoupon::getStatus, Collectors.counting()));
        Long noUse = map.get(PersonCoupon.Status.NO_USE.getCode());
        Long used = map.get(PersonCoupon.Status.USE.getCode());
        Long invalid = map.get(PersonCoupon.Status.INVALID.getCode());
        member.setCouponCount(noUse == null ? 0 : noUse.intValue());
        member.setUsedCouponCount(used == null ? 0 : used.intValue());
        member.setInvalidCouponCount(invalid == null ? 0 : invalid.intValue());
        return Resp.success(member);
    }

    @GetMapping("/merchant_member_detail")
    public Resp merchantMemberDetail(String id) {
        Member member = memberService.findOne(id);
        return Resp.success(member);
    }

    /**
     * @author Created by zk on 2019/6/3 11:19
     * @Description 保存微信传上来的用户信息
     */
    @PostMapping("/save_member_info")
    public Resp saveMemberInfo(@UserId String id, String nickName, String avatarUrl, String gender) {
        Member member = memberService.findOne(id);
        if (member == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无该用户");
        }
        if (ParamUtil.isBlank(member.getNickname())) {
            member.setNickname(nickName);
        }
        if (ParamUtil.isBlank(member.getSex())) {
            //如果为2则为女性，其余则为男性
            member.setSex("2".equals(gender) ? Member.Sex.FEMALE.getCode() : Member.Sex.MEM.getCode());
        }
        member.setHead(avatarUrl);
        member = memberService.save(member);
        return Resp.success(member);
    }

    @PostMapping("/member_import")
    @Transactional
    public Resp memberImport(@UserId String userId, MultipartFile file) {
        List<Member> memberList = EasyPoiUtil.importExcel(file, 1, 1, Member.class);
        List<String> messages = new LinkedList<>();
        List<StoredRecored> storedRecoredList = new LinkedList<>();
        List<Member> legalMemberList = new ArrayList<>();
        Map<String, Integer> duplicatePhoneMap = new HashMap<>();
        //操作员
        MerchantUserDTO user = merchantUserFeign.findUser(userId);
        if (user == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无该操作员");
        }
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            if (checkImportMember(member, messages, i, user.getMerchantId())) {
                if (ParamUtil.isBlank(duplicatePhoneMap.get(member.getPhone()))) {
                    duplicatePhoneMap.put(member.getPhone(), i);
                } else {
                    messages.add(StrFormatter.format("第{}条数据和第{}条数据电话号码重复<br>", i + 1, duplicatePhoneMap.get(member.getPhone()) + 1));
                }
                legalMemberList.add(member);
            }
        }
        if (messages.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String message : messages) {
                sb.append(message);
            }
            return new Resp().error(Resp.Status.PARAM_ERROR, sb.toString());
        }
        for (int i = 0; i < legalMemberList.size(); i++) {
            Member member = legalMemberList.get(i);
            StringBuilder sb = new StringBuilder(DateUtil.format(new Date(), "yyyyMMdd"));
            String memberNum;
            while (true) {
                memberNum = sb.append(RandomUtil.randomNumbers(6)).toString();
                if (!memberService.countMemberByMemberNum(memberNum)) {
                    member.setMemberNum(memberNum);
                    break;
                }
            }
            if (member.getBalance() == null) {
                member.setBalance(BigDecimal.ZERO);
            }
            if (member.getScores() == null) {
                member.setScores(0);
            }
            member.setMerchantId(user.getMerchantId());
            member.setChannel(Member.Channel.MERCHANT_IMPORT.getCode());
            member = memberService.save(member);
            //-----------------生成储值记录---------------------
            if (member.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                StoredRecored storedRecored = new StoredRecored();
                storedRecored.setStoredNum("8819" + RandomUtil.randomNumbers(20));
                storedRecored.setMemberId(member.getId());
                storedRecored.setStoreId(user.getStoreId());
                storedRecored.setPhone(member.getPhone());
                storedRecored.setStoreName(user.getStoreName());
                storedRecored.setMemberNum(member.getMemberNum());
                storedRecored.setTradeType(StoredRecored.TradeType.IMPORT.getCode());
                storedRecored.setSource(StoredRecored.Source.PC.getCode());
                storedRecored.setPayWay(StoredRecored.PayWay.IMPORT.getCode());
                storedRecored.setStatus(StoredRecored.Status.CARD.getCode());
                storedRecored.setOperationUser(user.getName());
                storedRecored.setGiftMoney(BigDecimal.ZERO);
                storedRecored.setTradingMoney(member.getBalance());
                storedRecored.setPostTradingMoney(member.getBalance());
                storedRecored.setDiscountMoney(BigDecimal.ZERO);
                storedRecoredList.add(storedRecored);
            }
        }
        if (storedRecoredList.size() > 0) {
            storedRecoredService.save(storedRecoredList);
        }
        return Resp.success("导入成功");
    }

    private boolean checkImportMember(Member member, List<String> messages, int index, String merchantId) {
        index++;
        if (ParamUtil.isBlank(member.getNickname()) && ParamUtil.isBlank(member.getPhone())
                && ParamUtil.isBlank(member.getSex())) {
            return false;
        }
        if (ParamUtil.isBlank(member.getPhone())) {
            messages.add(StrFormatter.format("第{}条数据的用户手机不能为空<br>", index));
            return false;
        }
        if (memberService.isNewMember(merchantId, member.getPhone())) {
            if (ParamUtil.isBlank(member.getNickname())) {
                messages.add(StrFormatter.format("第{}条数据的用户昵称不能为空<br>", index));
                return false;
            }
            if (ParamUtil.isBlank(member.getSex())) {
                messages.add(StrFormatter.format("第{}条数据的用户性别不能为空<br>", index));
                return false;
            }
        } else {
            messages.add(StrFormatter.format("第{}条数据的用户已存在<br>", index));
            return false;
        }
        return true;
    }

//    @GetMapping("/find_by_applet_openid")
//    public Resp findByAppletOpenid(String openid){
//        Member member = memberService.findByAppletOpenid(openid);
//    }


}
