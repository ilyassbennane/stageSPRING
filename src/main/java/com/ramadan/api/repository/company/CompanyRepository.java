// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.repository.company;

import com.ramadan.api.entity.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {
   @Query("select a from Company a where a.uuid=:Uuid")
   Company findByUuid(@Param("Uuid") String Uuid);

   @Query("SELECT u FROM Company u WHERE u.name LIKE %:name%")
   Page<Company> findByName(@Param("name") String name, Pageable pageable);
   
   @Query("SELECT u FROM Company u WHERE u.name LIKE %:name%")
   Company findByName(@Param("name") String name);
   
}
