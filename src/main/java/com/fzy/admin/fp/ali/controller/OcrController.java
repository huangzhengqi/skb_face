package com.fzy.admin.fp.ali.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.ali.utils.HttpUtils;
import com.fzy.admin.fp.ali.vo.BankCardVO;
import com.fzy.admin.fp.ali.vo.BusinessLicenseVO;
import com.fzy.admin.fp.ali.vo.IdCardVO;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.app.dto.MerchantMonthDTO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * ocr图片识别接口
 */
@Slf4j
@RestController
@RequestMapping("/ali_pay_info/ali_ocr")
@Api(value="OcrController", tags={"ocr图片识别接口"})
public class OcrController extends BaseContent {

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;



    @Value("${file.upload.path}")
    public String prefixPath;
    /**
     * 身份证图片识别
     */
    @ApiOperation(value="身份证图片识别", notes="身份证图片识别")
    @PostMapping("/id/card")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "id",dataType = "String",value = "图片id"),
                        @ApiImplicitParam(paramType = "query",name = "type",dataType = "Integer",value = "图片类型 1：正面 2：反面")})
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=IdCardVO.class)})
    public Resp ocrIdCard(String id, Integer type) {

         IdCardVO idCardVO = new IdCardVO();
         String path="/rest/160601/ocr/ocr_idcard.json";
         String appcode="320d920ff9bb46a7bcf3209445367fe5"; //阿里云获取
         String host="http://dm-51.data.aliyun.com";
         FileInfoVo idCardVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
         if(idCardVO == null){
             return new Resp().error(Resp.Status.PARAM_ERROR, "身份证图片不能为空");
         }
         String imgFile=prefixPath + idCardVo.getPath();
//       String imgFile="D:/WorkDir/temp/md5/FDD3B38FC8374D87A5C9AA2880DCD5CC.PNG";
        //请根据线上文档修改configure字段
        JSONObject configObj=new JSONObject();
        //type = 1 正面
        if (type.equals(Integer.valueOf(1))) {
            configObj.put("side", "face"); //正面
        }else {
            configObj.put("side", "back"); //反面
        }
        String config_str=configObj.toString();
        String method="POST";
        Map<String, String> headers=new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359d73e9498385570ec139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys=new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64="";
        try {
            if (imgFile.startsWith("http")) {
                imgBase64=imgFile;
            } else {
                File file=new File(imgFile);
                byte[] content=new byte[(int) file.length()];
                FileInputStream finputstream=new FileInputStream(file);
                finputstream.read(content);
                finputstream.close();
                imgBase64=new String(encodeBase64(content));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数有误");
        }
        // 拼装请求body的json字符串
        JSONObject requestObj=new JSONObject();
        try {
            requestObj.put("image", imgBase64);
            if (config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys=requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response=HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat=response.getStatusLine().getStatusCode();
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return new Resp().error(Resp.Status.PARAM_ERROR, EntityUtils.toString(response.getEntity()));
            }

            String res=EntityUtils.toString(response.getEntity());
            JSONObject res_obj=JSON.parseObject(res);

            if(res_obj.get("success").equals(false)){
                return new Resp().error(Resp.Status.PARAM_ERROR, "识别失败,请重新上传清晰且准确的图片");
            }

            if(type.equals(Integer.valueOf(1))){
                idCardVO.setRepresentativeName((String) res_obj.get("name")); //经营者/法人姓名
                idCardVO.setCertificateNum((String) res_obj.get("num"));//身份证号
            }else {
                idCardVO.setStartCertificateTime((String) res_obj.get("start_date"));//开始时间
                idCardVO.setEndCertificateTime((String) res_obj.get("end_date"));//结束时间
            }
            log.info("返回参数: {}",res_obj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.success(idCardVO);
    }


    @ApiOperation(value = "银行卡图片识别",notes = "银行卡图片识别")
    @PostMapping("/bank/card")
    @ApiImplicitParam(paramType = "query",name = "id",dataType = "String",value = "图片id")
    @ApiResponses(@ApiResponse(code = 200,message = "ok",response = BankCardVO.class))
    public Resp bankCard(String id){

        BankCardVO bankCardVO = new BankCardVO();
        String host = "https://yhk.market.alicloudapi.com";
        String path = "/rest/160601/ocr/ocr_bank_card.json";
        String method = "POST";
        String appcode = "320d920ff9bb46a7bcf3209445367fe5";//阿里云获取
        FileInfoVo bankCardVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
        if(bankCardVo == null){
            return new Resp().error(Resp.Status.PARAM_ERROR, "银行卡图片不能为空");
        }
        String imgFile=prefixPath + bankCardVo.getPath();
//       String imgFile="D:/WorkDir/temp/md5/FDD3B38FC8374D87A5C9AA2880DCD5CC.PNG";
        //请根据线上文档修改configure字段
        JSONObject configObj=new JSONObject();
        String config_str=configObj.toString();
        Map<String, String> headers=new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359d73e9498385570ec139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys=new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64="";
        try {
            if (imgFile.startsWith("http")) {
                imgBase64=imgFile;
            } else {
                File file=new File(imgFile);
                byte[] content=new byte[(int) file.length()];
                FileInputStream finputstream=new FileInputStream(file);
                finputstream.read(content);
                finputstream.close();
                imgBase64=new String(encodeBase64(content));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数有误");
        }
        // 拼装请求body的json字符串
        JSONObject requestObj=new JSONObject();
        try {
            requestObj.put("image", imgBase64);
            if (config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys=requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response=HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat=response.getStatusLine().getStatusCode();
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return new Resp().error(Resp.Status.PARAM_ERROR, EntityUtils.toString(response.getEntity()));
            }

            String res=EntityUtils.toString(response.getEntity());
            JSONObject res_obj=JSON.parseObject(res);

            if(res_obj.get("success").equals(false)){
                return new Resp().error(Resp.Status.PARAM_ERROR, "识别失败,请重新上传清晰且准确的图片");
            }
            bankCardVO.setBankName((String) res_obj.get("bank_name"));//银行名称
            bankCardVO.setCardNum((String) res_obj.get("card_num"));//银行卡号
            log.info("返回参数: {}",res_obj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.success(bankCardVO);

    }


    @ApiOperation(value = "营业执照图片识别",notes = "营业执照图片识别")
    @PostMapping("/business/license")
    @ApiImplicitParam(paramType = "query",name = "id",dataType = "String",value = "图片id")
    @ApiResponses(@ApiResponse(code = 200,message = "ok",response = BusinessLicenseVO.class))
    public Resp businessLicense(String id){
        BusinessLicenseVO businessLicenseVO = new BusinessLicenseVO();
        String host = "https://dm-58.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_business_license.json";
        String appcode = "320d920ff9bb46a7bcf3209445367fe5";//阿里云获取
        String method = "POST";
        FileInfoVo businessLicenseVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
        if(businessLicenseVo == null){
            return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照不能为空");
        }
        String imgFile=prefixPath + businessLicenseVo.getPath();
        //请根据线上文档修改configure字段
        JSONObject configObj=new JSONObject();
        String config_str=configObj.toString();
        Map<String, String> headers=new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359d73e9498385570ec139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys=new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64="";
        try {
            if (imgFile.startsWith("http")) {
                imgBase64=imgFile;
            } else {
                File file=new File(imgFile);
                byte[] content=new byte[(int) file.length()];
                FileInputStream finputstream=new FileInputStream(file);
                finputstream.read(content);
                finputstream.close();
                imgBase64=new String(encodeBase64(content));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数有误");
        }
        // 拼装请求body的json字符串
        JSONObject requestObj=new JSONObject();
        try {
            requestObj.put("image", imgBase64);
            if (config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys=requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response=HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat=response.getStatusLine().getStatusCode();
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return new Resp().error(Resp.Status.PARAM_ERROR, EntityUtils.toString(response.getEntity()));
            }

            String res=EntityUtils.toString(response.getEntity());
            JSONObject res_obj=JSON.parseObject(res);

            if(res_obj.get("success").equals(false)){
                return new Resp().error(Resp.Status.PARAM_ERROR, "识别失败,请重新上传清晰且准确的图片");
            }
            businessLicenseVO.setSubjectType((String) res_obj.get("type"));//商户类型
            businessLicenseVO.setLicense((String) res_obj.get("reg_num"));//营业执照注册号
            businessLicenseVO.setMerchantName((String) res_obj.get("name"));//商户全称
            businessLicenseVO.setRegisterAddress((String) res_obj.get("address"));//经营地址
            log.info("返回参数: {}",res_obj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Resp.success(businessLicenseVO);
    }

}
