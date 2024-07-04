package com.ramadan.api.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.stock.deposit.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
