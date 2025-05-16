package com.example.spring.kafka.consumer.config;

import com.example.spring.kafka.consumer.kafka.BeanRetriever;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaConsumersProperties.class)
public class KafkaConsumersConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumersConfiguration.class);

    private final BeanRetriever beanRetriever;
    private final KafkaProperties kafkaProperties;
    private final ObservationRegistry observationRegistry;
    private final ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers;

    public KafkaConsumersConfiguration(BeanRetriever beanRetriever,
                                       KafkaConsumersProperties topicConfigurations,
                                       KafkaProperties kafkaProperties,
                                       ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers,
                                       ObservationRegistry observationRegistry) {
        this.beanRetriever = beanRetriever;
        this.kafkaProperties = kafkaProperties;
        this.observationRegistry = observationRegistry;
        this.customizers = customizers;
        startMessageListeners(topicConfigurations.getCustomKafkaConsumers());
    }

    private void startMessageListeners(Collection<KafkaConsumerProperties> customKafkaConsumerConfigs) {
        customKafkaConsumerConfigs
                .stream()
                .peek(customKafkaConsumerConfig ->
                        log.info("The consumers for the following topics are enabled: {}, topic names: {}",
                                customKafkaConsumerConfig.isEnabled(),
                                Arrays.asList(customKafkaConsumerConfig.getTopicNames())))
                .filter(KafkaConsumerProperties::isEnabled)
                .map(this::createMessageListenerContainer)
                .forEach(AbstractMessageListenerContainer::start);
    }

    private KafkaMessageListenerContainer<String, Object> createMessageListenerContainer(
            KafkaConsumerProperties topicDetails) {

        log.info("Creating consumer for topics {}", Arrays.stream(topicDetails.getTopicNames()).toList());

        // An implementation of MessageListener<?, ?>, registered as a spring bean to handle specific message types
        MessageListener<?, ?> messageListener = (MessageListener<?, ?>) beanRetriever.getBean(topicDetails.getMessageListenerClass());

        ContainerProperties containerProperties = buildContainerProperties(topicDetails, messageListener);

        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = buildConsumerFactory(topicDetails);
        //Enable client side metrics
        customizers.orderedStream().forEach((customizer) -> customizer.customize(consumerFactory));
        KafkaMessageListenerContainer<String, Object> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener(messageListener);
        container.setAutoStartup(true);
        return container;
    }

    private ContainerProperties buildContainerProperties(KafkaConsumerProperties topicDetails, MessageListener<?, ?> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(topicDetails.getTopicNames());
        containerProperties.setMessageListener(messageListener);
        // Enable observation via Micrometer
        containerProperties.setObservationEnabled(true);
        containerProperties.setObservationRegistry(observationRegistry);
        return containerProperties;
    }

    private DefaultKafkaConsumerFactory<Object, Object> buildConsumerFactory(
            KafkaConsumerProperties kafkaConsumerProperties) {

        //Take defaults from the spring.kafka.consumer properties
        Map<String, Object> defaultSpringKafkaConsumerProperties = kafkaProperties.buildConsumerProperties();

        KafkaProperties.Consumer consumerConfigs = kafkaConsumerProperties.getConfigs();
        //Override defaults with the spring.kafka.consumer.custom-kafka-consumers.[consumer].configs properties
        //Only properties that are non-null are added
        Map<String, Object> customConsumerPropertyOverrides = consumerConfigs.buildProperties(null);

        defaultSpringKafkaConsumerProperties.putAll(customConsumerPropertyOverrides);
        log.info("Initializing consumer with properties: {}", defaultSpringKafkaConsumerProperties);

        return new DefaultKafkaConsumerFactory<>(defaultSpringKafkaConsumerProperties);
    }
}
