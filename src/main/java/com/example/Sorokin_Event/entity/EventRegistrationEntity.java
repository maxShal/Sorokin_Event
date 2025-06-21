package com.example.Sorokin_Event.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "registration")
public class EventRegistrationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long UserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventsEntity event;

    public EventRegistrationEntity(Long id, Long userId, EventsEntity event) {
        this.id = id;
        UserId = userId;
        this.event = event;
    }

    public EventRegistrationEntity() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public EventsEntity getEvent() {
        return event;
    }

    public void setEvent(EventsEntity event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventRegistrationEntity that = (EventRegistrationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(UserId, that.UserId) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, UserId, event);
    }

    @Override
    public String toString() {
        return "EventRegistrationEntity{" +
                "id=" + id +
                ", UserId=" + UserId +
                ", event=" + event +
                '}';
    }
}
