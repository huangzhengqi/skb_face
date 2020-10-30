package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 天阙随行付图片上传VO
 */
@Data
public class UploadPictureVO {

    @ApiModelProperty("返回码")
    private String bizCode;

    @ApiModelProperty("返回信息")
    private String bizMsg;

    @ApiModelProperty("图片路径")
    private String photoUrl;

    public UploadPictureVO(String bizCode, String bizMsg, String photoUrl) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
        this.photoUrl=photoUrl;
    }

    public UploadPictureVO(String bizCode, String bizMsg) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
    }
}
