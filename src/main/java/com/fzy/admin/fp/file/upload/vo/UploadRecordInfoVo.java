package com.fzy.admin.fp.file.upload.vo;

import lombok.Data;

/**
 * @author ZhangWenJian
 * @data 2019/1/9--9:30
 * @description 上传记录详情vo类
 */
@Data
public class UploadRecordInfoVo {
    private String fileName;
    private String pathName; //本地文件名称
    private String path;
    private String md5;

    public UploadRecordInfoVo() {
    }

    public UploadRecordInfoVo(String fileName, String pathName, String path, String md5) {
        this.fileName = fileName;
        this.pathName = pathName;
        this.path = path;
        this.md5 = md5;
    }
}
