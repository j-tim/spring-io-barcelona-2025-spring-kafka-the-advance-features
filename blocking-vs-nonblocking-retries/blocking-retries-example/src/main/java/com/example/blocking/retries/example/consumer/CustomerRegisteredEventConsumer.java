package com.example.blocking.retries.example.consumer;

import com.example.spring.kafka.non.blocking.retries.CustomerRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomerRegisteredEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(CustomerRegisteredEventConsumer.class);
    private final Random random = new Random();

    @KafkaListener(topics = "events")
    public void on(CustomerRegisteredEvent event,
                   @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, KafkaMessageHeaderAccessor accessor) {
        log.info("Attempt: {} to consume from topic: {}, partition: {} value: {}", accessor.getBlockingRetryDeliveryAttempt(), topic, partition, event);

        // Non-recoverable exception
        if ("NON_RECOVERABLE_EXCEPTION".equalsIgnoreCase(event.getAction())) {
            throw new NullPointerException("Oh no.........");
        }

        // Exception you might recover from
        if ("RANDOM_FAILURE".equalsIgnoreCase(event.getAction())) {
            boolean throwException = random.nextBoolean();

            if (throwException) {
                log.info("You are not lucky today. Exception for you!");
                throw new RuntimeException("Whoops.....");
            } else {
                log.info("You are lucky, no exception for you!");
            }
        }

        log.info("Event successfully handled");
    }
}
