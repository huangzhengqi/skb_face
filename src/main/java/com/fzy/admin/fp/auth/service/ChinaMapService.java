package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.domain.ChinaMapVO;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.repository.ChinaMapRepository;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * --中国地图数据采集服务层
 * --版权所有-锋之云科技
 * --作者-曹拴拴
 * --时间-2019-12-06
 */

@Slf4j
@Service
@Transactional
public class ChinaMapService implements BaseService<Company> {
    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private ChinaMapRepository chinaMapRepository;

    @Resource
    private UserService userService;

    @Override
    public CompanyRepository getRepository() {
        return companyRepository;
    }

    public List<ChinaMapVO> countByOrg(String userId, Integer showtype) {
        List<Object[]> page = new ArrayList();
        List<ChinaMapVO> chinaMap = new ArrayList<ChinaMapVO>();
        User user = userService.findOne(userId);
        Company userCompany = companyRepository.findOne(user.getCompanyId());
        //查询全部
        if(showtype.equals(1)){
            page = chinaMapRepository.getChinaMapAgent(userCompany.getId());
        }else if(showtype.equals(2)){
            //服务商商户
            if(userCompany.getType().equals(Company.Type.PROVIDERS.getCode())){
                page = chinaMapRepository.getChinaProvidersMapMerchant(userCompany.getId());
            //总管理员
            }else if(userCompany.getType().equals(Company.Type.ADMIN.getCode())){
                page = chinaMapRepository.getChinaAdminMapMerchant();
            }else {
                page = chinaMapRepository.getChinaCompanyMapMerchant(userCompany.getId());
            }
        }
        for (Object[] obj:page){
            ChinaMapVO chinaMapVO = new ChinaMapVO();
            chinaMapVO.setName(String.valueOf(obj[0]));
            chinaMapVO.setValue((BigInteger) obj[1]);
            chinaMap.add(chinaMapVO);
        }
        return chinaMap;
    }
}