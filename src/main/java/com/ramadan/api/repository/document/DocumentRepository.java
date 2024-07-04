package com.ramadan.api.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}