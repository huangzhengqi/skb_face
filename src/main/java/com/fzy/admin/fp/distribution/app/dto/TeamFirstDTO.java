package com.fzy.admin.fp.distribution.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yy
 * @Date 2020-1-14 11:32:13
 * @Desp
 **/
@Data
public class TeamFirstDTO {
    @ApiModelProperty("type=0查询直邀列表 type=1查询间邀列表 type=2查询其他")
    private Integer type;

    @ApiModelProperty("直邀id，查直邀明细时传的用户id")
    private String id;

    @ApiModelProperty("排序方式")
    private String sort;

    @ApiModelProperty("用户名、手机号、邀请码")
    private String param;
}
