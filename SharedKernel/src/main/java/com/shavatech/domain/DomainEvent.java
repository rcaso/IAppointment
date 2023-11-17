package com.shavatech.domain;

import java.time.LocalDateTime;

public abstract class DomainEvent {

    private LocalDateTime dateOccurred;

    public DomainEvent(){
        dateOccurred = LocalDateTime.now();
    }

    public LocalDateTime getDateOccurred() {
        return dateOccurred;
    }

    protected void setDateOccurred(LocalDateTime dateOccurred) {
        this.dateOccurred = dateOccurred;
    }
}
