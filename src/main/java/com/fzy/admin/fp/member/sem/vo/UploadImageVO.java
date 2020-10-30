package com.fzy.admin.fp.member.sem.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel
@Data
public class UploadImageVO {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "绝对路径")
    private String path;

    @ApiModelProperty(value = "支付宝图片ID")
    private String imageId;

    @ApiModelProperty(value = "支付宝图片路径")
    private String imageUrl;
}
