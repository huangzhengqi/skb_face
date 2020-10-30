package com.fzy.admin.fp.merchant.merchant.service;

import cn.hutool.core.util.ImageUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.repository.MchQrCodeRepository;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.fzy.admin.fp.DomainInterface;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MchQrCode;
import com.fzy.admin.fp.merchant.merchant.repository.MchQrCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.math.BigDecimal;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:38 2019/5/9
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class MchQrCodeService implements BaseService<MchQrCode>, DomainInterface {


    @Resource
    private HttpServletRequest httpServletRequest;

    @Value("${qrcode.path}")
    private String qrcodePath;


    @Resource
    private MchQrCodeRepository mchQrCodeRepository;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private StoreService storeService;

    @Override
    public MchQrCodeRepository getRepository() {
        return mchQrCodeRepository;
    }


    public Page<MchQrCode> listRewrite(MchQrCode entity, PageVo pageVo, String userId) {
        //获取当前登录
        MerchantUser user = merchantUserService.findOne(userId);
        entity.setMerchantId(user.getMerchantId());
        //如果当前登录不是商户
        if (!MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            entity.setStoreId(user.getStoreId());
        }
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<MchQrCode> page = mchQrCodeRepository.findAll(specification, pageable);
        for (MchQrCode mchQrCode : page) {
            mchQrCode.setStoreName(storeService.findOne(mchQrCode.getStoreId()).getName());
            mchQrCode.setUserName(merchantUserService.findOne(mchQrCode.getUserId()).getName());
        }
        return page;
    }


    public MchQrCode saveRewrite(MchQrCode entity, String userId) {
        if (ParamUtil.isBlank(entity.getName())) {
            throw new BaseException("名称不能为空");
        }
        if (ParamUtil.isBlank(entity.getMoney())) {
            entity.setMoney(BigDecimal.ZERO);
        }
        //TODO
        if (ParamUtil.isBlank(entity.getUserId())) {
            throw new BaseException("请指定用户");
        }
        //获取当前登录用户的角色类型
        MerchantUser user = merchantUserService.findOne(userId);
        entity.setMerchantId(user.getMerchantId());
        entity.setUserId(entity.getUserId());
        entity.setStatus(MchQrCode.Status.ENABLE.getCode());
        //如果当前登录用户为店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(user.getUserType())) {
            entity.setStoreId(user.getStoreId());
        }
        if (MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            if (ParamUtil.isBlank(entity.getStoreId())) {
                throw new BaseException("请指定门店");
            }
        }
        entity = mchQrCodeRepository.save(entity);
        entity.setQrCode(createQrCode(getDomain() + "/merchant/qrcode/app/judge" + "?id=" + entity.getId(), "qrcode/" + user.getStoreId()));
        mchQrCodeRepository.save(entity);
        return entity;
    }


    public String updateRewrite(MchQrCode entity, String userId) {
        if (ParamUtil.isBlank(entity.getName())) {
            throw new BaseException("名称不能为空");
        }
        MchQrCode mchQrCodeQuery = mchQrCodeRepository.findOne(entity.getId());
        if (ParamUtil.isBlank(entity.getMoney())) {
            entity.setMoney(BigDecimal.ZERO);
        }
        //TODO
        if (ParamUtil.isBlank(entity.getUserId())) {
            throw new BaseException("请指定用户");
        }
        //拷贝信息
        mchQrCodeQuery.setName(entity.getName());
        mchQrCodeQuery.setUserId(entity.getUserId());
        mchQrCodeQuery.setMoney(entity.getMoney());
        mchQrCodeQuery.setDescription(entity.getDescription());
        //获取当前登录用户的角色类型
        MerchantUser user = merchantUserService.findOne(userId);

        //如果当前登录用户为店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(user.getUserType())) {
            mchQrCodeQuery.setStoreId(user.getStoreId());
        }
        if (MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            if (ParamUtil.isBlank(entity.getStoreId())) {
                throw new BaseException("请指定门店");
            }
            mchQrCodeQuery.setStoreId(entity.getStoreId());
        }
        mchQrCodeRepository.save(mchQrCodeQuery);
        return "保存成功";
    }


    public String createQrCode(String url, String storeId) {
        int width = 300; //宽
        int height = 300;//高
        String format = "png";//格式

        String fileName = "";
        String thumbnailPath = "";
        try {
            BitMatrix bitMatrix = QrCodeUtil.encode(url, width, height);
            String path = qrcodePath + storeId + "/"; //默认路径+模块+日期
            //缩略图
            thumbnailPath = path + "/thumbnail/";
            File thumbnailDir = new File(thumbnailPath);
            fileName = ParamUtil.uuid() + ".png";
            File file = new File(path + fileName);//定义图片路径
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            MatrixToImageWriter.writeToFile(bitMatrix, format, file);//在对应路径二维码图片
            if (!thumbnailDir.exists()) {
                thumbnailDir.mkdirs();
            }
            ImageUtil.scale(file, new File(thumbnailPath + fileName), 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storeId + "/thumbnail/" + fileName;
    }


    public String updateStatus(String id, Integer status) {
        MchQrCode mchQrCode = mchQrCodeRepository.findOne(id);
        if (ParamUtil.isBlank(mchQrCode)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        mchQrCode.setStatus(status);
        mchQrCodeRepository.save(mchQrCode);
        return "操作成功";
    }

    @Override
    public HttpServletRequest getRequest() {
        return httpServletRequest;
    }
}
