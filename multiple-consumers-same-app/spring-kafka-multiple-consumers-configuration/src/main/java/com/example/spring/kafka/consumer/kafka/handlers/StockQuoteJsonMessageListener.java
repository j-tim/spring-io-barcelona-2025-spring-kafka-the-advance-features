package com.example.spring.kafka.consumer.kafka.handlers;

import com.example.kafka.event.api.json.StockQuoteEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class StockQuoteJsonMessageListener implements MessageListener<String, StockQuoteEvent> {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteJsonMessageListener.class);

    @Override
    public void onMessage(ConsumerRecord<String, StockQuoteEvent> data) {
        log.info("Consumed from topic: {} in JSON format: {}", data.topic(), data.value());
    }
}
