package com.shavatech.management.domain.repository;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.UUID;

@RegisterForReflection
public class AppointmentIdProjection {

    private final UUID id;

    public AppointmentIdProjection(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
