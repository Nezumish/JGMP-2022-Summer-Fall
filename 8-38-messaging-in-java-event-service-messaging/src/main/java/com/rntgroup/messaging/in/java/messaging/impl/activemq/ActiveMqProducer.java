package com.rntgroup.messaging.in.java.messaging.impl.activemq;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@ConditionalOnMissingBean(EventMessaging.class)
@ConditionalOnProperty(value = "messaging.broker", havingValue = "activemq")
public class ActiveMqProducer implements EventMessaging {

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
