package com.fzy.admin.fp.common.validation.validate;

import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.validation.annotation.Min;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2018-12-10 17:40
 * @description
 */
public class MinValidate extends AbstractValidate<Min, BigDecimal> {

    @Override
    public void init(Min min) {

    }

    @Override
    public boolean isValid(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return true;
        }
        BigDecimal min;
        if (ParamUtil.isBlank(annotation.value())) {
            min = new BigDecimal(String.valueOf(Long.MIN_VALUE));
        } else {
            min = new BigDecimal(annotation.value());
        }
        //-1 小于 0 等于 1 大于
        if (annotation.minEqual()) {
            //若取等 大于等于
            return bigDecimal.compareTo(min) > (-1);//取 0 1
        } else {
            return bigDecimal.compareTo(min) > 0;//取 1
        }
    }
}
