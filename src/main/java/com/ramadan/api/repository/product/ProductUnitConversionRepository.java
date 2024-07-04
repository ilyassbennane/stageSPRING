package com.ramadan.api.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ramadan.api.entity.product.ProductUnitConversion;

public interface ProductUnitConversionRepository extends JpaRepository<ProductUnitConversion, Long> {

    
       @Query("select a from ProductUnitConversion a where a.uuid=:Uuid")
      ProductUnitConversion findByUuid(@Param("Uuid") String Uuid);
      
    @Query("select a from ProductUnitConversion a where a.product.uuid=:Uuid")
    Page<ProductUnitConversion> findByProductUuid(@Param("Uuid")String productUuid, PageRequest of);
}