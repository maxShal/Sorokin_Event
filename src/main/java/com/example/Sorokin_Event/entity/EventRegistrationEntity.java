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
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public EventRegistrationEntity(Long id, Long userId, EventEntity event) {

        this.id = id;
        this.userId = userId;
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
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventRegistrationEntity that = (EventRegistrationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, event);
    }

    @Override
    public String
    toString() {
        return "EventRegistrationEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", event=" + event +
                '}';
    }
}
