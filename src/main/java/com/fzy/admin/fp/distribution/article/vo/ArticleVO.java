package com.fzy.admin.fp.distribution.article.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author yy
 * @Date 2020-2-27 09:35:44
 * @Desp
 **/
@Data
public class ArticleVO {
    private String id;

    @ApiModelProperty("咨询内容")
    private String content;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("状态0显示 1隐藏")
    private Integer status;

    @ApiModelProperty("二级分类名称")
    private String secondName;

    @ApiModelProperty("三级分类名称")
    private String thirdName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;//创建时间 数据插入时自动赋值

}
