package com.shavatech.management.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AppointmentDTO implements Serializable {

    private String id;

    private String patientId;

    private String appointmentType;

    private String title;

    private String scheduleId;

    private String teacherId;

    private String therapistId;

    private LocalDateTime start;

    private LocalDateTime end;

    private boolean isConflicted;

    private boolean isScheduleGoogle;



    public AppointmentDTO(String appointmentType, String title, String scheduleId, String teacherId, String therapistId, LocalDateTime start, LocalDateTime end, boolean isConflicted, boolean isScheduleGoogle) {
        this.appointmentType = appointmentType;
        this.title = title;
        this.scheduleId = scheduleId;
        this.teacherId = teacherId;
        this.therapistId = therapistId;
        this.start = start;
        this.end = end;
        this.isConflicted = isConflicted;
        this.isScheduleGoogle = isScheduleGoogle;
    }

    public AppointmentDTO() {
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public boolean isConflicted() {
        return isConflicted;
    }

    public void setConflicted(boolean conflicted) {
        isConflicted = conflicted;
    }

    public boolean isScheduleGoogle() {
        return isScheduleGoogle;
    }

    public void setScheduleGoogle(boolean scheduleGoogle) {
        isScheduleGoogle = scheduleGoogle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
