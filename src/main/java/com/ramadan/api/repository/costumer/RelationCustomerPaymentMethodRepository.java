package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.costumer.Costumer;

import com.ramadan.api.entity.costumer.RelationCustomerPaymentMethod;

public interface RelationCustomerPaymentMethodRepository extends JpaRepository<RelationCustomerPaymentMethod, Long> {
	List<RelationCustomerPaymentMethod> findByCostumer(Costumer customer);

}