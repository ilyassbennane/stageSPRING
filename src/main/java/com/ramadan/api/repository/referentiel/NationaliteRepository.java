package com.ramadan.api.repository.referentiel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.referentiel.Nationalite;



@Repository
public interface  NationaliteRepository extends JpaRepository<Nationalite, Long>{

	@Override
	Page<Nationalite> findAll( Pageable pageable);

	@Query("SELECT a FROM Nationalite a WHERE a.code = :value OR a.uuid = :value")
	Nationalite findByCodeOrUuid(@Param("value") String value);
}
