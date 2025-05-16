package com.example.spring.kafka.producer.generator;

import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class RandomStockQuoteGenerator extends AbstractRandomStockQuoteGenerator {

    public StockQuote generate() {
        Instrument randomInstrument = pickRandomInstrument();
        BigDecimal randomPrice = generateRandomPrice();
        return new StockQuote(randomInstrument.symbol(), randomInstrument.exchange(), randomPrice.toPlainString(),
        randomInstrument.currency(), randomInstrument.symbol() + " stock", Instant.now());
    }
}
