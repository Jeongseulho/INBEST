package com.jrjr.invest.simulation.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.user.entity.User;

@Repository
public interface SimulationUserRepository extends JpaRepository<SimulationUser, Long> {
	List<SimulationUser> findBySimulationSeq(Long simulationSeq);

	SimulationUser findBySimulationAndUser(Simulation simulation, User user);

	@Query("select count( distinct su.user.seq) from SimulationUser su where su.simulation.startDate is not null")
	Optional<Long> countAllStartUser();

	@Query("select count( distinct su.user.seq) from SimulationUser su where su.simulation.startDate >= :today")
	Optional<Long> countAllTodayStartUser(LocalDateTime today);

	@Query("select count( distinct su.seq) from Simulation su where su.startDate is not null and su.finishedDate is null")
	Optional<Long> countAllStartSimulation();

	@Query("select count( distinct su.seq) from Simulation su where su.startDate >= :today and su.finishedDate is null")
	Optional<Long> countAllTodayStartSimulation(LocalDateTime today);

	@Query("select count( distinct su.seq) from Simulation su where su.finishedDate is not null")
	Optional<Long> countAllFinishedSimulation();

	@Query("select count( distinct su.seq) from Simulation su where su.finishedDate >= :yesterday and su.finishedDate < :today")
	Optional<Long> countAllTodayFinishedSimulation(LocalDateTime yesterday, LocalDateTime today);

	@Query("select count( su.simulation.seq) from SimulationUser su where su.simulation.seq = :simulationSeq")
	Long countBySimulation(Long simulationSeq);

	List<SimulationUser> findAllBySimulationOrderByCurrentMoneyDesc(Simulation simulation);
}
