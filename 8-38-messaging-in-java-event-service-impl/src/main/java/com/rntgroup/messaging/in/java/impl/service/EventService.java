package com.rntgroup.messaging.in.java.impl.service;

import com.rntgroup.messaging.in.java.dto.Event;

import java.util.List;

public interface EventService {

    void createEvent(Event event);
    void updateEvent(Long eventId, Event event);
    void deleteEvent(Long eventId);
    Event getEvent(Long eventId);
    List<Event> getAllEvents();

}
