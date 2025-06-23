package com.example.Sorokin_Event.service;

import com.example.Sorokin_Event.entity.EventRegistrationEntity;
import com.example.Sorokin_Event.mapper.EventsMapper;
import com.example.Sorokin_Event.model.EventStatus;
import com.example.Sorokin_Event.model.Events;
import com.example.Sorokin_Event.repository.EventsRepository;
import com.example.Sorokin_Event.repository.RegistrationRepository;
import com.example.Sorokin_Event.security.jwt.JwtAuthentificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService
{
    private final JwtAuthentificationService service;

    private final EventsService eventsService;

    private final RegistrationRepository repository;

    private final EventsRepository eventsRepository;

    private final EventsMapper mapper;

    public RegistrationService(JwtAuthentificationService service, EventsService eventsService, RegistrationRepository repository, EventsRepository eventsRepository, EventsMapper mapper) {
        this.service = service;
        this.eventsService = eventsService;
        this.repository = repository;
        this.eventsRepository = eventsRepository;
        this.mapper = mapper;

    }

    public void registrationOnEvent(Long eventId)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var event = eventsService.findEventById(eventId);
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
                        eventsRepository.findById(eventId).orElseThrow()
                )
        );
    }

    public void deleteRegistrationOnEvent(Long eventId)
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var event = eventsService.findEventById(eventId);

        if(currentUser.getId().equals(event.ownerId()))
        {
            throw new IllegalArgumentException("Вы создатель мероприятия");
        }

        if(!event.status().equals(EventStatus.WAIT_START))
        {
            throw new IllegalArgumentException("Нельзя удалить регистрацию с мероприятия");
        }
        var registration = repository.findRegistration(currentUser.getId(), eventId);
        if(registration.isEmpty())
        {
            throw new IllegalArgumentException("Вы не зарегистрированы на мероприятие");
        }
        repository.delete(registration.orElseThrow());
    }

    public List<Events> getAllRegistrations()
    {
        var currentUser = service.getCurrentAuthentificatedUser();
        var events = repository.findRegisteredEvents(currentUser.getId());
        return events.stream()
                .map(mapper::toModel)
                .toList();
    }
}
