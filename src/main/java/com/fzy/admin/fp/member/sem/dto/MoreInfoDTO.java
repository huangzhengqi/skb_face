package com.fzy.admin.fp.member.sem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MoreInfoDTO {

    /**
     * more_info 扩展信息(可选)
     */
    @ApiModelProperty(value = "二级界面标题operate_type为openNative时有效")
    private String title;//必填

    @ApiModelProperty(value = "超链接选择openweb的时候必须填写url参数内容")
    private String url;

    @ApiModelProperty(value = "扩展参数，需要URL地址回带的值")
    private String params;

    @ApiModelProperty(value="选择opennative的时候必须填写descs的内容")
    private String[] descs;

}
