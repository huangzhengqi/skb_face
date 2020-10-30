package com.fzy.admin.fp.distribution.app.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.order.order.EnumInterface;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author hzq
 * @Date 2020-03-10 10:32:34
 */
@Data
@Setter
@Getter
@Entity
@Table(name = "lysj_dist_window")
public class DistWindow extends CompanyBaseEntity {


    @Getter
    public enum Status implements EnumInterface{
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

    /**
     * 文章标题
     */
    @NotNull(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    private String title;

    /**
     * 引导语
     */
    @ApiModelProperty(value = "引导语")
    private String guidance;

    /**
     * 封面图片
     */
    @ApiModelProperty(value = "封面图片")
    private String imgUrl;

    /**
     * 文章内容
     */
    @NotNull(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容")
    private String contents;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("投放状态：0-禁用中 1-待投放，2-投放中，3-已投放")
    private Integer type;

    @ApiModelProperty(value = "推送状态")
    private Integer status;

    private Integer weight;//排序 值越高，越靠前

}
