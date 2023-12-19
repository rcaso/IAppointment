package com.shavatech.management.infraestructure.google;

public class Override {
    public String method;
    public Integer minutes;

    public Override() {
    }

    public Override(String method, Integer minutes) {
        this.method = method;
        this.minutes = minutes;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
