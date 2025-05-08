package com.example.personnel_management_service.kafka;

import java.time.Instant;

public record DomainEvent(String type, Object data, Instant timestamp) {
    public DomainEvent(String type, Object data) {
        this(type, data, Instant.now());
    }
}
