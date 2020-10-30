package com.fzy.admin.fp.notice.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hzq
 * @date 2019-10-15 10:08:10
 * @Description
 */
public interface NoticeRepository extends BaseRepository<Notice> {


}
