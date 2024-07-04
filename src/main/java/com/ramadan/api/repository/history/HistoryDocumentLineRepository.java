package com.ramadan.api.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.history.HistoryDocumentLine;

public interface HistoryDocumentLineRepository extends JpaRepository<HistoryDocumentLine, Long> {
}