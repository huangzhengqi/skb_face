package com.fzy.admin.fp.merchant.merchant.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;

/**
 * @author: huxiangqiang
 * @since: 2019/8/1
 */
@Data
public class MchInfoVO {
    @ApiModelProperty(value = "id")
    String id;

    @ApiModelProperty(value = "商户名称")
    String merchantName;

    @ApiModelProperty(value = "商户账号")
    String phone;

    @ApiModelProperty(value = "签约状态")
    Integer status;

    @ApiModelProperty(value = "上级代理")
    String companyName;

    @Transient
    @ApiModelProperty(value = "1直接开通的商户 0不是")
    private Integer isDirect;

    @Transient
    private String companyId;


    private String merchantId;


    @ApiModelProperty(value = "设备数")
    private Integer equipmentNum;


    public MchInfoVO(){}

    public MchInfoVO(String id,String representativeName,String phone,Integer status,String companyName,String companyId){
        this.id=id;
        this.status=status;
        this.phone=phone;
        this.merchantName=representativeName;
        this.companyName=companyName;
        this.companyId=companyId;
    }

    public MchInfoVO(String id,String representativeName,String phone,Integer status,String merchantId){
        this.id=id;
        this.status=status;
        this.phone=phone;
        this.merchantName=representativeName;
        this.merchantId=merchantId;
    }

}
