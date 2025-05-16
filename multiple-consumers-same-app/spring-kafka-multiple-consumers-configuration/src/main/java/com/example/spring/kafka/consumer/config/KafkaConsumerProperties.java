package com.example.spring.kafka.consumer.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.Objects;

public class KafkaConsumerProperties {

    /**
     * Topic names to consume from
     */
    private final String[] topicNames;

    /**
     * Enables consumer toggling with no code change
     */
    private final boolean enabled;

    /**
     * The class containing the business logic to handle specific events
     */
    private final String messageListenerClass;

    /**
     * Consumer property overrides
     */
    private final KafkaConsumerConfigs configs;

    public KafkaConsumerProperties(String[] topicNames, boolean enabled, String messageListenerClass, KafkaConsumerConfigs configs) {
        this.topicNames = topicNames;
        this.enabled = enabled;
        this.messageListenerClass = messageListenerClass;
        //in case the user does not specify config overrides, use empty object
        this.configs = Objects.requireNonNullElseGet(configs, KafkaConsumerConfigs::new);
    }

    public String[] getTopicNames() {
        return topicNames;
    }

    public KafkaProperties.Consumer getConfigs() {
        return configs;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getMessageListenerClass() {
        return messageListenerClass;
    }
}
