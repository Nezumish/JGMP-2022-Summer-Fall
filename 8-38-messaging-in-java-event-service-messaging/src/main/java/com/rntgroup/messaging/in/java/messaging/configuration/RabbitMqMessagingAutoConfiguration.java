package com.rntgroup.messaging.in.java.messaging.configuration;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.configuration.property.MessagingProperties;
import com.rntgroup.messaging.in.java.messaging.impl.rabbit.RabbitMqNotificationConsumer;
import com.rntgroup.messaging.in.java.messaging.impl.rabbit.RabbitMqProducer;
import com.rntgroup.messaging.in.java.messaging.impl.rabbit.RabbitMqRequestConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableRabbit
@ConditionalOnProperty(
        name = "messaging.broker", havingValue = "rabbitmq"
)
@EnableConfigurationProperties(MessagingProperties.class)
public class RabbitMqMessagingAutoConfiguration {

    private final MessagingProperties messagingProperties;

    public RabbitMqMessagingAutoConfiguration(MessagingProperties messagingProperties) {
        this.messagingProperties = messagingProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var connectionFactory = new CachingConnectionFactory();

        connectionFactory.setHost(messagingProperties.getHost());
        connectionFactory.setUsername(messagingProperties.getUser());
        connectionFactory.setPassword(messagingProperties.getPassword());

        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        var rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Queue createEventRequestQueue() {
        return new Queue(Requesting.CREATE, false);
    }

    @Bean
    public Queue updateEventRequestQueue() {
        return new Queue(Requesting.UPDATE, false);
    }

    @Bean
    public Queue deleteEventRequestQueue() {
        return new Queue(Requesting.DELETE, false);
    }

    @Bean
    public Queue createEventNotificationQueue() {
        return new Queue(Notifying.CREATE, false);
    }

    @Bean
    public Queue updateEventNotificationQueue() {
        return new Queue(Notifying.UPDATE, false);
    }

    @Bean
    public Queue deleteEventNotificationQueue() {
        return new Queue(Notifying.DELETE, false);
    }

    @Bean
    public EventMessaging rabbitMqProducer() {
        return new RabbitMqProducer(rabbitTemplate());
    }

    @Bean
    public RabbitMqRequestConsumer rabbitMqRequestConsumer() {
        return new RabbitMqRequestConsumer();
    }

    @Bean
    public RabbitMqNotificationConsumer rabbitMqNotificationConsumer() {
        return new RabbitMqNotificationConsumer();
    }

}
