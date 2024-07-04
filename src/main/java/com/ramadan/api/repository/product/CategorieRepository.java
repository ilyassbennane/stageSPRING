package com.ramadan.api.repository.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.product.ProductCategory;

public interface CategorieRepository extends JpaRepository<ProductCategory, Long> {
    @Query("select a from ProductCategory a where a.company.uuid=:Uuid")
    Page<ProductCategory> findAllByCompany(@Param("Uuid") String uuid,Pageable pageable);

    @Query("select a from ProductCategory a where a.uuid=:Uuid")
    ProductCategory findByUuid(@Param("Uuid") String Uuid);

    @Query("select a from ProductCategory a where a.parent.uuid=:Uuid")
    Page<ProductCategory> findAllByCategoryParente(@Param("Uuid") String uuid,Pageable pageable);

}
