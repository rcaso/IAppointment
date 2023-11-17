package com.shavatech.management.domain.entity;

import java.util.Arrays;

public enum PeopleEventType {

    DOCTOR_CREATED("doctor_created"),
    DOCTOR_UPDATED("doctor_updated"),
    PATIENT_CREATED("patient_created"),
    PATIENT_UPDATED("patient_updated"),
    TEACHER_CREATED("teacher_created"),
    TEACHER_UPDATED("teacher_updated"),
    THERAPIST_CREATED("therapist_created"),
    THERAPIST_UPDATED("therapist_updated");

    private String value;

    private PeopleEventType(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }

    public static PeopleEventType getOfValue(String value){
        return Arrays.stream(PeopleEventType.values()).filter(p-> p.getValue().equals(value)).findFirst().orElse(null);
    }
}
