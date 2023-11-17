package com.shavatech.management.infraestructure.messaging;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class PeopleEvent {
    public String id;
    public String fullName;
    public String eventType;

    public LocalDateTime dateOccurred;

    public PeopleEvent(){

    }


}
