package com.fzy.admin.fp.auth.service;


import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.dto.RateSetDTO;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author drj
 * @date 2019-04-22 9:15
 * @Description :三级代理商管理服务层
 */
@Slf4j
@Service
@Transactional
public class CompanyCommonService implements BaseService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserService userService;


    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;


    @Value("${secret.header}")
    private String header;

    @Resource
    private HttpServletRequest request;

    public CompanyRepository getRepository() {
        return companyRepository;
    }


    /**
     * 获取代理商商列表（）
     *
     * @param model
     * @param pageVo
     * @param userId
     * @param type   4三级 3二级 2一级 1贴牌商
     * @return
     */
    public Page<Company> listRewrite(Company model, PageVo pageVo, String userId, Integer type) {
        Pageable pageable = PageUtil.initPage(pageVo);

        model.setType(type);
        //查询当前登录用户对应的公司,若为一级代理商根据父id查询,服务商根据idPath查询
        User user = userService.findOne(userId);
        Company userCompany = companyRepository.findOne(user.getCompanyId());

        //判断该用户为普通服务商,查询条件增加管理员id
        UserRole userRoleQuery = userRoleService.getRepository().findByUserId(userId);
        Role role = roleService.findOne(userRoleQuery.getRoleId());
        //判断是否是贴牌商
        if (userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
            model.setIdPath(userCompany.getId());
        } else {
            model.setIdPath(userCompany.getIdPath()+userCompany.getId());
        }
        if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
            model.setManagerId(userId);
        }
        //查询
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Company> page = findAll(specification, pageable);
        //获取业务员名称,设置合作到期时间
        for (Company company : page) {
            User user1 = userService.findOne(company.getManagerId());
            //归属业务员
            company.setSaleName(user1.getName());
            Company companyParent = companyRepository.findOne(company.getParentId());
            company.setParentName(companyParent.getName());
            company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));
            if (userCompany.getId().equals(company.getParentId())) {
                company.setIsDirect(1);
            } else {
                company.setIsDirect(0);
            }
        }
        return page;
    }

    /**
     * 详情页代理商列表
     * @param model
     * @param pageVo
     * @return
     */
    public Page<Company> findByParentId(Company model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Company> page = findAll(specification, pageable);
        if (page.getContent().size() < 1) {
            return page;
        }
        //获取业务员名称,设置合作到期时间
        User user = userService.findOne(page.getContent().get(0).getManagerId());
        for (Company company : page) {
            company.setSaleName(user.getName()); //一级代理商名称
        }
        return page;
    }


    public Resp<RateSetDTO> getRate(String id) {
        Company company = companyRepository.findOne(id);
        if (company == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "公司不存在");
        } else {
            try {
                RateSetDTO rateSetDTO = new RateSetDTO();
                BeanUtil.copyProperties(company, rateSetDTO);
                return Resp.success(rateSetDTO, "修改成功");
            } catch (Exception e) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "未知错误");
            }
        }
    }

    /**
     * 分润检查 一二级代理相加不能超过100
     *
     * @param rateSetDTO
     * @return
     */
    private Resp checkRate(RateSetDTO rateSetDTO, Company company) {
        //获取上级利率
        Company parentCompany = companyRepository.findOne(company.getParentId());
        //如果上级是服务商不需要判断下级的利率是否大于自己 因为贴牌商没有利率
        if (!parentCompany.getType().equals(Company.Type.PROVIDERS.getCode())){
            if (rateSetDTO.getZfbPayProrata().compareTo(parentCompany.getZfbPayProrata()) == 1) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝支付通道利率不能大于上级:" + parentCompany.getZfbPayProrata().multiply(new BigDecimal(100)).setScale(2));
            }
            if (rateSetDTO.getWxPayProrata().compareTo(parentCompany.getWxPayProrata()) == 1) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "微信支付通道利率不能大于上级:" + parentCompany.getWxPayProrata().multiply(new BigDecimal(100)).setScale(2));
            }
            if (rateSetDTO.getSxfPayProrata().compareTo(parentCompany.getSxfPayProrata()) == 1) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付通道利率不能大于上级:" + parentCompany.getSxfPayProrata().multiply(new BigDecimal(100)).setScale(2));
            }
            if (rateSetDTO.getFyPayProrata().compareTo(parentCompany.getFyPayProrata()) == 1) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "富有支付通道利率不能大于上级:" + parentCompany.getFyPayProrata().multiply(new BigDecimal(100)).setScale(2));
            }
        }


        //获取所有下级利率
        List<Company> secondCompanys = companyRepository.findByParentId(company.getId());
        //获取下级最大的利率
        BigDecimal wxRate = BigDecimal.ZERO;
        BigDecimal zfbRate = BigDecimal.ZERO;
        BigDecimal sxfRate = BigDecimal.ZERO;
        BigDecimal fyRate = BigDecimal.ZERO;
        for (Company secondCompany : secondCompanys) {
            if (secondCompany.getWxPayProrata().compareTo(wxRate) == 1) {
                wxRate = secondCompany.getWxPayProrata();
            }
            if (secondCompany.getZfbPayProrata().compareTo(zfbRate) == 1) {
                zfbRate = secondCompany.getWxPayProrata();
            }
            if (secondCompany.getSxfPayProrata().compareTo(sxfRate) == 1) {
                sxfRate = secondCompany.getWxPayProrata();
            }
            if (secondCompany.getFyPayProrata().compareTo(fyRate) == 1) {
                fyRate = secondCompany.getWxPayProrata();
            }
        }

        if (rateSetDTO.getZfbPayProrata().compareTo(zfbRate) == -1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝支付通道利率不能小于下级:" + zfbRate.multiply(new BigDecimal(100)).setScale(2));
        }
        if (rateSetDTO.getWxPayProrata().compareTo(wxRate) == -1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信支付通道利率不能小于下级:" + wxRate.multiply(new BigDecimal(100)).setScale(2));
        }
        if (rateSetDTO.getSxfPayProrata().compareTo(sxfRate) == -1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付通道利不能小于下级:" + sxfRate.multiply(new BigDecimal(100)).setScale(2));
        }
        if (rateSetDTO.getFyPayProrata().compareTo(fyRate) == -1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "富有支付通道利率不能小于下级:" + fyRate.multiply(new BigDecimal(100)).setScale(2));
        }

        //不能大于1
        if (rateSetDTO.getZfbPayProrata().compareTo(new BigDecimal(1)) == 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "支付宝支付通道利率不能大于:100.00");
        }
        if (rateSetDTO.getWxPayProrata().compareTo(new BigDecimal(1)) == 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "微信支付通道利率不能大于:100.00");
        }
        if (rateSetDTO.getSxfPayProrata().compareTo(new BigDecimal(1)) == 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "随行付支付通道利率不能大于:100.00");
        }
        if (rateSetDTO.getFyPayProrata().compareTo(new BigDecimal(1)) == 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "富有支付通道利率不能大于:100.00");
        }
        return Resp.success("");
    }

    public Resp rateSet(RateSetDTO rateSetDTO) {
        Company company = companyRepository.findOne(rateSetDTO.getId());
        if (company == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "公司不存在");
        } else {
            try {
                Resp resp = checkRate(rateSetDTO, company);
                if (resp.getCode() != 200) {
                    return resp;
                }
                company.setWxPayProrata(rateSetDTO.getWxPayProrata());
                company.setZfbPayProrata(rateSetDTO.getZfbPayProrata());
                company.setSxfPayProrata(rateSetDTO.getSxfPayProrata());
                company.setFyPayProrata(rateSetDTO.getFyPayProrata());
                company.setTqSxfPayProrata(rateSetDTO.getTqSxfPayProrata());
                companyRepository.save(company);
                return Resp.success("", "修改成功");
            } catch (Exception e) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "未知错误");
            }
        }
    }


    public Long listRewriteCount(Company model, String userId, Integer type) {

        model.setType(type);
        //查询当前登录用户对应的公司,若为一级代理商根据父id查询,服务商根据idPath查询
        User user = userService.findOne(userId);
        Company userCompany = companyRepository.findOne(user.getCompanyId());

        //判断该用户为普通服务商,查询条件增加管理员id
        UserRole userRoleQuery = userRoleService.getRepository().findByUserId(userId);
        Role role = roleService.findOne(userRoleQuery.getRoleId());
        //判断是否是贴牌商
        if (userCompany.getType().equals(Company.Type.PROVIDERS.getCode())) {
            model.setIdPath(userCompany.getId());
        } else {
            model.setIdPath(userCompany.getIdPath()+userCompany.getId());
        }
        if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
            model.setManagerId(userId);
        }
        //查询
        Specification specification = ConditionUtil.createSpecification(model);
        //查询总数
        long count = count(specification);
        log.info("count:" + count);

        return count;
    }

    public Resp advertiseSet(String id, Integer type) {
        Company company = companyRepository.findOne(id);
        if(company == null){
            return new Resp().error(Resp.Status.PARAM_ERROR, "公司不存在");
        }else {
            company.setAdvertiseType(type);
            companyRepository.save(company);
        }
        return Resp.success("", "修改成功");
    }

    public Resp getAdvertise(String id) {
        Company company = companyRepository.findOne(id);
        if(company == null){
            return new Resp().error(Resp.Status.PARAM_ERROR, "公司不存在");
        }
        return Resp.success(company);
    }
}