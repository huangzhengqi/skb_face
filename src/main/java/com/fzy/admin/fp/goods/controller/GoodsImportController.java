package com.fzy.admin.fp.goods.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.GoodsImport;
import com.fzy.admin.fp.goods.domain.GoodsImportRecord;
import com.fzy.admin.fp.goods.service.GoodsImportService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品管理-导入
 */
@Slf4j
@RestController
@RequestMapping("/merchant/goods/import")
public class GoodsImportController extends BaseController<GoodsImport> {

    @Autowired
    private GoodsImportService goodsImportService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantUserService merchantUserService;


    public BaseService<GoodsImport> getService() {
        return this.goodsImportService;
    }

    @ApiOperation(value="获取分页", notes="获取分页")
    @GetMapping({""})
    public Resp<Page<GoodsImport>> getPage(PageVo pageVo) {
        String merchantId=TokenUtils.getMerchantId();
        GoodsImport goodsImport=new GoodsImport();
        goodsImport.setMerchantId(merchantId);
        Pageable pageable=PageUtil.initPage(pageVo);
        Specification specification=ConditionUtil.createSpecification(goodsImport);
        Page<GoodsImport> page=this.goodsImportService.findAll(specification, pageable);
        List<GoodsImport> list=page.getContent();
        for (GoodsImport item : list) {

            Merchant merchan=(Merchant) this.merchantRepository.findOne(item.getMerchantId());
            if (merchan != null) {
                item.setName(merchan.getName());
            }
        }
        return Resp.success(page);
    }


    @ApiOperation(value="导入报表", notes="导入报表")
    @PostMapping({""})
    @ApiImplicitParams({@ApiImplicitParam(name="editormd-image-file", value="文件", required=true, dataType="MultipartFile", allowMultiple=true)})
    public Resp<String> getPage(@RequestParam("editormd-image-file") MultipartFile file, Integer isShelf) throws Exception {
        String merchantId=TokenUtils.getMerchantId();
        String merchantUserId=TokenUtils.getUserId();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(merchantUserId);
        ImportParams params=new ImportParams();
        List<GoodsImportRecord> goodsImportRecordList=ExcelImportUtil.importExcel(file.getInputStream(), GoodsImportRecord.class, params);
        if (goodsImportRecordList.size() == 0) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "文件解析失败");
        }
        return this.goodsImportService.importGoods(goodsImportRecordList, merchantId, merchantUserId, isShelf, merchantUser.getStoreId());
    }


    @ApiOperation(value="下载报表", notes="下载报表")
    @GetMapping({"/report"})
    public void report(String id) {
        this.goodsImportService.report(id);
    }


}
