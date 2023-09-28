package com.jrjr.inbest.trading.handler;

import com.jrjr.inbest.trading.dto.RedisStockDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KoreaTradingHandler implements TradingHandler{
    private TradingHandler tradingHandler;
    private final RedisTemplate<String, RedisStockDTO> redisStockTemplate;
    @Override
    public void setNext(TradingHandler tradingHandler) {
        this.tradingHandler = tradingHandler;
    }

    @Override
    public boolean handle(TradingDTO tradingDTO,Long price) throws Exception{
        log.info("===== 한국 주식 시가 확인 시작 =====");
        if(tradingDTO == null){
            log.info("매매 정보가 없음");
            return false;
        }

        if(tradingDTO.getCreatedDate() == null){
            log.info("===== 매매 시작 정보가 없음 =====");
            return false;
        }

        if(this.tradingHandler != null){
            this.tradingHandler.handle(tradingDTO, price);
        }

        return false;
    }
}