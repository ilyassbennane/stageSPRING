package com.ramadan.api.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.document.PaymentDeadline;

public interface PaymentDeadlineRepository extends JpaRepository<PaymentDeadline, Long> {
}