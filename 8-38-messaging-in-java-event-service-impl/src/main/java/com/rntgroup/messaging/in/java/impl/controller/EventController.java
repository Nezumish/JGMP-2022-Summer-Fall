package com.rntgroup.messaging.in.java.impl.controller;

import com.rntgroup.messaging.in.java.api.rest.EventResource;
import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.impl.service.EventService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController implements EventResource {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    @PostMapping
    public void createEvent(@RequestBody Event event) {
        eventService.createEvent(event);
    }

    @Override
    @PutMapping("/{eventId}")
    public void updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        eventService.updateEvent(eventId, event);
    }

    @Override
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @Override
    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }

    @Override
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

}
