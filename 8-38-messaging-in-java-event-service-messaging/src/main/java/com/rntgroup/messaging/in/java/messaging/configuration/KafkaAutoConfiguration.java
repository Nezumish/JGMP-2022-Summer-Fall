package com.rntgroup.messaging.in.java.messaging.configuration;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.configuration.property.MessagingProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@EnableConfigurationProperties(MessagingProperties.class)
@ConditionalOnProperty(
        name = "broker", havingValue = "kafka"
)
public class KafkaAutoConfiguration {

    private final MessagingProperties properties;

    public KafkaAutoConfiguration(MessagingProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ProducerFactory<String, Event> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                getBootstrapAddress());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    private String getBootstrapAddress() {
        return properties.getHost() + ":" + properties.getPort();
    }

    @Bean
    public KafkaTemplate<String, Event> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
