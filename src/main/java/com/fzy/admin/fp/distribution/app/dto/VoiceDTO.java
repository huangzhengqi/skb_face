package com.fzy.admin.fp.distribution.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author fyz lzy
 * @create 2020/7/14 14:14
 * @Description: 语音推送
 */
@Data
public class VoiceDTO {

    @ApiModelProperty("设备SN号")
    private String msn;

    @ApiModelProperty("需要语音播报的字符串，与call_url只能使用一个，无数据时传””")
    private String callContent;

    @ApiModelProperty("需要设备播放的语音文件下载路径，与call_content只能使用一个，无数据时传””。文件格式只能为MP3或WAV格式（MP3要求16bit单声道8000Hz采样率。WAV要求16bit单声道8000Hz采样率）")
    private String callUrl;

    @ApiModelProperty("语音消息有效截止Unix系统时间戳，默认为当前时间延后300s")
    private int expTimestamp;

    @ApiModelProperty("语音播放次数，0 不提示，1 播报1次（默认），3 播报3次，999 循环播放")
    private int cycle;

    @ApiModelProperty("播报中间间隔时间，单位秒，默认2秒")
    private int delay;

}
