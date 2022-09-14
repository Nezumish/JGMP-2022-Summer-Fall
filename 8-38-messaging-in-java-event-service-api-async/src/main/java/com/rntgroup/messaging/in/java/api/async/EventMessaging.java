package com.rntgroup.messaging.in.java.api.async;

import com.rntgroup.messaging.in.java.dto.Event;

import java.util.function.Function;

public interface EventMessaging {

    void createEvent(Event event, Function<Event, Event> callbackFunction);

    void updateEvent(Event event, Function<Event, Event> callbackFunction);

    void deleteEvent(Event event, Function<Event, Event> callbackFunction);

}
