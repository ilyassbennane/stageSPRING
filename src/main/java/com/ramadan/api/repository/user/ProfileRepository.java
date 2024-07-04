package com.ramadan.api.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.user.profile.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

   @Query("SELECT a FROM Profile a WHERE a.uuid = :value ")
    Profile findByUuid(@Param("value")String uuid);
   
   @Query("select p from Profile p where p.company.uuid=:Uuid")
   Page<Profile> findAllByCompany(@Param("Uuid")String uuid, Pageable pageable);
   
}