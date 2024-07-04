package com.ramadan.api.repository.tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.tour.Tour;

public interface MissionRepository extends JpaRepository<Tour, Long> {
    @Query("select a from Tour a where a.uuid=:Uuid") 
    Tour findByUuid(@Param("Uuid") String Uuid);

    @Query("select a from Tour a where a.company.uuid=:Uuid")
    Page<Tour> findAllByCompany(@Param("Uuid") String uuid,Pageable pageable);
}
