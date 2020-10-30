package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.DisOrderDTO;
import com.fzy.admin.fp.auth.dto.EquipmenSearchDTO;
import com.fzy.admin.fp.auth.dto.EquipmentDTO;
import com.fzy.admin.fp.auth.repository.EquipmentRepository;
import com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO;
import com.fzy.admin.fp.auth.vo.EquipmentSummaryVO;
import com.fzy.admin.fp.auth.vo.EquipmentVO;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentService extends Object implements BaseService<Equipment> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentService.class);


    @Resource
    private EquipmentRepository equipmentRepository;


    @Resource
    private CompanyService companyService;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private MerchantService merchantService;

    @Resource
    private DistUserService distUserService;

    @Resource
    private UserService userService;

    @Override
    public EquipmentRepository getRepository() {
        return this.equipmentRepository;
    }


    public Page<EquipmentVO> getPage(String companyId, EquipmentDTO equipmentDTO, String merchantId, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        List<String> allmerchantIds = new ArrayList<String>();
        if (merchantId == null) {
            allmerchantIds = this.companyService.findAllmerchantIds(companyId);
        } else {
            allmerchantIds.add(merchantId);
        }
        allmerchantIds.add("-9999999");
        if (equipmentDTO.getType() == null) {
            return this.equipmentRepository.getPage(pageable, allmerchantIds, equipmentDTO.getKeyWord());
        }
        return this.equipmentRepository.getPage(pageable, allmerchantIds, equipmentDTO.getKeyWord(), equipmentDTO.getType());
    }


    public Page<EquipmentVO> getPage1(String companyId, EquipmentDTO equipmentDTO, String merchantId, PageVo pageVo) {
        Page<EquipmentVO> page;
        Pageable pageable = PageUtil.initPage(pageVo);
        List<String> allmerchantIds = new ArrayList<String>();
        Merchant merchant = new Merchant();
       //商户ID等于空查询服务商下面的所有的商户设备列表
        if (merchantId == null) {
            allmerchantIds = this.companyService.findAllmerchantIds(companyId);
        } else {
            allmerchantIds.add(merchantId);
            merchant=merchantService.getRepository().findOne(merchantId);
        }
            //服务商
        if(null==merchant.getType()){
            if (equipmentDTO.getType() == null) {
                page = this.equipmentRepository.getPage2(pageable, allmerchantIds, equipmentDTO.getKeyWord());
            } else {
                page = this.equipmentRepository.getPage2(pageable, allmerchantIds, equipmentDTO.getKeyWord(), equipmentDTO.getType());
            }
        }else if(Merchant.Type.DIST.getCode().equals(merchant.getType())){
            //分销商户
            if (equipmentDTO.getType() == null) {
                page = this.equipmentRepository.getPage3(pageable, allmerchantIds, equipmentDTO.getKeyWord());
            } else {
                page = this.equipmentRepository.getPage3(pageable, allmerchantIds, equipmentDTO.getKeyWord(), equipmentDTO.getType());
            }

        } else {
            //普通商户
            if (equipmentDTO.getType() == null) {
                page = this.equipmentRepository.getPage1(pageable, allmerchantIds, equipmentDTO.getKeyWord());
            } else {
                page = this.equipmentRepository.getPage1(pageable, allmerchantIds, equipmentDTO.getKeyWord(), equipmentDTO.getType());
            }
        }
        for (EquipmentVO equipmentVO : page.getContent()) {
            //服务商查询所属业务员
            if(null != equipmentVO.getType()){
                if("1".equals(equipmentVO.getType().toString())){
                    DistUser distUser= distUserService.getRepository().getOne(equipmentVO.getManagerId());
                    equipmentVO.setCompanyName(distUser.getName());
                }
                if("0".equals(equipmentVO.getType().toString())){
                    User user= userService.getRepository().findOne(equipmentVO.getManagerId());
                    equipmentVO.setCompanyName(user.getName());
                }
            }
            List<Order> orders = this.orderRepository.findByEquipmentId(equipmentVO.getId());
            if (orders.size() == 0) {
                equipmentVO.setActPayPrice(BigDecimal.ZERO);
                equipmentVO.setOrderCount(0L);
                continue;
            }
            BigDecimal actPayPrice = new BigDecimal(0);
            long orderCount = 0L;
            for (Order order : orders) {
                if (order.getStatus().equals(Integer.valueOf(2)) || order.getStatus().equals(Integer.valueOf(5)) || order.getStatus().equals(Integer.valueOf(6))) {
                    orderCount++;
                    actPayPrice = actPayPrice.add(order.getActPayPrice());
                }
            }
            equipmentVO.setActPayPrice(actPayPrice);
            equipmentVO.setOrderCount(orderCount);
        }
        return page;
    }

    public EquipmentSummaryVO summary(EquipmenSearchDTO equipmenSearchDTO) {
        List<Order> orders;
        if (StringUtils.isEmpty(equipmenSearchDTO.getOrderNum())) {
            equipmenSearchDTO.setOrderNum("");
        }
        if (StringUtils.isEmpty(equipmenSearchDTO.getStaffName())) {
            equipmenSearchDTO.setStaffName("");
        }

        List<Integer> orderStatus = new ArrayList<Integer>();
        orderStatus.add(Integer.valueOf(2));
        orderStatus.add(Integer.valueOf(5));
        orderStatus.add(Integer.valueOf(6));
        if (equipmenSearchDTO.getBeginTime() == null) {
            orders = this.orderRepository.findByEquipmentIdAndUserNameLikeAndOrderNumberLikeAndStatusIn(equipmenSearchDTO.getEquipmentId(), "%" + equipmenSearchDTO
                    .getStaffName() + "%", "%" + equipmenSearchDTO.getOrderNum() + "%", orderStatus);
        } else {
            orders = this.orderRepository.findByEquipmentIdAndUserNameLikeAndOrderNumberLikeAndPayTimeBetweenAndStatusIn(equipmenSearchDTO.getEquipmentId(), "%" + equipmenSearchDTO
                    .getStaffName() + "%", "%" + equipmenSearchDTO.getOrderNum() + "%", equipmenSearchDTO.getBeginTime(), equipmenSearchDTO.getEndTime(), orderStatus);
        }
        EquipmentSummaryVO equipmentSummaryVO = new EquipmentSummaryVO();
        equipmentSummaryVO.setOrderNum(Integer.valueOf(orders.size()));
        BigDecimal totalMoney = new BigDecimal(0);
        BigDecimal refundMoney = new BigDecimal(0);
        BigDecimal payMoney = new BigDecimal(0);
        BigDecimal DiscountMoney = new BigDecimal(0);
        for (Order order : orders) {
            totalMoney = totalMoney.add(order.getTotalPrice());
            refundMoney = refundMoney.add(order.getRefundPayPrice());
            payMoney = payMoney.add(order.getActPayPrice());
            DiscountMoney = DiscountMoney.add(order.getDisCountPrice());
        }
        equipmentSummaryVO.setDiscountMoney(DiscountMoney);
        equipmentSummaryVO.setPayMoney(payMoney);
        equipmentSummaryVO.setTotalMoney(totalMoney);
        equipmentSummaryVO.setRefundMoney(refundMoney);
        return equipmentSummaryVO;
    }

    public Page<EquipmentDetailPageVO> detailByEquId(EquipmenSearchDTO equipmenSearchDTO, PageVo pageVo) {
        Page<EquipmentDetailPageVO> pageVOS;
        Pageable pageable = PageUtil.initPage(pageVo);
        if (equipmenSearchDTO.getBeginTime() == null) {
            pageVOS = this.equipmentRepository.getOrderPage(equipmenSearchDTO.getEquipmentId(), equipmenSearchDTO.getStaffName(), equipmenSearchDTO
                    .getOrderNum(), pageable);
        } else {
            pageVOS = this.equipmentRepository.getOrderPage(equipmenSearchDTO.getEquipmentId(), equipmenSearchDTO.getStaffName(), equipmenSearchDTO
                    .getOrderNum(), equipmenSearchDTO.getBeginTime(), equipmenSearchDTO.getEndTime(), pageable);
        }
        return pageVOS;
    }

    public void exportDetailByEquId(EquipmenSearchDTO equipmenSearchDTO, HttpServletResponse response) throws IOException {
        List<EquipmentDetailPageVO> list;
        if (equipmenSearchDTO.getBeginTime() == null) {
            list = this.equipmentRepository.getOrderList(equipmenSearchDTO.getEquipmentId(), equipmenSearchDTO.getStaffName(), equipmenSearchDTO
                    .getOrderNum());
        } else {
            list = this.equipmentRepository.getOrderList(equipmenSearchDTO.getEquipmentId(), equipmenSearchDTO.getStaffName(), equipmenSearchDTO
                    .getOrderNum(), equipmenSearchDTO.getBeginTime(), equipmenSearchDTO.getEndTime());
        }
        EasyPoiUtil.exportExcel(list, "流水清单", "sheetName", EquipmentDetailPageVO.class, "fileNameb.xls", response);
    }

    public Integer getDeviceType(List<String> merchantIdList, Integer type) {
       return equipmentRepository.countByMerchantIdInAndDeviceType(merchantIdList,type);
    }
}