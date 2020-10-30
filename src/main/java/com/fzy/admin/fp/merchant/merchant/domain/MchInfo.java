package com.fzy.admin.fp.merchant.merchant.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:45 2019/4/30
 * @ Description:商户进件材料
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name="lysj_merchant_mer_info")
public class MchInfo extends CompanyBaseEntity {


    @Getter
    public enum Status {
        NOTOPEN(1, "未开通"),
        AUDIT(2, "待审核"),
        SIGNSUCCESS(3, "签约成功"),
        SIGNFAIL(4, "签约失败");

        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code=code;
            this.status=status;
        }
    }

    @ApiModelProperty(value="商户id")
    private String merchantId;

    @ApiModelProperty(value="状态 1未开通 2待审核 3签约成功 4签约失败 5激活设备")
    private Integer status;


    // ========================  公共信息 =====================================
    @ApiModelProperty(value="经营者身份证正面照")
    private String epresentativePhotoId;

    @ApiModelProperty(value="经营者身份证国徽照")
    private String epresentativePhotoId2;

    @ApiModelProperty(value="经营者/法人姓名")
    private String representativeName;

    @ApiModelProperty(value="身份证号码")
    private String certificateNum;

    @ApiModelProperty(value="身份证开始证件期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startCertificateTime;

    @ApiModelProperty(value="身份证结束证件期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endCertificateTime;

    @ApiModelProperty(value="手机号")
    private String phone;

    @ApiModelProperty(value="邮箱")
    private String email;

    @ApiModelProperty(value="营业执照照片")
    private String businessLicensePhotoId;

    @ApiModelProperty(value="商户类型 个体工商户 企业 小微商户")
    private String subjectType;

    @ApiModelProperty(value="营业执照注册号")
    private String license;

    @ApiModelProperty(value="商户全称")
    private String merchantName;

    @ApiModelProperty(value="门头照片")
    private String doorPhoto;

    @ApiModelProperty(value="店内照片")
    private String storePhoto;

    @ApiModelProperty(value="商户简称")
    private String shortName;

    @ApiModelProperty(value="门店省市区")
    private String storeAddress;

    @ApiModelProperty(value="经营地址")
    private String registerAddress;

    @ApiModelProperty(value="客服电话")
    private String cusServiceTel;

    @ApiModelProperty("银行卡卡号面")
    private String bankCardNumber;

    @ApiModelProperty(value="账户类型")
    private String accountType;

    @ApiModelProperty(value="开户名称")
    private String accountHolder;

    @ApiModelProperty(value="开户银行")
    private String bankName;

    @ApiModelProperty(value="银行账号")
    private String accountNumber;

    @ApiModelProperty(value="开户城市")
    private String bankCity;

    @ApiModelProperty(value="开户支行")
    private String bankOutlet;

    // =====================  公共信息结束 =============================



    // ======================= 微信参数 ================================

    @ApiModelProperty("费率结算规则Id")
    private String settlementId;

    @ApiModelProperty("所属行业")
    private String qualificationType;

    @ApiModelProperty(value="特殊资质截图")
    private String specialQualificationPhotoId;

    @ApiModelProperty(value="补充材料图片  (暂时用不到)")
    private String supplementPhotoId;

    @ApiModelProperty("补充材料 （微信使用的）")
    private String businessAdditionPics;

    @ApiModelProperty("微信官方子商户号")
    private String subMchid;

    @ApiModelProperty("微信进件状态 0未提交（空，不显示）1编辑中 2审核中 3已驳回 4待账户验证 5待签约 6开通权限中 7已完成 8已作废")
    private Integer wxSuccess;

    @ApiModelProperty("微信进件结果")
    private String applymentStateMsg;

    @ApiModelProperty("微信/签约链接")
    @Transient
    private String signUrl;

    @ApiModelProperty("驳回的字段名")
    @Transient
    private String field;

    @ApiModelProperty("驳回的字段名称")
    @Transient
    private String fieldName;

    @ApiModelProperty("驳回原因")
    @Transient
    private String rejectReason;

    @ApiModelProperty(value="业务申请编号")
    private String businessCode;

    @ApiModelProperty(value="微信申请单")
    private String applymentId;

    // ======================= 微信参数结束 ================================



    // ======================= 支付宝参数 ====================================

    @ApiModelProperty(value="商家支付宝账号")
    private String aliAccountName;

    @ApiModelProperty(value="支付宝经营类目编号")
    private String mccCode;

    @ApiModelProperty(value="支付宝经营类目名称")
    private String mccName;

    @ApiModelProperty(value="支付宝费率")
    @Column(columnDefinition="decimal(10,2) default 0")
    private BigDecimal zfbRate;

    @ApiModelProperty("支付宝特殊资质截图")
    private String specialQualificationPhotoIdZfb;

    @ApiModelProperty(value="商家pid")
    @Column(length=100)
    private String merchantPid;

    @ApiModelProperty("支付宝进件状态 1暂存 2审核中 3待商户确认 4商户确认成功 5商户超时未确定 6审核失败或商户拒绝 ")
    private Integer zfbSuccess;

    @ApiModelProperty(value="支付宝申请时的驳回信息")
    private String zfbMsg;

    @ApiModelProperty(value="支付宝的事务编号，用于查询申请单状态")
    private String batchNo;

    @ApiModelProperty("是否给支付宝自动进件 0否 1是")
    private Integer isZfb;

    @ApiModelProperty("支付宝商户确认链接")
    @Column(length=256)
    private String confirmUrl;

    // ======================= 支付宝参数结束 ================================

    // =====================天阙随行付参数 ================================
    @ApiModelProperty("天阙随行付mccId")
    private String tqSxfMccId;

    @ApiModelProperty("天阙随行付经营类目名称")
    private String tqSxfMccName;

    @ApiModelProperty("天阙随行付微信")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal tqsxfWxRate;

    @ApiModelProperty(value="天阙随行付支付宝")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal tqsxfZfbRate;

    @ApiModelProperty(value="天阙随行付申请时的驳回信息")
    private String tqSxfMsg;

    @ApiModelProperty("天阙随行付进件成功 0 首次提交 1 入驻通过 2 入驻驳回 3 入驻图片驳回 4 入驻审核中")
    private Integer tqSxfSuccess;

    @ApiModelProperty("天阙随行付商编")
    private String tqSxfMno;

    @ApiModelProperty("开户许可证 天阙随行付对公结算必传")
    private String openingPermit;

    @ApiModelProperty("天阙进件申请ID")
    private String applicationId;

   // ============================== 天阙随行付参数 ================================


    //=====================没有用到的参数===============================
    @ApiModelProperty(value="经营范围")
    @Column(columnDefinition="text")
    private String businessScope;

    @ApiModelProperty(value="开始营业期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startBusinessTime;

    @ApiModelProperty(value="结束营业期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endBusinessTime;

    @ApiModelProperty(value="证件持有人类型 1:法人，2：经办人")
    private Integer representativeType;

    @ApiModelProperty(value="证件类型 1:身份证,2:护照")
    private Integer certificate;

    @ApiModelProperty(value="微信费率")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal wxRate;
    @ApiModelProperty(value="随行付微信")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal sxfWxRate;
    @ApiModelProperty(value="随行付支付宝")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal sxfZfbRate;
    @ApiModelProperty(value="富有微信")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal fyWxRate;
    @ApiModelProperty(value="富有支付宝")
    @Column(columnDefinition="decimal(10,4) default 0")
    private BigDecimal fyZfbRate;

    @ApiModelProperty(value="联系人姓名")
    private String contact;

    @ApiModelProperty(value="一级经营类别")
    private String businessLevOne;

    @ApiModelProperty(value="二级经营类别")
    private String businessLevTwo;

    @ApiModelProperty(value="三级经营类别")
    private String businessLevThree;

    @ApiModelProperty(value="售卖商品场景 采用id拼接的形式 1:线下,2:公众号/小程序,3:网站,4:APP")
    private String sellCheck;

    @ApiModelProperty(value="门店地址")
    private String address;

    @ApiModelProperty(value="公众号/小程序名称")
    private String miniProgramName;

    @ApiModelProperty(value="公众号/小程序截图id")
    private String miniProgramPhotoId;

    @ApiModelProperty(value="公司网站")
    private String companyWeb;

    @ApiModelProperty(value="APP上线状态  1:未上线,2:已上线")
    private Integer appStatus;

    @ApiModelProperty(value="app图片id")
    private String appPhotoId;

    @ApiModelProperty(value="组织机构代码")
    private String organizationCode;

    @ApiModelProperty(value="开始组织机构期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startOrganizationTime;

    @ApiModelProperty(value="结束组织机构期限")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endOrganizationTime;

    @ApiModelProperty(value="组织机构图片ID")
    private String organizationPhotoId;

    @ApiModelProperty(value="服务商id")
    private String serviceProviderId;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="是否开通电子发票 1.是 0.否")
    private Integer isOpenElectronicInvoice;

    @ApiModelProperty(value="经营许可证")
    private String businessCertificate;

    @ApiModelProperty(value="微信申请时的驳回信息")
    private String wxMsg;

    @ApiModelProperty(value="证件类型")
    private String idDocType;

    @ApiModelProperty("是否给微信自动进件 0否 1是")
    private Integer isWx;

    @ApiModelProperty("一级代理商或二级代理商名称")
    @Transient
    private String companyName;//一级代理商或二级代理商名称

    //=====================没有用到的参数===============================
}
