package com.example.spring.kafka.consumer.kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanRetriever {

    private final ApplicationContext applicationContext;

    public BeanRetriever(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object getBean(String className) {
        try {
            return applicationContext.getBean(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

