package com.ramadan.api.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.stock.deposit.LocationGPSAddressDeposit;

public interface LocationGPSDepositRepository extends JpaRepository<LocationGPSAddressDeposit, Long> {

}
