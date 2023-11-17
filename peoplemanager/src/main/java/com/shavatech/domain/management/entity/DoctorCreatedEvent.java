package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DoctorCreatedEvent extends IntegrationEvent {
    private String fullName;

    private String id;

    public DoctorCreatedEvent(Doctor doctor){
        setEventType("doctor_created");
        fullName = doctor.lastName + " "+ doctor.firstName;
        id = doctor.id.toString();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
