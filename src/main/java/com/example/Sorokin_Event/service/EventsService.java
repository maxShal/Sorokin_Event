package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.dto.EventSearchRequestDto;
import com.example.Sorokin_Event.dto.EventUpdateDto;
import com.example.Sorokin_Event.dto.EventsResponseDto;
import com.example.Sorokin_Event.entity.EventsEntity;
import com.example.Sorokin_Event.mapper.EventsMapper;
import com.example.Sorokin_Event.model.EventStatus;
import com.example.Sorokin_Event.model.Events;
import com.example.Sorokin_Event.model.Role;
import com.example.Sorokin_Event.repository.EventsRepository;
import com.example.Sorokin_Event.security.jwt.JwtAuthentificationService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventsService {
    private final static Logger log = LoggerFactory.getLogger(EventsService.class);

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

    public Events createEvent(Events events)
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
        checkUserCanModify(id);
        EventsEntity entity = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));
        if(entity.getStatus().equals(EventStatus.CANCELLED))
        {
            log.info("Event was already cancelled");
            return;
        }
        if(entity.getStatus().equals(EventStatus.STARTED) || entity.getStatus().equals(EventStatus.FINISHED))
        {
            throw new IllegalArgumentException("Событие нельзя отменить");
        }
        repository.changeEventStatus(id, EventStatus.CANCELLED);
        //entity.setStatus(EventStatus.CANCELLED);
        //repository.deleteById(entity.getId());
    }

    public Events updateEvents(Long eventId, Events dto)
    {
        checkUserCanModify(eventId);
        EventsEntity entity = repository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));
        if((dto.maxPlaces() != null) && (dto.maxPlaces() < entity.getRegistrationList().size()))
        {
            throw new IllegalArgumentException("Максимальное число мест меньше, чем зарегистрированных пользователей");
        }
        if((dto.cost() != null) && (dto.cost() <= 0 ))
        {
            throw new IllegalArgumentException("Стоимость <= 0");
        }
        if((dto.duration() != null) && (dto.duration() <= 0 ))
        {
            throw new IllegalArgumentException("длительность <= 0");
        }
        if((dto.locationId() != null) && (locationService.findById(dto.locationId()) == null))
        {
            throw new IllegalArgumentException("Нет данной локации");
        }
        Optional.ofNullable(dto.name())
                .ifPresent(entity::setName);
        Optional.ofNullable(dto.maxPlaces())
                .ifPresent(entity::setMaxPlaces);
        Optional.ofNullable(dto.date())
                .ifPresent(entity::setDate);
        Optional.ofNullable(dto.cost())
                .ifPresent(entity::setCost);
        Optional.ofNullable(dto.duration())
                .ifPresent(entity::setDuration);
        Optional.ofNullable(dto.locationId())
                .ifPresent(entity::setLocationId);
        repository.save(entity);
        return mapper.toModel(entity);
    }

    public List<Events> searchEvents(EventSearchRequestDto dto)
    {
        var entity = repository.findEvents(
                dto.name(),
                dto.placesMin(),
                dto.placesMax(),
                dto.dateStartAfter(),
                dto.dateStartBefore(),
                dto.costMin(),
                dto.costMax(),
                dto.durationMin(),
                dto.durationMax(),
                dto.locationId(),
                dto.eventStatus()
        );
        return entity.stream()
                .map(mapper::toModel)
                .toList();
    }


    public List<Events> findAllUserEvents()
    {
        var user = service.getCurrentAuthentificatedUser();
        var userEvents = repository.findAllByOwnerIdIs(user.getId());
        return userEvents.stream()
                .map(mapper::toModel)
                .toList();
    }

    public void checkUserCanModify(Long eventId)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var event = findEventById(eventId);

        if(!event.ownerId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN))
        {
            throw new IllegalArgumentException("This user cannot modify this event");
        }
    }

}
