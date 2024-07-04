package com.ramadan.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.user.RequestRestPasword;

public interface RequestRestPaswordRepository extends JpaRepository<RequestRestPasword, Long> {
}