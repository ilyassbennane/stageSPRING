package com.ramadan.api.repository.agence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.costumer.Costumer;

public interface SecteurRepository extends JpaRepository<Sector, Long>  ,JpaSpecificationExecutor<Sector>{
    @Query("select a from Sector a where a.uuid=:Uuid")
    Sector findByUuid(@Param("Uuid") String Uuid);
    @Query("select a from Sector a where a.agency.uuid=:Uuid")
    Page<Sector> findAllByAgency(@Param("Uuid") String uuid,Pageable pageable);
    
    boolean existsByUuid(String uuid);
    
    @Query("SELECT COUNT(s) FROM Sector s WHERE s.agency.id = :agencyId")
    int countByAgencyId(@Param("agencyId") Long agencyId);

}
