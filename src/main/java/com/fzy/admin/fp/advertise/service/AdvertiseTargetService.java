package com.fzy.admin.fp.advertise.service;

import com.fzy.admin.fp.advertise.repository.AdvertiseTargetRepository;
import com.fzy.admin.fp.advertise.domain.AdvertiseTarget;
import com.fzy.admin.fp.advertise.repository.AdvertiseTargetRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/7/1 16:14
 * @Description
 */
@Service
public class AdvertiseTargetService implements BaseService<AdvertiseTarget> {

    @Resource()
    private com.fzy.admin.fp.advertise.repository.AdvertiseTargetRepository AdvertiseTargetRepository;

    @Override
    public BaseRepository<AdvertiseTarget> getRepository() {
        return AdvertiseTargetRepository;
    }


    /**
     * 根据广告id删除对应关系
     *
     * @param id
     */
    public void deleteByAdvertiseId(String id) {
        AdvertiseTargetRepository.deleteByAdvertiseId(id);
    }

    public List<AdvertiseTarget> findAllByTargetIdIn(List<String> companyIds) {
        return AdvertiseTargetRepository.findAllByTargetIdIn(companyIds);
    }

    public List<AdvertiseTarget> findAllByCityIds(String city) {
        return AdvertiseTargetRepository.findAllByCityIds(city);
    }

    /**
     * 获取广告投放城市
     * @param id
     * @return
     */
    public List<String> findCitysByAdvId(String id) {
        List<AdvertiseTarget> advertiseTargets = AdvertiseTargetRepository.findAllByAdvertiseId(id);
        return advertiseTargets.stream().map(AdvertiseTarget::getCityIds).distinct().collect(Collectors.toList());
    }
}
