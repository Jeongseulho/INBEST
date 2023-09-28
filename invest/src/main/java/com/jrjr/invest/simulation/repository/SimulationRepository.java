package com.jrjr.invest.simulation.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Simulation;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
	Simulation findBySeq(Long seq);
	@Query("select s from Simulation s where s.startDate is null and s.finishedDate is null and s.seq not in (select su.simulation.seq from SimulationUser su where su.user.seq = :userSeq) ")
	List<Simulation> findJoinableGroup(Long userSeq);
	List<Simulation> findByFinishedDateIsNotNullOrderByRevenuRateDesc();
	@Query("select avg (s.revenuRate) from Simulation s where s.revenuRate is not null and s.finishedDate is not null and s.finishedDate <= :date")
	Optional<Double> getAverageRevenuRate(LocalDateTime date);
}
