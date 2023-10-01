package com.jrjr.inbest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jrjr.inbest.simulation.entity.Simulation;
import com.jrjr.inbest.user.dto.SimulationRecordDTO;

public interface SimulationRepository extends JpaRepository<Simulation, Long> {

	@Query(
		"SELECT NEW com.jrjr.inbest.user.dto.SimulationRecordDTO(s.seq, s.title, s.startDate, s.finishedDate, s.period, s.memberNum, su.currentRank, r.rate) "
			+ "FROM Simulation s "
			+ "JOIN SimulationUser su ON s.seq = su.simulation.seq "
			+ "LEFT JOIN Rate r ON su.user.seq = r.userSeq AND s.seq = r.simulationSeq "
			+ "WHERE su.user.seq = :userSeq AND s.finishedDate IS NOT NULL")
	List<SimulationRecordDTO> getSimulationByUserSeq(@Param("userSeq") Long userSeq);
}
