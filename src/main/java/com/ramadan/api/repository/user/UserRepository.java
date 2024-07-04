package com.ramadan.api.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.company.Company;
import com.ramadan.api.entity.user.User;
import com.ramadan.api.entity.user.profile.Profile;
@Repository
public interface  UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT a FROM User a WHERE a.username = :value ")
	User findByUsername(@Param("value")String username);

	@Query("SELECT a FROM User a WHERE a.idKeycloak = :value ")
	User findByIdKeycloak(@Param("value") String value);

	@Query("SELECT a FROM User a WHERE a.company = :company ")
	Page<User> findAllByCompany(@Param("company") Company company,Pageable pageable);

	@Query("SELECT a FROM User a WHERE a.profile = :profile ")
	Page<User> findAllByProfile(@Param("profile") Profile profile,Pageable pageable);

	@Query("SELECT a FROM User a WHERE a.uuid = :value ")
	User findByUuid(@Param("value") String value);


}
