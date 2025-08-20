package br.com.rodrigobs.dio.storefront.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.product-change-availability}")
    private String queueName;

    /**
     * Fila: product.change.availability.queue
     */
    @Bean
    public Queue productChangeAvailabilityQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    /**
     * Exchange: product.exchange (TopicExchange para flexibilidade)
     */
    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange("product.exchange");
    }

    /**
     * Binding: product.change.availability
     */
    @Bean
    public Binding productAvailabilityBinding() {
        return BindingBuilder.bind(productChangeAvailabilityQueue())
                .to(productExchange())
                .with("product.change.availability");
    }
}