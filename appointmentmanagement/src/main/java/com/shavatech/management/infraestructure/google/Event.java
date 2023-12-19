package com.shavatech.management.infraestructure.google;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Event {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String id;
    public String summary;
    public String kind = "calendar#event";
    public DateTimeEvent start;
    public DateTimeEvent end;

    public List<Attendee> attendees;

    public Reminders reminders;

    public Reminders getReminders() {
        return reminders;
    }

    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }

    public Event() {
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public DateTimeEvent getStart() {
        return start;
    }

    public void setStart(DateTimeEvent start) {
        this.start = start;
    }

    public DateTimeEvent getEnd() {
        return end;
    }

    public void setEnd(DateTimeEvent end) {
        this.end = end;
    }

}
