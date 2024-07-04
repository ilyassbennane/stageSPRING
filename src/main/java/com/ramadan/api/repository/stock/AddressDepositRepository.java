package com.ramadan.api.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.stock.deposit.AddressDeposit;

public interface AddressDepositRepository extends JpaRepository<AddressDeposit, Long>{

}
