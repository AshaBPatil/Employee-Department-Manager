package com.example.personnel_management_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(String eventType, Object payload) {
        DomainEvent event = new DomainEvent(eventType, payload);
        kafkaTemplate.send("hr.domain-events", event);
    }
}
