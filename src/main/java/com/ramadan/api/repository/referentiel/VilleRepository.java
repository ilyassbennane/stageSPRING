package com.ramadan.api.repository.referentiel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.referentiel.Ville;



@Repository
public interface  VilleRepository extends JpaRepository<Ville, Long>{

	@Override
	List<Ville> findAll();

	@Query("SELECT a FROM Ville a WHERE a.code = :value OR a.uuid = :value")
	Ville findByCodeOrUuid(@Param("value") String value);
}
