package com.fzy.admin.fp.advertise.device.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 设备广告表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_device")
public class AdvertiseDevice extends CompanyBaseEntity {

    @ApiModelProperty(value = "群组id")
    private String groupId;

    @Transient
//    @ApiModelProperty(value = "群组名称")
    private String groupName;

    @NotNull(message = "广告投放者公司id")
    @ApiModelProperty(value = "广告投放者公司id")
    private String companyId;

    @ApiModelProperty(value = "状态 1待投放 2投放中 3已结束 4已作废 5已暂停")
    private Integer status;

    @ApiModelProperty(value = "投放中的广告状态 1恢复 0暂停")
    private Integer advertiseStatus;

    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "视频/图片")
    private Integer selectSt; //(0图片  1视频)

    @NotNull(message = "投放对象不能为空")
    @ApiModelProperty(value = "广告类型 1指定商户群 2全部商户 3指定商户")
    private Integer targetType;

    @ApiModelProperty("投放位置 0首屏  1收银页面  2支付成功页")
    private Integer targetRange;

    @ApiModelProperty("是否默认广告 1不是，2默认广告")
    @Column(columnDefinition = "int(1) default 2")
    private Integer type;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    private Date beginTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty("设备类型 0全部 1支付宝 2微信")
    private Integer deviceType;

    @Transient
    @ApiModelProperty(value="曝光数")
    private Integer exposureNum;

    @Transient
    @ApiModelProperty(value="点击数")
    private Integer clickNum;

    @ApiModelProperty("添加时间")
    private Date createTime;

    @ApiModelProperty("标题")
    private String name;

    @Transient
    @ApiModelProperty("预计投放")
    private String expectedName;

    @ApiModelProperty("服务商id")
    private String serviceProviderId;

    @ApiModelProperty("商户Id,多个逗号隔开")
    private String merchantIds;

    @Transient
//    @ApiModelProperty("商户名称")
    private List<Merchant> merchants;

}
