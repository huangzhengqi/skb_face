package com.fzy.admin.fp.goods.controller;

import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.bo.GoodsCount;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import com.fzy.admin.fp.goods.service.GoodsService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping({"/merchant/goods"})
@Api(value="GoodsController", tags={"商品管理-商品"})
public class GoodsController extends BaseController<Goods> {
    private static final Logger log=LoggerFactory.getLogger(GoodsController.class);


    @Autowired
    private GoodsService goodsService;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private MerchantUserService merchantUserService;
    /**
     * 行业分类 0超市 1自助点餐 2医药 3加油站 4景区
     */
    public static final String  INDUSTRY_CATEGORY ="1";

    public BaseService<Goods> getService() {
        return this.goodsService;
    }


    @ApiOperation(value="获取分页", notes="获取分页")
    @GetMapping({""})
    public Resp<Page<Goods>> getPage(PageVo pageVo, Goods goods) {
        String merchantId=TokenUtils.getMerchantId();
        String merchantUserId=TokenUtils.getUserId();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(merchantUserId);
        goods.setMerchantId(merchantId);
        goods.setStoreId(merchantUser.getStoreId());
        Pageable pageable=PageUtil.initPage(pageVo);
        Page<Goods> page=this.goodsService.findByGoodsNameOriOrItemNumber(goods, pageable);
        return Resp.success(page);
    }

    /**
     * 自助点餐商品List
     * @param pageVo 分页
     * @param goods 参数
     * @return
     */
    @ApiOperation(value="获取分页", notes="获取分页")
    @GetMapping({"/getGoodsPage"})
    public Resp<Page<Goods>> getGoodsPage(PageVo pageVo, Goods goods) {
        String merchantId=TokenUtils.getMerchantId();
        String merchantUserId=TokenUtils.getUserId();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(merchantUserId);
        goods.setMerchantId(merchantId);
        goods.setStoreId(merchantUser.getStoreId());
        Pageable pageable=PageUtil.initPage(pageVo);
        Page<Goods> page=this.goodsService.findByGoodsList(goods, pageable);
        return Resp.success(page);
    }

    @ApiOperation(value="保存或更新", notes="保存或更新")
    @PostMapping({""})
    public Resp saveGoods(@RequestBody Goods entity) {
        String merchantId=TokenUtils.getMerchantId();
        String merchantUserId=TokenUtils.getUserId();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(merchantUserId);
        //判断同一分类下排序是否重复
        if(INDUSTRY_CATEGORY.equals(entity.getIndustryCategory())){
            List<Goods> goods=this.goodsRepository.findByGoodsCategoryAndStoreId(entity.getGoodsCategory(),entity.getStoreId());
            if(goods.size()>1){
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "同一分类下排序值不能重复");
            }
        }
        entity.setStoreId(merchantUser.getStoreId());
        entity.setMerchantId(merchantId);
        entity.setMerchantUserId(merchantUserId);
        return this.goodsService.saveGoods(entity);
    }


    @ApiOperation(value="根据条形码获取商品", notes="根据条形码获取商品")
    @GetMapping({"/detail"})
    public Resp<Goods> getDetail(@UserId String userId, String code) {
        MerchantUser merchantUser=merchantUserService.findOne(userId);
        Goods goods=this.goodsRepository.findByStoreIdAndGoodsCode(merchantUser.getStoreId(), code);
        if (goods == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "商品不存在");
        }
        return Resp.success(goods);
    }


    @ApiOperation(value="批量上下架", notes="批量上下架")
    @PostMapping({"/batch/shelf"})
    @ResponseBody
    @Transactional
    public Resp shelf(String[] ids, Integer isShelf) {
        List<Goods> list=getService().findAll(ids);
        for (Goods e : list) {
            e.setIsShelf(isShelf);
            this.goodsService.save(e);
        }
        return Resp.success("操作成功");
    }

    @ApiOperation(value="获取商品数量", notes="获取商品数量")
    @GetMapping({"/count"})
    public Resp<GoodsCount> count(Integer industryCategory) {
        String merchantId=TokenUtils.getMerchantId();
        GoodsCount goodsCount=new GoodsCount();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(TokenUtils.getUserId());
        goodsCount.setIsShelf(this.goodsRepository.countByMerchantIdAndIsShelfAndIndustryCategoryAndStoreId(merchantId, Integer.valueOf(1),industryCategory,merchantUser.getStoreId()));
        goodsCount.setNoShelf(this.goodsRepository.countByMerchantIdAndIsShelfAndIndustryCategoryAndStoreId(merchantId, Integer.valueOf(0),industryCategory,merchantUser.getStoreId()));
        goodsCount.setAll(Integer.valueOf(goodsCount.getIsShelf().intValue() + goodsCount.getNoShelf().intValue()));
        return Resp.success(goodsCount);
    }
}
