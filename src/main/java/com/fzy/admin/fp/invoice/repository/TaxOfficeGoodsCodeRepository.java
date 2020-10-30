package com.fzy.admin.fp.invoice.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.invoice.domain.TaxOfficeGoodsCode;

import java.util.List;

public interface TaxOfficeGoodsCodeRepository extends BaseRepository<TaxOfficeGoodsCode> {
//    List<TaxOfficeGoodsCode> findTaxOfficeGoodsCodeByAndCodeLevel(Integer paramInteger);
//
//    List<TaxOfficeGoodsCode> findTaxOfficeGoodsCodeByParentCodeAndCodeLevel(String paramString, Integer paramInteger);
//
//    List<TaxOfficeGoodsCode> findTaxOfficeGoodsCodeByNameLike(String paramString);

    TaxOfficeGoodsCode findByGoodsCode(String paramString);
}
