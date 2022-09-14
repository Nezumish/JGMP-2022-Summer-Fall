package com.rntgroup.messaging.in.java.api.rest;

import com.rntgroup.messaging.in.java.dto.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Event Resource", description = "Event resource management API")
public interface EventResource {

    @Operation(
            method = "Create Event",
            description = "Creates An Event With Given Data"
    )
    void createEvent(Event event);

    @Operation(
            method = "Update Event",
            description = "Updates An Existing Event By Its Id"
    )
    void updateEvent(Long eventId, Event event);

    @Operation(
            method = "Delete Event",
            description = "Deletes The Event By Its Id"
    )
    void deleteEvent(Long eventId);

    @Operation(
            method = "Get Event",
            description = "Returns The Event By Its Id"
    )
    Event getEvent(Long eventId);

    @Operation(
            method = "Get All Events",
            description = "Returns All Saved Events"
    )
    List<Event> getAllEvents();

}
