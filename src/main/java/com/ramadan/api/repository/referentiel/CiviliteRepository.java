package com.ramadan.api.repository.referentiel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.referentiel.Civilite;

@Repository
public interface  CiviliteRepository extends JpaRepository<Civilite, Long>{

	@Override
	List<Civilite> findAll();

	@Query("SELECT a FROM Civilite a WHERE a.code = :value OR a.uuid = :value")
	Civilite findByCodeOrUuid(@Param("value") String value);
}
