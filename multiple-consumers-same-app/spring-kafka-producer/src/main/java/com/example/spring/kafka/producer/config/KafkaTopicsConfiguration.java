package com.example.spring.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfiguration {

    public static final String STOCK_QUOTES_TOPIC_NAME_AVRO = "stock-quotes-avro";
    public static final String STOCK_QUOTES_TOPIC_NAME_AVRO_2ND = "stock-quotes-avro-second";
    public static final String STOCK_QUOTES_TOPIC_NAME_JSON = "stock-quotes-json";

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.enabled", havingValue = "true")
    public NewTopic stockQuotesTopic() {
        return TopicBuilder.name(STOCK_QUOTES_TOPIC_NAME_AVRO)
        .partitions(9)
        .build();
    }


    @Bean
    @ConditionalOnProperty(name = "kafka.producer.enabled", havingValue = "true")
    public NewTopic stockQuotesTopic2() {
        return TopicBuilder.name(STOCK_QUOTES_TOPIC_NAME_AVRO_2ND)
        .partitions(9)
        .build();
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.enabled", havingValue = "true")
    public NewTopic stockQuotesTopicJSON() {
        return TopicBuilder.name(STOCK_QUOTES_TOPIC_NAME_JSON)
        .partitions(9)
        .build();
    }
}
