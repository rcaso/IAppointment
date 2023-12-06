package com.shavatech.management.infraestructure.handlers;

import com.shavatech.infraestructure.AuditFieldListener;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;

import java.util.logging.Logger;

@ApplicationScoped
public class AppointmentScheduledHandler {

    private static final Logger logger = Logger.getLogger(AppointmentScheduledHandler.class.getName());

    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS)AppointmentScheduledEvent event){
        logger.info("Cita registrada :"+event.getAppointmentScheduled().getTitle());
    }
}
