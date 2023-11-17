package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;

public class PatientUpdatedEvent extends IntegrationEvent {
    private String fullName;

    private String id;

    public PatientUpdatedEvent(Patient patient){
        setEventType("patient_updated");
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
