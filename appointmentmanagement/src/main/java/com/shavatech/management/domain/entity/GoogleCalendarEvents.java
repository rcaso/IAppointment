package com.shavatech.management.domain.entity;

import com.shavatech.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class GoogleCalendarEvents extends BaseEntity {

    @Column(name = "id_appointment")
    private String idAppointment;

    @Column(name = "id_google_event")
    private String idGoogleEvent;

    @Column(name = "html_link",length = 300)
    private String htmlLink;

    public String getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(String idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getIdGoogleEvent() {
        return idGoogleEvent;
    }

    public void setIdGoogleEvent(String idGoogleEvent) {
        this.idGoogleEvent = idGoogleEvent;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }
}
