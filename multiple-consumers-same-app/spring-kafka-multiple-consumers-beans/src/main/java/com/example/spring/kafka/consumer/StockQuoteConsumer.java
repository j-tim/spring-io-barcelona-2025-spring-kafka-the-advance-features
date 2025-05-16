package com.example.spring.kafka.consumer;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class StockQuoteConsumer {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteConsumer.class);

    /**
     * Bean with name "kafkaListenerContainerFactory" is the default.
     * See: {@link org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration#kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer, ObjectProvider, ObjectProvider, ObjectProvider)}
     * We use our specific container factories beans in this example.
     * See {@link KafkaConsumerConfiguration}
     */
    @KafkaListener(topics = "stock-quotes-avro", containerFactory = "avroKafkaListenerContainerFactory")
    public void on(StockQuote stockQuote, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumed from topic: {} in Avro format: {}", topic, stockQuote);
    }

    @KafkaListener(topics = "stock-quotes-json", containerFactory = "jsonKafkaListenerContainerFactory")
    public void on(StockQuoteEvent stockQuoteEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumed from topic: {} in JSON format: {}", topic, stockQuoteEvent);
    }
}
