package com.fzy.admin.fp.common.validation.validate;


import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.common.web.ParamUtil;

/**
 * @author Created by zk on 2019-01-08 10:27
 * @description
 */
public class NotNullValidate extends AbstractValidate<NotNull, Object> {
    @Override
    public void init(NotNull notNull) {

    }

    @Override
    public boolean isValid(Object o) {
        if (ParamUtil.isBlank(o)) {
            return false;
        }
        return true;
    }
}
