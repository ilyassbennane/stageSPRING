package com.ramadan.api.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.document.Deadline;

public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}