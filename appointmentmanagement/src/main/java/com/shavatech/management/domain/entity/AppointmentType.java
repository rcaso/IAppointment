package com.shavatech.management.domain.entity;

import java.util.Arrays;
import java.util.stream.Stream;

public enum AppointmentType {

    THERAPY("T"), EDUCATIONAL("E");

    private String value;

    private AppointmentType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }

    public static AppointmentType of(String value){
        return Arrays.stream(AppointmentType.values()).
                filter(a->a.getValue().equals(value)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
