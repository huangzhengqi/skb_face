package com.fzy.admin.fp.common.spring.base;

import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

/**
 * @author zk
 * @description 基础服务层
 * @create 2018-07-15 15:39
 **/
public interface BaseService<E extends BaseEntity> {

    BaseRepository<E> getRepository();

    /**
     * 根据ID获取
     */
    default E findOne(String id) {
        return getRepository().findOne(id);
    }


    /**
     * 根据ID获取
     */
    default Object findOne(Specification<E> spec) {
        return getRepository().findOne(spec);
    }

    /**
     * 获取所有列表
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 根据ID数组获取列表
     */
    default List<E> findAll(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return getRepository().findAll(idList);
    }

    /**
     * 根据ID数组获取列表
     */
    default List<E> findAll(List<String> ids) {
        return getRepository().findAll(ids);
    }

    /**
     * 获取总数
     *
     * @return
     */
    default Long getTotalCount() {
        return getRepository().count();
    }

    /**
     * 保存
     */
    default E save(E entity) {

        return getRepository().save(entity);
    }

    /**
     * 保存
     */
    default Iterable<E> save(Iterable<E> list) {
        return getRepository().save(list);
    }

    /**
     * 修改
     */
    default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }


    /**
     * 删除
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 根据Id删除
     */
    default void delete(String id) {
        getRepository().delete(id);
    }

    /**
     * 批量删除
     */
    default void delete(Iterable<E> entities) {
        getRepository().delete(entities);
    }

    /**
     * 清空缓存，提交持久化
     */
    default void flush() {
        getRepository().flush();
    }

    /**
     * 根据条件查询获取
     *
     * @param spec
     * @return
     */
    default List<E> findAll(Specification<E> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * 分页获取
     */
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据查询条件分页获取
     */
    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 获取查询条件的结果数
     */
    default long count(Specification<E> spec) {
        return getRepository().count(spec);
    }

    /**
     * @author zk
     * @Description 分页查询
     * @date 2018-07-18 16:47
     */
    default Page<E> list(Object model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<E> page = findAll(specification, pageable);
        return page;
    }
}
