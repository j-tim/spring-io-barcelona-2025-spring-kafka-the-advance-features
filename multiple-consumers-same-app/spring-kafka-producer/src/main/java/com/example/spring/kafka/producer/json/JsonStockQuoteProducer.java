package com.example.spring.kafka.producer.json;

import com.example.kafka.event.api.json.StockQuoteEvent;
import com.example.spring.kafka.avro.stock.quote.StockQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import static com.example.spring.kafka.producer.config.KafkaTopicsConfiguration.STOCK_QUOTES_TOPIC_NAME_JSON;

public class JsonStockQuoteProducer {

    private static final Logger log = LoggerFactory.getLogger(JsonStockQuoteProducer.class);

    private final KafkaTemplate<String, StockQuoteEvent> jsonKafkaTemplate;

    public JsonStockQuoteProducer(KafkaTemplate<String, StockQuoteEvent> jsonKafkaTemplate) {
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    public void produceJson(StockQuote stockQuote) {
        produceToTopicInJSON(STOCK_QUOTES_TOPIC_NAME_JSON, stockQuote);
    }

    private void produceToTopicInJSON(String topic, StockQuote stockQuote) {
        StockQuoteEvent event = StockQuoteEventMapper.from(stockQuote);
        jsonKafkaTemplate.send(topic, stockQuote.getSymbol(), event);
        log.info("Produced to topic: {} JSON data: {}", topic, event);
    }
}
