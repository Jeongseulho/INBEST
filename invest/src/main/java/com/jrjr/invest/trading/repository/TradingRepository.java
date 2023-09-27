package com.jrjr.invest.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.trading.entity.Trading;

@Repository
public interface TradingRepository extends JpaRepository<Trading, Long> {

	List<Trading> findBySimulationSeqAndConclusionType(Long simulationSeq, Integer conclusionType);
}
