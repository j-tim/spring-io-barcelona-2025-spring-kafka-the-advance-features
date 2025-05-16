package com.example.spring.kafka.consumer.kafka.handlers;

import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class StockQuoteAvroMessageListener implements MessageListener<String, StockQuote> {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteAvroMessageListener.class);

    @Override
    public void onMessage(ConsumerRecord<String, StockQuote> data) {
        log.info("Consumed from topic: {} in Avro format: {}", data.topic(), data.value());
    }
}
