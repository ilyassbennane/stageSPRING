package com.ramadan.api.repository.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.company.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

   @Query("select a from Supplier a where a.uuid=:Uuid")
    Supplier findByUuid(@Param("Uuid")String uuid);

    @Query("select a from Supplier a where a.company=:company")
    Page<Supplier> findAllByCompany(@Param("company")Company company, Pageable pageable);
}