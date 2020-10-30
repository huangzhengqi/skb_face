package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.MchBlankQrCode;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MchBlankQrCodeRepository extends BaseRepository<MchBlankQrCode> {

    MchBlankQrCode findByQrCodeId(String qrCodeId);

    List<MchBlankQrCode> findByMerchantId(String merchantId);

    @Query("from MchBlankQrCode de where de.qrCodeId= ?1 and de.qrCodeId not in (?2)")
    MchBlankQrCode getMchBlankQrCodeList(String qrcode, List<String> qrcodes);
}
