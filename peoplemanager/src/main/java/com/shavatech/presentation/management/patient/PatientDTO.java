package com.shavatech.presentation.management.patient;

import java.io.Serializable;
import java.time.LocalDate;

public class PatientDTO implements Serializable {

    private String id;
    private String name;
    private String lastName;
    private LocalDate birthDay;

    public PatientDTO(String id, String name, String lastName, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDay = birthDay;
    }

    public PatientDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
}
