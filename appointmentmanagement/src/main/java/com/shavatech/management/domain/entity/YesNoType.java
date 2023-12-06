package com.shavatech.management.domain.entity;

import java.util.Arrays;

public enum YesNoType {
    YES("Y"),
    NO("N");

    private String value;

    private YesNoType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }

    public static YesNoType of(String value){
        return Arrays.stream(YesNoType.values()).
                filter(t-> t.getValue().equals(value)).
                findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
