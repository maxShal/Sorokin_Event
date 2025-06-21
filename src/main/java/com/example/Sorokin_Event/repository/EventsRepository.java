package com.example.Sorokin_Event.repository;

import com.example.Sorokin_Event.entity.EventsEntity;
import com.example.Sorokin_Event.model.EventStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<EventsEntity,Long> {

    @Modifying
    @Transactional
    @Query("update EventEntity e set e.status = :status where e.id = :id")
    default void changeEventStatus(
            @Param("id") Long eventId,
            @Param("status") EventStatus status
    ) {

    }
}
