package com.ramadan.api.repository.tour;

import com.ramadan.api.entity.tour.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.tour.RelationTourCostumer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationTourCostumerRepository extends JpaRepository<RelationTourCostumer, Long> {

    @Query(" SELECT c.tour FROM RelationTourCostumer c WHERE c.costumer.uuid=:uuid")
    Page<Tour> getTourCostumer(@Param("uuid") String UuidClient, Pageable pageable);

}