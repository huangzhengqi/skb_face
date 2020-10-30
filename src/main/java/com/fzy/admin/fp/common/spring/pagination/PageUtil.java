package com.fzy.admin.fp.common.spring.pagination;

import cn.hutool.core.util.StrUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author Exrickx
 */
public class PageUtil {

    public static Pageable initPage(PageVo page) {

        Pageable pageable = null;
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        String sort = page.getPageSort();
        String order = page.getPageOrder();

        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (StrUtil.isNotBlank(sort)) {
            Sort.Direction d;
            if (StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            } else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d, sort);
            pageable = new PageRequest(pageNumber - 1, pageSize, s);
        } else {
            Sort s = new Sort(Sort.Direction.DESC, CommonConstant.CREATE_TIME_NAME);
            pageable = new PageRequest(pageNumber - 1, pageSize, s);
        }
        return pageable;
    }
}
