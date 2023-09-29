package com.jrjr.inbest.trading.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.trading.entity.RedisTradingEntity;

@Repository
public interface RedisTradingRepository extends CrudRepository<RedisTradingEntity,String> {
}
