package com.ramadan.api.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ramadan.api.entity.product.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>,JpaSpecificationExecutor<Brand> {


      @Query("select a from Brand a where a.uuid=:Uuid")
      Brand findByUuid(@Param("Uuid") String Uuid);
    @Query("select a from Brand a where a.company.uuid=:Uuid")
    Page<Brand> findAllByCompany(@Param("Uuid")String company, Pageable pageable);
   
}