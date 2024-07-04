package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.EmailCostumer;
import com.ramadan.api.entity.costumer.PhoneCostumer;

@Repository
public interface EmailCostumerRepository extends JpaRepository<EmailCostumer, Long>{
     @Query("select c from EmailCostumer c where c.email=:email")
    EmailCostumer findByEmail(@Param("email") String email);

    @Query("select a from EmailCostumer a where a.uuid=:Uuid")
    EmailCostumer findByUuid(@Param("Uuid")String uuid);

    @Query("select a from EmailCostumer a where a.costumer.uuid=:Uuid")
    List<EmailCostumer> findAllByCostumer(@Param("Uuid")String uuid );
    
	List<EmailCostumer> findByCostumer(Costumer customer);

} 