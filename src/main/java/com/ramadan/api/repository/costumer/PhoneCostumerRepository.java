package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.PhoneCostumer;

public interface PhoneCostumerRepository extends JpaRepository<PhoneCostumer, Long> {
    @Query("select a from PhoneCostumer a where a.numbre=:Uuid")
    PhoneCostumer findByPhoneNumber(@Param("Uuid")String phoneNumber);

    @Query("select a from PhoneCostumer a where a.uuid=:Uuid")
    PhoneCostumer findByUuid(@Param("Uuid")String uuid);

    @Query("select a from PhoneCostumer a where a.costumer.uuid=:Uuid")
    List<PhoneCostumer> findAllByCostumer(@Param("Uuid")String uuid );
    
	List<PhoneCostumer> findByCostumer(Costumer customer);


    
}
