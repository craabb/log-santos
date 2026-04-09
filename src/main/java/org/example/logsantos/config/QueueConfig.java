package org.example.logsantos.config;

import org.example.logsantos.model.NormalizedLogEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;

@Configuration
public class QueueConfig {
    private static final int QUEUE_CAPACITY = 10_000
    ;

    @Bean
    public ArrayBlockingQueue<NormalizedLogEvent> logQueue() {
        return new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    }
}