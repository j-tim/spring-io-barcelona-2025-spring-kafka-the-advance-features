package com.example.spring.kafka.consumer;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {

    private final KafkaProperties kafkaProperties;

    public KafkaConsumerConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    /**
     * We override the Spring Kafka bean from the autoconfiguration {@link org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration#kafkaConsumerFactory(KafkaConnectionDetails, ObjectProvider, ObjectProvider)}
     */
    @Bean
    public DefaultKafkaConsumerFactory<String, StockQuote> avroKafkaConsumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers, ObjectProvider<SslBundles> sslBundles) {
        Map<String, Object> properties = this.kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable());
        DefaultKafkaConsumerFactory<String, StockQuote> factory = new DefaultKafkaConsumerFactory<>(properties);
        // This part is important. Without the next line your Kafka client side metrics will not be exposed!
        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> avroKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<String, StockQuote> avroKafkaConsumerFactory,
            ObjectProvider<ContainerCustomizer<Object, Object, ConcurrentMessageListenerContainer<Object, Object>>> kafkaContainerCustomizer) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, (ConsumerFactory) avroKafkaConsumerFactory);
        kafkaContainerCustomizer.ifAvailable(factory::setContainerCustomizer);
        return factory;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, StockQuoteEvent> jsonKafkaConsumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers, ObjectProvider<SslBundles> sslBundles) {
        Map<String, Object> properties = this.kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable());
        Map<String, Object> configurationOverrides = new HashMap<>(properties);
        configurationOverrides.put(ConsumerConfig.GROUP_ID_CONFIG, "multiple-consumer-beans-group-json");
        configurationOverrides.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurationOverrides.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurationOverrides.put("spring.json.trusted.packages", "com.example.kafka.event.api.json");

        DefaultKafkaConsumerFactory<String, StockQuoteEvent> factory = new DefaultKafkaConsumerFactory<>(configurationOverrides);
        // This part is important. Without the next line your Kafka client side metrics will not be exposed!
        customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> jsonKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<String, StockQuoteEvent> jsonKafkaConsumerFactory,
            ObjectProvider<ContainerCustomizer<Object, Object, ConcurrentMessageListenerContainer<Object, Object>>> kafkaContainerCustomizer) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, (ConsumerFactory) jsonKafkaConsumerFactory);
        kafkaContainerCustomizer.ifAvailable(factory::setContainerCustomizer);
        return factory;
    }
}
