package com.fzy.admin.fp.ali.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * OCR身份证返回VO参数
 */
@Data
public class IdCardVO {

    @ApiModelProperty(value="经营者/法人姓名")
    private String representativeName;

    @ApiModelProperty(value="身份证号码")
    private String certificateNum;

    @ApiModelProperty(value="身份证开始证件期限")
    private String startCertificateTime;

    @ApiModelProperty(value="身份证结束证件期限")
    private String endCertificateTime;

}
