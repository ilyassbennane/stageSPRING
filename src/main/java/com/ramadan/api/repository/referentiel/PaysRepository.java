package com.ramadan.api.repository.referentiel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.referentiel.Pays;



@Repository
public interface  PaysRepository extends JpaRepository<Pays, Long>{

	@Override
	List<Pays> findAll();

	@Query("SELECT a FROM Pays a WHERE a.code = :value OR a.uuid = :value")
	Pays findByCodeOrUuid(@Param("value") String value);
}
