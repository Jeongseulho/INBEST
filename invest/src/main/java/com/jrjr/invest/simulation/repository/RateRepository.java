package com.jrjr.invest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Long, Rate> {

	List<Rate> findAllByUserSeq(Long userSeq);
}
