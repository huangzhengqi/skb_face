package com.fzy.admin.fp.file.util.resp;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * @author ZhangWenJian
 * @data 2019/1/9--10:11
 * @description
 */
@Data
public class MyPage<T> {

    private Object content;
    private boolean first;
    private boolean last;
    private int number;
    private int numberOfElements;
    private int size;
    private Sort sort;
    private long totalElements;
    private int totalPages;

    public MyPage() {
    }

    public MyPage(Page<T> page) {
        this.content = page.getContent();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
        this.sort = page.getSort();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public MyPage(Object content, boolean first, boolean last, int number, int numberOfElements, int size, Sort sort, long totalElements, int totalPages) {
        this.content = content;
        this.first = first;
        this.last = last;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.sort = sort;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
