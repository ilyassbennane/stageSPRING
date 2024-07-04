package com.ramadan.api.repository.global;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.address.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUuid(String uuid);

    @Query("select a from Address a where a.company.uuid=:Uuid")
    List<Address> findAllByCompany(@Param("Uuid")String uuid);

    @Query("select a from Address a where a.costumer.uuid=:Uuid")
    List<Address> findAllByCostumer(@Param("Uuid")String uuid);

    @Query("select a from Address a where a.agency.uuid=:Uuid")
    List<Address> findAllByAgency(@Param("Uuid")String uuid);

    @Query("select a from Address a where a.deposit.uuid=:Uuid")
    List<Address> findAllByDeposit(@Param("Uuid")String uuid);

}