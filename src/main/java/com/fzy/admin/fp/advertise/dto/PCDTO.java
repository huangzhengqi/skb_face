package com.fzy.admin.fp.advertise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lb
 * @date 2019/7/11 10:31
 * @Description
 */
@Data
@AllArgsConstructor
public class PCDTO {

    private String id;//投放ID
    private String imageId;//图片ID
    private String imageLink;//链接
    private Integer type;//广告类型
}
