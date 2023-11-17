package com.shavatech.domain.management.entity;

import com.shavatech.domain.IntegrationEvent;

public class TeacherUpdatedEvent extends IntegrationEvent {

    private String fullName;

    private String id;

    public TeacherUpdatedEvent(Teacher teacher){
        setEventType("teacher_updated");
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
