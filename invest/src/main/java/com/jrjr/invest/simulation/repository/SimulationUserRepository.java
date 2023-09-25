package com.jrjr.invest.simulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.SimulationUser;

@Repository
public interface SimulationUserRepository extends JpaRepository<SimulationUser, Long> {

}
