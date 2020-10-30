package com.fzy.admin.fp.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户控制层
 */
@Data
public class MerchantVO {


    private String id;//唯一标识

    private String name;//名称


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;//创建时间 数据插入时自动赋值


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间 数据更新时自动赋值

    private Integer delFlag = CommonConstant.NORMAL_FLAG;//删除标志 CommonConstant.NORMAL_FLAG 删除 CommonConstant.DEL_FLAG


    private String serviceProviderId; //服务商id


    private String contact; //联系人

    private String phone; //手机(商户账号暂时用手机号)


    private String email; //邮箱


    private String address; //地址


    private String province; //省


    private String city;//市

    private String provinceName; //省


    private String cityName;//市


    private String businessLevOne;// 一级经营类别


    private String businessLevTwo;// 二级经营类别

    private String businessLevThree;// 三级经营类别


    private BigDecimal payProrata;//分佣比例

    private String companyId; //一级代理商或二级代理商id

    private String managerId; //业务员id

    private String photoId; //图片id


    private String companyName;//一级代理商或二级代理商名称


    private String managerName;//业务员名称

    private Integer status;//状态

    private String appKey; //  商户的key，开放接口给第三方用的时候给他用来加密


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCooperaTime;// 到期时间

    @ApiModelProperty(value = "是否开通电子发票 1.是 0.否")
    private Integer isOpenElectronicInvoice;

    @ApiModelProperty(value = "发票最低订单金额")
    private BigDecimal minElectronicInvoiceOrderPrice;

    @ApiModelProperty(value = "发票最高订单金额")
    private BigDecimal maxElectronicInvoiceOrderPrice;


}