package com.fzy.admin.fp.distribution.feedback.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-9 11:05:42
 * @Desp
 **/
@Data
public class FeedbackVO {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String name;

    private String userName;

    private String content;

    private String reply;

    private String img;

    private String id;

    public FeedbackVO(String id,Date createTime, String name, String userName, String content, String reply, String img) {
        this.id = id;
        this.createTime = createTime;
        this.name = name;
        this.userName = userName;
        this.content = content;
        this.reply = reply;
        this.img = img;
    }
}
