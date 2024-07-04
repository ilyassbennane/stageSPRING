package com.ramadan.api.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.stock.deposit.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query("select a from Deposit a where a.uuid=:Uuid")
    Deposit findByUuid(@Param("Uuid") String Uuid);

}
