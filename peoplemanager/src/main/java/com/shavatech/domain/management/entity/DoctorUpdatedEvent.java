package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DoctorUpdatedEvent extends IntegrationEvent {

    private String fullName;

    private String id;

    public DoctorUpdatedEvent(Doctor doctor){
        setEventType("doctor_updated");
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
