package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.dto.EquipmenSearchDTO;
import com.fzy.admin.fp.auth.dto.EquipmentDTO;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.vo.EquipmentDetailPageVO;
import com.fzy.admin.fp.auth.vo.EquipmentSummaryVO;
import com.fzy.admin.fp.auth.vo.EquipmentVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth/equipment")
@Api(value = "EquipmentController", tags = "设备管理")
public class EquipmentController {
    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Resource
    private EquipmentService equipmentService;

    @GetMapping("")
    @ApiOperation("设备列表")
    public Resp<Page<EquipmentVO>> listRewrite(@TokenInfo(property = "companyId") String companyId, @TokenInfo(property = "merchantId") String merchantId, EquipmentDTO equipmentDTO, PageVo pageVo) {
        Page<EquipmentVO> page = this.equipmentService.getPage1(companyId, equipmentDTO, merchantId, pageVo);
        return Resp.success(page, "获取列表");
    }

    @PutMapping("/mode")
    @ApiOperation("设置收银模式")
    public Resp listRewrite(String id, Integer mode) {
        Equipment equipment = this.equipmentService.findOne(id);
        if (equipment == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "设备不存在");
        }
        equipment.setMode(mode);
        this.equipmentService.save(equipment);
        return Resp.success("设置成功");
    }

    @GetMapping("/summary")
    @ApiOperation("数据总览")
    public Resp<EquipmentSummaryVO> summary(EquipmenSearchDTO equipmenSearchDTO) {
        EquipmentSummaryVO equipmentSummaryVO = this.equipmentService.summary(equipmenSearchDTO);
        return Resp.success(equipmentSummaryVO, "数据总览");
    }

    @GetMapping("/detail")
    @ApiOperation("设备流水清单")
    public Resp<Page<EquipmentDetailPageVO>> detail(EquipmenSearchDTO equipmenSearchDTO, PageVo pageVo) {
        Page<EquipmentDetailPageVO> pageVOS = this.equipmentService.detailByEquId(equipmenSearchDTO, pageVo);
        return Resp.success(pageVOS, "");
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(EquipmenSearchDTO equipmenSearchDTO, HttpServletResponse httpServletResponse) throws IOException { this.equipmentService.exportDetailByEquId(equipmenSearchDTO, httpServletResponse); }
}
