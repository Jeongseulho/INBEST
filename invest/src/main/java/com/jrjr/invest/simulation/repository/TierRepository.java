package com.jrjr.invest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

	List<Tier> findAllByUserSeq(Long userSeq);

	Tier findByUserSeqAndSimulationSeq(Long userSeq, Long simulationSeq);

	@Query("SELECT SUM(t.tier) FROM Tier t WHERE t.userSeq = :userSeq")
	Integer getTotalTierByUserSeq(@Param("userSeq") Long userSeq);

	@Query("SELECT SUM(t.tier) FROM Tier t GROUP BY t.userSeq ORDER BY t.userSeq")
	List<Integer> getAllTotalTier();
}