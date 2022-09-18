package com.rntgroup.messaging.in.java.messaging.configuration;

import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.configuration.converter.JmsWorkaroundJsonConverter;
import com.rntgroup.messaging.in.java.messaging.configuration.property.MessagingProperties;
import com.rntgroup.messaging.in.java.messaging.impl.activemq.ActiveMqNotificationConsumer;
import com.rntgroup.messaging.in.java.messaging.impl.activemq.ActiveMqProducer;
import com.rntgroup.messaging.in.java.messaging.impl.activemq.ActiveMqRequestConsumer;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@ConditionalOnProperty(
        name = "messaging.broker", havingValue = "activemq"
)
@EnableConfigurationProperties(MessagingProperties.class)
public class ActiveMqMessagingAutoConfiguration {

    private final MessagingProperties messagingProperties;

    public ActiveMqMessagingAutoConfiguration(MessagingProperties messagingProperties) {
        this.messagingProperties = messagingProperties;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(getBrokerUrl());
        connectionFactory.setUserName(messagingProperties.getUser());
        connectionFactory.setPassword(messagingProperties.getPassword());
        return connectionFactory;
    }

    private String getBrokerUrl() {
        return "tcp://" + messagingProperties.getHost() + ":" + messagingProperties.getPort();
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                      DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        return new JmsWorkaroundJsonConverter(new MappingJackson2MessageConverter());
    }

    @Bean
    public Queue createEventRequestQueue() {
        return new ActiveMQQueue(Requesting.CREATE);
    }

    @Bean
    public Queue updateEventRequestQueue() {
        return new ActiveMQQueue(Requesting.UPDATE);
    }

    @Bean
    public Queue deleteEventRequestQueue() {
        return new ActiveMQQueue(Requesting.DELETE);
    }

    @Bean
    public Queue createEventNotificationQueue() {
        return new ActiveMQQueue(Notifying.CREATE);
    }

    @Bean
    public Queue updateEventNotificationQueue() {
        return new ActiveMQQueue(Notifying.UPDATE);
    }

    @Bean
    public Queue deleteEventNotificationQueue() {
        return new ActiveMQQueue(Notifying.DELETE);
    }

    @Bean
    public ActiveMqProducer activeMqProducer() {
        return new ActiveMqProducer(jmsTemplate());
    }

    @Bean
    public ActiveMqRequestConsumer activeMqRequestConsumer() {
        return new ActiveMqRequestConsumer();
    }

    @Bean
    public ActiveMqNotificationConsumer activeMqNotificationConsumer() {
        return new ActiveMqNotificationConsumer();
    }

}
