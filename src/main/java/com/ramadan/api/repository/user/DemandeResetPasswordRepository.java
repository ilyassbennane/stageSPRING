package com.ramadan.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.user.DemandeResetPassword;
import com.ramadan.api.entity.user.User;

@Repository
public interface DemandeResetPasswordRepository extends JpaRepository<DemandeResetPassword, Long> {
	
    @Query("SELECT u FROM DemandeResetPassword u WHERE u.user= :user")
    DemandeResetPassword findByUser(@Param("user") User user);

    @Query(value = "SELECT u FROM DemandeResetPassword u WHERE u.uuid= :uuid")
    DemandeResetPassword findByUuid(@Param("uuid") String uuid);
}
