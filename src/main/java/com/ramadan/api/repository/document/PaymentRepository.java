package com.ramadan.api.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.document.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}