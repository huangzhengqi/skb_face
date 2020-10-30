package com.fzy.admin.fp.distribution.shop.domain;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @Author yy
 * @Date 2019-11-29 13:46:51
 * @Desp
 **/
@Data
@Entity
@Table(name="lysj_dist_goods_property")
public class GoodsProperty extends CompanyBaseEntity {
    private String property;

    private String goodsId;

    @Column(columnDefinition = "int(1) default 0")
    private Integer status;//0启用 1禁用

}
