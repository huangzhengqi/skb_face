package com.fzy.admin.fp.common.service;

import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.domain.Area;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.vo.ProvinceTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:34 2019/7/1
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class CityService {

    @Resource
    private CityRepository cityRepository;
    @Resource
    private AreaRepository areaRepository;
    @Resource
    private ProvinceRepository provinceRepository;


    /**
     * 获取省市区树
     *
     * @param level
     * @return
     */
    public List<ProvinceTree> findTree(Integer level) {
        List<ProvinceTree> resutl = new ArrayList<>();
        if (level.equals(1)) {
            List<Province> provinceList = provinceRepository.findAll();
            for (Province p : provinceList) {
                ProvinceTree provinceTree = new ProvinceTree();
                provinceTree.setId(p.getId());
                provinceTree.setName(p.getProvinceName());
                resutl.add(provinceTree);
            }
        } else if (level.equals(2)) {
            List<Province> provinceList = provinceRepository.findAll();
            List<City> AllCityLits = cityRepository.findAll();
            for (Province p : provinceList) {
                ProvinceTree provinceTree = new ProvinceTree();
                provinceTree.setId(p.getId());
                provinceTree.setName(p.getProvinceName());
                List<ProvinceTree> cityResult = new ArrayList<>();
                List<City> cityList = AllCityLits.stream().filter(c->c.getProvinceId().equals(p.getId())).collect(Collectors.toList());
                for (City c : cityList) {
                    ProvinceTree cityTree = new ProvinceTree();
                    cityTree.setId(c.getId());
                    cityTree.setName(c.getCityName());
                    cityResult.add(cityTree);

                }
                provinceTree.setChildren(cityResult);
                resutl.add(provinceTree);
            }
        } else {
            List<Province> provinceList = provinceRepository.findAllByOrderByProvinceCodeAsc();
            List<City> AllCityLits = cityRepository.findAll();
            List<Area> AllAreaList = areaRepository.findAll();
            for (Province p : provinceList) {
                ProvinceTree provinceTree = new ProvinceTree();
                provinceTree.setId(p.getId());
                provinceTree.setName(p.getProvinceName());
                List<ProvinceTree> cityResult = new ArrayList<>();
                List<City> cityList = AllCityLits.stream().filter(c->c.getProvinceId().equals(p.getId())).collect(Collectors.toList());
                for (City c : cityList) {
                    ProvinceTree cityTree = new ProvinceTree();
                    cityTree.setId(c.getId());
                    cityTree.setName(c.getCityName());
                    cityResult.add(cityTree);
                    List<ProvinceTree> areaResult = new ArrayList<>();
                    List<Area> areaList = AllAreaList.stream().filter(a->a.getCityId().equals(c.getId())).collect(Collectors.toList());
                    for (Area a: areaList) {
                        ProvinceTree areaTree = new ProvinceTree();
                        areaTree.setId(a.getId());
                        areaTree.setName(a.getAreaName());
                        areaResult.add(areaTree);

                    }
                    cityTree.setChildren(areaResult);
                    provinceTree.setChildren(cityResult);

                }
                provinceTree.setChildren(cityResult);
                resutl.add(provinceTree);
            }
        }

        return resutl;
    }
}
