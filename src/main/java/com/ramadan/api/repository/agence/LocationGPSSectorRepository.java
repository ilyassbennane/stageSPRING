package com.ramadan.api.repository.agence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.agence.LocationGPSSector;
import com.ramadan.api.entity.agence.Sector;


@Repository
public interface LocationGPSSectorRepository extends JpaRepository<LocationGPSSector, Long> {

	List<LocationGPSSector> findBySector(Sector sector);
	
}
