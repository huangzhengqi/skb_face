package com.fzy.admin.fp.common.validation.validate;

import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;

/**
 * @author Created by zk on 2018-11-26 17:46
 * @description
 */
public class NotBlankValidate extends AbstractValidate<NotBlank, String> {

    @Override
    public void init(NotBlank notBlank) {

    }

    @Override
    public boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        if (annotation.isTrim()) {
            s.trim();
        }
        return !s.equals("");
    }

}
