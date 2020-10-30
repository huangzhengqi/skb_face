package com.fzy.admin.fp.advertise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lb
 * @date 2019/7/9 18:53
 * @Description 返回广告需要数据
 */
@Data
@AllArgsConstructor
public class OnManagementDTO {

    private String id;//投放ID
    private String imageId;//图片ID
    private String imageLink;//链接

}
