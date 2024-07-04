package com.ramadan.api.repository.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.product.Product;

public interface ProduitRepository extends JpaRepository<Product, Long> {
    @Query("select a from Product a where a.uuid=:Uuid")
    Product findByUuid(@Param("Uuid") String Uuid);
    @Query("select a from Product a where a.productCategory.uuid=:Uuid")
    Page<Product> findAllByCategorie(@Param("Uuid") String uuid ,Pageable pageable);
}
