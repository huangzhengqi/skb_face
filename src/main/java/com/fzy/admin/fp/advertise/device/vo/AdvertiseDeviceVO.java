package com.fzy.admin.fp.advertise.device.vo;

import lombok.Data;

@Data
public class AdvertiseDeviceVO {

    private String id;

    private String name;

    private String companyId;

    private Data beginTime;

    private Data endTime;

    public AdvertiseDeviceVO(String id, String name, String companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }
}
