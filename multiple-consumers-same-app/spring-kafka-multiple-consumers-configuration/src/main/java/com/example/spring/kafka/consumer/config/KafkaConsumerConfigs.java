package com.example.spring.kafka.consumer.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class KafkaConsumerConfigs extends KafkaProperties.Consumer {

    public KafkaConsumerConfigs() {
        // These fields have default non-null values
        // and if not set to null will override the defaults from spring.kafka.consumer
        super.setKeyDeserializer(null);
        super.setValueDeserializer(null);
        super.setIsolationLevel(null);
        super.setAutoOffsetReset(null);
    }
}
