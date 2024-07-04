package com.ramadan.api.repository.tour;

import com.ramadan.api.entity.tour.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.tour.RelationTourAgency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationTourAgencyRepository extends JpaRepository<RelationTourAgency, Long> {
     @Query("SELECT a.tour from RelationTourAgency a where a.agency.uuid=:uuid")
     Page<Tour>getAgenceTours(@Param("uuid") String uUid,Pageable pageable);


}