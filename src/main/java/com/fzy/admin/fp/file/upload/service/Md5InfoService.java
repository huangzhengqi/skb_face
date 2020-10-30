package com.fzy.admin.fp.file.upload.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.file.upload.repository.Md5InfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.file.upload.domain.Md5Info;
import com.fzy.admin.fp.file.upload.repository.Md5InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author ZhangWenJian
 * @data 2019/1/8--11:47
 * @description md5Info 服务类
 */
@Slf4j
@Service
@Transactional
public class Md5InfoService implements BaseService<Md5Info> {

    @Resource
    private Md5InfoRepository md5InfoRepository;

    @Override
    public Md5InfoRepository getRepository() {
        return md5InfoRepository;
    }

    /**
     * @param md5
     * @param descFileName
     * @return com.lysj.admin.file.upload.domain.Md5Info
     * @author ZhangWenJian
     * @date 2019/1/8 14:18
     * @title buildMd5InfoBean
     * @description md5Info 实体类构建
     */
    public Md5Info buildMd5InfoBean(String md5, String path, String descFileName) {
        Md5Info md5Info = new Md5Info();
        md5Info.setMd5(md5);
        md5Info.setPath(path);
        md5Info.setName(descFileName);
        md5Info = md5InfoRepository.saveAndFlush(md5Info);
        return md5Info;
    }

}
