package com.ramadan.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.user.HistoriqueLogin;

public interface Historique_LoginRepository extends JpaRepository<HistoriqueLogin, Long> {
}
