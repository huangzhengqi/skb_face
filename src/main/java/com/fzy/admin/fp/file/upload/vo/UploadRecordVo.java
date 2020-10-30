package com.fzy.admin.fp.file.upload.vo;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--18:01
 * @description 上传记录vo类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadRecordVo extends BaseEntity {

    private String uploaderId;       //上传者Id
    private String uploadDetails;    //上传统计
    private List<UploadRecordInfoVo> uploadRecordInfoVos; //上传内容具体细节

    public UploadRecordVo() {
    }

    public UploadRecordVo(UploadInfo uploadInfo) {
        setId(uploadInfo.getId());
        setCreateTime(uploadInfo.getCreateTime());
        setDelFlag(uploadInfo.getDelFlag());
        setUpdateTime(uploadInfo.getUpdateTime());
        this.uploaderId = uploadInfo.getUploaderId();
        this.uploadDetails = uploadInfo.getUploadDetails();
    }


    public UploadRecordVo(String uploaderId, String uploadDetails, List<UploadRecordInfoVo> uploadRecordInfoVos) {
        this.uploaderId = uploaderId;
        this.uploadDetails = uploadDetails;
        this.uploadRecordInfoVos = uploadRecordInfoVos;
    }
}
