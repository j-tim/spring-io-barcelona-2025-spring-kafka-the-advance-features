package com.example.spring.kafka.consumer;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class StockQuoteConsumer {

    private static final Logger log = LoggerFactory.getLogger(StockQuoteConsumer.class);

    // @formatter:off
    @KafkaListener(topics = "stock-quotes-avro", id = "stockQuoteListenerAvro",
                   groupId = "vertical-scaling-consumer-group-avro",
//                   concurrency = "${stock.quote.consumer.avro.concurrency:3}",
                   idIsGroup = false)
    // @formatter:on
    public void on(StockQuote stockQuote, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumed from topic: {} in Avro format: {}", topic, stockQuote);
    }

    // @formatter:off
    @KafkaListener(topics = "stock-quotes-json", id = "stockQuoteListenerJson", groupId = "non-vertical-scaling-consumer-group-json",
                    idIsGroup = false,
                    properties = {
                        "value.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
                        "spring.json.trusted.packages=com.example.kafka.event.api.json"
                    })
    // @formatter:on
    public void on(StockQuoteEvent stockQuoteEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumed from topic: {} in JSON format: {}", topic, stockQuoteEvent);
    }
}
