package com.example.spring.kafka.producer.avro;

import com.example.spring.kafka.avro.stock.quote.StockQuote;
import com.example.spring.kafka.producer.generator.RandomStockQuoteGenerator;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledAvroStockQuoteProducer {

    private final AvroStockQuoteProducer avroStockQuoteProducer;
    private final RandomStockQuoteGenerator generator;

    public ScheduledAvroStockQuoteProducer(AvroStockQuoteProducer avroStockQuoteProducer, RandomStockQuoteGenerator generator) {
        this.avroStockQuoteProducer = avroStockQuoteProducer;
        this.generator = generator;
    }

    @Scheduled(fixedRateString = "${kafka.producer.avro.rate}")
    public void produce() {
        StockQuote stockQuote = generator.generate();
        avroStockQuoteProducer.produce(stockQuote);
    }
}
