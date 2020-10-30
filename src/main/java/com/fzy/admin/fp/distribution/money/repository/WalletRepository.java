package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

public interface WalletRepository extends BaseRepository<Wallet> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Wallet findByUserId(String userId);

    @Modifying
    @Query("update Wallet set updateTime = :updateTime,balance=:balance,bonus=:bonus where id=:id and balance=:oldBalance and bonus=:oldBonus")
    int updateWallet(@Param("updateTime")Date updateTime, @Param("balance")BigDecimal balance,
                      @Param("bonus")BigDecimal bonus, @Param("id")String id,@Param("oldBalance")BigDecimal oldBalance,@Param("oldBonus")BigDecimal oldBonus);

    @Modifying
    @Query("update Wallet set updateTime = :updateTime,balance=:balance,take=:take where id=:id and balance=:oldBalance and take=:oldTake")
    int take(@Param("updateTime")Date updateTime, @Param("balance")BigDecimal balance,
                     @Param("take")BigDecimal take, @Param("id")String id,@Param("oldBalance")BigDecimal oldBalance,@Param("oldTake")BigDecimal oldTake);
}
