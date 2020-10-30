package com.fzy.admin.fp.common.validation.validate;


import com.fzy.admin.fp.common.validation.annotation.Range;
import com.fzy.admin.fp.common.validation.annotation.Range;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2018-12-10 15:53
 * @description
 */
public class RangeValidate extends AbstractValidate<Range, BigDecimal> {


    @Override
    public void init(Range range) {

    }

    @Override
    public boolean isValid(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return true;
        }
        BigDecimal min;
        BigDecimal max;
        boolean minResult;
        boolean maxResult;
        //若没有赋予最小值，则取Long最小值
        if (ParamUtil.isBlank(annotation.min())) {
            min = new BigDecimal(String.valueOf(Long.MIN_VALUE));
        } else {
            min = new BigDecimal(annotation.min());
        }
        //若没有赋予最大值，则取Long最大值
        if (ParamUtil.isBlank(annotation.max())) {
            max = new BigDecimal(String.valueOf(Long.MAX_VALUE));
        } else {
            max = new BigDecimal(annotation.max());
        }
        //返回的结果是int类型，-1表示小于，0是等于，1是大于。
        //判断小于等于或小于
        if (annotation.minEqual()) {
            minResult = (bigDecimal.compareTo(min) > -1);
        } else {
            minResult = (bigDecimal.compareTo(min) > 0);
        }
        if (!minResult) {
            return false;
        }
        if (annotation.maxEqual()) {
            maxResult = (bigDecimal.compareTo(max) < 1);
        } else {
            maxResult = (bigDecimal.compareTo(max) < 0);
        }
        if (!maxResult) {
            return false;
        }
        return true;
    }
}
