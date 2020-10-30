package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-16 09:33:42
 * @Desp app下载链接
 **/

@Data
@Entity
@Table(name = "lysj_dist_app_down")
public class AppDown extends CompanyBaseEntity {
    private String url;
}
