package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Appointment;

public class AppointmentScheduledEvent extends DomainEvent {

    private Appointment appointmentScheduled;


    public AppointmentScheduledEvent(Appointment appointmentScheduled) {
        this.appointmentScheduled = appointmentScheduled;
    }


    public Appointment getAppointmentScheduled() {
        return appointmentScheduled;
    }
}
