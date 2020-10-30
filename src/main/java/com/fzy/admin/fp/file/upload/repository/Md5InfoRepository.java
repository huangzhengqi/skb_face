package com.fzy.admin.fp.file.upload.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.file.upload.domain.Md5Info;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--11:47
 * @description
 */
public interface Md5InfoRepository extends BaseRepository<Md5Info> {
    Md5Info findByMd5(String md5);
}
