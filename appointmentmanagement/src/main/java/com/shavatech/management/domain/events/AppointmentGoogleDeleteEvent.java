package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Appointment;

public class AppointmentGoogleDeleteEvent extends DomainEvent {

    private Appointment googleDelete;

    public AppointmentGoogleDeleteEvent(Appointment googleDelete) {
        this.googleDelete = googleDelete;
    }

    public Appointment getGoogleDelete() {
        return googleDelete;
    }
}
