package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 22:15 2019/5/26
 * @ Description: 批量二维码记录
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_qr_code_record")
public class QrCodeRecord extends CompanyBaseEntity {

    private Integer generateNumber;// 生成数量

    private String creatorName;//操作人

    private String downloadUrl;//下载链接
}
