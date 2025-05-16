package com.example.spring.kafka.producer.config;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;
import com.example.spring.kafka.producer.avro.AvroStockQuoteProducer;
import com.example.spring.kafka.producer.avro.ScheduledAvroStockQuoteProducer;
import com.example.spring.kafka.producer.generator.RandomStockQuoteGenerator;
import com.example.spring.kafka.producer.json.JsonStockQuoteProducer;
import com.example.spring.kafka.producer.json.ScheduledJsonStockQuoteProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @ConditionalOnProperty(name = "kafka.producer.avro.enabled", havingValue = "true")
    @Bean
    public KafkaTemplate<String, StockQuote> avroKafkaTemplate(
            ProducerFactory<String, StockQuote> kafkaProducerFactory,
            ProducerListener<Object, Object> kafkaProducerListener,
            ObjectProvider<RecordMessageConverter> messageConverter,
            KafkaProperties properties) {

        return createKafkaTemplate(kafkaProducerFactory, kafkaProducerListener, messageConverter,
                properties, Collections.emptyMap());
    }

    @ConditionalOnProperty(name = "kafka.producer.json.enabled", havingValue = "true")
    @Bean
    public KafkaTemplate<String, StockQuoteEvent> jsonKafkaTemplate(
            ProducerFactory<String, StockQuoteEvent> kafkaProducerFactory,
            ProducerListener<Object, Object> kafkaProducerListener,
            ObjectProvider<RecordMessageConverter> messageConverter,
            KafkaProperties properties) {

        Map<String, Object> configurationOverrides = new HashMap<>();
        configurationOverrides.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return createKafkaTemplate(kafkaProducerFactory, kafkaProducerListener, messageConverter, properties, configurationOverrides);
    }

    /**
     * See: {@link org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration#kafkaTemplate(ProducerFactory, ProducerListener, ObjectProvider)}
     */
    private <T> KafkaTemplate<String, T> createKafkaTemplate(ProducerFactory<String, T> kafkaProducerFactory,
                                                             ProducerListener<Object, Object> kafkaProducerListener, ObjectProvider<RecordMessageConverter> messageConverter,
                                                             KafkaProperties properties, Map<String, Object> configurationOverrides) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        KafkaTemplate<String, T> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory, configurationOverrides);
        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
        map.from(kafkaProducerListener).to(((KafkaTemplate) kafkaTemplate)::setProducerListener);
        map.from(properties.getTemplate().getDefaultTopic()).to(kafkaTemplate::setDefaultTopic);
        map.from(properties.getTemplate().getTransactionIdPrefix()).to(kafkaTemplate::setTransactionIdPrefix);
        map.from(properties.getTemplate().isObservationEnabled()).to(kafkaTemplate::setObservationEnabled);
        return kafkaTemplate;
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.json.enabled", havingValue = "true")
    public ScheduledJsonStockQuoteProducer scheduledJsonStockQuoteProducer(JsonStockQuoteProducer jsonStockQuoteProducer, RandomStockQuoteGenerator generator) {
        return new ScheduledJsonStockQuoteProducer(jsonStockQuoteProducer, generator);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.avro.enabled", havingValue = "true")
    public ScheduledAvroStockQuoteProducer scheduledAvroStockQuoteProducer(AvroStockQuoteProducer avroStockQuoteProducer, RandomStockQuoteGenerator generator) {
        return new ScheduledAvroStockQuoteProducer(avroStockQuoteProducer, generator);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.json.enabled", havingValue = "true")
    public JsonStockQuoteProducer jsonStockQuoteProducer(KafkaTemplate<String, StockQuoteEvent> jsonKafkaTemplate) {
        return new JsonStockQuoteProducer(jsonKafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "kafka.producer.avro.enabled", havingValue = "true")
    public AvroStockQuoteProducer avroStockQuoteProducer(KafkaTemplate<String, StockQuote> avroKafkaTemplate) {
        return new AvroStockQuoteProducer(avroKafkaTemplate);
    }


}
