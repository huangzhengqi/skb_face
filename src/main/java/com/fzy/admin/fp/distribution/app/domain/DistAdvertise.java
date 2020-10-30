package com.fzy.admin.fp.distribution.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 分销app广告类
 */
@Data
@Entity
@Table(name = "lysj_dist_advertise")
public class DistAdvertise extends CompanyBaseEntity {

    @Getter
    public enum Status implements EnumInterface {
        DISABLE(1, "启用"),
        ENABLED(2, "禁用");

        private Integer code;
        private String status;

        Status(Integer code,String status){
            this.code = code;
            this.status =status;
        }
    }

    @Getter
    public enum Type implements EnumInterface{
        DISABLED(0,"禁用中"),
        STAYLAUNCH(1,"待投放"),
        INLAUNCH(2,"投放中"),
        ENDLAUNCH(3,"已投放");

        private Integer code;
        private String status;

        Type(Integer code,String status){
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum TargetRange implements EnumInterface{
        STARTPAGE (0,"启动页"),
        HOMEPAGE(1,"首页");

        private Integer code;
        private String status;

        TargetRange(Integer code,String status){
            this.code = code;
            this.status = status;
        }
    }

    /**
     * 生效时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("投放状态：0-禁用中 1-待投放，2-投放中，3-已过期")
    private Integer type;

    @ApiModelProperty(value = "推送状态")
    private Integer status;

    @ApiModelProperty(value = "排序值")
    private Integer weight;//排序 值越高，越靠前

    @ApiModelProperty(value = "广告图片url")
    private String imageUrl;

    @ApiModelProperty(value = "广告图片跳转链接")
    private String imageLink;

    @ApiModelProperty(value = "是否跳转 1是 0不是")
    private Integer isJump;

    @ApiModelProperty("投放位置 0启动页  1首页")
    private Integer targetRange;
}
