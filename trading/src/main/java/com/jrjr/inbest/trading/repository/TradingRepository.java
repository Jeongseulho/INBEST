package com.jrjr.inbest.trading.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.trading.entity.RedisTradingEntity;
import com.jrjr.inbest.trading.entity.TradingEntity;

@Repository
public interface TradingRepository extends CrudRepository<TradingEntity,String> {
}
