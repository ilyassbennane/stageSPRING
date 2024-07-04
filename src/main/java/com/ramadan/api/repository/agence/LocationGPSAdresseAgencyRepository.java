package com.ramadan.api.repository.agence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.agence.LocationGPSAdresseAgency;

@Repository
public interface LocationGPSAdresseAgencyRepository extends JpaRepository<LocationGPSAdresseAgency, Long> {
}

