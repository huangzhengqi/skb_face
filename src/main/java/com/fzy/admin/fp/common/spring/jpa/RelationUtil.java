package com.fzy.admin.fp.common.spring.jpa;


import cn.hutool.core.util.ReflectUtil;
import com.fzy.admin.fp.common.annotation.Relation;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.annotation.Relation;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.SelectItem;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zk
 * @description 处理带有@Relation注解的属性
 * @create 2018-07-18 10:43
 **/
public class RelationUtil {

    public enum MapType {
        IGNORE,
        NEED;

        private MapType() {
        }
    }

    public static <T extends BaseEntity> T initRelation(T entity) {
        return initRelation(entity, null, null);
    }

    public static <T extends BaseEntity> T initRelation(T entity, Set<String> set) {
        return initRelation(entity, set, null);
    }

    /**
     * @author zk
     * @Description 处理带有@Relation注解的属性
     * entity:需要处理的实体类 必填
     * set：需要忽略的关联实体名，如student，值应与关联实体类变量名一致 可null
     * type：自定义枚举类MapType 可null
     * 当set type都为null时，则默认填充所有关联实体
     * @date 2018-07-18 15:40
     */
    public static <T extends BaseEntity> T initRelation(T entity, Set<String> set, MapType type) {
        if (entity == null) {
            return entity;
        }
        if (set == null) {
            set = new HashSet<String>();
        }
        Class cls = entity.getClass();
        //获取该类中的所有属性
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            //当set type都为null时，则默认填充所有关联实体
            if (MapType.IGNORE.equals(type) && set.contains(f.getName())) {
                //当set类型为忽略属性集并且set中有该属性 则忽略
                continue;
            } else if (MapType.NEED.equals(type) && !set.contains(f.getName())) {
                //当set类型为需要属性集并且set中无该属性 则忽略
                continue;
            }
            //获取该属性上的@Relation注解
            Relation relation = f.getAnnotation(Relation.class);
            if (relation == null) {
                continue;
            }
            //该实体类对应的service
            BaseService baseService = null;
            //相关联实体类的Id Field对象
            Field idField = null;
            //如果该属性类型为"一"方，并且带有关联类的id的变量名(所以"一方"的Id变量名为必填项)
            if (relation.relationType().equals(RelationType.ONE) &&
                    !ParamUtil.isBlank(relation.idProperty())) {
                try {
                    idField = cls.getDeclaredField(relation.idProperty());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    continue;
                }
                //从上下文中获取该关联类的service
                if (ParamUtil.isBlank(relation.serviceName())) {
                    //如果注解中的serviceName中没有值，则通过类名+Service拼接
                    baseService = (BaseService) SpringContextUtil.getBean(ParamUtil.first2LowerCase(f.getName()) + "Service");
                } else {
                    baseService = (BaseService) SpringContextUtil.getBean(ParamUtil.first2LowerCase(relation.serviceName()));
                }
                if (baseService == null || idField == null) {
                    continue;
                }
                //获取关联实体类的ID
                String id = (String) ReflectUtil.getFieldValue(entity, idField);
                if (ParamUtil.isBlank(id)) {
                    continue;
                }
                //对关联实体类进行赋值
                ReflectUtil.setFieldValue(entity, f, (T) baseService.findOne(id));
            } else if (relation.relationType().equals(RelationType.MANY)) {
                // TODO: 2018/7/19 由于不知道实际应用场景是否频繁，先预留

            }
            baseService = null;
        }
        return entity;
    }

    /**
     * @author zk
     * @Description 主动级联删除 直接调用父类baseEntity的EntityManager
     * @date 2018-07-19 16:03
     */
    public static <T extends BaseEntity, E extends BaseEntity> T delRelation(T entity, EntityManager em, Boolean isLogical) {
        //假设entity为学校类
        if (entity == null) {
            return entity;
        }
        Class cls = entity.getClass();
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            //获取entity中的@Relation注解的属性 假设该f为教师类
            Relation relation = f.getAnnotation(Relation.class);
            if (relation == null) {
                continue;
            }
            //教师类的service
            BaseService baseService = null;
            //学校类的id
            Field idField = null;
            //教师类的delFlag
            Field delFlagField = null;
            if (relation.relationType().equals(RelationType.MANY)) {
                idField = ReflectUtil.getField(cls, CommonConstant.ID_NAME);
                delFlagField = ReflectUtil.getField(cls, CommonConstant.DEL_FLAG_NAME);
                baseService = (BaseService) SpringContextUtil.getBean(ParamUtil.first2LowerCase(relation.serviceName()));
                if (baseService == null || idField == null || delFlagField == null) {
                    continue;
                }
                //获取学校类的id
                String id = (String) ReflectUtil.getFieldValue(entity, idField);
                StringBuilder sb = new StringBuilder();
//                sb.append("select new cn.lysj.framework.common.utils.com.lysj.admin.utils.web.SelectItem(a.id)");
//                sb.append("select new ").append(SelectItem.class.getName()).append("(a.id)");
                sb.append("select new ").append(SelectItem.class.getName()).append("(a.").append(CommonConstant.ID_NAME).append(") ");
                Type t = f.getGenericType();
                ParameterizedType pt = (ParameterizedType) t;
                Type[] ts = pt.getActualTypeArguments();//获取List<T>中的泛型
                Class c = (Class) ts[0];
                sb.append(" from ");
                sb.append(c.getSimpleName());
//                sb.append(" a where a.delFlag = ");
                sb.append(" a where a.").append(CommonConstant.DEL_FLAG_NAME).append(" = ");
                sb.append(CommonConstant.NORMAL_FLAG);
                sb.append(" and a.");
                // TODO: 2018/12/26 此处是否使用relation注解的idpro？
                sb.append(ParamUtil.first2LowerCase(cls.getSimpleName()));
                sb.append("Id = ");
                sb.append(id);
                System.err.println(sb.toString());
                Query query = em.createQuery(sb.toString(), SelectItem.class);
                List<SelectItem> list = query.getResultList();
                for (SelectItem selectItem : list) {
                    if (ParamUtil.isBlank(selectItem.getValue())) {
                        continue;
                    }
                    E e = (E) baseService.findOne(selectItem.getValue());
                    if (e == null) {
                        continue;
                    }
                    if (isLogical) {
                        e.setDelFlag(CommonConstant.DEL_FLAG);
                        baseService.save(e);
                    } else {
                        baseService.delete(e.getId());
                    }
                    baseService = null;
                    //递归调用
                    delRelation(e, em, isLogical);
                }
            }
        }
        return entity;
    }


}
