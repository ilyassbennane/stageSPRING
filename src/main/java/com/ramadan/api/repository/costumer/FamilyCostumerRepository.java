package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.costumer.FamilyCostumer;

public interface FamilyCostumerRepository extends JpaRepository<FamilyCostumer, Long> ,JpaSpecificationExecutor<FamilyCostumer>{
	
	 
    @Query("select a from FamilyCostumer a where a.uuid=:Uuid")
    FamilyCostumer findByUuid(@Param("Uuid") String uuid);

    @Query("select a from FamilyCostumer a where a.company.uuid=:Uuid")
    Page<FamilyCostumer> findAllByCompany(@Param("Uuid") String uuid,Pageable pageable);

    @Query("select a from FamilyCostumer a where a.parent.uuid=:Uuid")
    Page<FamilyCostumer> findAllByParent(@Param("Uuid") String uuid,Pageable pageable);
    
    @Query("select a from FamilyCostumer a where a.parent.uuid=:Uuid")
    List<FamilyCostumer> findAllByParent(@Param("Uuid") String uuid);
    
    @Query("select a from FamilyCostumer a where uuid=:Uuid")
    FamilyCostumer findParentByUuid(@Param("Uuid") String uuid);
}