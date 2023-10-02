package com.jrjr.inbest.trading.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;

import java.util.Optional;

@Repository
public interface TradingRepository extends CrudRepository<TradingEntity,String> {
    public Optional<TradingEntity> findBySeq(Long seq);

    @Query(value = "select s.title from simulation s, trading t where t.simulation_seq  = s.seq and t.seq = ?1",nativeQuery = true)
    public String getSimulationTitle(Long tradingSeq);
}
