package com.fzy.admin.fp.file.upload.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.file.upload.repository.UploadInfoMd5InfoRelationRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.file.upload.domain.UploadInfoMd5InfoRelation;
import com.fzy.admin.fp.file.upload.repository.UploadInfoMd5InfoRelationRepository;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--14:23
 * @description 关联表服务类
 */
@Slf4j
@Service
@Transactional
public class UploadInfoMd5InfoRelationService implements BaseService<UploadInfoMd5InfoRelation> {
    @Resource
    private UploadInfoMd5InfoRelationRepository uploadInfoMd5InfoRelationRepository;

    @Override
    public UploadInfoMd5InfoRelationRepository getRepository() {
        return this.uploadInfoMd5InfoRelationRepository;
    }

    /**
     * @param uploadInfoMd5InfoRelations 需要变更得关系表List
     * @param fileName                   上传文件名称
     * @param uploadInfoId               上传信息Id
     * @param md5InfoId                  MD5表信息
     * @param rank                       排序值
     * @return void
     * @author ZhangWenJian
     * @date 2019/1/8 14:45
     * @title changeListUploadInfoAndMd5InfoRelations
     * @description
     */
    public void changeListUploadInfoAndMd5InfoRelations(
            List<UploadInfoMd5InfoRelation> uploadInfoMd5InfoRelations,
            String fileName,
            String uploadInfoId,
            String md5InfoId,
            Integer rank) {
        UploadInfoMd5InfoRelation uploadInfoMd5InfoRelation = new UploadInfoMd5InfoRelation(
                uploadInfoId,
                md5InfoId,
                rank
        );
        uploadInfoMd5InfoRelation.setName(fileName);
        uploadInfoMd5InfoRelations.add(uploadInfoMd5InfoRelation);
    }

    public Map<String, String> getFilePathMore(String[] ids) {
        Map<String, String> paths = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            FileInfoVo fileInfoVo = getRepository().queryFileInfoVo(ids[i]);
            String path = fileInfoVo.getPath();
            path = "WorkDir/temp/md5/thumbnail/" + path;
            paths.put(ids[i], path);
        }
        return paths;
    }
}
