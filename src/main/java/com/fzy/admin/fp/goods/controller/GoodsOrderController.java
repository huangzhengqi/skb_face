package com.fzy.admin.fp.goods.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.pc.vo.StatisticsAgentVO;
import com.fzy.admin.fp.goods.bo.GoodsOrderBO;
import com.fzy.admin.fp.goods.bo.GoodsOrderDetailsBO;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.domain.GoodsOrderItems;
import com.fzy.admin.fp.goods.dto.GoodsOrderDTO;
import com.fzy.admin.fp.goods.dto.GoodsOrderItemsDTO;
import com.fzy.admin.fp.goods.dto.GoodsOrderSalesDTO;
import com.fzy.admin.fp.goods.dto.GoodsOrderStatisticsDTO;
import com.fzy.admin.fp.goods.repository.GoodsOrderItemsRepository;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import com.fzy.admin.fp.goods.service.GoodsOrderService;
import com.fzy.admin.fp.goods.vo.GoodsOrderSalesVo;
import com.fzy.admin.fp.goods.vo.StatisticsGoodsOrderVO;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */

@RestController
@RequestMapping("/merchant/goods_order")
@Slf4j
@Api(value = "GoodsOrderController", tags = {"商品销量"})
public class GoodsOrderController extends BaseController<GoodsOrder> {

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsOrderItemsRepository goodsOrderItemsRepository;

    @Autowired
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantRepository merchantRepository;

    public BaseService<GoodsOrder> getService() { return this.goodsOrderService; }


    @ApiOperation(value = "获取分页", notes = "获取分页")
    @GetMapping({""})
    public Resp<Page<GoodsOrderBO>> getPage(GoodsOrderDTO goodsOrderDTO, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(TokenUtils.getUserId());
        return this.goodsOrderService.getPage(goodsOrderDTO, pageable,merchantUser.getStoreId());
    }

    @ApiOperation(value = "获取详情", notes = "获取详情")
    @GetMapping({"/details"})
    public Resp<GoodsOrderDetailsBO> details(String orderId) { return this.goodsOrderService.details(orderId); }

    /**
     *  商品销量
     * @return
     */
    @GetMapping("get_goods_order_sales")
    public Resp getGoodsOrderSales(GoodsOrderSalesVo goodsOrderSalesVo){
        String merchantId = TokenUtils.getMerchantId();
        goodsOrderSalesVo.setMerchantId(merchantId);
//        List<GoodsOrderSalesDto> list = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();

        if(goodsOrderSalesVo.getStartTime() == null){
            throw new BaseException("开始时间不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if(goodsOrderSalesVo.getEndTime() == null){
            throw new BaseException("结束时间不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        //查询出当前商户下的商品列表
        //list = goodsOrderService.getGoodsOrderSales(goodsOrderSalesVo);

        Map<String, GoodsOrderSalesDTO> merchantCountVOMap = new LinkedHashMap<>();

//        List<GoodsOrderSalesDto> goodsOrderSalesDtoList = new ArrayList<>();

        //查询当前商户
        Merchant merchant = merchantRepository.findOne(goodsOrderSalesVo.getMerchantId());
        if(merchant == null || merchant.equals("")){
            throw new BaseException("当前商户不存在", Resp.Status.PARAM_ERROR.getCode());
        }

        DateTime startTime = DateUtil.beginOfDay(goodsOrderSalesVo.getStartTime());
        DateTime endTime = DateUtil.endOfDay(goodsOrderSalesVo.getEndTime());

        //查询商户下的所有商品
        //List<Goods> goodsList = goodsRepository.findByMerchantIdAndCreateTimeBetween(merchant.getId(), startTime, endTime);
        List<Goods> goodsList = goodsRepository.findByMerchantId(merchant.getId());
        if(goodsList.size() <= 0){
            throw new BaseException("当前商户没有商品", Resp.Status.PARAM_ERROR.getCode());
        }

        for (Goods goods:goodsList) {
            GoodsOrderSalesDTO goodsOrderSalesDto = merchantCountVOMap.get(goods.getId());
            if(goodsOrderSalesDto == null){
                goodsOrderSalesDto = new GoodsOrderSalesDTO();
                goodsOrderSalesDto.setGoodsId(goods.getId());
                goodsOrderSalesDto.setMerchantId(goods.getMerchantId()); //商户id
                goodsOrderSalesDto.setGoodsName(goods.getGoodsName());  //商品名称
                goodsOrderSalesDto.setGoodsImg(goods.getGoodsPic());   //商品图片
                GoodsOrderItemsDTO goodsOrderItemsDto = goodsOrderItemsRepository.findByCountTotalPrice(goods.getGoodsName(), startTime, endTime);
                if(goodsOrderItemsDto.getTotalPrice() == null){
                    goodsOrderSalesDto.setSalesNum(BigDecimal.ZERO.doubleValue());
                }else {
                    goodsOrderSalesDto.setSalesNum(goodsOrderItemsDto.getTotalPrice().doubleValue()); //销售额
                }
                goodsOrderSalesDto.setSalesCountNum(goods.getSalesNum());//销量份

                //该商品时间内的交易笔数
                List<GoodsOrderItems> itemsList = goodsOrderItemsRepository.findByGoodsNameAndCreateTimeBetween(goods.getGoodsName(), startTime, endTime);
                if(itemsList.size() <= 0){
                    goodsOrderSalesDto.setTradingVolume(0); //交易笔数
                }else {
                    goodsOrderSalesDto.setTradingVolume(itemsList.size()); //交易笔数
                }
                merchantCountVOMap.put(goods.getId(), goodsOrderSalesDto);
            }


            //goodsOrderSalesDtoList.add(goodsOrderSalesDto);
        }

        List<GoodsOrderSalesDTO> merchantCountVOList = new ArrayList<>(merchantCountVOMap.size());
        for (String s : merchantCountVOMap.keySet()) {
            merchantCountVOList.add(merchantCountVOMap.get(s));
        }
        merchantCountVOList = merchantCountVOList.stream().skip((goodsOrderSalesVo.getPageNum() - 1) * goodsOrderSalesVo.getPageSize()).limit(goodsOrderSalesVo.getPageSize()).collect(Collectors.toList());

        map.put("totalPage", merchantCountVOMap.size() % goodsOrderSalesVo.getPageSize() == 0 ? merchantCountVOMap.size() / goodsOrderSalesVo.getPageSize() : merchantCountVOMap.size() / goodsOrderSalesVo.getPageSize() + 1);
        map.put("totalElement", merchantCountVOMap.size());


//        double goodsPriceScale   = list.stream().mapToDouble(GoodsOrderSalesDto::getSalesNum).sum();// 销售总额
//        double salesCount   = list.stream().mapToDouble(GoodsOrderSalesDto::getSalesCountNum).sum(); //总销量（份）
//        double tradingVolumeScale   = list.stream().mapToDouble(GoodsOrderSalesDto::getTradingVolume).sum(); //总交易量（笔）

        double goodsPriceScale   = merchantCountVOList.stream().mapToDouble(GoodsOrderSalesDTO::getSalesNum).sum();// 销售总额
        double salesCount   = merchantCountVOList.stream().mapToDouble(GoodsOrderSalesDTO::getSalesCountNum).sum(); //总销量（份）
        double tradingVolumeScale   = merchantCountVOList.stream().mapToDouble(GoodsOrderSalesDTO::getTradingVolume).sum(); //总交易量（笔）

        //销售(份)等于1是顺序
        if(goodsOrderSalesVo.getSalesCountNumType() != null){
            if(goodsOrderSalesVo.getSalesCountNumType().equals(1)){
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getSalesCountNum));
            }else {
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getSalesCountNum).reversed());
            }
        }


        //交易笔等于1是顺序
        if(goodsOrderSalesVo.getTradingVolumeType() != null){
            if(goodsOrderSalesVo.getTradingVolumeType().equals(1)){
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getTradingVolume));
            }else {
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getTradingVolume).reversed());
            }
        }


        //销售额等于1是顺序
        if(goodsOrderSalesVo.getSalesNumType() !=null){
            if(goodsOrderSalesVo.getSalesNumType().equals(1)){
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getSalesNum));
            }else {
                merchantCountVOList.sort(Comparator.comparing(GoodsOrderSalesDTO::getSalesNum).reversed());
            }
        }


        map.put("goodsPriceScale",goodsPriceScale);
        map.put("salesCount",salesCount);
        map.put("tradingVolumeScale",tradingVolumeScale);
        map.put("list",merchantCountVOList);
        return Resp.success(map,"返回成功");

    }

    @GetMapping("/statistics")
    @ApiOperation(value = "获取商品销量统计图", notes = "获取商品销量统计图")
    public Resp statistics(GoodsOrderStatisticsDTO goodsOrderStatisticsDTO) {
        //List<GoodsOrderItems> itemsList = goodsOrderItemsRepository.findAllByGoodsIdAndCreateTimeBetween(goodsOrderStatisticsDTO.getGoodsId(), goodsOrderStatisticsDTO.getStartTime(), goodsOrderStatisticsDTO.getEndTime());
        List<GoodsOrderItems> itemsList = goodsOrderItemsRepository.findAllByGoodsNameAndCreateTimeBetween(goodsOrderStatisticsDTO.getGoodsName(), goodsOrderStatisticsDTO.getStartTime(), goodsOrderStatisticsDTO.getEndTime());
        double goodsPriceScale   = itemsList.stream().mapToDouble(GoodsOrderItems::getSalesNum).sum();// 销售总额
        double salesCount   = itemsList.stream().mapToDouble(GoodsOrderItems::getSalesNum).sum(); //总销量（份）
        String format="yyyy-MM-dd";
        if(goodsOrderStatisticsDTO.getType()==1){
            format="yyyy-MM";
        }
        List<String> xData = DateUtils.dateRangeList(goodsOrderStatisticsDTO.getStartTime(), goodsOrderStatisticsDTO.getEndTime(),
                DateField.DAY_OF_WEEK, format);
        List<StatisticsGoodsOrderVO> statisticsGoodsOrderVOList = new ArrayList<>();
        for (String dateTime : xData) {
            StatisticsGoodsOrderVO statisticsGoodsOrderVO = new StatisticsGoodsOrderVO();
            statisticsGoodsOrderVO.setDateTime(dateTime);
            int num=0;
            for (GoodsOrderItems goodsOrderItems : itemsList) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                if (sdf.format(goodsOrderItems.getCreateTime()).equals(dateTime)) {
                    num=num+goodsOrderItems.getSalesNum();
                }
            }
            statisticsGoodsOrderVO.setSalesCountNum(num);
            statisticsGoodsOrderVOList.add(statisticsGoodsOrderVO);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("goodsPriceScale",goodsPriceScale);//销售总额
        result.put("salesCount",salesCount);//总销量（份）
        result.put("total",itemsList.size());//带来交易量
        result.put("list",statisticsGoodsOrderVOList);//图标数据
        return Resp.success(result);
    }
}
