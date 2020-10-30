package com.fzy.admin.fp.distribution.app.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.app.domain.DistAdvertise;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppDistAdvertiseRepository extends BaseRepository<DistAdvertise> {

    Integer countByServiceProviderIdAndWeightAndTargetRange(String ServiceProviderId, Integer weight,Integer targetRange);

    int countByServiceProviderIdAndTargetRange(String serviceProviderId, Integer targetRange);

    List<DistAdvertise> findByServiceProviderIdAndStatusAndType(String serviceProviderId, Integer status, Integer type);

    DistAdvertise findByServiceProviderIdAndId(String serviceProviderId, String id);

    @Query(value = "SELECT * FROM lysj_dist_advertise WHERE service_provider_id = ?1 AND target_range = ?2 AND ((( ?3 >= begin_time AND ?3 < end_time ) \n" +
            "\tOR ( ?3 < end_time AND ?4 > end_time ) \n" +
            "\tOR ( ?3 <= begin_time AND ?4 > begin_time ) \n" +
            "\t) \n" +
            "\t)",nativeQuery = true)
    DistAdvertise findByGetDistAdvertise(String serviceProviderId, Integer targetRange, Date beginTime, Date endTime);
}
