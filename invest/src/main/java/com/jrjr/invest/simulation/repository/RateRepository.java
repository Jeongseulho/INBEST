package com.jrjr.invest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

	List<Rate> findAllByUserSeq(Long userSeq);

	@Query("SELECT ROUND(AVG(r.rate), 0) FROM Rate r WHERE r.userSeq = :userSeq")
	Integer getAvgRateByUserSeq(@Param("userSeq") Long userSeq);

	@Query("SELECT ROUND(AVG(r.rate), 0) FROM Rate r GROUP BY r.userSeq ORDER BY r.userSeq")
	List<Integer> getAllAvgRate();
}