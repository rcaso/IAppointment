package com.shavatech.management.application.dto;

import java.io.Serializable;
import java.util.List;

public class SchedulePatientDTO implements Serializable {

    private String scheduleId;

    private String patientId;

    private List<AppointmentDTO> appointments;

    private Integer year;

    private Long version;

    private AppointmentDTO currentAppointment;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public AppointmentDTO getCurrentAppointment() {
        return currentAppointment;
    }

    public void setCurrentAppointment(AppointmentDTO currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
