package com.fzy.admin.fp.member.sem.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplateStyleInfoDTO {

    /**
     * template_style_info  模板样式信息(必选)
     */

    @ApiModelProperty(value = "钱包端显示名称（字符串长度)")
    private String card_show_name; //花呗联名卡 必填

    @ApiModelProperty(value = "商家LOGO图片ID")
    private String logo_id;//必填

    @ApiModelProperty(value = "卡片颜色")
    private String color;

    @ApiModelProperty(value = "背景图片ID,需先上传图片")
    private String background_id;//必填

    @ApiModelProperty(value = "字体颜色")
    private String bg_color;//必填

    @ApiModelProperty(value = "设置是否在卡面展示文案信息，默认不展示")
    private Boolean front_text_list_enable;

    @ApiModelProperty(value = "设置是否展示个人头像")
    private Boolean front_image_enable;

    @ApiModelProperty(value = "特色信息,用于领卡预览")
    private String[] feature_descriptions;

    @ApiModelProperty(value = "标语")
    private String slogan;

    @ApiModelProperty(value = "标语图片")
    private String slogan_img_id;

    @ApiModelProperty(value = "品牌商名称")
    private String brand_name;

    @ApiModelProperty(value = "banner图片地址")
    private String banner_img_id;

    @ApiModelProperty(value = "banner跳转地址")
    private String banner_url;

    @ApiModelProperty(value = "栏位信息布局值为list或grid,默认list")
    private String column_info_layout;

}
