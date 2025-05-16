package com.example.blocking.retries.example.rest;

import com.example.blocking.retries.example.producer.CustomerRegisteredEventProducer;
import com.example.spring.kafka.non.blocking.retries.CustomerRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class RestResource {

    private static final Logger log = LoggerFactory.getLogger(RestResource.class);
    private final CustomerRegisteredEventProducer customerRegisteredEventProducer;

    public RestResource(CustomerRegisteredEventProducer customerRegisteredEventProducer) {
        this.customerRegisteredEventProducer = customerRegisteredEventProducer;
    }

    @PostMapping
    public void produce(@RequestBody Request request) {
        log.info("Received request: {}", request);
        CustomerRegisteredEvent event = new CustomerRegisteredEvent(request.customerId(), request.action(), request.description());
        customerRegisteredEventProducer.produce(event);
    }
}
