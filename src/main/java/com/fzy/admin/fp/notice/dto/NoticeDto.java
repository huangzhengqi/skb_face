package com.fzy.admin.fp.notice.dto;

import lombok.Data;

@Data
public class NoticeDto {

    private String id;

    private String serviceProviderId;

    private String companyId;

    private String title;

    private String content;
}
