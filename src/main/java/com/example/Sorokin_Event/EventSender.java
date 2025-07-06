package com.example.Sorokin_Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventSender {

    private static final Logger log = LoggerFactory.getLogger(EventSender.class);

    private final KafkaTemplate<Long, KafkaChangeEvent> kafkaTemplate;

    public EventSender(KafkaTemplate<Long, KafkaChangeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(KafkaChangeEvent kafkaEvent)
    {
        log.info("Sending event: event={}", kafkaEvent);
        var result = kafkaTemplate.send(
                "books-topic",
                kafkaEvent.getEventId(),
                kafkaEvent
        );

        result.thenAccept(sendResult -> {
            log.info("Send successful");
        });
    }
}
