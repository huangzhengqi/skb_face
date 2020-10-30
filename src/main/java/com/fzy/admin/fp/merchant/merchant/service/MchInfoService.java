package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.merchant.merchant.dto.DisMchInfoDTO;
import com.fzy.admin.fp.merchant.merchant.repository.MchInfoRepository;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.dto.MchInfoDTO;
import com.fzy.admin.fp.merchant.merchant.repository.MchInfoRepository;
import com.fzy.admin.fp.merchant.merchant.vo.DisMchInfoVO;
import com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:34 2019/4/30
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class MchInfoService implements BaseService<MchInfo> {

    @Resource
    private MchInfoRepository mchInfoRepository;

    @Resource
    private MerchantService merchantService;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private CompanyService companyService;

    @Resource
    private EquipmentService equipmentService;

    @Override
    public MchInfoRepository getRepository() {
        return mchInfoRepository;
    }


    public Page<MchInfo> listRewrite(String companyId, Integer status, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        //筛选出跟当前公司直联的商户列表
        List<Merchant> merchantList = merchantService.getRepository().findByCompanyIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG);
        //list转string数组
        List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
        String[] strings = new String[merchantIds.size()];
        String[] s = merchantIds.toArray(strings);
        Page<MchInfo> page ;
        if (StringUtils.isEmpty(status)) {
            page = mchInfoRepository.findByMerchantIdIn(s, pageable);
        } else {
            page = mchInfoRepository.findByMerchantIdInAndStatus(s, status,pageable);
        }

        HibernateEntityManager hibernateEntityManager = (HibernateEntityManager) entityManager;
        Session session = hibernateEntityManager.getSession();
        for (MchInfo mchInfo : page.getContent()) {
            Merchant merchant = merchantService.findOne(mchInfo.getMerchantId());
            mchInfo.setMerchantName(merchant.getName());
            mchInfo.setPhone(merchant.getPhone());
            //mchInfo.setStatus(merchant.getStatus());
            //禁止将实体类的修改同步到数据库
            session.evict(mchInfo);
        }
        return page;
    }


    public Page<MchInfo> findByFuwushang(MchInfo model, PageVo pageVo, String serviceProviderId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        model.setServiceProviderId(serviceProviderId);
        Page<MchInfo> page = mchInfoRepository.findByServiceProviderIdAndOrganizationCodeNotNull(serviceProviderId, pageable);
        for (MchInfo mchInfo : page) {
            Merchant merchant = merchantService.findOne(mchInfo.getMerchantId());
            if (!ParamUtil.isBlank(merchant)) {
                mchInfo.setMerchantName(merchant.getName());
                mchInfo.setPhone(merchant.getPhone());
                //mchInfo.setStatus(merchant.getStatus());
            }
        }
        return page;
    }

    public MchInfo findByMerchantId(String merchantId) {
        return mchInfoRepository.findByMerchantId(merchantId);
    }

    public Page<MchInfoVO> findByFuwushangNew(MchInfoDTO mchInfoDTO, PageVo pageVo,String companyId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (StringUtils.isEmpty(mchInfoDTO.getCompanyName())) {
            mchInfoDTO.setCompanyName("");
        }
        if (StringUtils.isEmpty(mchInfoDTO.getName())) {
            mchInfoDTO.setName("");
        }
        Page<MchInfoVO> page = null;
        List<String> merchantIds = companyService.findAllmerchantIds(companyId);
        if(merchantIds ==null ||merchantIds.size() <= 0){
            return page;
        }
        if (StringUtils.isEmpty(mchInfoDTO.getStatus())) {
            page = mchInfoRepository.fuwushangPage(merchantIds, mchInfoDTO.getCompanyName(), mchInfoDTO.getName(), pageable);
        } else {
            page = mchInfoRepository.fuwushangPage(merchantIds, mchInfoDTO.getCompanyName(), mchInfoDTO.getName(), mchInfoDTO.getStatus(), pageable);
        }
        for (MchInfoVO mchInfoVO : page.getContent()) {
            if (companyId.equals(mchInfoVO.getCompanyId())){
                mchInfoVO.setIsDirect(1);
            } else {
                mchInfoVO.setIsDirect(0);
            }
        }
        return page;
    }

    /**
     * 后台管理查看分销商进件列表
     * @param disMchInfoDTO
     * @param pageVo
     * @param serviceProviderId
     * @return
     */
    public Page<DisMchInfoVO> findByFenXiaoNew(DisMchInfoDTO disMchInfoDTO, PageVo pageVo, String serviceProviderId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (StringUtils.isEmpty(disMchInfoDTO.getManagerName())) {
            disMchInfoDTO.setManagerName("");
        }
        if (StringUtils.isEmpty(disMchInfoDTO.getName())) {
            disMchInfoDTO.setName("");
        }
        Page<DisMchInfoVO> page = null;
        if (StringUtils.isEmpty(disMchInfoDTO.getStatus())) {
            page = mchInfoRepository.fenxiaoshangPage(serviceProviderId, disMchInfoDTO.getName(), disMchInfoDTO.getManagerName(), pageable);
        } else {
            page = mchInfoRepository.fenxiaoshangPage(serviceProviderId,  disMchInfoDTO.getStatus(), pageable);
        }
        for(DisMchInfoVO disMchInfoVO:page.getContent()){
            Integer count = equipmentService.getRepository().countByMerchantIdAndStatus(disMchInfoVO.getMerchantId(), 1);
            disMchInfoVO.setEquipmentNum(count);
        }
        return page;
    }


    /**
     * app查询分销的进件列表
     * @param mchInfoDTO
     * @param pageVo
     * @param userId
     * @return
     */
    public Page<MchInfoVO> findByAgentMch(MchInfoDTO mchInfoDTO, PageVo pageVo,String userId) {
        Pageable pageable = PageUtil.initPage(pageVo);
        if (StringUtils.isEmpty(mchInfoDTO.getCompanyName())) {
            mchInfoDTO.setCompanyName("");
        }
        if (StringUtils.isEmpty(mchInfoDTO.getName())) {
            mchInfoDTO.setName("");
        }
        Page<MchInfoVO> page = null;
        List<Merchant> merchants = merchantService.getRepository().findByManagerIdAndTypeOrderByCreateTimeDesc(userId, 1);
        List<String> merchantIds = merchants.stream().map(Merchant::getId).collect(Collectors.toList());
        if(merchantIds ==null ||merchantIds.size() <= 0){
            return null;
        }
        if (StringUtils.isEmpty(mchInfoDTO.getStatus())) {
            page = mchInfoRepository.fuwushangPage(merchantIds, pageable);
        } else {
            page = mchInfoRepository.fuwushangPage(merchantIds, mchInfoDTO.getStatus(), pageable);
        }
        for (MchInfoVO mchInfoVO : page.getContent()) {
            Integer count = equipmentService.getRepository().countByMerchantIdAndStatus(mchInfoVO.getMerchantId(), 1);
            mchInfoVO.setEquipmentNum(count);
        }
        return page;
    }
}
