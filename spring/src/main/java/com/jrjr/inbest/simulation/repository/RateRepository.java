package com.jrjr.inbest.simulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.simulation.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
