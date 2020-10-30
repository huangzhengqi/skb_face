package com.fzy.admin.fp.common.validation.validate;


import com.fzy.admin.fp.common.validation.annotation.Max;
import com.fzy.admin.fp.common.validation.annotation.Max;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2018-12-10 17:30
 * @description
 */
public class MaxValidate extends AbstractValidate<Max, BigDecimal> {


    @Override
    public void init(Max max) {

    }

    @Override
    public boolean isValid(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return true;
        }
        BigDecimal max;
        if (ParamUtil.isBlank(annotation.value())) {
            max = new BigDecimal(String.valueOf(Long.MAX_VALUE));
        } else {
            max = new BigDecimal(annotation.value());
        }
        //-1 小于 0 等于 1 大于
        if (annotation.maxEqual()) {
            //若取等 小于等于
            return bigDecimal.compareTo(max) < 1;//取 0  -1
        } else {
            return bigDecimal.compareTo(max) < 0;//取 -1
        }
    }
}
