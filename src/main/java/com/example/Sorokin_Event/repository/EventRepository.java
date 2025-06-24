package com.example.Sorokin_Event.repository;

import com.example.Sorokin_Event.entity.EventEntity;
import com.example.Sorokin_Event.model.EventStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    @Modifying
    @Transactional
    @Query("update EventsEntity e set e.status = :status where e.id = :id")
    void changeEventStatus(
            @Param("id") Long eventId,
            @Param("status") EventStatus status
    );

    @EntityGraph(attributePaths = {"registrationList"})
    Optional<EventEntity> findById(Long id);


    @Query("""
            SELECT e FROM EventsEntity e 
            WHERE (:name IS NULL OR e.name LIKE %:name%) 
            AND (:placesMin IS NULL OR e.maxPlaces >= :placesMin) 
            AND (:placesMax IS NULL OR e.maxPlaces <= :placesMax) 
            AND (CAST(:dateStartAfter as date) IS NULL OR e.date >= :dateStartAfter) 
            AND (CAST(:dateStartBefore as date) IS NULL OR e.date <= :dateStartBefore) 
            AND (:costMin IS NULL OR e.cost >= :costMin) 
            AND (:costMax IS NULL OR e.cost <= :costMax) 
            AND (:durationMin IS NULL OR e.duration >= :durationMin) 
            AND (:durationMax IS NULL OR e.duration <= :durationMax) 
            AND (:locationId IS NULL OR e.locationId = :locationId) 
            AND (:eventStatus IS NULL OR e.status = :eventStatus)
            """)
    List<EventEntity> findEvents(
            @Param("name") String name,
            @Param("placesMin") Integer placesMin,
            @Param("placesMax") Integer placesMax,
            @Param("dateStartAfter") LocalDateTime dateStartAfter,
            @Param("dateStartBefore") LocalDateTime dateStartBefore,
            @Param("costMin") BigDecimal costMin,
            @Param("costMax") BigDecimal costMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("locationId") Integer locationId,
            @Param("eventStatus") EventStatus eventStatus
    );

    @EntityGraph(attributePaths = "registrationList")
    List<EventEntity> findAllByOwnerIdIs(Long ownerId);


    @Query("""
        SELECT e.id from EventsEntity e
        where e.date < CURRENT_TIMESTAMP
        AND e.status = :status
    """)
    List<Long> findStartedEventsWithStatus(@Param("status") EventStatus status);

    @Query(value = """
        SELECT e.id from event e
        where e.date + INTERVAL '1 minute' * e.duration < NOW()
        AND e.status = :status
    """, nativeQuery = true)
    List<Long> findEndedEventsWithStatus(@Param("status") EventStatus status);
}
