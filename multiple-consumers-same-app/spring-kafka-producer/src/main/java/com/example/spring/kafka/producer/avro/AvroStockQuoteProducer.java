package com.example.spring.kafka.producer.avro;

import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

import static com.example.spring.kafka.producer.config.KafkaTopicsConfiguration.STOCK_QUOTES_TOPIC_NAME_AVRO;
import static com.example.spring.kafka.producer.config.KafkaTopicsConfiguration.STOCK_QUOTES_TOPIC_NAME_AVRO_2ND;

public class AvroStockQuoteProducer {

    private static final Logger log = LoggerFactory.getLogger(AvroStockQuoteProducer.class);

    private final KafkaTemplate<String, StockQuote> kafkaTemplate;

    public AvroStockQuoteProducer(KafkaTemplate<String, StockQuote> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(StockQuote stockQuote) {
        produceToTopic(STOCK_QUOTES_TOPIC_NAME_AVRO, stockQuote);
        produceToTopic(STOCK_QUOTES_TOPIC_NAME_AVRO_2ND, stockQuote);
    }

    private void produceToTopic(String topic, StockQuote stockQuote) {
        kafkaTemplate.send(topic, stockQuote.getSymbol(), stockQuote);
        log.info("Produced to topic: {} Avro data: {}", topic, stockQuote);
    }
}
