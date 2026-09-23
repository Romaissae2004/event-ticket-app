package com.example.tickets.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name="id",updatable=false,nullable=false)
    private UUID id;

    @Column(name="name",nullable=false)
    private String name;

    @Column(name="email",nullable=false)
    private String email;

    // TODO : Organized Events : an organizer can organize many events / Un User → peut organiser plusieurs Events.
    /*Cela signifie que les infos concernant cette relation peut etre trouvée only in instance var organizer inside the event entity
    CascadeType.ALL means any changes that we make to the event that the user referencies is in saved attention à All car si on supprime un user ts les events associés seront supprimés aussi
     */
    @OneToMany(mappedBy = "organizer" , cascade = CascadeType.ALL) //La relation est déjà définie par la variable organizer dans Event Donc tu ne dois pas créer une deuxième colonne user_id dans User.
    private List<Event> organizedEvents = new ArrayList<>(){};

    // TODO : Attending Events : les events auxquels ils participent
    @ManyToMany
    @JoinTable(
            name="user_attending_events" ,
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id")
    )
    private List<Event> attendingEvents = new ArrayList<>(){};

    // TODO : Staffing Events
    @ManyToMany
    @JoinTable(
            name="user_staffing_events" ,
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id")
    )
    private List<Event> staffingEvents = new ArrayList<>(){};

    @CreatedDate
    @Column(name="created_at", updatable=false, nullable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdAt, updatedAt);
    }
}
