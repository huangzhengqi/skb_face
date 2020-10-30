package com.fzy.admin.fp.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ author ：drj.
 * @ Date  ：Created in 16:06 2019/4/19
 * @ Description:
 **/
@Data
public class CompanyVO {
    private String id;

    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;



    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    private Integer delFlag;


    private String contact;//联系人

    private String phone;//手机

    private Integer type;// 公司类型

    private Integer status;//状态

    private Integer cooperationLev;//合作级别

    private String province;//省

    private String city;// 市

    private String provinceName;//省

    private String cityName;// 市

    private BigDecimal payProrata;// 分佣比例

    private String address;// 联系地址

    private BigDecimal wechatReward;// 微信奖励

    private BigDecimal alipayReward;// 支付宝奖励

    private String parentId; //上级id null为服务商

    private String managerId;//业务员id



    private String saleName;//业务员


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCooperaTime;// 到期时间


    private String parentName;//上级名称


    private String idPath;//约定格式：|服务商Id|一级代理商Id| 如：|1|2|

}
