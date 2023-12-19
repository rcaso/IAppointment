package com.shavatech.management.infraestructure.google;

public class DateTimeEvent {
    public String dateTime;
    public String timeZone = "America/Lima";

    public DateTimeEvent(String dateTime) {
        this.dateTime = dateTime;
    }

    public DateTimeEvent() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
