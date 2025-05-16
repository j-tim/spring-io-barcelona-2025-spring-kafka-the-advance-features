package com.example.kafka.event.api.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class StockQuoteEvent {

    private final String symbol;
    /** The stock exchange the stock was traded. */
    private final String exchange;
    /** The value the stock was traded for. */
    private final String tradeValue;
    /** The currency the stock was traded in. */
    private final String currency;
    /** Description about the stock. */
    private final String description;
    /** Epoch millis timestamp at which the stock trade took place. */
    private final Instant tradeTime;


    @JsonCreator
    public StockQuoteEvent(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("exchange") String exchange,
      @JsonProperty("tradeValue") String tradeValue,
      @JsonProperty("currency") String currency,
      @JsonProperty("description") String description,
      @JsonProperty("tradeTime") Instant tradeTime) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.tradeValue = tradeValue;
        this.currency = currency;
        this.description = description;
        this.tradeTime = tradeTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTradeTime() {
        return tradeTime;
    }

    @Override
    public String toString() {
        return "{" +
        "symbol='" + symbol + '\'' +
        ", exchange='" + exchange + '\'' +
        ", tradeValue='" + tradeValue + '\'' +
        ", currency='" + currency + '\'' +
        ", description='" + description + '\'' +
        ", tradeTime=" + tradeTime +
        '}';
    }
}
