package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.distribution.app.dto.DistWindowDTO;
import com.fzy.admin.fp.distribution.app.repository.AppDistWindowLogRepository;
import com.fzy.admin.fp.distribution.app.service.AppDistWindowLogService;
import com.fzy.admin.fp.distribution.app.service.AppDistWindowService;
import com.fzy.admin.fp.distribution.app.utils.DateUtil;
import com.fzy.admin.fp.distribution.app.vo.*;
import com.fzy.admin.fp.distribution.article.dto.ArticleSortDTO;
import com.fzy.admin.fp.distribution.article.vo.ArticlePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.PublicKey;
import java.util.*;

/**
 * @Author hzq
 * @Date 2020-03-10 10:44:27
 * @Desp 首页弹窗
 **/
@RestController
@RequestMapping("/dist/app/window")
@Api(value="AppDistWindowController", tags={"分销-app弹窗页面"})
public class AppDistWindowController extends BaseContent {

    @Resource
    private AppDistWindowService appDistWindowService;

    @Resource
    private AppDistWindowLogService appDistWindowLogService;

    @Resource
    private AppDistWindowLogRepository appDistWindowLogRepository;

    /**
     * 后台添加弹窗内容
     *
     * @param distWindowVO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value="添加推送内容", notes="添加推送内容")
    public Resp addWindow(DistWindowVO distWindowVO) throws Exception {

        if (distWindowVO.getBeginTime() == null || distWindowVO.getBeginTime().equals("")) {
            throw new BaseException("开始日期不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (distWindowVO.getEndTime() == null || distWindowVO.getEndTime().equals("")) {
            throw new BaseException("结束日期不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (distWindowVO.getTitle() == null || distWindowVO.getTitle().equals("")) {
            throw new BaseException("文章标题不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        if (distWindowVO.getContents() == null || distWindowVO.getContents().equals("")) {
            throw new BaseException("文章内容不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        DistWindow distWindow=new DistWindow();
        BeanUtil.copyProperties(distWindowVO, distWindow);

        String serviceProviderId=(String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        distWindow.setServiceProviderId(serviceProviderId);
        distWindow.setStatus(Integer.valueOf(1));
        distWindow.setWeight(Integer.valueOf(0));

        //当前时间
        long nowTime=System.currentTimeMillis();
        //投放时间
        long beginTime=distWindowVO.getBeginTime().getTime();
        //结束时间
        long endTime=distWindowVO.getEndTime().getTime();
        int type=DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
        distWindow.setType(Integer.valueOf(type));
        Date endTimeDate=DateUtil.getEndOfDay(distWindowVO.getEndTime());
        distWindow.setEndTime(endTimeDate);
        DistWindow window=appDistWindowService.save(distWindow);

        return Resp.success(window, "保存成功");

    }


    /**
     * 推送列表
     */
    @GetMapping("/query")
    @ApiOperation(value="服务商后台推送列表", notes="服务商后台推送列表")
    public Resp queryDistWindow(@TokenInfo(property="serviceProviderId") String serviceProviderId, DistWindowDayVO distWindowDayVO, PageVo pageVo) throws Exception {

        Pageable pageable=PageUtil.initPage(pageVo);
        Specification<DistWindow> specification=new Specification<DistWindow>() {
            @Override
            public Predicate toPredicate(Root<DistWindow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                //日期查询
                if (distWindowDayVO.getStartTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), distWindowDayVO.getStartTime()));
                }
                if (distWindowDayVO.getEndTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), distWindowDayVO.getEndTime()));
                }
                Predicate[] pre=new Predicate[predicates.size()];
                Predicate Pre_And=cb.and(predicates.toArray(pre));
                if (StringUtil.isNotEmpty(distWindowDayVO.getKeyword())) {
                    List<Predicate> listPermission=new ArrayList<>();
                    listPermission.add(cb.like(root.get("contents"), distWindowDayVO.getKeyword() + "%"));
                    listPermission.add(cb.like(root.get("title"), distWindowDayVO.getKeyword() + "%"));
                    Predicate[] predicatesPermissionArr=new Predicate[listPermission.size()];
                    Predicate predicatesPermission=cb.or(listPermission.toArray(predicatesPermissionArr));
                    return query.where(Pre_And, predicatesPermission).getRestriction();
                }
                return query.where(Pre_And).getRestriction();
            }
        };
        Map map=new HashMap();
        ArticlePageVO articlePageVO=new ArticlePageVO();
        Page<DistWindow> all=appDistWindowService.findAll(specification, pageable);
        articlePageVO.setTotalElements(all.getTotalElements());
        articlePageVO.setTotalPages(all.getTotalPages());
        map.put("totalElements", articlePageVO.getTotalElements());
        map.put("totalPages", articlePageVO.getTotalPages());
        List<DistWindow> content=all.getContent();
        if (content.size() <= 0 || content == null) {
            map.put("all", content);
            return Resp.success(map);
        }

        List list=new ArrayList();
        for (DistWindow distWindow : content) {

            long beginTime=distWindow.getBeginTime().getTime();//投放开始时间
            long endTime=distWindow.getEndTime().getTime();//投放结束时间
            long nowTime=new Date().getTime();//当前时间
            int type=DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            if (Integer.valueOf(type) != distWindow.getType()) {
                distWindow.setType(type);
                appDistWindowService.update(distWindow);//修改状态
            }
            list.add(distWindow);
        }
        map.put("all", list);
        return Resp.success(map);
    }

    /**
     * 修改排序值
     */
    @PutMapping("/update_weight")
    @ApiOperation(value="修改排序值", notes="修改排序值")
    public Resp updateWeight(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid ArticleSortDTO articleSortDTO) {

        DistWindow distWindow=appDistWindowService.getRepository().findByServiceProviderIdAndId(serviceProviderId, articleSortDTO.getId());
        if (distWindow == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (appDistWindowService.getRepository().countByServiceProviderIdAndWeight(serviceProviderId, articleSortDTO.getWeight()) != 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该排序数值已存在，不可重复！");
        }
        distWindow.setWeight(articleSortDTO.getWeight());
        DistWindow window=appDistWindowService.update(distWindow);
        return Resp.success(window, "修改成功");

    }

    /**
     * 修改状态
     */
    @PutMapping("/update_status")
    @ApiOperation(value="修改状态", notes="修改状态")
    public Resp updateStatus(@TokenInfo(property="serviceProviderId") String serviceProviderId, @Valid WindowStateVo windowStateVo) {

        DistWindow distWindow=appDistWindowService.getRepository().findByServiceProviderIdAndId(serviceProviderId, windowStateVo.getId());
        if (distWindow == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        distWindow.setStatus(windowStateVo.getStatus());
        if (windowStateVo.getStatus().equals(DistWindow.Status.ENABLED)) {
            distWindow.setType(0);
        } else {
            distWindow.setType(1);
        }
        DistWindow window=appDistWindowService.update(distWindow);
        return Resp.success(window, "修改成功");
    }

    /**
     * 删除推送消息
     */
    @DeleteMapping("/delete")
    @ApiOperation(value="删除推送消息", notes="删除推送消息")
    public Resp delete(String id) {
        appDistWindowService.delete(id);

        //根据推送消息把app推送详情消息也删除
        appDistWindowLogService.getRepository().deleteByIds(id);
        return Resp.success("删除成功");
    }


    /**
     * app首页弹窗显示
     */
    @GetMapping("/home_view")
    @ApiOperation(value="app首页弹窗显示", notes="app首页弹窗显示")
    public Resp homeView(@TokenInfo(property="serviceProviderId") String serviceProviderId, String userId) {
        return Resp.success(appDistWindowService.homeView(serviceProviderId, userId));
    }

    /**
     * 推送详情页面
     */
    @GetMapping("/details")
    @ApiOperation(value="app推送详情页面", notes="app推送详情页面")
    public Resp details(String windowId, String userId) {

        DistWindowLog distWindowLog=appDistWindowLogRepository.findByWindowIdAndUserId(windowId, userId);
        List<DistAppWindowUserDetailsVO> userDetails=appDistWindowLogRepository.getUserDetails(windowId, userId);
        if (userDetails.size() == 0 || userDetails == null) {
            return Resp.success("推送不存在");
        }

        for (DistAppWindowUserDetailsVO distAppWindowUserDetailsVO : userDetails){
            if (distAppWindowUserDetailsVO.getIsRead().equals("0")) {
                distWindowLog.setIsRead(1);
                appDistWindowLogRepository.save(distWindowLog);
            }
        }
        return Resp.success(userDetails);
    }

    /**
     * app推送列表
     */
    @GetMapping("/query_app_window")
    @ApiOperation(value="app推送列表", notes="app推送列表")
    public Resp queryDistAppWindow(@TokenInfo(property="serviceProviderId") String serviceProviderId, String userId) {

        HashMap map=new HashMap();
        map.put("isRead", "1");
        List<DistAppWindowUserListVO> distAppWindowUserList=appDistWindowLogRepository.getDistAppWindowUserList(serviceProviderId, userId);
        if (distAppWindowUserList.size() == 0 || distAppWindowUserList == null) {
            map.put("all", distAppWindowUserList);
            return Resp.success(map);
        }
        for (DistAppWindowUserListVO DistAppWindowUserListVO : distAppWindowUserList) {
            if (DistAppWindowUserListVO.getIsRead().equals("0")) {
                map.put("isRead", "0");
            }
        }
        map.put("all", distAppWindowUserList);
        return Resp.success(map);
    }

    @PutMapping("/update_window")
    @ApiOperation(value = "编辑公告" ,notes = "编辑公告")
    public Resp updateWindow(DistWindowDTO distWindowDTO) {
        DistWindow distWindow=appDistWindowService.getRepository().findOne(distWindowDTO.getId());
        BeanUtil.copyProperties(distWindowDTO, distWindow);
        return Resp.success(appDistWindowService.update(distWindow));
    }

    @GetMapping("/pc_details")
    @ApiOperation(value = "后台公告详情" ,notes = "后台公告详情")
    public Resp<DistWindow> pcDetails(String id){
        return Resp.success(appDistWindowService.getRepository().findOne(id));
    }
}
