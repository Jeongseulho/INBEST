package com.jrjr.inbest.trading.handler;

import com.jrjr.inbest.trading.dto.TradingDTO;

public interface TradingHandler {
    public void setNext(TradingHandler tradingHandler);
    public boolean handle(TradingDTO tradingDTO,Long price) throws Exception;
}
