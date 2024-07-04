package com.ramadan.api.repository.agence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.costumer.Costumer;

public interface AgenceRepository extends JpaRepository<Agency, Long> ,JpaSpecificationExecutor<Agency> {
    @Query("select a from Agency a where a.uuid=:Uuid")
    Agency findByUuid(@Param("Uuid") String Uuid);
    @Query("select a from Agency a where a.company.uuid=:Uuid")
    Page<Agency> findAllByCompany(@Param("Uuid")String uuid, Pageable pageable);
    
    @Query("SELECT a FROM Agency a WHERE a.name LIKE %:name%")
    Page<Agency> findByName(@Param("name") String name, Pageable pageable);
   
    
}
