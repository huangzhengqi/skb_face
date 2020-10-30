package com.fzy.admin.fp.file.upload.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.repository.UploadInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.domain.UploadInfo;
import com.fzy.admin.fp.file.upload.repository.UploadInfoRepository;
import com.fzy.admin.fp.file.upload.vo.UploadRecordVo;
import com.fzy.admin.fp.file.util.resp.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangWenJian
 * @data 2019/1/4--17:15
 * @description uoloadInfo 服务类
 */
@Slf4j
@Service
@Transactional
public class UploadInfoService implements BaseService<UploadInfo> {


    @Resource
    private UploadInfoRepository uploadInfoRepository;
    @Resource
    public UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Override
    public UploadInfoRepository getRepository() {
        return uploadInfoRepository;
    }

    public Resp listRewrite(UploadInfo entity, PageVo pageVo) {
        MyPage<UploadInfo> page = new MyPage<>(list(entity, pageVo));
        List<UploadInfo> uploadInfos = (List<UploadInfo>) page.getContent();
        List<UploadRecordVo> uploadRecordVos = new ArrayList<>();
        for (UploadInfo uploadInfo : uploadInfos) {
            UploadRecordVo uploadRecordVo = new UploadRecordVo(uploadInfo);
            uploadRecordVo.setUploadRecordInfoVos(uploadInfoMd5InfoRelationService.getRepository().queryUploadRecordInfoVos(uploadRecordVo.getId()));
            uploadRecordVos.add(uploadRecordVo);
        }
        page.setContent(uploadRecordVos);
        return Resp.success(page);
    }

    /**
     * @param uploaderId
     * @return com.lysj.admin.file.upload.domain.UploadInfo
     * @author ZhangWenJian
     * @date 2019/1/8 14:20
     * @title buildUploadInfo
     * @description uploadInfo 实体类构建类
     */
    public UploadInfo buildUploadInfo(String uploaderId, String uploadDetails, String project, String module, boolean result) {
        UploadInfo uploadInfo = new UploadInfo(uploaderId, uploadDetails, project, module, result);
        uploadInfo = uploadInfoRepository.saveAndFlush(uploadInfo);
        return uploadInfo;
    }


    public UploadInfo changeUploadDetails(UploadInfo uploadInfo, String uploadDetails, boolean result) {
        uploadInfo.setUploadDetails(uploadDetails);
        uploadInfo.setResult(result);
        uploadInfo = uploadInfoRepository.saveAndFlush(uploadInfo);
        return uploadInfo;
    }


}
