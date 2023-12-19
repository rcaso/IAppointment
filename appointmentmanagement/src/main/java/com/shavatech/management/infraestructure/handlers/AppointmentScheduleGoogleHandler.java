package com.shavatech.management.infraestructure.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shavatech.management.domain.entity.Appointment;
import com.shavatech.management.domain.entity.GoogleCalendarEvents;
import com.shavatech.management.domain.entity.YesNoType;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import com.shavatech.management.domain.repository.GoogleCalendarEventsRepository;
import com.shavatech.management.infraestructure.google.Override;
import com.shavatech.management.infraestructure.google.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class AppointmentScheduleGoogleHandler {



    private static final Logger logger = Logger.getLogger(AppointmentScheduleGoogleHandler.class.getName());

    @Inject
    @RestClient
    GoogleCalendarClient googleCalendarClient;

    @Inject
    GoogleCalendarEventsRepository googleCalendarEventsRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS) AppointmentScheduledEvent event){
        Appointment appointment = event.getAppointmentScheduled();
        if (appointment.getIsScheduledGoogle().getValue().equals(YesNoType.YES.getValue())) {
            try {
            logger.info("Agregar Cita Google Calendar :"+appointment.getTitle());
            Event googleEvent = new Event();
            googleEvent.setSummary(appointment.getTitle());
            DateTimeEvent start = new DateTimeEvent(appointment.getTimeRange().getStart().plusSeconds(5).toString());
            googleEvent.setStart(start);
            DateTimeEvent end = new DateTimeEvent(appointment.getTimeRange().getEnd().plusSeconds(5).toString());
            googleEvent.setEnd(end);
            List<Attendee> attendees = new ArrayList<>();
            attendees.add(new Attendee("feve18@gmail.com"));
            attendees.add(new Attendee("anbaal@gmail.com"));
            googleEvent.setAttendees(attendees);
            Reminders reminders = new Reminders();
            reminders.setUseDefault(false);
            reminders.setOverrides(new ArrayList<>());
            reminders.getOverrides().add(new Override("email",24*60));
            reminders.getOverrides().add(new Override("email",60));
            reminders.getOverrides().add(new Override("popup",60));
            reminders.getOverrides().add(new Override("popup",30));
            reminders.getOverrides().add(new Override("popup",10));
            googleEvent.setReminders(reminders);
            JsonObject result = googleCalendarClient.addEvent("all",googleEvent);
            String idGoogleEvent = result.getString("id");
            String htmlLink = result.getString("htmlLink");
            String appointmentId = appointment.getId().toString();
            GoogleCalendarEvents calendarEvents = new GoogleCalendarEvents();
            calendarEvents.setIdGoogleEvent(idGoogleEvent);
            calendarEvents.setIdAppointment(appointmentId);
            calendarEvents.setHtmlLink(htmlLink);
            calendarEvents = googleCalendarEventsRepository.saveOrMerge(calendarEvents);
            logger.info("evento registrado en Google Calendar : "+calendarEvents.getHtmlLink());
            } catch (Exception exception){
                logger.log(Level.ERROR,exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

}
