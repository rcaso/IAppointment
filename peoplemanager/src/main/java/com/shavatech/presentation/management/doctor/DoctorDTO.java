package com.shavatech.presentation.management.doctor;

import java.io.Serializable;

public class DoctorDTO implements Serializable {

    private String id;
    private String name;
    private String lastName;

    public DoctorDTO(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public DoctorDTO() {
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
}
