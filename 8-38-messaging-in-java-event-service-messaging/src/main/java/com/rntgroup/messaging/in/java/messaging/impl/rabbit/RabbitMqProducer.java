package com.rntgroup.messaging.in.java.messaging.impl.rabbit;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@ConditionalOnMissingBean(EventMessaging.class)
@ConditionalOnProperty(value = "messaging.broker", havingValue = "rabbitmq")
public class RabbitMqProducer implements EventMessaging {

    @Override
    public void createEvent(Event event, Function<Event, Event> callbackFunction) {

    }

    @Override
    public void updateEvent(Event event, Function<Event, Event> callbackFunction) {

    }

    @Override
    public void deleteEvent(Event event, Function<Event, Event> callbackFunction) {

    }

}
