package com.ramadan.api.repository.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.stock.deposit.AddressDeposit;
import com.ramadan.api.entity.stock.deposit.Deposit;

public interface AddressDepositRepository extends JpaRepository<AddressDeposit, Long>{
    List<AddressDeposit> findByDeposit(Deposit depot);
    
	AddressDeposit findByUuid(String uuid);


}
