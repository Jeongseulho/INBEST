package com.jrjr.inbest.trading.handler;

import com.jrjr.inbest.trading.constant.TradingResultType;
import com.jrjr.inbest.trading.constant.TradingType;
import com.jrjr.inbest.trading.dto.RedisSimulationUserDTO;
import com.jrjr.inbest.trading.dto.StockDTO;
import com.jrjr.inbest.trading.dto.StockUserDTO;
import com.jrjr.inbest.trading.dto.TradingDTO;
import com.jrjr.inbest.trading.entity.TradingEntity;
import com.jrjr.inbest.trading.repository.TradingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class KoreaRegularTradingHandler implements TradingHandler{
    private final RedisTemplate<String, RedisSimulationUserDTO> redisSimulationUserTemplate;
    private final RedisTemplate<String, TradingDTO> redisTradingTemplate;
    private final RedisTemplate<String, StockUserDTO> redisStockUserTemplate;
    private final TradingRepository tradingRepository;
    private TradingHandler tradingHandler;
    private LocalTime startTime = LocalTime.of(8,59,59);
    private LocalTime end = LocalTime.of(15,31,0);
    @Value("${eureka.instance.instance-id}")
    public String instanceId;
    @Override
    public void setNext(TradingHandler tradingHandler) {
        this.tradingHandler = tradingHandler;
    }

    @Override
    public boolean handle(TradingDTO tradingDTO,Long price) throws Exception{
        LocalDate tradingDate = tradingDTO.getCreatedDate().toLocalDate();
        //9시~ 15시 30분
        if(tradingDTO.getCreatedDate().isAfter(LocalDateTime.of(tradingDate,startTime))
                && tradingDTO.getCreatedDate().isBefore(LocalDateTime.of(tradingDate,end))){
            log.info("정규 시간 거래");

            if(tradingDTO.getTradingType().equals(TradingType.BUY)){
                log.info("매도 : "+tradingDTO.getPrice() +" vs "+price);
                //매도 성공
                if(tradingDTO.getPrice() <= price){
                    //mariaDB에 거래 체결
                    TradingEntity tradingEntity = tradingRepository.findBySeq(tradingDTO.getSeq()).orElse(null);
                    tradingEntity.setConclusionType(TradingResultType.SUCCESS);
                    tradingRepository.save(tradingEntity);

                    //레디스의 거래 목록에서 제거
                    HashOperations<String, String, TradingDTO> tradingHashOperations = redisTradingTemplate.opsForHash();
                    String  tradingHashKey = instanceId+"-trading-task";
                    tradingHashOperations.delete(tradingHashKey,tradingDTO.getSeq());

                    //거래로 인한 유저의 자산 보유량 변경
                    HashOperations<String, String, RedisSimulationUserDTO> simulationUserHashOperations = redisSimulationUserTemplate.opsForHash();
                    String simulationHashKey = "simulation_"+tradingDTO.getGameSeq();
                    RedisSimulationUserDTO redisSimulationUserDTO = simulationUserHashOperations.get(simulationHashKey,String.valueOf(tradingDTO.getUserSeq()));
                    redisSimulationUserDTO.setCurrentMoney(redisSimulationUserDTO.getCurrentMoney() + tradingDTO.getPrice());
                    simulationUserHashOperations.put(simulationHashKey,String.valueOf(redisSimulationUserDTO.getUserSeq()),redisSimulationUserDTO);

                    //거래로 인한 주식 개수 변경
                    String simulationUserHashKey = "simulation_"+tradingDTO.getGameSeq()+"_"+tradingDTO.getUserSeq();
                    String simulationUserKey = tradingDTO.getStockType()+"_"+tradingDTO.getStockCode();
                    HashOperations<String, String, StockUserDTO> stockUserHashOperations = redisStockUserTemplate.opsForHash();
                    StockUserDTO stockUserDTO = stockUserHashOperations.get(simulationUserHashKey,simulationUserKey);
                    stockUserDTO.setMarketPrice(price);
                    stockUserDTO.setAmount(stockUserDTO.getAmount()-tradingDTO.getAmount());

                    //모두 매도하면 삭제
                    if(stockUserDTO.getAmount() == 0){
                        stockUserHashOperations.delete(simulationUserHashKey,simulationUserKey);
                    }else if(stockUserDTO.getAmount()>0){
                        stockUserHashOperations.put(simulationUserHashKey,simulationUserKey,stockUserDTO);
                    }else{
                        throw new Exception("보유양보다 매도 양이 많습니다.");
                    }
                }
            }//매수
            else if(tradingDTO.getTradingType().equals(TradingType.SELL)){
                log.info("매수 : "+tradingDTO.getPrice() +" vs "+price);
                //매수 성공
                if(tradingDTO.getPrice() >= price){
                    //매매 대기열에서 매매 정보 삭제
                    //db 매매에 저장
                    //수익률 저장
                }
            }

        }
        if(this.tradingHandler != null){
            this.tradingHandler.handle(tradingDTO,price);
        }

        return false;
    }
}
