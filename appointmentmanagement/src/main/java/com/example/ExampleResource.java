package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shavatech.management.infraestructure.google.Attendee;
import com.shavatech.management.infraestructure.google.DateTimeEvent;
import com.shavatech.management.infraestructure.google.Event;
import com.shavatech.management.infraestructure.google.Override;
import com.shavatech.management.infraestructure.google.Reminders;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/jackson")
    @Produces(MediaType.APPLICATION_JSON)
    public String testJackson() {
        ObjectMapper mapper = new ObjectMapper();
        String result ="";
        try {

            Event googleEvent = new Event();
            googleEvent.setSummary("Probando cita");
            DateTimeEvent start = new DateTimeEvent(LocalDateTime.now().minusHours(1).toString());
            googleEvent.setStart(start);
            DateTimeEvent end = new DateTimeEvent(LocalDateTime.now().toString());
            googleEvent.setEnd(end);
            List<Attendee> attendees = new ArrayList<>();
            attendees.add(new Attendee("feve18@gmail.com"));
            attendees.add(new Attendee("anbaal@gmail.com"));
            googleEvent.setAttendees(attendees);
            Reminders reminders = new Reminders();
            reminders.setUseDefault(false);
            reminders.setOverrides(new ArrayList<>());
            reminders.getOverrides().add(new com.shavatech.management.infraestructure.google.Override("email",24*60));
            reminders.getOverrides().add(new com.shavatech.management.infraestructure.google.Override("email",60));
            reminders.getOverrides().add(new com.shavatech.management.infraestructure.google.Override("popup",60));
            reminders.getOverrides().add(new com.shavatech.management.infraestructure.google.Override("popup",30));
            reminders.getOverrides().add(new Override("popup",10));
            googleEvent.setReminders(reminders);
            result = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(googleEvent);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
}
