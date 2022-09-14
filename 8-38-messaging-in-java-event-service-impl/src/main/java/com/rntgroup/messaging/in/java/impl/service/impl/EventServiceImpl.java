package com.rntgroup.messaging.in.java.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.impl.model.EventModel;
import com.rntgroup.messaging.in.java.impl.repository.EventRepository;
import com.rntgroup.messaging.in.java.impl.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {

    private final EventMessaging eventMessaging;
    private final EventRepository eventRepository;

    @Autowired
    ObjectMapper objectMapper;

    public EventServiceImpl(EventMessaging eventMessaging, EventRepository eventRepository) {
        this.eventMessaging = eventMessaging;
        this.eventRepository = eventRepository;
    }

    @Override
    public void createEvent(Event event) {
        eventMessaging.createEvent(event, createEventFunction());
    }

    private Function<Event, Event> createEventFunction() {
        return e -> {
            var newEvent = objectMapper.convertValue(e, EventModel.class);
            newEvent.setId(null);

            newEvent = eventRepository.save(newEvent);

            return objectMapper.convertValue(newEvent, Event.class);
        };
    }

    @Override
    public void updateEvent(Long eventId, Event event) {
        event.setId(eventId);

        eventMessaging.updateEvent(event, updateEventFunction());
    }

    private Function<Event, Event> updateEventFunction() {
        return e -> {
            var updateEvent = objectMapper.convertValue(e, EventModel.class);
            updateEvent = eventRepository.save(updateEvent);

            return objectMapper.convertValue(updateEvent, Event.class);
        };
    }

    @Override
    public void deleteEvent(Long eventId) {
        var deletedEvent = getEvent(eventId);

        eventMessaging.deleteEvent(deletedEvent, deleteEventFunction());
    }

    private Function<Event, Event> deleteEventFunction() {
        return e -> {
            eventRepository.deleteById(e.getId());
            return e;
        };
    }

    @Override
    public Event getEvent(Long eventId) {
        var eventModel = eventRepository.findById(eventId).orElseThrow(
                () -> new IllegalArgumentException("Unknown event id: " + eventId)
        );

        return objectMapper.convertValue(eventModel, Event.class);
    }

    @Override
    public List<Event> getAllEvents() {
        return objectMapper.convertValue(
                StreamSupport
                        .stream(eventRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList()),
                new TypeReference<>() {}
        );
    }

}
