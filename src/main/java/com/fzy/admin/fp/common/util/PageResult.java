package com.fzy.admin.fp.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * @Author fyz123
 * @create 2020/7/23 20:11
 * @Description:
 */
public class PageResult implements Serializable {

    private int total;//总记录数
    private List rows;//当前页面结果

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public PageResult(int total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
