package com.example.tickets.services;

import com.example.tickets.domain.CreateEventRequest;
import com.example.tickets.domain.UpdateEventRequest;
import com.example.tickets.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
   Event createEvent(UUID organizerId, CreateEventRequest event);
   Page<Event> listEventsForOrgaizers(UUID organizerId, Pageable pageable);
   // id is the id of the event :
   Optional<Event>GetEventForOrganizer(UUID organzierId,UUID id);
   Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event);
   //WE'RE GOING TO DELETE AN EVENT IF ONLY organizer has access to it
   void deleteEventForOrganizer(UUID organizerId, UUID id);
   Page<Event> listPublishedEvents(Pageable pageable);
   Page<Event> searchPublishedEvents(String query, Pageable pageable);
   Optional<Event> getPublishedEvent(UUID id) ;
}
