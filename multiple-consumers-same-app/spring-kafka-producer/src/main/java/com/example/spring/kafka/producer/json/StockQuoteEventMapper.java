package com.example.spring.kafka.producer.json;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;

public class StockQuoteEventMapper {

    private StockQuoteEventMapper() {
    }

    public static StockQuoteEvent from(StockQuote stockQuote) {
        return new StockQuoteEvent(stockQuote.getSymbol(), stockQuote.getExchange(), stockQuote.getTradeValue(), stockQuote.getCurrency(), stockQuote.getDescription(), stockQuote.getTradeTime());
    }
}
