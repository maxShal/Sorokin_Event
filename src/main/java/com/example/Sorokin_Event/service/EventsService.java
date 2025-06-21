package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.dto.EventsResponseDto;
import com.example.Sorokin_Event.entity.EventsEntity;
import com.example.Sorokin_Event.mapper.EventsMapper;
import com.example.Sorokin_Event.model.EventStatus;
import com.example.Sorokin_Event.model.Events;
import com.example.Sorokin_Event.model.Role;
import com.example.Sorokin_Event.repository.EventsRepository;
import com.example.Sorokin_Event.security.jwt.JwtAuthentificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventsService {

    private final EventsRepository repository;

    private final LocationService locationService;

    private final EventsMapper mapper;

    private final JwtAuthentificationService service;

    public EventsService(EventsRepository repository, LocationService locationService, EventsMapper mapper, JwtAuthentificationService service) {

        this.repository = repository;
        this.locationService = locationService;
        this.mapper = mapper;
        this.service = service;
    }

    public Events createEvents(EventsResponseDto events)
    {
        var location = locationService.findById(events.locationId());
        if(location.getCapacity() < events.maxPlaces())
        {
            throw new IllegalArgumentException("На мероприятии приглашены " + location.getCapacity() + "людей, но вместимость = " + events.maxPlaces());
        }
        var currentUser = service.getCurrentAuthentificatedUser();
        var entity = new EventsEntity(
                null,
                events.name(),
                currentUser.getId(),
                events.maxPlaces(),
                List.of(),
                events.date(),
                events.cost(),
                events.duration(),
                events.locationId(),
                EventStatus.WAIT_START
        );
        return mapper.toModel(repository.save(entity));
    }

    public Events findEventById(Long id)
    {
        EventsEntity events = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено."));
        return  mapper.toModel(events);
    }

    public void deleteEventsById(Long id)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        EventsEntity entity = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));
        if(!entity.getOwnerId().equals(currentUser.getId()) || !currentUser.getRole().equals(Role.ADMIN))
        {
             throw new IllegalArgumentException("Нет доступа на удаление");
        }
        if(!entity.getStatus().equals(EventStatus.WAIT_START))
        {
            throw new IllegalArgumentException("Событие нельзя отменить");
        }
        if(entity.getStatus().equals(EventStatus.CANCELLED))
        {
            return;
        }
        repository.changeEventStatus(id, EventStatus.CANCELLED);
        //entity.setStatus(EventStatus.CANCELLED);
        //repository.deleteById(entity.getId());
    }


    public Events updateEventById(Long id, Events events) {
        EventsEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Событие с id " + id + " не найдено."));
        if (events.name() != null) entity.setName(events.name());
        if (events.ownerId() != null) entity.setOwnerId(events.ownerId());
        if (events.maxPlaces() > 0) entity.setMaxPlaces(events.maxPlaces());
        if (events.date() != null) entity.setDate(events.date());
        if (events.cost() > 0) entity.setCost(events.cost());
        if (events.duration() > 0) entity.setDuration(events.duration());
        if (events.locationId() != 0) entity.setLocationId(events.locationId());
        if (events.status() != null) entity.setStatus(events.status());
        EventsEntity updatedEntity = repository.save(entity);
        return mapper.toModel(updatedEntity);
    }
}
