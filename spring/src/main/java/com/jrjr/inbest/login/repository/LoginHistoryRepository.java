package com.jrjr.inbest.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.login.entity.Login;
import com.jrjr.inbest.user.entity.LoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
