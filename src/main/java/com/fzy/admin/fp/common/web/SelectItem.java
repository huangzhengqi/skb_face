package com.fzy.admin.fp.common.web;

import lombok.Data;

@Data
public class SelectItem {

    private String value;
    private String name;

    public SelectItem() {
    }

    public SelectItem(String value) {
        this.value = value;
    }

    public SelectItem(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
