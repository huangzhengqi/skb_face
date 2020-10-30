package com.fzy.admin.fp.advertise.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.advertise.repository.RegionCityRepository;
import com.fzy.admin.fp.advertise.repository.StrategicDistributorsRepository;
import com.fzy.admin.fp.advertise.repository.StrategicManagementRepository;
import com.fzy.admin.fp.advertise.repository.StrategicTimeRepository;
import com.fzy.admin.fp.advertise.domain.RegionCity;
import com.fzy.admin.fp.advertise.domain.StrategicDistributors;
import com.fzy.admin.fp.advertise.domain.StrategicManagement;
import com.fzy.admin.fp.advertise.domain.StrategicTime;
import com.fzy.admin.fp.advertise.repository.RegionCityRepository;
import com.fzy.admin.fp.advertise.repository.StrategicDistributorsRepository;
import com.fzy.admin.fp.advertise.repository.StrategicManagementRepository;
import com.fzy.admin.fp.advertise.repository.StrategicTimeRepository;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author lb
 * @date 2019/7/2 17:04
 * @Description
 */
@Service
public class StrategicManagementService implements BaseService<StrategicManagement> {

    @Resource
    private StrategicManagementRepository strategicManagementRepository;

    @Resource
    private StrategicDistributorsRepository strategicDistributorsRepository;

    @Resource
    private StrategicTimeRepository strategicTimeRepository;

    @Resource
    private RegionCityRepository regionCityRepository;

    @Resource
    private CompanyRepository companyRepository;

    @Override
    public BaseRepository<StrategicManagement> getRepository() {
        return strategicManagementRepository;
    }

    public Page<StrategicManagement> getAll(StrategicManagement strategicManagement, PageVo pageVo) {
        Page<StrategicManagement> page = list(strategicManagement, pageVo);
        List<StrategicManagement> strategicManagementList = page.getContent();
        for (StrategicManagement strategicManagement1 : strategicManagementList) {
            String id = strategicManagement1.getId();
            List<StrategicDistributors> list = strategicDistributorsRepository.findByStrategicId(id);
            List<RegionCity> list1 = regionCityRepository.findByStrategicId(id);
            List<StrategicTime> list2 = strategicTimeRepository.findByStrategicId(id);
            strategicManagement1.setDistributors(list);
            strategicManagement1.setCity(list1);
            strategicManagement1.setStrategicTimeList(list2);
        }

        Page<StrategicManagement> page1 = new Page<StrategicManagement>() {
            @Override
            public int getTotalPages() {
                return page.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return page.getTotalElements();
            }

            @Override
            public <S> Page<S> map(Converter<? super StrategicManagement, ? extends S> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return page.getNumber();
            }

            @Override
            public int getSize() {
                return page.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return page.getNumberOfElements();
            }

            @Override
            public List<StrategicManagement> getContent() {
                return strategicManagementList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return page.getSort();
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<StrategicManagement> iterator() {
                return null;
            }
        };
        return page1;

    }


    @Transactional
    public void saveOne(StrategicManagement strategicManagement, String city, String time, String operators, String distributors) {

        //保存策略管理
        StrategicManagement strategicManagement1 = strategicManagementRepository.save(strategicManagement);

        //对关联表进行添加
        saveTool(strategicManagement, strategicManagement1, city, time, operators, distributors);
    }

    @Transactional
    public void updateOne(StrategicManagement strategicManagement, String city, String time, String operators, String distributors) {

        //更新策略管理
        StrategicManagement strategicManagement1 = strategicManagementRepository.findOne(strategicManagement.getId());
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(true);
        BeanUtil.copyProperties(strategicManagement, strategicManagement1, copyOptions);
        StrategicManagement strategicManagement2 = strategicManagementRepository.save(strategicManagement1);

        //对本策略关联的表进行修改
        strategicTimeRepository.deleteByStrategicId(strategicManagement.getId());
        strategicDistributorsRepository.deleteByStrategicId(strategicManagement.getId());
        regionCityRepository.deleteByStrategicId(strategicManagement.getId());
        //对关联表进行添加
        saveTool(strategicManagement, strategicManagement2, city, time, operators, distributors);


    }

    @Transactional
    public void deleteOne(StrategicManagement strategicManagement) {
        strategicManagementRepository.delete(strategicManagement.getId());
        strategicTimeRepository.deleteByStrategicId(strategicManagement.getId());
        strategicDistributorsRepository.deleteByStrategicId(strategicManagement.getId());
        regionCityRepository.deleteByStrategicId(strategicManagement.getId());
    }


    //关联表操作
    public void saveTool(StrategicManagement strategicManagement, StrategicManagement strategicManagement1, String city, String time, String operators, String distributors) {
        if (strategicManagement.getOperatorRange().equals(StrategicManagement.Range.SOME.getCode())) {
            //分割渠道商Id
            String[] distri = distributors.split(",");
            //分割运营商Id
            String[] operato = operators.split(",");
            Set<String> stringSet = new HashSet<>();
            for (String dis : distri) {
                StrategicDistributors sd = new StrategicDistributors();
                sd.setDistributorsId(dis);
                Company company = companyRepository.findOne(dis);
                sd.setDistributorsName(company.getName());
                sd.setStrategicId(strategicManagement1.getId());
                sd.setOperatorId(company.getParentId());
                stringSet.add(company.getParentId());
                Company company1 = companyRepository.findOne(company.getParentId());
                sd.setOperatorName(company1.getName());
                strategicDistributorsRepository.save(sd);
            }
            Set<String> set = new HashSet<>(Arrays.asList(operato));
            set.removeAll(stringSet);
            System.out.println(set);
            //存勾选了但是运营商旗下没有渠道商的商户
            for (String ss : set) {
                StrategicDistributors sd = new StrategicDistributors();
                Company company = companyRepository.findOne(ss);
                sd.setStrategicId(strategicManagement1.getId());
                sd.setDistributorsId(company.getId());
                sd.setDistributorsName(company.getName());
                sd.setOperatorId(company.getId());
                sd.setOperatorName(company.getName());
                strategicDistributorsRepository.save(sd);
            }

        }
        if (strategicManagement.getTimeRange().equals(StrategicManagement.TimeRange.SOME.getCode())) {
            System.out.println(time);
            Map<String, String> timeMap = JacksonUtil.toStringMap(time);
            System.out.println(timeMap);
            for (int i = 0; i < timeMap.size(); i++) {
                System.out.println(JacksonUtil.toJson(timeMap.get(String.valueOf(i))));
                Map<String, String> map = JacksonUtil.toStringMap(JacksonUtil.toJson(timeMap.get(String.valueOf(i))));
                StrategicTime strategicTime = new StrategicTime();
                strategicTime.setStrategicId(strategicManagement1.getId());
                strategicTime.setStartTime(map.get("startTime"));
                strategicTime.setEndTime(map.get("endTime"));
                strategicTimeRepository.save(strategicTime);
            }
        }
        if (strategicManagement.getRegionType().equals(StrategicManagement.RegionType.SOME.getCode())) {
            Map<String, String> citys = JacksonUtil.toStringMap(city);
            for (int i = 0; i < citys.size(); i++) {
                Map<String, String> map = JacksonUtil.toStringMap(JacksonUtil.toJson(citys.get(String.valueOf(i))));
                RegionCity regionCity = new RegionCity();
                regionCity.setStrategicId(strategicManagement1.getId());
                regionCity.setProvince(map.get("province"));
                regionCity.setCityName(map.get("city"));
                regionCityRepository.save(regionCity);
            }
        }
    }

    public StrategicManagement findOne(String id) {
        StrategicManagement strategicManagement = strategicManagementRepository.findOne(id);
        List<StrategicDistributors> list = strategicDistributorsRepository.findByStrategicId(id);
        List<RegionCity> list1 = regionCityRepository.findByStrategicId(id);
        List<StrategicTime> list2 = strategicTimeRepository.findByStrategicId(id);
        strategicManagement.setStrategicTimeList(list2);
        strategicManagement.setCity(list1);
        strategicManagement.setDistributors(list);
        return strategicManagement;
    }


}
