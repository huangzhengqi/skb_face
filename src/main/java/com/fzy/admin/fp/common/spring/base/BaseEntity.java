package com.fzy.admin.fp.common.spring.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zk
 * @description 基础实体类
 * @create 2018-07-15 15:32
 **/
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @author Created by zk on 2018/12/26 10:05
     * @Description 若有需要改动字段名，改动后请到constant.CommonConstant中进行再次修改
     */
    @Id
    @Column(length = 128)
    @GeneratedValue(generator = "snowFlakeIdStrategy")
    @GenericGenerator(name = "snowFlakeIdStrategy", strategy = CommonConstant.SNOW_FLAKE_ID_STRATEGY)
    private String id;//唯一标识






    private String name;//名称

    @ApiModelProperty("添加时间")
    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//将String转换成Date，一般前台给后台传值时用
    private Date createTime;//创建时间 数据插入时自动赋值

    @ApiModelProperty("修改时间")
    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间 数据更新时自动赋值

    @ApiModelProperty("是否删除 0是，1否")
    private Integer delFlag = CommonConstant.NORMAL_FLAG;//删除标志 CommonConstant.NORMAL_FLAG 删除 CommonConstant.DEL_FLAG
}
