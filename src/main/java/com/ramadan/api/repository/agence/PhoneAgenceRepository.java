package com.ramadan.api.repository.agence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.PhoneAgency;
import com.ramadan.api.entity.costumer.PhoneCostumer;

public interface PhoneAgenceRepository extends JpaRepository<PhoneAgency,Long>{
	List<PhoneAgency> findByAgency(Agency agency);
    @Query("select a from PhoneAgency a where a.uuid=:Uuid")
    PhoneAgency findByUuid(@Param("Uuid")String uuid);
    PhoneAgency findByAgencyUuidAndIsMainTrue(String agencyUuid);

}
