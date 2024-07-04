package com.ramadan.api.repository.agence;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;

public interface AdresseAgencyRepository extends JpaRepository<AdresseAgency, Long> {
    AdresseAgency findByUuid(String uuid);

    List<AdresseAgency> findByAgency(Agency agency);
    AdresseAgency findByAgencyUuidAndIsMainTrue(String agencyUuid);


    
}
