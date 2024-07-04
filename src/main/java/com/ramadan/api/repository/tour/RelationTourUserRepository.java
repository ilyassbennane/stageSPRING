package com.ramadan.api.repository.tour;

import com.ramadan.api.entity.tour.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.tour.RelationTourUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationTourUserRepository extends JpaRepository<RelationTourUser, Long> {
    @Query("select u.tour from RelationTourUser u where u.user.uuid=:uuid")
    Page<Tour> getUserTours(@Param("uuid") String uidUser, Pageable pageable);
}