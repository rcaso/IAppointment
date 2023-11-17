package com.shavatech.domain;

import java.time.LocalDateTime;

public abstract class IntegrationEvent {

    private LocalDateTime dateOccurred;

    private String eventType;

    public IntegrationEvent(){
        dateOccurred = LocalDateTime.now();
    }

    public LocalDateTime getDateOccurred() {
        return dateOccurred;
    }

    protected void setDateOccurred(LocalDateTime dateOccurred) {
        this.dateOccurred = dateOccurred;
    }

    public String getEventType() {
        return eventType;
    }

    protected  void setEventType(String eventType) {
        this.eventType=eventType;
    }
}
