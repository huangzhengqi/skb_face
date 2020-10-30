package com.fzy.admin.fp.common.validation.validate;

import com.fzy.admin.fp.common.validation.annotation.Length;
import com.fzy.admin.fp.common.validation.annotation.Length;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.math.BigDecimal;

/**
 * @author Created by zk on 2019-01-18 23:37
 * @description
 */
public class LengthValidate extends AbstractValidate<Length, String> {

    @Override
    public void init(Length length) {

    }

    @Override
    public boolean isValid(String s) {
        if (ParamUtil.isBlank(s)) {
            return true;
        }
        BigDecimal min;
        BigDecimal max;
        boolean minResult;
        boolean maxResult;
        BigDecimal stringLength = new BigDecimal(s.length());
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
            minResult = (stringLength.compareTo(min) > -1);
        } else {
            minResult = (stringLength.compareTo(min) > 0);
        }
        if (!minResult) {
            return false;
        }
        if (annotation.maxEqual()) {
            maxResult = (stringLength.compareTo(max) < 1);
        } else {
            maxResult = (stringLength.compareTo(max) < 0);
        }
        if (!maxResult) {
            return false;
        }
        return true;
    }
}
