package com.fzy.admin.fp.distribution.app.controller;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.app.dto.MerchantMonthDTO;
import com.fzy.admin.fp.merchant.app.dto.MyMerchantDTO;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantUserFeign;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author fyz lzy
 * @create 2020/7/15 9:14
 * @Description: 分销商，我的商户Controller
 */
@RestController
@RequestMapping("/dist/app/my/merchant")
@Api(value="AppMerchantController", tags={"我的商户-交易数据"})
@Slf4j
public class AppMyMerchantController extends BaseContent {

    @Resource
    private MerchantService merchantService;

    @Resource
    private OrderService orderService;

    @Resource
    private MerchantUserService merchantUserService;

    @Autowired
    private StoreService storeService;

    @Resource
    private MerchantUserFeign merchantUserFeign;

    @GetMapping("/distMerchantList")
    @ApiOperation(value="分销我的商户", notes="分销我的商户")
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=MyMerchantDTO.class)})
    public Resp<List<MyMerchantDTO>> distMerchantList(@UserId String userId) {
        //查询分销商下的商户
        List<Merchant> merchants = merchantService.getRepository().findByManagerIdAndTypeOrderByCreateTimeDesc(userId, 1);
        List<MyMerchantDTO> myMerchantDTOList = new ArrayList<>();
        for(Merchant merchant:merchants){
            List<Object[]> myMerchantDTO =merchantService.getRepository().getMyMerchantList(merchant.getId());
            MyMerchantDTO monthDTO =new MyMerchantDTO();
            for (Object[] obj : myMerchantDTO) {
                if(null != obj[1]){
                    monthDTO.setTotalPrice((BigDecimal) obj[0]);
                    monthDTO.setMerchantId((String) obj[1]);
                    monthDTO.setMerchantName((String) obj[2]);
                    monthDTO.setCreateTime((String) obj[3]);
                    monthDTO.setFaseNum((BigInteger) obj[4]);
                    myMerchantDTOList.add(monthDTO);
                }
                break;
            }
        }
        return Resp.success(myMerchantDTOList);
    }

    @GetMapping("/disStoreList")
    @ApiOperation(value="分销商商户门店", notes="分销商商户门店")
    @ApiImplicitParams(@ApiImplicitParam(paramType="query",name="merchantId",dataType="String",value="商户ID",required = true))
    public Resp<List<Map<String,Object>>> disStoreList(@UserId String userId,String merchantId) {
        if(StringUtil.isEmpty(merchantId)){
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户Id,不能为空");
        }
        //获取商户下的全部门店列表
        List<Store> storeList=storeService.getRepository().findByMerchantId(merchantId);
        List<Map<String,Object>>mapList=storeMapList(storeList);
        return Resp.success(mapList,"查询成功");
    }

    @GetMapping("/list")
    @ApiOperation(value="商户APP交易数据月", notes="商户APP我的商户月")
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=MerchantMonthDTO.class)})
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="beginTime",dataType="String",value="开始时间格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="endTime",dataType="String",value="结束时间格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="storeId",dataType="String",value="门店Id,全部为空",required = true)})
    public Resp<Map<String,Object>> myMerchantList(@UserId String userId,String beginTime, String endTime, String storeId)throws Exception {
        if(StringUtil.isEmpty(beginTime) ||StringUtil.isEmpty(endTime)){
            return new Resp().error(Resp.Status.PARAM_ERROR, "beginTime:"+beginTime+" endTime:"+endTime + "必填参数不能为空");
        }
        Map<String,Object> map=new HashMap<>();
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        Order order;
        List<MerchantMonthDTO> dtoList=new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        //店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            //店员的最早收款记录
             order=orderService.getRepository().queryTop1ByAndUserIdOrderByCreateTimeAsc(userId);
             List<Map<String,String>> mapList= dateTimeMonthList(beginTime,endTime);
             for(Map<String,String> stringMap:mapList){
                 List<Object[]> merchantMonth=merchantService.getRepository().getMyUserIdMonth(userId,stringMap.get("beginTime"),stringMap.get("endTime"));
                 MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
                 Date date = dateFormat.parse(stringMap.get("beginTime"));
                 monthDTO.setMonthDate(dateFormat.format(date));
                 dtoList.add(monthDTO);
             }
        }else{
            List<String> storeIds=new ArrayList<>();
            // 店长
            if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
                storeIds.add(merchantUserDTO.getStoreId());
            }else{
                //获取门店Id,商户
                storeIds=storeList(userId,storeId);
            }
            //全部门店时显示最早收款的门店
            order=orderService.getRepository().queryTop1ByAndStoreIdInOrderByCreateTimeAsc(storeIds);
            List<Map<String,String>> mapList= dateTimeMonthList(beginTime,endTime);
            for(Map<String,String> stringMap:mapList){
                List<Object[]> merchantMonth=merchantService.getRepository().getMyMerchantMonth(storeIds,stringMap.get("beginTime"),stringMap.get("endTime"));
                MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
                Date date = dateFormat.parse(stringMap.get("beginTime"));
                monthDTO.setMonthDate(dateFormat.format(date));
                dtoList.add(monthDTO);
            }
        }
        dtoList.sort(Comparator.comparing(MerchantMonthDTO::getMonthDate).reversed());
        map.put("monthList",dtoList);
        map.put("merchantName","");
        map.put("orderTime","");
        if(null!=order){
            map.put("merchantName",order.getStoreName());
            map.put("orderTime",com.fzy.admin.fp.common.util.DateUtil.getDateFormat(order.getPayTime()));
        }
        return Resp.success(map,"查询成功");
    }



    @GetMapping("/merDay")
    @ApiOperation(value="商户交易数据天", notes="商户交易数据天")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="beginMonth",dataType="String",value="月份格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="storeId",dataType="String",value="门店Id,为空查询全部",required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=MerchantMonthDTO.class)})
    public Resp merDay(@UserId String userId, String beginMonth, String storeId) throws Exception{
        MerchantUserDTO merchantUserDTO = merchantUserFeign.findUser(userId);
        List<MerchantMonthDTO> dtoList=new ArrayList<>();
        //根据月份获取天数，不超过当前时间
        List<Map<String,String>> maps = dateTimeDayList(beginMonth);
        //店员
        if (MerchantUserDTO.UserType.EMPLOYEES.getCode().equals(merchantUserDTO.getUserType())) {
            for(Map<String,String> stringMap:maps){
                Date date = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(stringMap.get("beginTime"));
                List<Object[]> merchantMonth=merchantService.getRepository().getMyUserIdMonth(userId,stringMap.get("beginTime"),stringMap.get("endTime"));
                MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
                monthDTO.setMonthDate(com.fzy.admin.fp.common.util.DateUtil.getDateFormat(date));
                dtoList.add(monthDTO);
            }
        }else {
            //商户，店长
            List<String> storeIds=new ArrayList<>();
            //店长
            if (MerchantUserDTO.UserType.MANAGER.getCode().equals(merchantUserDTO.getUserType())) {
                storeIds.add(merchantUserDTO.getStoreId());
            }else{
                //获取门店Id,商户
                storeIds=storeList(userId,storeId);
            }
            for(Map<String,String> stringMap:maps){
                Date date = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(stringMap.get("beginTime").toString());
                List<Object[]> merchantMonth=merchantService.getRepository().getMyMerchantMonth(storeIds,stringMap.get("beginTime"),stringMap.get("endTime"));
                MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
                monthDTO.setMonthDate(com.fzy.admin.fp.common.util.DateUtil.getDateFormat(date));
                dtoList.add(monthDTO);
            }
        }
        //按时间排序
        dtoList.sort(Comparator.comparing(MerchantMonthDTO::getMonthDate).reversed());
        return Resp.success(dtoList,"查询成功");
    }


    @GetMapping("/disMonth")
    @ApiOperation(value="分销交易数据按月", notes="分销交易数据按月")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="beginTime",dataType="String",value="开始时间格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="endTime",dataType="String",value="结束时间格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="merchantId",dataType="String",value="商户Id",required = true),
            @ApiImplicitParam(paramType="query",name="storeId",dataType="String",value="门店Id,为空查询全部",required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=MerchantMonthDTO.class)})
    public Resp disMonth(@UserId String userId,String beginTime, String endTime, String merchantId,String storeId) throws Exception{
        if(StringUtil.isEmpty(beginTime) ||StringUtil.isEmpty(endTime) || StringUtil.isEmpty(merchantId)  ){
            return new Resp().error(Resp.Status.PARAM_ERROR, "beginTime:"+beginTime+" endTime:"+endTime + " merchantId:" + merchantId + "必填参数不能为空");
        }
        Map<String,Object> map=new HashMap<>();
        //查询商户名称
        Merchant merchant=merchantService.getRepository().getOne(merchantId);
        //获取商户下的全部门店列表
        List<Store> storeList=storeService.getRepository().findByMerchantId(merchant.getId());
        //获取门店ID
        List<String> storeIds=new ArrayList<>();
        if(StringUtil.isNotEmpty(storeId)){
            storeIds.add(storeId);
        }else{
            storeIds = storeList.stream().map(Store::getId).collect(Collectors.toList());
        }
        //查询商户最早的收款信息
        Order order=orderService.getRepository().queryTop1ByAndStoreIdInOrderByCreateTimeAsc(storeIds);
        map.put("merchantName","");
        map.put("orderTime","");
        if(null != order){
            map.put("merchantName",order.getStoreName());
            map.put("orderTime",com.fzy.admin.fp.common.util.DateUtil.getDateFormat(order.getPayTime()));
        }
        //时间按月查询
        List<Map<String,String>>  maps=dateTimeMonthList(beginTime,endTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        List<MerchantMonthDTO> dtoList=new ArrayList<>();
        for(Map<String,String> stringMap:maps){
            //按月份查询开始结束
            List<Object[]> merchantMonth=merchantService.getRepository().getMyMerchantMonth(storeIds,stringMap.get("beginTime"),stringMap.get("endTime"));
            MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
            Date date = dateFormat.parse(stringMap.get("beginTime"));
            monthDTO.setMonthDate(dateFormat.format(date));
            dtoList.add(monthDTO);
        }
        dtoList.sort(Comparator.comparing(MerchantMonthDTO::getMonthDate).reversed());
        map.put("monthList",dtoList);
        return Resp.success(map,"查询成功");
    }


    @GetMapping("/disDay")
    @ApiOperation(value="分销交易数据按天", notes="分销交易数据按天")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="beginMonth",dataType="String",value="月份格式 yyyy-mm",required = true),
            @ApiImplicitParam(paramType="query",name="merchantId",dataType="String",value="商户Id",required = true),
            @ApiImplicitParam(paramType="query",name="storeId",dataType="String",value="门店Id",required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "ok", response=MerchantMonthDTO.class)})
    public Resp myMerchantDay(@UserId String userId, String beginMonth, String merchantId,String storeId) throws Exception{
        //根据月份获取天数查询,结束时间不能超过当前时间
        List<Map<String,String>> maps = dateTimeDayList(beginMonth);
        //查询商户名称
        Merchant merchant=merchantService.getRepository().getOne(merchantId);
        //获取门店ID
        List<String> storeIds=new ArrayList<>();
        if(StringUtil.isNotEmpty(storeId)){
            storeIds.add(storeId);
        }else{
            //获取商户下的全部门店列表
            List<Store> storeList=storeService.getRepository().findByMerchantId(merchant.getId());
            storeIds = storeList.stream().map(Store::getId).collect(Collectors.toList());
        }
        List<MerchantMonthDTO> dtoList=new ArrayList<>();
        //获取每天结束时间，按照天数查询
            for(Map<String,String> map:maps){
            Date date = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(map.get("beginTime"));
            //循环查询
            List<Object[]> merchantMonth=merchantService.getRepository().getMyMerchantMonth(storeIds,map.get("beginTime"),map.get("endTime"));
            MerchantMonthDTO monthDTO=getMerchantObject(merchantMonth);
            monthDTO.setMonthDate(com.fzy.admin.fp.common.util.DateUtil.getDateFormat(date));
            dtoList.add(monthDTO);
        }
        dtoList.sort(Comparator.comparing(MerchantMonthDTO::getMonthDate).reversed());
        return Resp.success(dtoList,"查询成功");
    }

    /**
     * 商户按月份统计
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static List<Map<String,String>> dateTimeMonthList(String beginTime,String endTime)throws Exception{
        //日期转换
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date beginDate=dateFormat.parse(beginTime);
        Date endDate=dateFormat.parse(endTime);
        List<DateTime> dateTimeList = DateUtil.rangeToList(beginDate, endDate, DateField.MONTH);
        List<Map<String,String>> mapList=new ArrayList<>();
        for(DateTime dateTime:dateTimeList){
            Date date = dateFormat.parse(dateTime.toString());
            DateTime endOfMonth = DateUtil.endOfMonth(date);
            Map<String,String> map=new HashMap<>();
            map.put("beginTime",dateTime.toString());
            map.put("endTime",endOfMonth.toString());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 商户按天份统计
     * @param beginMonth
     * @return
     */
    public List<Map<String,String>> dateTimeDayList(String beginMonth){
        List<Map<String,String>> maps=new ArrayList<>();
        //根据月份获取天数查询,结束时间不能超过当前时间
        beginMonth=beginMonth+"-01";
        //获取月份
        String beginM=beginMonth.substring(5,7);
        //当前时间
        String endM=com.fzy.admin.fp.common.util.DateUtil.getDateFormat(new Date()).substring(5,7);
        Date begin=com.fzy.admin.fp.common.util.DateUtil.getDateFormat(beginMonth);
        DateTime endOfDay;
        //判断日期是否当前月份
        if(beginM.equals(endM)){
            endOfDay=new DateTime();
        }else{
            endOfDay = DateUtil.endOfMonth(begin);
        }
        //获取每天开始时间
        List<DateTime> dateTimeList = DateUtil.rangeToList(begin, endOfDay, DateField.DAY_OF_WEEK);
        //获取每天结束时间，按照天数查询
        for(DateTime dateTime:dateTimeList){
            Date date = com.fzy.admin.fp.common.util.DateUtil.getDateTimeFormat(dateTime.toString());
            DateTime endDay = DateUtil.endOfDay(date);
            Map<String,String> map=new HashMap<>();
            map.put("beginTime",dateTime.toString());
            map.put("endTime",endDay.toString());
            maps.add(map);
        }
        return maps;
    }

    /**
     * 查询数据转换
     * @param merchantMonth
     * @return
     */
    public static MerchantMonthDTO getMerchantObject(List<Object[]> merchantMonth) {
        MerchantMonthDTO monthDTO = new MerchantMonthDTO();
        for (Object[] obj : merchantMonth) {
            monthDTO.setTotalPrice((BigDecimal) obj[0]);
            monthDTO.setTotalNum((BigInteger) obj[1]);
            monthDTO.setFaseNum((BigInteger) obj[2]);
            monthDTO.setFacePrice((BigDecimal) obj[3]);
            monthDTO.setFase2Num((BigInteger) obj[4]);
            return monthDTO;
        }
        return monthDTO;
    }

    /**
     * 如果门店Id为空，根据userId查询全部
     * @param userId
     * @param storeId
     * @return
     */
    public  List<String> storeList(String userId,String storeId){
        List<String> storeIds=new ArrayList<>();
        //判断门店ID是否为如果为空查询全部
        if("".equals(storeId) || null==storeId){
            MerchantUser user = merchantUserService.findOne(userId);
            //查询商户
            Merchant merchant= merchantService.getRepository().getOne(user.getMerchantId());
            //获取商户下的全部门店列表
            List<Store> storeList=storeService.getRepository().findByMerchantId(merchant.getId());
            //获取门店ID
            storeIds = storeList.stream().map(Store::getId).collect(Collectors.toList());
        }else{
            storeIds.add(storeId);
        }
        return  storeIds;
    }

    /**
     * 商户门店信息
     * @param storeList
     * @return
     */
    public List<Map<String,Object>> storeMapList(List<Store> storeList){
        List<Map<String,Object>> mapList=new ArrayList<>();
        for(Store store:storeList){
            Map<String,Object> objectMap=new HashMap<>();
            objectMap.put("storeName",store.getName());
            objectMap.put("storeId",store.getId());
            mapList.add(objectMap);
        }
        return mapList;
    }

}
