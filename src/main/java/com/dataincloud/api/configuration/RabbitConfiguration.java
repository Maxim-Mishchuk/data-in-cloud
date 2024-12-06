package com.dataincloud.api.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {
    @Bean
    public Queue profileByUserAddingQueue() {
        return new Queue("profile-by-user-adding-queue");
    }

    @Bean
    public Queue profileByUserDeletingQueue() {
        return new Queue("profile-by-user-deleting-queue");
    }

    @Bean
    public Exchange messageExchangeDirect() {
        return new DirectExchange("message-direct");
    }

    @Bean
    public Binding profileByUserAddingBinding() {
        return BindingBuilder
                .bind(profileByUserAddingQueue())
                .to(messageExchangeDirect())
                .with("profile-by-user.add")
                .noargs();
    }

    @Bean
    public Binding profileByUserDeletingBinding() {
        return BindingBuilder
                .bind(profileByUserDeletingQueue())
                .to(messageExchangeDirect())
                .with("profile-by-user.delete")
                .noargs();
    }
}
