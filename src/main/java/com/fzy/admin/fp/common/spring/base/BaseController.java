package com.fzy.admin.fp.common.spring.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.jpa.RelationUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author zk
 * @description 基础控制层
 * @create 2018-07-15 15:43
 **/
@Slf4j
public abstract class BaseController<E extends BaseEntity> extends BaseContent {

    @PersistenceContext
    private EntityManager em;

    /**
     * 获取service
     *
     * @return
     */
    @Resource
    public abstract BaseService<E> getService();


    /**
     * @author zk
     * @Description 单表多条件分页查询 此处delFlag 默认为 1
     * @date 2018-07-19 15:29
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Resp list(E entity, PageVo pageVo) {
        Page<E> page = getService().list(entity, pageVo);

        for (E e : page.getContent()) {
            RelationUtil.initRelation(e, null, RelationUtil.MapType.IGNORE);
        }
        return Resp.success(page);
    }


    /**
     * @author zk
     * @Description 添加数据
     * @date 2018-07-19 15:31
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Resp save(@Valid E entity) {
        E e = getService().save(entity);
        return Resp.success("添加成功");
    }

    /**
     * @author zk
     * @Description 更新数据
     * @date 2018-07-19 15:32
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Resp update(E entity) {
        if (ParamUtil.isBlank(entity.getId())) {
            log.error("The entity id is blank->e:{}", entity);
            return new Resp().error(Resp.Status.PARAM_ERROR, "Id will not be blank");
        }
        E e = getService().findOne(entity.getId());
        if (e == null) {
            log.error("The entity is not exist->e:{}", entity);
            return new Resp().error(Resp.Status.PARAM_ERROR, "This id is not exist," + entity.getId());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(entity, e, copyOptions);
        //对实体类中的@validation注解进行校验
        BindingResult bindingResult = Validation.valid(e);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
        getService().update(e);
        return Resp.success("修改成功");
    }

    /**
     * @author zk
     * @Description 通过ids批量删除 此处为物理删除
     * @date 2018-07-19 15:34
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Resp delete(String[] ids) {
        List<E> list = getService().findAll(ids);
        for (E e : list) {
            getService().delete(e.getId());
            RelationUtil.delRelation(e, em, false);
        }
        return Resp.success("删除成功");
    }

    /**
     * @author zk
     * @Description 通过ids批量删除 此处为逻辑删除
     * @date 2018-07-19 15:34
     */
    @RequestMapping(value = "/logical_del", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Resp logicalDel(String[] ids) {
        List<E> list = getService().findAll(ids);
        for (E e : list) {
            if (e == null) {
                continue;
            }
            //进行级联逻辑删除 若无需要可不使用
            RelationUtil.delRelation(e, em, true);
            e.setDelFlag(CommonConstant.DEL_FLAG);
        }
        getService().save(list);
        return Resp.success("删除成功");
    }

    /**
     * @author zk
     * @Description 获取下拉菜单
     * 获取所有未逻辑删除元素
     * @date 2018-07-16 14:08
     */
    @RequestMapping(value = "/select_item", method = RequestMethod.GET)
    @ResponseBody
    public Resp selectItem() {
        //用于获取泛型E的类名
        Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        StringBuilder sb = new StringBuilder();
        sb.append("select new ").append(SelectItem.class.getName()).append("(a.").append(CommonConstant.ID_NAME).append(",a.")
                .append(CommonConstant.NAME_NAME).append(") from ");
        sb.append(entityClass.getSimpleName());
        sb.append(" a where ").append(CommonConstant.DEL_FLAG_NAME).append(" = ");
        sb.append(CommonConstant.NORMAL_FLAG);
        Query query = em.createQuery(sb.toString(), SelectItem.class);
        List list = query.getResultList();
        return Resp.success(list);
    }


}
