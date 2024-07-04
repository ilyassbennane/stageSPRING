package com.ramadan.api.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.history.HistoryDocument;

public interface HistoryDocumentRepository extends JpaRepository<HistoryDocument, Long> {
}