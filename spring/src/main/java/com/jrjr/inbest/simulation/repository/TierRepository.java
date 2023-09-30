package com.jrjr.inbest.simulation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.simulation.entity.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

	@Query("SELECT SUM(t.tier) FROM Tier t WHERE t.userSeq = :userSeq")
	Optional<Integer> getTotalTier(@Param("userSeq") Long userSeq);
}
