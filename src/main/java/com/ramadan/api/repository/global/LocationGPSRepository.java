package com.ramadan.api.repository.global;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.address.LocationGPS;

public interface LocationGPSRepository extends JpaRepository<LocationGPS, Long> {
}