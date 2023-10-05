package com.jrjr.invest.simulation.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.user.entity.LoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

	@Query("select count (distinct userSeq) from LoginHistory where createdDate >= :localDateTime")
	Long countTodayLoginHistory(LocalDateTime localDateTime);

}