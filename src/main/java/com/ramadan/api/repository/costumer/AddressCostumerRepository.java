package com.ramadan.api.repository.costumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.costumer.AddressCostumer;
import com.ramadan.api.entity.costumer.Costumer;

public interface AddressCostumerRepository extends JpaRepository<AddressCostumer, Long> {
	AddressCostumer findByUuid(String uuid);

	List<AddressCostumer> findByCostumer(Costumer customer);
}
