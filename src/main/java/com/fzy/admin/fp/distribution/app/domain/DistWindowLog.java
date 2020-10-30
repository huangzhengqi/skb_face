package com.fzy.admin.fp.distribution.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 弹窗记录表
 */
@Data
@Getter
@Setter
@Entity
@Table(name = "lysj_dist_window_log")
public class DistWindowLog extends CompanyBaseEntity {

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


    @ApiModelProperty(value = "窗口id")
    private String windowId;

    /**
     * 生效时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "生效时间不能为空")
    @Transient
    @ApiModelProperty(value = "生效时间")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    @NotNull(message = "结束时间不能为空")
    @Transient
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 文章标题
     */
    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    @Transient
    private String title;

    /**
     * 引导语
     */
    @ApiModelProperty(value = "引导语")
    @Transient
    private String guidance;

    /**
     * 封面
     */
    @ApiModelProperty(value = "封面图片")
    @Transient
    private String imgUrl;

    /**
     * 文章内容
     */
    @NotNull(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容")
    @Transient
    private String contents;

    @ApiModelProperty("是否已经弹窗了 0未弹窗 1已弹窗")
    private Integer type;

    @ApiModelProperty("是否已读了 0未读 1已读")
    private Integer isRead;

    @ApiModelProperty(value = "推送状态")
    @Transient
    private Integer status;

}
