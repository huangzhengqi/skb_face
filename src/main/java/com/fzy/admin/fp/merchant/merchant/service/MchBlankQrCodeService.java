package com.fzy.admin.fp.merchant.merchant.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MchBlankQrCode;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.dto.MchBlankQrCodeDTO;
import com.fzy.admin.fp.merchant.merchant.repository.MchBlankQrCodeRepository;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MchBlankQrCodeService implements BaseService<MchBlankQrCode> {

    @Resource
    private MchBlankQrCodeRepository mchBlankQrCodeRepository;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private StoreService storeService;

    @Override
    public MchBlankQrCodeRepository getRepository() {
        return mchBlankQrCodeRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Resp saveMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        String[] split = mchBlankQrCodeDTO.getQrcode().split(",");
        for (int i = 0; i < split.length; i++) {
            MchBlankQrCode mchBlankQrCode = mchBlankQrCodeRepository.findByQrCodeId(split[i]);
            if (mchBlankQrCode != null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Resp().error(Resp.Status.PARAM_ERROR, "二维码id" + mchBlankQrCode.getQrCodeId() + "已存在");
            }
            Merchant merchant = merchantService.getRepository().findOne(mchBlankQrCodeDTO.getMerchantId());
            if (ParamUtil.isBlank(merchant)) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户不存在");
            }
            MerchantUser merchantUser = merchantUserService.getRepository().findByUsernameAndUserType(merchant.getPhone(), MerchantUser.UserType.MERCHANT.getCode());
            Store store = storeService.getRepository().findOne(merchantUser.getStoreId());
            MchBlankQrCode mchBlankQrCode1 = new MchBlankQrCode();
            mchBlankQrCode1.setMerchantId(merchant.getId());
            mchBlankQrCode1.setQrCodeId(split[i]);
            mchBlankQrCode1.setStatus(mchBlankQrCodeDTO.getStatus());
            mchBlankQrCode1.setUserId(merchantUser.getId());
            mchBlankQrCode1.setUserName(merchantUser.getUsername());
            mchBlankQrCode1.setStoreId(store.getId());
            mchBlankQrCode1.setStoreName(store.getName());
            mchBlankQrCodeRepository.save(mchBlankQrCode1);
        }
        return Resp.success("添加成功");

    }

    public List<MchBlankQrCode> queryMerQrcode(String merchantId) {
        return mchBlankQrCodeRepository.findByMerchantId(merchantId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Resp updateMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        //添加的时候,查找二维码是否存在,不包含当前的,二维码Id,
        //以前的二维码
        if (ParamUtil.isBlank(mchBlankQrCodeDTO.getMerchantId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户Id不能为空");
        }
        List<MchBlankQrCode> mchBlankQrCodeList = mchBlankQrCodeRepository.findByMerchantId(mchBlankQrCodeDTO.getMerchantId());
        List<String> mchBlanckQrcodes = mchBlankQrCodeList.stream().map(MchBlankQrCode::getQrCodeId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        String[] qrcodes = mchBlankQrCodeDTO.getQrcode().split(",");
        List<MchBlankQrCode> mchBlankQrCodes = new ArrayList<>();
        for (int i = 0; i < qrcodes.length; i++) {
            //二维码查找,判断是否存在
            if (mchBlanckQrcodes.size() == 0) {
                MchBlankQrCode mchBlankQrCode = mchBlankQrCodeRepository.findByQrCodeId(qrcodes[i]);
                if (mchBlankQrCode != null) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "二维码id:" + mchBlankQrCode.getQrCodeId() + "已存在");
                }
            } else {
                MchBlankQrCode mchBlankQrCode = mchBlankQrCodeRepository.getMchBlankQrCodeList(qrcodes[i], mchBlanckQrcodes);
                if (mchBlankQrCode != null) {
                    return new Resp().error(Resp.Status.PARAM_ERROR, "二维码id:" + mchBlankQrCode.getQrCodeId() + "已存在");
                }
            }
            Merchant merchant = merchantService.getRepository().findOne(mchBlankQrCodeDTO.getMerchantId());
            if (ParamUtil.isBlank(merchant)) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "商户不存在");
            }
            MerchantUser merchantUser = merchantUserService.getRepository().findByUsernameAndUserType(merchant.getPhone(), MerchantUser.UserType.MERCHANT.getCode());
            Store store = storeService.getRepository().findOne(merchantUser.getStoreId());
            MchBlankQrCode blankQrCode = new MchBlankQrCode();
            blankQrCode.setMerchantId(merchant.getId());
            blankQrCode.setQrCodeId(qrcodes[i]);
            blankQrCode.setStatus(mchBlankQrCodeDTO.getStatus());
            blankQrCode.setUserId(merchantUser.getId());
            blankQrCode.setUserName(merchantUser.getUsername());
            blankQrCode.setStoreId(store.getId());
            blankQrCode.setStoreName(store.getName());
            mchBlankQrCodes.add(blankQrCode);
        }
        mchBlankQrCodeRepository.deleteInBatch(mchBlankQrCodeList);
        mchBlankQrCodeRepository.save(mchBlankQrCodes);
        return Resp.success("修改成功");
    }


    @Transactional(rollbackFor = Exception.class)
    public Resp addMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        MchBlankQrCode mchBlankQrCode = mchBlankQrCodeRepository.findByQrCodeId(mchBlankQrCodeDTO.getQrcode());
        if (mchBlankQrCode != null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "二维码id" + mchBlankQrCode.getQrCodeId() + "已绑定");
        }
        MchBlankQrCode mchBlankQrCode1 = new MchBlankQrCode();
        mchBlankQrCode1.setMerchantId(mchBlankQrCodeDTO.getMerchantId());
        mchBlankQrCode1.setQrCodeId(mchBlankQrCodeDTO.getQrcode());
        mchBlankQrCode1.setStatus(mchBlankQrCodeDTO.getStatus());
        mchBlankQrCode1.setUserId(mchBlankQrCodeDTO.getUserId());
        mchBlankQrCode1.setStoreId(mchBlankQrCodeDTO.getStoreId());
        mchBlankQrCodeRepository.save(mchBlankQrCode1);
        return Resp.success("添加成功");
    }

    public String untieMerchantQrcode(String id) {
        mchBlankQrCodeRepository.delete(id);
        return "解绑成功";
    }
}
