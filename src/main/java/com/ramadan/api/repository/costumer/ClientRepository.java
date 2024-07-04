package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.costumer.Costumer;

public interface ClientRepository extends JpaRepository<Costumer, Long> {
    @Query("select a from Costumer a where a.uuid=:Uuid")
    Costumer findByUuid(@Param("Uuid") String Uuid);

    @Query("select a from Costumer a where a.sector.uuid=:Uuid")
    List<Costumer> findAllBySector(@Param("Uuid") String uuid);

    @Query("select a from Costumer a where a.familyCostumer.uuid=:Uuid")
    List<Costumer> findAllByFamily(@Param("Uuid") String uuid);
}
