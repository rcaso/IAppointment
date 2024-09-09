package com.shavatech.management.domain.entity;

import java.util.Arrays;

public enum RepeatType {
    NO("N"),DIARY("D"),WEEKLY("W");

    private String value;

    private RepeatType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }

    public static RepeatType of(String value){
        return Arrays.stream(RepeatType.values()).
                filter(a->a.getValue().equals(value)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
