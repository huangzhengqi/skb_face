package com.fzy.admin.fp.notice.controller;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.notice.domain.Notice;
import com.fzy.admin.fp.notice.dto.NoticeDto;
import com.fzy.admin.fp.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hzq
 * @description 公告控制层
 * @create 2019-10-15 10:00:12
 */
@Slf4j
@RestController
@RequestMapping("/notice/notice")
public class NoticeController extends BaseController<Notice>{

    @Resource
    private NoticeService noticeService;

    @Resource
    private CompanyService companyService;

    @Override
    public BaseService<Notice> getService() {
        return noticeService;
    }

    /**
     * @Description :添加公告
     */
    @PostMapping("/addNotice")
    @Transactional
    public Resp addNotice(NoticeDto model){

        if(ParamUtil.isBlank(model.getTitle())){
            throw new BaseException("请填写标题", Resp.Status.PARAM_ERROR.getCode());
        }

        if(ParamUtil.isBlank(model.getContent())){
            throw new BaseException("请填写内容", Resp.Status.PARAM_ERROR.getCode());
        }

        if(ParamUtil.isBlank(model.getCompanyId())){
            throw new BaseException("代理商不存在", Resp.Status.PARAM_ERROR.getCode());
        }

        Company company = companyService.findOne(model.getCompanyId());

        //是否是服务商
        if(!company.getType().equals(Company.Type.PROVIDERS.getCode())){
            throw new BaseException("当前用户没有添加公告权限", Resp.Status.NO_ACCESS.getCode());
        }

        return Resp.success(noticeService.addNotice(model.getTitle(),model.getContent(),company));
    }

    /**
     * @Description :修改公告
     */
    @PostMapping("/updateNotice")
    @Transactional
    public Resp updateNotice(NoticeDto model){

        if(ParamUtil.isBlank(model.getTitle())){
            throw new BaseException("请填写标题", Resp.Status.PARAM_ERROR.getCode());
        }

        if(ParamUtil.isBlank(model.getContent())){
            throw new BaseException("请填写内容", Resp.Status.PARAM_ERROR.getCode());
        }

        return Resp.success(noticeService.updateNotice(model));
    }

    /**
     *  查询单条公告
     */
    @GetMapping("/getNotice")
    public Resp getNotice(String id){
        return Resp.success(noticeService.getRepository().findOne(id));
    }

    /**
     * 查询公告列表
     */
    @GetMapping("/listNotice")
    public Resp listNotice(NoticeDto model, PageVo pageVo){
        return Resp.success(noticeService.listNotice(model, pageVo));
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/deleteNotice")
    @Transactional
    public Resp deleteNotice(String[] ids){

        if (ParamUtil.isBlank(ids)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择删除公告");
        }
        List<Notice> noticeList = getService().findAll(ids);
        for (Notice notice : noticeList) {
            //删除公告
            noticeService.delete(notice);
        }
        return Resp.success("删除成功");
    }
}
