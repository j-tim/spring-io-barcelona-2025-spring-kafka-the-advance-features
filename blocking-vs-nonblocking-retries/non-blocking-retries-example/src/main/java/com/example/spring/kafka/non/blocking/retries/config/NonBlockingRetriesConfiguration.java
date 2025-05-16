package com.example.spring.kafka.non.blocking.retries.config;

import com.example.spring.kafka.non.blocking.retries.CustomerRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.net.SocketTimeoutException;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

// @EnableKafka is not needed because @EnableKafkaRetryTopic is meta-annotated including @EnableKafka
@EnableKafkaRetryTopic
@Configuration
public class NonBlockingRetriesConfiguration {

    /**
     * This will create retry topics and a dlt, as well as the corresponding consumers,
     * for all topics in methods annotated with '@KafkaListener' using the default configurations.
     * <p>
     * The KafkaTemplate instance is required for message forwarding.
     * <p>
     * Want to set up non-blocking retries for topic: "events"
     * exponential backoff: 1000ms
     * multiplier: of 2
     * max attempts: 4
     * <p>
     * Topics created:
     * <p>
     * events
     * events-retry-topic-1000
     * events-retry-topic-2000
     * events-retry-topic-4000
     * events-retry-topic-dlt
     */
//    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(KafkaTemplate<String, CustomerRegisteredEvent> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .exponentialBackoff(SECONDS.toMillis(1), 2, SECONDS.toMillis(5))
                .maxAttempts(4)
                .includeTopic("events")
                .retryTopicSuffix("-retry-topic")
                .dltSuffix("-retry-topic-dlt")
                .dltHandlerMethod("customerRegisteredEventConsumer", "processMessageFromDeadLetterTopic")

                // Think about exceptions you can and can't recover from!
                // It doesn't make sense to retry on non-recoverable exceptions

                // The exception class names that should not be retried.
                // When the message processing throws these exceptions the message goes straight to the Dead letter topic.
//                .notRetryOn(List.of(NullPointerException.class, IllegalArgumentException.class))

                // The exception class names that should be retried.
//                .retryOn(List.of(SocketTimeoutException.class))
                .create(template);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
