package com.fzy.admin.fp.merchant.merchant.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzy
 * @since: 2019/8/1
 * 分销商户进件列表
 */
@Data
public class DisMchInfoVO {

    @ApiModelProperty(value = "进件ID")
    String id;

    @ApiModelProperty(value = "商户名称")
    String merchantName;

    @ApiModelProperty(value = "商户账号")
    String phone;

    @ApiModelProperty(value = "签约状态")
    Integer status;

    @ApiModelProperty(value = "归属业务员")
    String managerName;

    @ApiModelProperty("天阙随行付进件成功 0 入驻审核中 1 入驻通过 2 入驻驳回 3 入驻图片驳回")
    private Integer tqSxfSuccess;

//    @ApiModelProperty("天阙随行付进件状态名称")
//    private Integer tqSxfSuccessName;

    @ApiModelProperty("微信进件成功 0否 1是 2审核中")
    private Integer wxSuccess;

//    @ApiModelProperty("微信进件状态名称")
//    private Integer wxSuccessName;

    @ApiModelProperty("支付宝进件成功 0否 1是 2审核中")
    private Integer zfbSuccess;

//    @ApiModelProperty("微信进件状态名称")
//    private Integer zfbSuccessName;

    @ApiModelProperty("激活设备数量")
    private Integer equipmentNum;


    private String merchantId;

    public DisMchInfoVO(){}

    public DisMchInfoVO(String id,String merchantName,String phone,Integer status,String merchantId,String managerName,Integer tqSxfSuccess,Integer zfbSuccess,Integer wxSuccess){
        this.id=id;
        this.status=status;
        this.phone=phone;
        this.merchantName=merchantName;
        this.merchantId=merchantId;
        this.managerName=managerName;
        this.tqSxfSuccess=tqSxfSuccess;
        this.zfbSuccess=zfbSuccess;
        this.wxSuccess=wxSuccess;

    }



}
