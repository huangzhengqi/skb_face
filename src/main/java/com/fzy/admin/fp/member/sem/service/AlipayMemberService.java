package com.fzy.admin.fp.member.sem.service;



import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMarketingCardTemplateCreateRequest;
import com.alipay.api.request.AlipayMarketingCardTemplateModifyRequest;
import com.alipay.api.response.AlipayMarketingCardTemplateCreateResponse;
import com.alipay.api.response.AlipayMarketingCardTemplateModifyResponse;
import com.fzy.admin.fp.ali.config.AliConfig;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.sem.domain.MemberCardTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AlipayMemberService {

    public AlipayClient initClient() {
        return new DefaultAlipayClient(AliConfig.URL, AliConfig.APPID,AliConfig.APP_PRIVATE_KEY,AliConfig.FORMAT,"GBK",AliConfig.ALIPAY_PUBLIC_KEY,AliConfig.SIGN_TYPE);
    }

    public MemberCardTemplate templateCreate(MemberCardTemplate memberCardTemplate){
        AlipayClient alipayClient = this.initClient();
        AlipayMarketingCardTemplateCreateRequest request = new AlipayMarketingCardTemplateCreateRequest();
         Map<String,Object>  templateCreateMap = this.commentParam(memberCardTemplate);
        request.setBizContent(JacksonUtil.toJson(templateCreateMap));
        log.info(JSONObject.toJSONString(templateCreateMap));
        try {
            AlipayMarketingCardTemplateCreateResponse response = alipayClient.execute(request);
            if(response.isSuccess()) {
                log.info("调用成功");
                memberCardTemplate.setTempateId(response.getTemplateId());
                log.info("------------------------------------------------------------------------------------ response.getTemplateId{}",response.getTemplateId());
            }else{
                log.info("调用失败");
                log.info(response.getCode() + "," + response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return memberCardTemplate;
    }



    public Map<String,Object> commentParam(MemberCardTemplate memberCardTemplate) {

        Map<String,Object> templateCreateMap = new HashMap<String,Object>();

        //组装AlipayMarketingCardTemplateCreateModel参数
        templateCreateMap.put("request_id",memberCardTemplate.getRequestId());
        templateCreateMap.put("card_type","OUT_MEMBER_CARD");
        templateCreateMap.put("biz_no_prefix","SKB");
        templateCreateMap.put("biz_no_suffix_len","12");
        templateCreateMap.put("write_off_type","qrcode");

        //组装TemplateStyleInfoDTO参数
        //复制样式属性
        Map<String,Object> templateStyleInfo = new HashMap<String, Object>();
        if(StringUtils.isBlank(memberCardTemplate.getName())) {
            throw new BaseException("模板名称为空",Resp.Status.PARAM_ERROR.getCode());
        }
        if(memberCardTemplate.getName().length() > 10){
            throw new BaseException("模板名称长度不能大于10位",Resp.Status.PARAM_ERROR.getCode());
        }
        if(StringUtils.isBlank(memberCardTemplate.getLogoId())) {
            log.info("----------------------------------LOGOid{}",memberCardTemplate.getLogoId());

            throw new BaseException("LOGOID为空",Resp.Status.PARAM_ERROR.getCode());
        }
        if(StringUtils.isBlank(memberCardTemplate.getColor())) {
            templateStyleInfo.put("color","rgb(55,112,179)");
        }else {
            templateStyleInfo.put("color",memberCardTemplate.getColor());
        }
        if (StringUtils.isBlank(memberCardTemplate.getBgColor())) {
            templateStyleInfo.put("bg_color","rgb(55,112,179)");
        } else {
            templateStyleInfo.put("bg_color",memberCardTemplate.getBgColor());
        }
        if(StringUtils.isBlank(memberCardTemplate.getBackgroudId())) {
            log.info("----------------------------------backgroundId{}",memberCardTemplate.getBackgroudId());
            throw new BaseException("背景图片为空",Resp.Status.PARAM_ERROR.getCode());
        }

        //        templateStyleInfo.put("logo_id","1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC");
        //        templateStyleInfo.put("color","rgb(55,112,179)");
//        templateStyleInfo.put("bg_color","rgb(55,112,179)");
//        templateStyleInfo.put("background_id","1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC");
        templateStyleInfo.put("card_show_name",memberCardTemplate.getName());
        templateStyleInfo.put("logo_id",memberCardTemplate.getLogoId());
        templateStyleInfo.put("background_id",memberCardTemplate.getBackgroudId());
        templateCreateMap.put("template_style_info",templateStyleInfo);

//        //添加权益信息
//        List<TemplateBenefitInfoDTO> templateBenefitInfoDTOList = new ArrayList<TemplateBenefitInfoDTO>();
//        TemplateBenefitInfoDTO templateBenefitInfoDTO = new TemplateBenefitInfoDTO();
//        templateBenefitInfoDTO.setTitle("会员权益说明");
//        List<String> strList = new ArrayList<String>();
//        strList.add(memberCardTemplate.getPrivilegeExplain());
//        templateBenefitInfoDTO.setBenefitDesc(strList);
//
//        templateBenefitInfoDTOList.add(templateBenefitInfoDTO);
//
//        templateCreateModel.setTemplateBenefitInfo(templateBenefitInfoDTOList);

//        List<Map<String,Object>> templateBenefitList = new ArrayList<Map<String,Object>>();
//
//        Map<String,Object> templateBenefitMap = new HashMap<String, Object>();
//        templateBenefitMap.put("title","会员权益说明");
//        List<String> strList = new ArrayList<String>();
//        strList.add(memberCardTemplate.getPrivilegeExplain());
//        templateBenefitMap.put("benefit_desc",strList);
//        templateBenefitMap.put("start_date",new Date());
//        templateBenefitMap.put("end_date",);
//        templateBenefitList.add(templateBenefitMap);
//
//        templateCreateMap.put("template_benefit_info",templateBenefitList);

        List<Map<String,Object>> templateColumnInfoList = new ArrayList<Map<String,Object>>();
        Map<String,Object> balanceColumnMap  =new HashMap<String, Object>();
        //添加余额到栏位集合
        balanceColumnMap.put("code","BALANCE");
        balanceColumnMap.put("title","余额");
        templateColumnInfoList.add(balanceColumnMap);

        //添加积分到栏位集合
        Map<String,Object> pointColumnMap = new HashMap<String, Object>();
        pointColumnMap.put("code","POINT");
        pointColumnMap.put("title","积分");

        templateColumnInfoList.add(pointColumnMap);

//
        //添加会员等级到栏位集合
        Map<String,Object> levelColumnMap = new HashMap<String, Object>();
        levelColumnMap.put("code","LEVEL");
        levelColumnMap.put("title","等级");

        templateColumnInfoList.add(levelColumnMap);

        Map<String,Object> phoneColumnMap = new HashMap<String, Object>();
        phoneColumnMap.put("code","TELEPHONE");
        phoneColumnMap.put("title","商家电话");
        templateColumnInfoList.add(phoneColumnMap);

        templateCreateMap.put("column_info_list",templateColumnInfoList);

        List<Map<String,Object>> templateFieldRuleList = new ArrayList<Map<String,Object>>();
        Map<String,Object> balanceTemplateField = new HashMap<String, Object>();
//        //添加字段规则
        balanceTemplateField.put("field_name","Balance");
        balanceTemplateField.put("rule_name","CONST");
        balanceTemplateField.put("rule_value","0");

        templateFieldRuleList.add(balanceTemplateField);

        if(null != memberCardTemplate.getPresentScores()) {
            Map<String,Object> pointTemplateField = new HashMap<String, Object>();
            pointTemplateField.put("field_name","Point");
            pointTemplateField.put("rule_name","CONST");
            pointTemplateField.put("rule_value",memberCardTemplate.getPresentScores().toString());
            templateFieldRuleList.add(pointTemplateField);
        }

        Map<String,Object> validTemplateField = new HashMap<String, Object>();

        validTemplateField.put("field_name","ValidDate");
        validTemplateField.put("rule_name","DATE_IN_FUTURE");
        validTemplateField.put("rule_value","1200m");

        templateFieldRuleList.add(validTemplateField);
        templateCreateMap.put("field_rule_list",templateFieldRuleList);
        return templateCreateMap;
    }

    public MemberCardTemplate modifyCardTemplate(MemberCardTemplate memberCardTemplate) {

        AlipayClient alipayClient = this.initClient();
        AlipayMarketingCardTemplateModifyRequest request = new AlipayMarketingCardTemplateModifyRequest();
        Map<String,Object> templateModifyModel = this.commentParam(memberCardTemplate);
        request.setBizContent(JacksonUtil.toJson(templateModifyModel));
        try {
            AlipayMarketingCardTemplateModifyResponse modifyResponse = alipayClient.execute(request);

            if(modifyResponse.isSuccess()) {
                log.info("调用成功");
                memberCardTemplate.setTempateId(modifyResponse.getTemplateId());
                log.info("------------------------------------------------------------------------------------ response.getTemplateId",modifyResponse.getTemplateId());
            }else{
                log.info("调用失败");
                log.info(modifyResponse.getCode() + "," + modifyResponse.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return memberCardTemplate;
    }
}
