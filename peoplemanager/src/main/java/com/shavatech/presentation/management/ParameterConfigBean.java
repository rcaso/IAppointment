package com.shavatech.presentation.management;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Named
public class ParameterConfigBean {

    @ConfigProperty(name = "appointmentapp.url")
    String appointmentappUrl;


    public String getAppointmentappUrl() {
        return appointmentappUrl;
    }
}
