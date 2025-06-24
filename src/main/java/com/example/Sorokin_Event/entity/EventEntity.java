package com.example.Sorokin_Event.entity;

import com.example.Sorokin_Event.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events")
public class EventEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(min = 1, max = 100)
        private String name;

        private Long ownerId;

        @Min(1)
        private int maxPlaces;

        @OneToMany(mappedBy = "event")
        private List<EventRegistrationEntity> registrationList;

        @Future
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime date;

        @PositiveOrZero
        private int cost;

        @PositiveOrZero
        private int duration;

        private Long locationId;

        private EventStatus status;

        public EventEntity(Long id, String name, Long ownerId, int maxPlaces, List<EventRegistrationEntity> registrationList, LocalDateTime date, int cost, int duration, Long locationId, EventStatus status) {
                this.id = id;
                this.name = name;
                this.ownerId = ownerId;
                this.maxPlaces = maxPlaces;
                this.registrationList = registrationList;
                this.date = date;
                this.cost = cost;
                this.duration = duration;
                this.locationId = locationId;
                this.status = status;
        }

        public EventEntity() {
        }

        public Long getId() {

                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Long getOwnerId() {
                return ownerId;
        }

        public void setOwnerId(Long ownerId) {
                this.ownerId = ownerId;
        }

        public int getMaxPlaces() {
                return maxPlaces;
        }

        public void setMaxPlaces(int maxPlaces) {
                this.maxPlaces = maxPlaces;
        }

        public List<EventRegistrationEntity> getRegistrationList() {
                return registrationList;
        }

        public void setRegistrationList(List<EventRegistrationEntity> registrationList) {
                this.registrationList = registrationList;
        }

        public LocalDateTime getDate() {
                return date;
        }

        public void setDate(LocalDateTime date) {
                this.date = date;
        }

        public int getCost() {
                return cost;
        }

        public void setCost(int cost) {
                this.cost = cost;
        }

        public int getDuration() {
                return duration;
        }

        public void setDuration(int duration) {
                this.duration = duration;
        }

        public Long getLocationId() {
                return locationId;
        }

        public void setLocationId(Long locationId) {
                this.locationId = locationId;
        }

        public EventStatus getStatus() {
                return status;
        }

        public void setStatus(EventStatus status) {
                this.status = status;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                EventEntity entity = (EventEntity) o;
                return maxPlaces == entity.maxPlaces && cost == entity.cost && duration == entity.duration && locationId == entity.locationId && Objects.equals(id, entity.id) && Objects.equals(name, entity.name) && Objects.equals(ownerId, entity.ownerId) && Objects.equals(registrationList, entity.registrationList) && Objects.equals(date, entity.date) && status == entity.status;
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, name, ownerId, maxPlaces, registrationList, date, cost, duration, locationId, status);
        }

        @Override
        public String toString() {
                return "EventsEntity{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", ownerId=" + ownerId +
                        ", maxPlaces=" + maxPlaces +
                        ", registrationList=" + registrationList +
                        ", date=" + date +
                        ", cost=" + cost +
                        ", duration=" + duration +
                        ", locationId=" + locationId +
                        ", status=" + status +
                        '}';
        }
}