package com.example.tickets.repositories;

import com.example.tickets.domain.entities.Event;
import com.example.tickets.domain.entities.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository <Event, UUID>{
    //Pageable object which contains all the information which spring data JPA can use in order to return the right data for the requested page
    // A page being a collection of events
    Page<Event> findByOrganizerId(UUID OrganizerId,Pageable pageable);
    //this method should return an event if both the event matching  the ID and the organizer id exists . Otherwise, an optional empty
    Optional<Event> findByIdAndOrganizerId(UUID uuid,UUID organizerId);
    Page<Event>findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(value = "SELECT * FROM events WHERE " +
            "status = 'PUBLISHED' AND " +
            "to_tsvector('english', COALESCE(name, '') || ' ' || COALESCE(venue, '')) " +
            "@@ plainto_tsquery('english', :searchTerm)",
            countQuery = "SELECT count(*) FROM events WHERE " +
                    "status = 'PUBLISHED' AND " +
                    "to_tsvector('english', COALESCE(name, '') || ' ' || COALESCE(venue, '')) " +
                    "@@ plainto_tsquery('english', :searchTerm)",
            nativeQuery = true)
    Page<Event>searchEvents(@Param("searchTerm") String searchTerm , Pageable pageable);

    Optional<Event> findByIdAndStatus(UUID id , EventStatusEnum status);


}
