package com.jrjr.invest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.User;

@Repository
public interface SimulationUserRepository extends JpaRepository<SimulationUser, Long> {
	List<SimulationUser> findBySimulationSeq(Long simulationSeq);
	SimulationUser findBySimulationAndUser(Simulation simulation, User user);
}
