package com.ramadan.api.repository.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.device.RelationDeviceUtilisateur;

public interface RelationDeviceUtilisateurRepository extends JpaRepository<RelationDeviceUtilisateur, Long> {

  @Query("select a from RelationDeviceUtilisateur a where a.uuid=:Uuid")
  RelationDeviceUtilisateur findByUuid(@Param("Uuid") String Uuid);

    @Query("select a from RelationDeviceUtilisateur a where a.device.uuid=:Uuid")
    Page<RelationDeviceUtilisateur> findByDevice(@Param("Uuid")String deviceUuid, Pageable pageable);

    @Query("select a from RelationDeviceUtilisateur a where a.user.uuid=:Uuid")
    Page<RelationDeviceUtilisateur> findAllByUser(@Param("Uuid")String userUuid, Pageable pageable);
}