package com.fzy.admin.fp.file.upload.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.file.upload.domain.UploadInfoMd5InfoRelation;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.file.upload.vo.UploadRecordInfoVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--14:23
 * @description
 */
public interface UploadInfoMd5InfoRelationRepository extends BaseRepository<UploadInfoMd5InfoRelation> {
    @Query(value = "select new com.fzy.admin.fp.file.upload.vo.UploadRecordInfoVo(a.name,b.name, b.path, b.md5)from UploadInfoMd5InfoRelation a,Md5Info b where a.uploadInfoId = ?1 and a.md5InfoId = b.id order by a.rank asc")
    List<UploadRecordInfoVo> queryUploadRecordInfoVos(String uploadInfoId);

    @Query(value = "select new com.fzy.admin.fp.file.upload.vo.FileInfoVo(a.id,a.name,b.project,b.module,c.name,c.path) " +
            "from UploadInfoMd5InfoRelation a,UploadInfo b,Md5Info c where a.md5InfoId = c.id and a.uploadInfoId = b.id and a.id = ?1")
    FileInfoVo queryFileInfoVo(String uploadInfoMd5InfoRelationId);

}
