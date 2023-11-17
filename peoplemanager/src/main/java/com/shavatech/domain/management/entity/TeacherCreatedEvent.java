package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;

public class TeacherCreatedEvent extends IntegrationEvent {

    private String fullName;

    private String id;

    public TeacherCreatedEvent(Teacher teacher){
        setEventType("teacher_created");
        fullName = teacher.lastName + " "+ teacher.firstName;
        id = teacher.id.toString();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
