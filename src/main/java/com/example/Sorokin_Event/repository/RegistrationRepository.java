package com.example.Sorokin_Event.repository;

import com.example.Sorokin_Event.entity.EventRegistrationEntity;
import com.example.Sorokin_Event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<EventRegistrationEntity,Long> {

    @Query("""
        SELECT reg from EventRegistrationEntity reg
        where reg.event.id = :eventId
        and reg.userId = :userId
    """)
    Optional<EventRegistrationEntity> findRegistration(
            @Param("eventId") Long eventId,
            @Param("userId") Long userId
    );

    @Query("""
        SELECT reg.event FROM EventRegistrationEntity reg
        WHERE reg.userId = :userId
    """)
    List<EventEntity> findRegisteredEvents(@Param("userId") Long userId);
}
