package com.jrjr.invest.simulation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.LoginHistory;
import com.jrjr.invest.simulation.entity.Rate;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

	@Query("select count (distinct userSeq) from LoginHistory where createdDate >= :localDateTime")
	Long countTodayLoginHistory(LocalDateTime localDateTime);

}