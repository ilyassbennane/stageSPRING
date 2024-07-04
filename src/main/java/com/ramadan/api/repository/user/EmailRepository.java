package com.ramadan.api.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramadan.api.entity.user.EmailUser;
import com.ramadan.api.entity.user.User;

public interface EmailRepository extends JpaRepository<EmailUser, Long> {

	@Query("select a from EmailUser a where a.uuid=:Uuid")
	EmailUser findByUuid(@Param("Uuid") String uuid);

	@Query("select a from EmailUser a where a.user.uuid=:Uuid")
	List<EmailUser> findAllByUser(@Param("Uuid") String uuid);

	@Query("select a from EmailUser a where a.email=:Uuid")
	EmailUser findByEmail(@Param("Uuid") String uuid);

	@Query("select a from EmailUser a where a.user=:user and a.isMain=true")
	EmailUser findByUserAndIsMain(@Param("user") User user);


}