package com.shavatech.management.infraestructure.handlers;

import com.shavatech.management.domain.entity.Appointment;
import com.shavatech.management.domain.entity.GoogleCalendarEvents;
import com.shavatech.management.domain.entity.YesNoType;
import com.shavatech.management.domain.events.AppointmentGoogleDeleteEvent;
import com.shavatech.management.domain.events.AppointmentGoogleUpdatedEvent;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import com.shavatech.management.domain.repository.GoogleCalendarEventsRepository;
import com.shavatech.management.infraestructure.google.Override;
import com.shavatech.management.infraestructure.google.*;
import jakarta.enterprise.context.ApplicationScoped;
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
            var googleEvent = createGoogleEvent(appointment);
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

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS) AppointmentGoogleUpdatedEvent event) {
        Appointment appointment = event.getGoogleConfigurated();
        try {
            logger.info("Actualizar Cita Google Calendar: " + appointment.getTitle());
            var calendarEvent = googleCalendarEventsRepository.getGoogleEventFromAppointment(appointment.getId().toString());
            if(calendarEvent != null){
                //actualiza Calendar
                String googleEventId = calendarEvent.getIdGoogleEvent();
                var googleEvent = createGoogleEvent(appointment);
                JsonObject result = googleCalendarClient.updateEvent(googleEventId,googleEvent);
                String htmlLink = result.getString("htmlLink");
                logger.info("evento actualizado en Google Calendar : "+htmlLink);
            } else {
                // se creo la cita anteriormente sin agendar en Google
                var googleEvent = createGoogleEvent(appointment);
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
            }

        } catch (Exception exception) {
            logger.log(Level.ERROR, exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS) AppointmentGoogleDeleteEvent event) {
        var appointment = event.getGoogleDelete();
        try {
            logger.info("Borrar Cita Google Calendar: " + appointment.getTitle());
            var calendarEvent = googleCalendarEventsRepository.getGoogleEventFromAppointment(appointment.getId().toString());
            String googleEventId = calendarEvent.getIdGoogleEvent();
            googleCalendarClient.deleteEvent("all",googleEventId);
            googleCalendarEventsRepository.delete(appointment.getId().toString());
            logger.info("evento borrado en Google Calendar : "+googleEventId);
        } catch (Exception exception) {
            logger.log(Level.ERROR, exception.getMessage());
            exception.printStackTrace();
        }
    }

    private Event createGoogleEvent(Appointment appointment ){
        Event googleEvent = new Event();
        googleEvent.setSummary(appointment.getTitle());
        DateTimeEvent start = new DateTimeEvent(appointment.getTimeRange().getStart().plusSeconds(5).toString());
        googleEvent.setStart(start);
        DateTimeEvent end = new DateTimeEvent(appointment.getTimeRange().getEnd().plusSeconds(5).toString());
        googleEvent.setEnd(end);
        List<Attendee> attendees = new ArrayList<>();
        if(appointment.getAsistentes() != null && !appointment.getAsistentes().isBlank()){
            String[] correos = appointment.getAsistentes().split(",");
            for(int i =0; i< correos.length; i++){
                attendees.add(new Attendee(correos[i]));
            }
        } else {
            // default invitados al evento
            attendees.add(new Attendee("feve18@gmail.com"));
            attendees.add(new Attendee("anbaal@gmail.com"));
        }
        googleEvent.setAttendees(attendees);
        Reminders reminders = new Reminders();
        reminders.setUseDefault(false);
        reminders.setOverrides(new ArrayList<>());
        // notificar 1 dia antes
        reminders.getOverrides().add(new Override("email",24*60));
        // notificar 1 hora antes
        reminders.getOverrides().add(new Override("email",60));
        // notificar 1 hora antes
        reminders.getOverrides().add(new Override("popup",60));
        // notificar 30 minutos antes
        reminders.getOverrides().add(new Override("popup",30));
        // notificar 10 minutos antes
        reminders.getOverrides().add(new Override("popup",10));
        googleEvent.setReminders(reminders);
        return googleEvent;
    }
}
