package com.fzy.admin.fp.advertise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author: huxiangqiang
 * @since: 2019/7/16
 */
@Data
public class AdvertiseViewListVO {


    @ApiModelProperty(value = "广告标题")
    private String title;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "生效时间")
    private Date createTime;

    public AdvertiseViewListVO() {

    }

    public AdvertiseViewListVO(String title, Date createTime) {
        this.createTime = createTime;
        this.title = title;
    }
}