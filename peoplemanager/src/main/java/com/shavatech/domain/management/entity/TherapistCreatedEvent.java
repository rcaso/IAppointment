package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;

public class TherapistCreatedEvent extends IntegrationEvent {

    private String fullName;

    private String id;

    public TherapistCreatedEvent(Therapist therapist){
        setEventType("therapist_created");
        fullName = therapist.lastName + " "+ therapist.firstName;
        id = therapist.id.toString();
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
