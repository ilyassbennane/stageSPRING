package com.ramadan.api.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.document.DocumentLine;

public interface DocumentLineRepository extends JpaRepository<DocumentLine, Long> {
}