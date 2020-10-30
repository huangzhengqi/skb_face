package com.fzy.admin.fp.advertise.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新建群组入参
 */
@Data
public class GroupDTO {

    /**
     * 群组名称
     */
    @NotNull(message = "群组名称不能为空")
    @ApiModelProperty(value = "群组名称" ,required = true)
    private String groupName;

    @ApiModelProperty(value = "公司id" ,required = true)
    private String companyId;

    @ApiModelProperty(value = "投放城市id")
    private String cityIds;

    @ApiModelProperty("代理商户类型 0:全部商户 1：服务商商户 2：一级商户 3：二级商户 4：三级商户 5：分销商户")
    private Integer proxyType;

    @ApiModelProperty("流水类型")
    private Integer turnoverType;

    @ApiModelProperty("笔数至今时间（1:全部时间 ,如果有时间范围就不用传这个参数）")
    private Integer countDateType;

    @ApiModelProperty("流水至今时间（1:全部时间 ,如果有时间范围就不用传这个参数）")
    private Integer sumDateType;

    @ApiModelProperty("1:and 和 2 or 或")
    private Integer conditionType;

    @NotBlank(message = "请输入一级经营类别")
    @ApiModelProperty("一级经营类别")
    private String businessLevOne;// 一级经营类别

    @NotBlank(message = "请输入二级经营类别")
    @ApiModelProperty("二级经营类别")
    private String businessLevTwo;// 二级经营类别

    @NotBlank(message = "请输入三级经营类别")
    @ApiModelProperty("三级经营类别")
    private String businessLevThree;// 三级经营类别

    @ApiModelProperty("开始笔数")
    private Integer startCount;

    @ApiModelProperty("结束笔数")
    private Integer endCount;

    @ApiModelProperty("开始流水")
    private BigDecimal startTurnover;

    @ApiModelProperty("结束流水")
    private BigDecimal endTurnover;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("笔数开始时间")
    private Date countStartTime;//笔数开始时间

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("笔数结束时间")
    private Date countEndTime;//笔数结束时间


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("流水开始时间")
    private Date sumStartTime;//流水开始时间

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @ApiModelProperty("流水结束时间")
    private Date sumEndTime;//流水结束时间

}
