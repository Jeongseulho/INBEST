package com.jrjr.inbest.simulation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.simulation.entity.Tier;
import com.jrjr.inbest.user.dto.TierByDateDTO;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

	@Query("SELECT SUM(t.tier) FROM Tier t WHERE t.userSeq = :userSeq")
	Optional<Integer> getTotalTier(@Param("userSeq") Long userSeq);

	@Query(
		"SELECT NEW com.jrjr.inbest.user.dto.TierByDateDTO(t.createdDate, SUM(t.tier)) "
			+ "FROM Tier t "
			+ "WHERE t.userSeq = :userSeq "
			+ "GROUP BY DATE_FORMAT(t.createdDate, '%Y-%m-%d')")
	List<TierByDateDTO> getTierByDate(@Param("userSeq") Long userSeq);
}
