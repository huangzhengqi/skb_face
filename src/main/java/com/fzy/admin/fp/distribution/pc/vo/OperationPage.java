package com.fzy.admin.fp.distribution.pc.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author yy
 * @Date 2020-1-16 17:33:46
 * @Desp
 **/
@Data
public class OperationPage {
    private Integer totalPages;

    private Long totalElements;

    private List<OperationVO> operationVOList;
}
