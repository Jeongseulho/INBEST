package com.jrjr.invest.trading.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.trading.entity.Trading;

@Repository
public interface TradingRepository extends CrudRepository<Trading, Long> {

	List<Trading> findBySimulationSeqAndConclusionType(Long simulationSeq, Integer conclusionType);
	List<Trading> findBySimulationSeqAndUserSeqAndConclusionTypeOrderByCreatedDateAsc(Long simulationSeq, Long userSeq,Integer conclusionType);
	Page<Trading> findBySimulationSeqAndUserSeqAndConclusionTypeOrderByCreatedDateDesc(Long simulationSeq, Long userSeq,Integer conclusionType,Pageable pageable);
}
