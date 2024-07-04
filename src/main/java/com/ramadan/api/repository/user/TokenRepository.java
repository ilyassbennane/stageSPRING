package com.ramadan.api.repository.user;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramadan.api.entity.user.DemandeResetPassword;
import com.ramadan.api.entity.user.Token;
import com.ramadan.api.entity.user.User;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("SELECT p FROM Token p WHERE p.uuid = :uuid")
    Token findByUuid(@Param("uuid") String uuid);
    
    @Query("SELECT p FROM Token p WHERE p.demandeResetPassword = :demandeResetPassword and p.isValid = true")
    Token findByResetPasswordDemandeAndValid(@Param("demandeResetPassword") DemandeResetPassword demandeResetPassword);
    
    @Query("SELECT p FROM Token p WHERE p.demandeResetPassword = :demandeResetPassword and p.expiryTime > :expiryTime")
    Token findByResetPasswordDemandeAndExpiryTimeAfter(@Param("demandeResetPassword") DemandeResetPassword demandeResetPassword , @Param("expiryTime") LocalDateTime expiryTime);

    @Query("SELECT p FROM Token p WHERE p.user = :user and p.isValid = true")
    Token findByUserAndValid(@Param("user") User user);
    
    @Query("SELECT p FROM Token p WHERE p.user = :user and p.expiryTime > :expiryTime")
    Token findByUserAndExpiryTimeAfter(@Param("user") User user, @Param("expiryTime") LocalDateTime expiryTime);
    
    @Query("SELECT p FROM Token p WHERE p.token = :token and p.expiryTime > :expiryTime")
    Token findByTokenAndExpiryTimeAfter(@Param("token") String token , @Param("expiryTime") LocalDateTime expiryTime);


}
