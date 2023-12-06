package com.shavatech.management.presentation.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Named
public class ParameterConfigBean {



    @ConfigProperty(name = "appmanagement.url")
    String appmanagementUrl;


    public String getAppmanagementUrl() {
        return appmanagementUrl;
    }

}
