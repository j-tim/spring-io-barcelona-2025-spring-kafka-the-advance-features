package com.example.spring.kafka.producer.json;

import com.example.spring.kafka.avro.stock.quote.StockQuote;
import com.example.spring.kafka.producer.generator.RandomStockQuoteGenerator;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledJsonStockQuoteProducer {

    private final JsonStockQuoteProducer jsonStockQuoteProducer;
    private final RandomStockQuoteGenerator generator;

    public ScheduledJsonStockQuoteProducer(JsonStockQuoteProducer jsonStockQuoteProducer, RandomStockQuoteGenerator generator) {
        this.jsonStockQuoteProducer = jsonStockQuoteProducer;
        this.generator = generator;
    }

    @Scheduled(fixedRateString = "${kafka.producer.json.rate}")
    public void produceJson() {
        StockQuote stockQuote = generator.generate();
        jsonStockQuoteProducer.produceJson(stockQuote);
    }
}
