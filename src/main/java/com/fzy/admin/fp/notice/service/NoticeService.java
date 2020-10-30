package com.fzy.admin.fp.notice.service;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.ServiceProviderService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.notice.domain.Notice;
import com.fzy.admin.fp.notice.dto.NoticeDto;
import com.fzy.admin.fp.notice.repository.NoticeRepository;
import com.fzy.admin.fp.notice.vo.NoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hzq
 * @description 公告业务层
 * @create 2019-10-15 10:06:33
 */
@Slf4j
@Service
@Transactional
public class NoticeService implements BaseService<Notice> {

    @Resource
    private NoticeRepository noticeRepository;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private ServiceProviderService serviceProviderService;

    @Resource
    private HttpServletRequest request;

    @Override
    public BaseRepository<Notice> getRepository() {
        return noticeRepository;
    }

    public String addNotice(String title,String content,Company company) {

        Notice notice = new Notice();

        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        log.info("serviceId" + serviceId);
        notice.setServiceProviderId(serviceId);

        notice.setTitle(title);
        notice.setCompanyId(company.getId());

        //过滤符号
        //String replaceContent = content.replaceAll("<[.[^<]]*>", "");

        notice.setContent(content);
        notice.setType(company.getType());
        noticeRepository.save(notice);
        return "添加成功";
    }

    public String updateNotice(NoticeDto model) {

        Notice entity = noticeRepository.findOne(model.getId());
        entity.setTitle(model.getTitle());
        //过滤符号
        //String replaceContent = model.getContent().replaceAll("<[.[^<]]*>", "");
        entity.setContent(model.getContent());
        noticeRepository.save(entity);
        return "修改成功";
    }

    public Page<Notice> listNotice(NoticeDto model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo); //分页
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        log.info("serviceId:" + serviceId);
        model.setServiceProviderId(serviceId);
        Specification specification = ConditionUtil.createSpecification(model); //对象
        Page<Notice> page = noticeRepository.findAll(specification, pageable); //查询对象
        return page;
    }


}
