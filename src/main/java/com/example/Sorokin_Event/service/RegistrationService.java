package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.entity.EventRegistrationEntity;
import com.example.Sorokin_Event.mapper.EventMapper;
import com.example.Sorokin_Event.model.EventStatus;
import com.example.Sorokin_Event.model.Event;
import com.example.Sorokin_Event.repository.EventRepository;
import com.example.Sorokin_Event.repository.RegistrationRepository;
import com.example.Sorokin_Event.security.jwt.JwtAuthentificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService
{
    private final JwtAuthentificationService service;

    private final EventService eventService;

    private final RegistrationRepository repository;

    private final EventRepository eventRepository;

    private final EventMapper mapper;

    public RegistrationService(JwtAuthentificationService service, EventService eventService, RegistrationRepository repository, EventRepository eventRepository, EventMapper mapper) {
        this.service = service;
        this.eventService = eventService;
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;

    }

    public void registrationOnEvent(Long eventId)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var event = eventService.findEventById(eventId);
        if(!event.status().equals(EventStatus.WAIT_START))
        {
            throw new IllegalArgumentException("На мероприятие нельзя зарегистрироваться");
        }

        if(event.registrationList().size()>=event.maxPlaces())
        {
            throw new IllegalArgumentException("Все места заняты");
        }

        var registration = repository.findRegistration(eventId, currentUser.getId());
        if(registration.isPresent())
        {
            throw new IllegalArgumentException("Уже зарегистрированы");
        }

        repository.save(
                new EventRegistrationEntity(
                        null,
                        currentUser.getId(),
                        eventRepository.findById(eventId).orElseThrow()
                )
        );
    }

    public void deleteRegistrationOnEvent(Long eventId)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var event = eventService.findEventById(eventId);

        if(currentUser.getId().equals(event.ownerId()))
        {
            throw new IllegalArgumentException("Вы создатель мероприятия");
        }

        if(!event.status().equals(EventStatus.WAIT_START))
        {
            throw new IllegalArgumentException("Нельзя удалить регистрацию с мероприятия");
        }
        var registration = repository.findRegistration(currentUser.getId(), eventId)
                .orElseThrow( () -> new IllegalArgumentException("Вы не зарегистрированы на мероприятие"));

        repository.delete(registration);
    }

    public List<Event> getAllRegistrations()
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var events = repository.findRegisteredEvents(currentUser.getId());
        return events.stream()
                .map(mapper::toModel)
                .toList();
    }
}
