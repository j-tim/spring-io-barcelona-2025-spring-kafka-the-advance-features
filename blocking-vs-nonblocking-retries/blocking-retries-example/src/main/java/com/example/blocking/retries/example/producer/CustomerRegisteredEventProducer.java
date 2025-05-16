package com.example.blocking.retries.example.producer;

import com.example.spring.kafka.non.blocking.retries.CustomerRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegisteredEventProducer {

    private static final Logger log = LoggerFactory.getLogger(CustomerRegisteredEventProducer.class);

    private final KafkaTemplate<String, CustomerRegisteredEvent> kafkaTemplate;

    public CustomerRegisteredEventProducer(KafkaTemplate<String, CustomerRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(CustomerRegisteredEvent event) {
        kafkaTemplate.send("events", event.getCustomerId(), event);
        log.info("Produced event: {}", event);
    }
}
