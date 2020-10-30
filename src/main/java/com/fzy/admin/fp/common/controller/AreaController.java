package com.fzy.admin.fp.common.controller;

import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.domain.Area;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.service.CityService;
import com.fzy.admin.fp.common.vo.ProvinceTree;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "AreaController", tags = {"地区类公共接口"})
public class AreaController {
    @Resource
    private CityRepository cityRepository;
    @Resource
    private AreaRepository areaRepository;
    @Resource
    private ProvinceRepository provinceRepository;

    @Resource
    private CityService cityService;

    @GetMapping("/area")
    @ApiOperation(value = "地区信息", notes = "地区信息")
    public Resp<Area> area (String id){
        Area area = areaRepository.findOne(id);
        return Resp.success(area);
    }

    @GetMapping("/area/list")
    @ApiOperation(value = "地区列表", notes = "地区列表")
    public Resp<List<Area>> areaList (String cityId){
        List<Area> areaList = areaRepository.findAllByCityId(cityId);
        return Resp.success(areaList);
    }

    @GetMapping("/city")
    @ApiOperation(value = "市信息", notes = "市信息")
    public Resp<City> city (String id){
        City city = cityRepository.findOne(id);
        return Resp.success(city);
    }

    @GetMapping("/city/list")
    @ApiOperation(value = "市列表", notes = "市列表")
    public Resp<List<City>> cityList (String provinceId){
        List<City> cityList = cityRepository.findAllByProvinceId(provinceId);
        return Resp.success(cityList);
    }

    @GetMapping("/province")
    @ApiOperation(value = "省信息", notes = "省信息")
    public Resp<Province> province (String id){
        Province province = provinceRepository.findOne(id);
        return Resp.success(province);
    }

    @GetMapping("/province/list")
    @ApiOperation(value = "省列表", notes = "省列表")
    public Resp<List<Province>> provinceList (){
        List<Province> provinceList = provinceRepository.findAll();
        return Resp.success(provinceList);
    }

    @GetMapping("/province/tree")
    @ApiOperation(value = "省市区树", notes = "省市区树")
    public Resp<List<ProvinceTree>> provinceTree (@RequestParam(value = "level")  @ApiParam(value = "1省 2省市 3省市区") Integer level){
        List<ProvinceTree> trees = cityService.findTree(level);
        return Resp.success(trees);
    }

    @GetMapping("/ip")
    @ApiOperation(value = "获取ip",notes = "获取ip")
    public Resp getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return Resp.success(ip);
    }
}
