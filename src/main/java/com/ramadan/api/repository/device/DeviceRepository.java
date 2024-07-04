package com.ramadan.api.repository.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.device.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

     @Query("select a from Device a where a.uuid=:Uuid")
     Device findByUuid(@Param("Uuid") String Uuid);
    @Query("select a from Device a where a.company.uuid=:Uuid")
    Page<Device> findAllByCompany(@Param("Uuid")String uuid, Pageable pageable);
}