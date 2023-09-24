package com.jrjr.invest.simulation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.simulation.entity.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

	List<Tier> findAllByUserSeq(Long userSeq);
}
