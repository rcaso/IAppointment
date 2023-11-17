package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;

public class PatientCreatedEvent extends IntegrationEvent {
    private String fullName;

    private String id;

    public PatientCreatedEvent(Patient patient){
        setEventType("patient_created");
        fullName = patient.lastName + " "+ patient.firstName;
        id = patient.id.toString();
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
