package com.ramadan.api.repository.costumer;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.costumer.Costumer;

public interface CostumerRepository extends JpaRepository<Costumer, Long>   ,JpaSpecificationExecutor<Costumer>{
    @Query("select a from Costumer a where a.uuid=:Uuid")
    Costumer findByUuid(@Param("Uuid") String Uuid);
    
    Page<Costumer> findBySectorUuid(String sectorUuid, Pageable pageable);
    
    

}
