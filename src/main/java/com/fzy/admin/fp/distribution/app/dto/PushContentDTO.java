package com.fzy.admin.fp.distribution.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author fyz LZY
 * @create 2020/7/14 14:49
 * @Description: 推送订单详情
 */
@Data
public class PushContentDTO {

    @ApiModelProperty("设备SN号")
    private String msn;

    @ApiModelProperty("推送唯一ID，每张打印票据ID不同就认为是不同的打印内容")
    private String pushId;

    @ApiModelProperty("票据语音播放次数，0 不提示，1 播报1次（默认），3 播报3次，999 循环播放")
    private int voiceCnt;

    @ApiModelProperty("需要语音播报的字符串，voice与voiceUrl内容只能使用一个，无数据时传”””")
    private String voice="";

    @ApiModelProperty("需要设备播放的语音文件http下载路径，voice与voiceUrl内容只能使用一个，无数据时传””。文件格式只能为MP3或WAV格式（MP3要求16bit单声道8000Hz采样率。WAV要求16bit单声道8000Hz采样率）")
    private String voiceUrl="";

    @ApiModelProperty("打印票据张数（默认1张）")
    private int orderCnt;

    @ApiModelProperty("打印票据类型，1 新订单，2 取消订单，3 催单，4 退单，5 其它")
    private int orderType;

}
