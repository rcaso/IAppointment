package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Appointment;

public class AppointmentGoogleUpdatedEvent extends DomainEvent {

    private Appointment googleConfigurated;


    public AppointmentGoogleUpdatedEvent(Appointment googleConfigurated) {
        this.googleConfigurated = googleConfigurated;
    }

    public Appointment getGoogleConfigurated() {
        return googleConfigurated;
    }
}
