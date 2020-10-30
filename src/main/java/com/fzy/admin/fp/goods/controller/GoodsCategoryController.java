package com.fzy.admin.fp.goods.controller;

import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.bo.GoodsCategoryBO;
import com.fzy.admin.fp.goods.bo.GoodsCategoryTree;
import com.fzy.admin.fp.goods.domain.GoodsCategory;
import com.fzy.admin.fp.goods.service.GoodsCategoryService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/merchant/goods/category"})
@Api(value="GoodsCategoryController", tags={"分类管理-分类"})
public class GoodsCategoryController extends BaseController<GoodsCategory> {
    private static final Logger log=LoggerFactory.getLogger(GoodsCategoryController.class);


    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private MerchantUserService merchantUserService;


    public BaseService<GoodsCategory> getService() {
        return this.goodsCategoryService;
    }


    @ApiOperation(value="获取分页", notes="获取分页")
    @GetMapping({""})
    public Resp<Page<GoodsCategoryBO>> getPage(@UserId String userId, PageVo pageVo, String parentId, Integer industryCategory) {
        String merchantId=TokenUtils.getMerchantId();
        MerchantUser merchantUser=merchantUserService.findOne(userId);
        GoodsCategory goodsCategory=new GoodsCategory();
        goodsCategory.setParentId(parentId);
        goodsCategory.setMerchantId(merchantId);
        goodsCategory.setIndustryCategory(industryCategory);
        goodsCategory.setStoreId(merchantUser.getStoreId());
        Pageable pageable=PageUtil.initPage(pageVo);
        Page<GoodsCategoryBO> page=this.goodsCategoryService.getCategoryPage(goodsCategory, pageable);
        return Resp.success(page);
    }

    @ApiOperation(value="保存或更新", notes="保存或更新")
    @PostMapping({""})
    public Resp saveCategory(@RequestBody GoodsCategory entity) {
        String merchantId=TokenUtils.getMerchantId();
        entity.setMerchantId(merchantId);
        entity.setMerchantUserId(TokenUtils.getUserId());
        MerchantUser merchantUser=merchantUserService.findOne(TokenUtils.getUserId());
        entity.setStoreId(merchantUser.getStoreId());
        if ("0".equals(entity.getParentId())) {
            entity.setLevel(Integer.valueOf(1));
        } else {
            entity.setLevel(Integer.valueOf(2));
        }
        return this.goodsCategoryService.saveCategory(entity);
    }

    @ApiOperation(value="获取多级下拉列表", notes="获取多级下拉列表 一级传0")
    @GetMapping({"/list/parentid"})
    public Resp<List<GoodsCategory>> getList(String parentId, Integer industryCategory) {
        String merchantId=TokenUtils.getMerchantId();
        MerchantUser merchantUser=merchantUserService.findOne(TokenUtils.getUserId());
        List<GoodsCategory> list=this.goodsCategoryService.getList(merchantId, parentId, industryCategory, merchantUser.getStoreId());
        return Resp.success(list);
    }


    @ApiOperation(value="获取多级下拉列表 树形", notes="获取多级下拉列表 树形")
    @GetMapping({"/tree"})
    public Resp<List<GoodsCategoryTree>> getList(Integer industryCategory) {
        String merchantId=TokenUtils.getMerchantId();
        MerchantUser merchantUser=merchantUserService.findOne(TokenUtils.getUserId());
        List<GoodsCategory> list=this.goodsCategoryService.getList(merchantId, "0", industryCategory, merchantUser.getStoreId());
        List<GoodsCategoryTree> categoryTrees=getCategoryTree(list);
        for (GoodsCategoryTree tree : categoryTrees) {
            List<GoodsCategory> secondNode=this.goodsCategoryService.getList(merchantId, tree.getId(),industryCategory, merchantUser.getStoreId());
            tree.setChildren(getCategoryTree(secondNode));
        }
        return Resp.success(categoryTrees);
    }


    private List<GoodsCategoryTree> getCategoryTree(List<GoodsCategory> list) {
        List<GoodsCategoryTree> categoryTrees=new ArrayList<GoodsCategoryTree>();
        for (GoodsCategory category : list) {
            GoodsCategoryTree node=new GoodsCategoryTree();
            BeanUtils.copyProperties(category, node);
            categoryTrees.add(node);
        }
        return categoryTrees;
    }

}
