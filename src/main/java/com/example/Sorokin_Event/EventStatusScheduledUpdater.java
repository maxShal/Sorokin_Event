package com.example.Sorokin_Event;

import com.example.Sorokin_Event.model.EventStatus;
import com.example.Sorokin_Event.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class EventStatusScheduledUpdater
{

    private final static Logger log = LoggerFactory.getLogger(EventStatusScheduledUpdater.class);

    private final EventRepository eventRepository;
    private final EventSender eventSender;

    public EventStatusScheduledUpdater(EventRepository eventRepository, EventSender eventSender) {
        this.eventRepository = eventRepository;
        this.eventSender = eventSender;
    }

    @Scheduled(cron = "${event.stats.cron}")
    public void updateEventStatuses() {
        log.info("EventStatusScheduledUpdater started");

        var startedEvents = eventRepository.findStartedEventsWithStatus(EventStatus.WAIT_START);
        startedEvents.forEach(eventId -> {
            var event = eventRepository.findById(eventId).orElseThrow();
            eventRepository.changeEventStatus(eventId, EventStatus.STARTED);

            var changeStatus = new FieldChange<EventStatus>();
            changeStatus.setOldField(event.getStatus());
            changeStatus.setNewField(EventStatus.STARTED);

            eventSender.sendEvent(new KafkaChangeEvent(
                    event.getId(),
                    event.getRegistrationList().stream().map(r -> r.getUserId()).toList(),
                    event.getOwnerId(),
                    null,
                    new FieldChange<>(), new FieldChange<>(), new FieldChange<>(),
                    new FieldChange<>(), new FieldChange<>(), new FieldChange<>(),
                    changeStatus
            ));
        });

        var endedEvents = eventRepository.findEndedEventsWithStatus(EventStatus.STARTED);
        endedEvents.forEach(eventId -> {
            var event = eventRepository.findById(eventId).orElseThrow();
            eventRepository.changeEventStatus(eventId, EventStatus.FINISHED);

            var changeStatus = new FieldChange<EventStatus>();
            changeStatus.setOldField(event.getStatus());
            changeStatus.setNewField(EventStatus.FINISHED);

            eventSender.sendEvent(new KafkaChangeEvent(
                    event.getId(),
                    event.getRegistrationList().stream().map(r -> r.getUserId()).toList(),
                    event.getOwnerId(),
                    null,
                    new FieldChange<>(), new FieldChange<>(), new FieldChange<>(),
                    new FieldChange<>(), new FieldChange<>(), new FieldChange<>(),
                    changeStatus
            ));
        });
    }
}
