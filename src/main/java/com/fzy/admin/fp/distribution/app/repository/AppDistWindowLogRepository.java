package com.fzy.admin.fp.distribution.app.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.distribution.app.vo.DistAppWindowUserDetailsVO;
import com.fzy.admin.fp.distribution.app.vo.DistAppWindowUserListVO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AppDistWindowLogRepository extends BaseRepository<DistWindowLog> {

    DistWindowLog findByUserIdAndAndWindowId(String userId, String windowId);

    DistWindowLog findByWindowIdAndUserId(String windowId, String userId);

    @Query(value="SELECT a.id AS id , a.title AS title , a.begin_time AS beginTime, b.is_read AS isRead FROM lysj_dist_window a  LEFT JOIN  lysj_dist_window_log b ON a.id = b.window_id WHERE 1=1 AND b.service_provider_id = ?1 AND  b.user_id = ?2 AND a.`status` = 1 ORDER BY a.create_time DESC ", nativeQuery=true)
    List<DistAppWindowUserListVO> getDistAppWindowUserList(String serviceProviderId, String userId);

    DistWindowLog deleteByWindowId(String id);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM DistWindowLog WHERE windowId  = (:id) ")
    int deleteByIds(@Param("id") String id);


    @Query(value="SELECT a.id AS id , a.title AS title , a.guidance AS guidance, a.contents AS contents, b.is_read AS isRead FROM lysj_dist_window a  LEFT JOIN  lysj_dist_window_log b ON a.id = b.window_id WHERE 1=1 AND b.window_id = ?1 AND  b.user_id = ?2 AND a.`status` = 1 ORDER BY a.create_time DESC ", nativeQuery=true)
    List<DistAppWindowUserDetailsVO> getUserDetails(String windowId, String userId);

}
