package com.dataincloud.api.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.RabbitMQContainer;

@TestConfiguration
public class RabbitTestConfiguration {
    @Bean
    @ServiceConnection
    public RabbitMQContainer rabbitMQContainer() {
        return new RabbitMQContainer("rabbitmq:3.12.14-management-alpine")
                .withExposedPorts(5672, 15672);
    }
}
