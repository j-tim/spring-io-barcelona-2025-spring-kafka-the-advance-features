package com.example.spring.kafka.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumersProperties {

    private final List<KafkaConsumerProperties> customKafkaConsumers;

    public KafkaConsumersProperties(List<KafkaConsumerProperties> customKafkaConsumers) {
        this.customKafkaConsumers = customKafkaConsumers;
    }

    public List<KafkaConsumerProperties> getCustomKafkaConsumers() {
        return customKafkaConsumers;
    }
}
