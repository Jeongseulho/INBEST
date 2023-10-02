package com.jrjr.invest.simulation.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.dto.SearchByTitleDTO;
import com.jrjr.invest.simulation.entity.Simulation;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
	Simulation findBySeq(Long seq);

	@Query("select s from Simulation s where s.startDate is null and s.finishedDate is null and s.seq not in (select su.simulation.seq from SimulationUser su where su.user.seq = :userSeq) ")
	List<Simulation> findJoinableGroup(Long userSeq);

	List<Simulation> findByFinishedDateIsNotNullOrderByRevenuRateDesc();

	Boolean existsBySeqAndFinishedDateIsNotNull(Long simulationSeq);

	@Query("select avg (s.revenuRate) from Simulation s where s.revenuRate is not null and s.finishedDate is not null and s.finishedDate <= :date")
	Optional<Double> getAverageRevenuRate(LocalDateTime date);

	@Query(value = "select * from simulation s where now() > date_add(date_format(s.start_date , '%Y-%m-%d') ,interval s.period day)", nativeQuery = true)
	List<Simulation> getFinishSimulation(LocalDateTime now);

	List<Simulation> findByStartDateIsNotNullAndFinishedDateIsNull();

	@Query("SELECT NEW com.jrjr.invest.simulation.dto.SearchByTitleDTO(s.seq, s.title, s.period, s.memberNum) "
		+ "FROM Simulation s "
		+ "WHERE s.title LIKE %:keyword%")
	List<SearchByTitleDTO> getSimulationSearchListByKeyword(@Param("keyword") String keyword);
}
