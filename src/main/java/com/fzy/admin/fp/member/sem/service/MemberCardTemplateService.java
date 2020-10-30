package com.fzy.admin.fp.member.sem.service;

import cn.hutool.core.bean.BeanUtil;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayMarketingCardFormtemplateSetModel;
import com.alipay.api.domain.MiniAppPackageInfo;
import com.alipay.api.domain.OpenFormFieldDO;
import com.alipay.api.request.AlipayMarketingCardActivateurlApplyRequest;
import com.alipay.api.request.AlipayMarketingCardFormtemplateSetRequest;
import com.alipay.api.response.AlipayMarketingCardActivateurlApplyResponse;
import com.alipay.api.response.AlipayMarketingCardFormtemplateSetResponse;
import com.fzy.admin.fp.ali.config.AliConfig;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;

import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberCardTemplate;
import com.fzy.admin.fp.member.sem.dto.MemberCardTemplateDTO;
import com.fzy.admin.fp.member.sem.repository.MemberCardTemplateRepository;
import com.fzy.admin.fp.pay.pay.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class MemberCardTemplateService implements BaseService<MemberCardTemplate>{

    @Resource
    private MemberCardTemplateRepository memberCardTemplateRepository;

    @Resource
    private AlipayMemberService alipayMemberService;

    @Resource
    private AliPayService aliPayService;


    @Override
    public BaseRepository<MemberCardTemplate> getRepository() {
        return memberCardTemplateRepository;
    }

    public Resp<MemberCardTemplate> save(MemberCardTemplateDTO memberCardTemplateDTO) {

        if(StringUtils.isBlank(memberCardTemplateDTO.getMerchantId())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        if(StringUtils.isBlank(memberCardTemplateDTO.getName())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"会员卡名称为空");
        }
        if(StringUtils.isBlank(memberCardTemplateDTO.getPrivilegeExplain())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"会员权益信息为空");
        }

        if(StringUtils.isBlank(memberCardTemplateDTO.getTempateId())){
            MemberCardTemplate  memberCardTemplate = new MemberCardTemplate();
            BeanUtil.copyProperties(memberCardTemplateDTO,memberCardTemplate);
            String requestId = System.currentTimeMillis() + "";
            memberCardTemplate.setRequestId(requestId);
            memberCardTemplate.setCardNumber("SKBxxxxxxxxxxxx");
            memberCardTemplate = alipayMemberService.templateCreate(memberCardTemplate);

            if(StringUtils.isNotBlank(memberCardTemplate.getTempateId())) {
//                Resp resp =this.setTemplateForm(memberCardTemplate.getTempateId());
//                if(resp.getCode() == 200 && ("调用成功").equals(resp.getMsg())) {
//                    log.info("----------------------------------------------调用成功-------------------------------------------------------");
//                }
                return Resp.success(memberCardTemplateRepository.save(memberCardTemplate),"保存成功");
            }
        }

        MemberCardTemplate memberCardTemplate = memberCardTemplateRepository.findByTempateId(memberCardTemplateDTO.getTempateId());
        if(null != memberCardTemplate) {
            memberCardTemplate= this.covert(memberCardTemplate,memberCardTemplateDTO);
            memberCardTemplate =  memberCardTemplateRepository.save(memberCardTemplate);
            memberCardTemplate = alipayMemberService.modifyCardTemplate(memberCardTemplate);
        }
        return Resp.success(memberCardTemplate,"保存成功");
    }


    public MemberCardTemplate covert(MemberCardTemplate memberCardTemplate,MemberCardTemplateDTO dto) {
        memberCardTemplate.setAcceptWay(dto.getAcceptWay());
        memberCardTemplate.setBackgroudId(dto.getBackgroudId());
        memberCardTemplate.setBackgroundUrl(dto.getBackgroundUrl());
        memberCardTemplate.setBgColor(dto.getBgColor());
        memberCardTemplate.setColor(dto.getColor());
        memberCardTemplate.setCouponId(dto.getCouponId());
        memberCardTemplate.setLogoId(dto.getLogoId());
        memberCardTemplate.setLogoUrl(dto.getLogoUrl());
        memberCardTemplate.setMerchantId(dto.getMerchantId());
        memberCardTemplate.setName(dto.getName());
        memberCardTemplate.setPhone(dto.getPhone());
        memberCardTemplate.setPresentScores(dto.getPresentScores());
        memberCardTemplate.setPrivilegeExplain(dto.getPrivilegeExplain());
        memberCardTemplate.setTip(dto.getTip());
        memberCardTemplate.setTerm(dto.getTerm());
        return memberCardTemplate;
    }

    public Resp<MemberCardTemplate> update(MemberCardTemplateDTO memberCardTemplateDTO) {
        if(StringUtils.isBlank(memberCardTemplateDTO.getMerchantId())) {
            return  new Resp<>().error(Resp.Status.PARAM_ERROR,"商户标识为空");
        }
        if(StringUtils.isBlank(memberCardTemplateDTO.getName())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"会员卡名称为空");
        }
        if(StringUtils.isBlank(memberCardTemplateDTO.getPrivilegeExplain())) {
            return new Resp<>().error(Resp.Status.PARAM_ERROR,"会员权益信息为空");
        }
        if(StringUtils.isNotBlank(memberCardTemplateDTO.getTempateId())) {
            MemberCardTemplate memberCardTemplate = memberCardTemplateRepository.findByTempateId(memberCardTemplateDTO.getTempateId());
            if(null != memberCardTemplate) {
                memberCardTemplate= this.covert(memberCardTemplate,memberCardTemplateDTO);
                memberCardTemplate =  memberCardTemplateRepository.save(memberCardTemplate);
                memberCardTemplate = alipayMemberService.templateCreate(memberCardTemplate);
            }
            return Resp.success(memberCardTemplate,"更新成功");
        }
        return Resp.success(null);
    }

    public MemberCardTemplate findMax(String merchantId){
       return memberCardTemplateRepository.findMaxId(merchantId);
    }


    public Resp setTemplateForm(String templateId,String merchantId) {

        AlipayClient alipayClient = new DefaultAlipayClient(AliConfig.URL, AliConfig.APPID,AliConfig.APP_PRIVATE_KEY,AliConfig.FORMAT,AliConfig.CHARSET,AliConfig.ALIPAY_PUBLIC_KEY,AliConfig.SIGN_TYPE);
//        AlipayClient alipayClient =aliPayService.initAlipayClient(merchantId);
        AlipayMarketingCardFormtemplateSetRequest request = new AlipayMarketingCardFormtemplateSetRequest();
        AlipayMarketingCardFormtemplateSetModel alipayMarketingCardFormtemplateSetModel   = new AlipayMarketingCardFormtemplateSetModel();
        OpenFormFieldDO openFormFieldDO = new OpenFormFieldDO();

        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(templateId)) {
            map.put("template_id",templateId);
        }

        Map<String,Object> json = new HashMap<String, Object>();

        String[] strArry1 = new String[]{"OPEN_FORM_FIELD_MOBILE","OPEN_FORM_FIELD_NAME","OPEN_FORM_FIELD_GENDER","OPEN_FORM_FIELD_BIRTHDAY_WITH_YEAR"};
        String[] strArry2 = new String[]{"OPEN_FORM_FIELD_CITY","OPEN_FORM_FIELD_EMAIL","OPEN_FORM_FIELD_ADDRESS"};

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("common_fields",strArry1);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("common_fields",strArry2);

        openFormFieldDO.setRequired(jsonObject.toJSONString());
        openFormFieldDO.setOptional(jsonObject1.toJSONString());

        alipayMarketingCardFormtemplateSetModel.setFields(openFormFieldDO);
        map.put("fields",openFormFieldDO);
        request.setBizContent(JacksonUtil.toJson(map));
        AlipayMarketingCardFormtemplateSetResponse response = null;
        System.out.println(request.getBizContent().toString());
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getCode()+ "," + response.getMsg());

          MemberCardTemplate memberCardTemplate = memberCardTemplateRepository.findByTempateId(templateId);
          if(null != memberCardTemplate) {
              memberCardTemplate.setIsSet(CommonConstant.IS_TRUE);
              memberCardTemplateRepository.save(memberCardTemplate);
          }
            return Resp.success("调用成功");
        } else {
            System.out.println("调用失败");
            return Resp.success("调用失败");
        }
    }


    public Map<String,Object> applyActivateUrl(String templateId,String merchantId) {
        AlipayClient alipayClient = new DefaultAlipayClient(AliConfig.URL,AliConfig.APPID,AliConfig.APP_PRIVATE_KEY,AliConfig.FORMAT,"GBK",AliConfig.ALIPAY_PUBLIC_KEY,AliConfig.SIGN_TYPE);
//        AlipayClient alipayClient =aliPayService.initAlipayClient(merchantId);
        AlipayMarketingCardActivateurlApplyRequest request = new AlipayMarketingCardActivateurlApplyRequest();

        Map<String,Object> map = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(templateId)) {
            map.put("template_id",templateId);
        }
        request.setBizContent(JacksonUtil.toJson(map));
        AlipayMarketingCardActivateurlApplyResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                response.getApplyCardUrl();
                map.put("applyCardUrl",response.getApplyCardUrl());

                MemberCardTemplate memberCardTemplate = new MemberCardTemplate();
                memberCardTemplate.setApplyCardUrl(response.getApplyCardUrl());

                memberCardTemplate = memberCardTemplateRepository.findByTempateId(templateId);
                memberCardTemplateRepository.save(memberCardTemplate);
                log.info("调用成功");
            } else {
              log.info("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return map;
    }
}
