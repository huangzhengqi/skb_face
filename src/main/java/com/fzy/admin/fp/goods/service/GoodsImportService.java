package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.util.HttpRequestUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.goods.domain.GoodsCategory;
import com.fzy.admin.fp.goods.domain.GoodsImport;
import com.fzy.admin.fp.goods.domain.GoodsImportRecord;
import com.fzy.admin.fp.goods.repository.GoodsCategoryRepository;
import com.fzy.admin.fp.goods.repository.GoodsImportRecordRepository;
import com.fzy.admin.fp.goods.repository.GoodsImportRepository;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsImportService extends Object implements BaseService<GoodsImport>{

    @Resource
    private GoodsImportRepository goodsImportRepository;
    @Autowired
    private GoodsImportRecordRepository goodsImportRecordRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    public BaseRepository<GoodsImport> getRepository() { return this.goodsImportRepository; }


    public Resp<String> importGoods(List<GoodsImportRecord> goodsImportRecordList, String merchantId, String merchantUserId, Integer isShelf,String storeId) {
        if (goodsImportRecordList == null || goodsImportRecordList.size() > 10000) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "每次导入不超过一万条");
        }
        GoodsImport goodsImport = new GoodsImport();
        goodsImport.setMerchantId(merchantId);
        goodsImport.setStoreId(storeId);
        this.goodsImportRepository.save(goodsImport);
        //导入成功集合
        List<GoodsImportRecord> successList = new ArrayList<GoodsImportRecord>();

        //导入失败集合
        List<GoodsImportRecord> failList = new ArrayList<GoodsImportRecord>();
        for (GoodsImportRecord record : goodsImportRecordList) {


            GoodsImportRecord goodsImportRecord = new GoodsImportRecord();
            BeanUtils.copyProperties(record, goodsImportRecord);
            goodsImportRecord.setImportId(goodsImport.getId());
            goodsImportRecord.setStatus(Integer.valueOf(1));

            if (StringUtils.isBlank(goodsImportRecord.getGoodsName())) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("商品名称格式错误");
                failList.add(goodsImportRecord);
                continue;
            }
            if (goodsImportRecord.getGoodsPrice() == null) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("价格格式错误");
                failList.add(goodsImportRecord);
                continue;
            }
            if (StringUtils.isBlank(goodsImportRecord.getGoodsCode())) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("条形码格式错误");
                failList.add(goodsImportRecord);
                continue;
            }
            if (goodsImportRecord.getStockNum() == null) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("库存格式错误");
                failList.add(goodsImportRecord);
                continue;
            }
            Goods goods = this.goodsRepository.findByStoreIdAndGoodsCode(storeId, record.getGoodsCode());
            if (goods != null) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("条形码重复");
                failList.add(goodsImportRecord);
                continue;
            }
            //判断商品名是否重复
            Goods goodsName = this.goodsRepository.findByStoreIdAndGoodsName(storeId, record.getGoodsName());
            if (goodsName != null) {
                goodsImportRecord.setStatus(Integer.valueOf(2));
                goodsImportRecord.setReason("商品名重复");
                failList.add(goodsImportRecord);
                continue;
            }
            if(null==record.getIndustryCategory()){
                if (StringUtils.isBlank(goodsImportRecord.getGoodsName())) {
                    goodsImportRecord.setStatus(Integer.valueOf(2));
                    goodsImportRecord.setReason("行业分类格式错误");
                    failList.add(goodsImportRecord);
                    continue;
                }
            }
            Integer industryCategory=Integer.parseInt(record.getIndustryCategory());
            if (StringUtils.isNotBlank(record.getItemNumber())) {
                goods = this.goodsRepository.findByStoreIdAndItemNumberAndIndustryCategory(storeId, record.getItemNumber(),industryCategory);
                if (goods != null) {
                    goodsImportRecord.setStatus(Integer.valueOf(2));
                    goodsImportRecord.setReason("商品编号重复");
                    failList.add(goodsImportRecord);
                    continue;
                }
            }
            goods = new Goods();
            goods.setGoodsName(goodsImportRecord.getGoodsName());
            goods.setGoodsCode(goodsImportRecord.getGoodsCode());
            goods.setGoodsPrice(goodsImportRecord.getGoodsPrice());
            goods.setItemNumber(goodsImportRecord.getItemNumber());
            goods.setStockNum(goodsImportRecord.getStockNum());
            goods.setMerchantId(merchantId);
            goods.setMerchantUserId(merchantUserId);
            goods.setSource(Integer.valueOf(2));
            goods.setIsShelf(isShelf);
            goods.setSort(Integer.valueOf(1));
            goods.setStoreId(storeId);
            goods.setSalesNum(Integer.valueOf(0));
            if (StringUtils.isNotBlank(goodsImportRecord.getGoodsCategory())) {
                String[] categoryArray = goodsImportRecord.getGoodsCategory().split("/");
                if (categoryArray.length > 0 && categoryArray.length <= 2) {
                    GoodsCategory goodsCategory = this.goodsCategoryRepository.findByMerchantIdAndCagegoryName(merchantId, categoryArray[0]);
                    if (goodsCategory == null) {
                        goodsImportRecord.setStatus(Integer.valueOf(2));
                        goodsImportRecord.setReason("一级类目不存在");
                        failList.add(goodsImportRecord);
                        continue;
                    }
                    if (categoryArray.length == 2) {
                        goodsCategory = this.goodsCategoryRepository.findByMerchantIdAndCagegoryName(merchantId, categoryArray[1]);
                        if (goodsCategory == null) {
                            goodsImportRecord.setStatus(Integer.valueOf(2));
                            goodsImportRecord.setReason("二级类目不存在");
                            failList.add(goodsImportRecord);
                            continue;
                        }
                        goods.setGoodsCategory(goodsCategory.getId());
                    } else {

                        goods.setGoodsCategory(goodsCategory.getId());
                    }
                } else {
                    goodsImportRecord.setStatus(Integer.valueOf(2));
                    goodsImportRecord.setReason("类目错误");
                    failList.add(goodsImportRecord);
                    continue;
                }
            }
            goods.setIndustryCategory(industryCategory);
            successList.add(goodsImportRecord);
            this.goodsRepository.save(goods);
        }
        goodsImport.setFailNum(Integer.valueOf(failList.size()));
        goodsImport.setSuccessNum(Integer.valueOf(successList.size()));
        goodsImport.setTotalNum(Integer.valueOf(goodsImport.getFailNum().intValue() + goodsImport.getSuccessNum().intValue()));
        this.goodsImportRepository.save(goodsImport);
        this.goodsImportRecordRepository.save(successList);
        this.goodsImportRecordRepository.save(failList);
        if (successList.size() > 0) {
            return Resp.success("导入成功");
        }
        return (new Resp()).error(Resp.Status.PARAM_ERROR, "导入失败");
    }


    public void report(String id) {
        List<GoodsImportRecord> list = this.goodsImportRecordRepository.findByImportId(id);
        for(GoodsImportRecord goodsImportRecord:list){
            if("1".equals(goodsImportRecord.getStatus().toString())){
                goodsImportRecord.setStatusName("成功");
            }else{
                goodsImportRecord.setStatusName("失败");
            }
            if(null!=goodsImportRecord.getIndustryCategory()) {
                switch (goodsImportRecord.getIndustryCategory()) {
                    case "0":
                        goodsImportRecord.setIndustryCategory("超市");
                        break;
                    case "1":
                        goodsImportRecord.setIndustryCategory("自助点餐");
                        break;
                    case "2":
                        goodsImportRecord.setIndustryCategory("医药");
                        break;
                    case "3":
                        goodsImportRecord.setIndustryCategory("加油站");
                        break;
                    case "4":
                        goodsImportRecord.setIndustryCategory("景区");
                        break;
                }
            }
        }
        EasyPoiUtil.exportExcel(list, "商品", "商品", GoodsImportRecord.class, "商品.xls",
                HttpRequestUtils.getResponse());
    }
}
