package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Appointment;

public class AppointmentGoogleConfiguratedEvent extends DomainEvent {

    private Appointment googleConfigurated;


    public AppointmentGoogleConfiguratedEvent(Appointment googleConfigurated) {
        this.googleConfigurated = googleConfigurated;
    }

    public Appointment getGoogleConfigurated() {
        return googleConfigurated;
    }
}
