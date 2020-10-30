package com.fzy.admin.fp.common.spring.jpa;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zk
 * @description Springdata条件构造器
 * @create 2018-07-16 23:15
 **/
public class ConditionUtil {
    /**
     * @author zk
     * @Description
     * @date 2018-07-16 23:35
     */
    public static Specification createSpecification(final Object model) {

        Specification specification = new Specification() {
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                // 得到类对象
                Class cls = model.getClass();
                /* 得到类中的所有属性集合 */
                Field[] fs = ReflectUtil.getFields(cls);
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    if (f.getName().equals("serialVersionUID")) {
                        continue;
                    }
                    f.setAccessible(true); // 设置些属性是可以访问的
                    Object val = new Object();
                    try {
                        val = f.get(model);
                        if (!ParamUtil.isBlank(val)) {
                            if (String.class.equals(f.getType())) {
                                String s = (String) val;
                                if (ParamUtil.isBlank(s)) {
                                    continue;
                                }
                                if (f.getName().endsWith("Id") && !("isNull").equals(s)) {
                                    predicates.add(criteriaBuilder.equal(root.get(f.getName()), val));
                                }if(("isNull").equals(s)){
                                    predicates.add(criteriaBuilder.isNull(root.get(f.getName())));
                                }else {
                                    predicates.add(criteriaBuilder.like(root.get(f.getName()), "%" + val + "%"));
                                }
                            } else if (Long.class.equals(f.getType()) || long.class.equals(f.getType())) {
                                Long l = (Long) val;
                                /*查询金钱区间*/
                                if (f.getName().startsWith("start_")) {
                                    predicates.add(criteriaBuilder.greaterThan(root.get(f.getName().substring(6)), l));
                                } else if (f.getName().startsWith("end_")) {
                                    predicates.add(criteriaBuilder.lessThan(root.get(f.getName().substring(4)), l));
                                } else {
                                    predicates.add(criteriaBuilder.equal(root.get(f.getName()), l));
                                }

                            } else if (Integer.class.equals(f.getType()) || int.class.equals(f.getType())) {
                                Integer l = (Integer) val;
                                if (f.getName().startsWith("start_")) {
                                    predicates.add(criteriaBuilder.greaterThan(root.get(f.getName().substring(6)), l));
                                } else if (f.getName().startsWith("end_")) {
                                    predicates.add(criteriaBuilder.lessThan(root.get(f.getName().substring(4)), l));
                                } else {
                                    predicates.add(criteriaBuilder.equal(root.get(f.getName()), l));
                                }
                            } else if (Date.class.equals(f.getType())) {
                                /*查询时间区间*/
                                Date d = (Date) val;
                                if (f.getName().startsWith("start_")) {
                                    d = DateUtil.beginOfDay(d).toJdkDate();
                                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(f.getName().substring(6)), d));
                                } else if (f.getName().startsWith("end_")) {
                                    d = DateUtil.endOfDay(d).toJdkDate();
                                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(f.getName().substring(4)), d));
                                } else {
                                    predicates.add(criteriaBuilder.equal(root.get(f.getName()), val));
                                }
                            } else if (boolean.class.equals(f.getType()) || Boolean.class.equals(f.getType())) {
                                Boolean s = (Boolean) val;
                                predicates.add(criteriaBuilder.equal(root.get(f.getName()), s));
                            }
                        }
                        // 得到此属性的值
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return specification;
    }
}
