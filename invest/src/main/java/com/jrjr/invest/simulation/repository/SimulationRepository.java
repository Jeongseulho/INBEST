package com.jrjr.invest.simulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.Tier;

import java.util.List;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

}
