package com.fzy.admin.fp.common.validation.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by zk on 2018-11-26 17:56
 * @description 校验结果类
 */
@Data
public class BindingResult {
    private boolean flag = true;

    private List<String> message = new ArrayList<String>();
}
