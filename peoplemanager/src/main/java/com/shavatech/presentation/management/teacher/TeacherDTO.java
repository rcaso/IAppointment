package com.shavatech.presentation.management.teacher;

import java.io.Serializable;

public class TeacherDTO implements Serializable {

    private String id;
    private String name;
    private String lastName;

    public TeacherDTO(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public TeacherDTO() {
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
