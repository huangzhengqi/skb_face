package com.fzy.admin.fp.common.validation.validate;

import cn.hutool.core.util.ReUtil;
import com.fzy.admin.fp.common.validation.annotation.Pattern;
import com.fzy.admin.fp.common.validation.annotation.Pattern;

/**
 * @author Created by zk on 2018-12-10 17:53
 * @description
 */
public class PatternValidate extends AbstractValidate<Pattern, String> {

    @Override
    public void init(Pattern pattern) {

    }

    @Override
    public boolean isValid(String s) {
        if (s == null) {
            return true;
        }
        return ReUtil.isMatch(annotation.pattern(), s);
    }
}
