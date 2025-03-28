package com.shavatech.management.domain.entity;

import com.shavatech.domain.BaseEntity;
import com.shavatech.domain.DateTimeRange;
import com.shavatech.management.domain.events.AppointmentGoogleUpdatedEvent;
import com.shavatech.management.domain.events.AppointmentGoogleDeleteEvent;
import jakarta.persistence.*;

@Entity
public class Appointment extends BaseEntity {
    private AppointmentType type;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private SchedulePatient schedulePatient;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "therapist_id")
    private String therapistId;

    @Column(name = "doctor_id")
    private String doctorId;

    @Embedded
    private DateTimeRange timeRange;

    private YesNoType isConflicted;

    private YesNoType isScheduledGoogle;

    private String asistentes;

    public void updateType(AppointmentType newType){
        if (type.getValue().equals(newType.getValue())) return;

        type = newType;
        switch (type){
            case EDUCATIONAL -> {
                therapistId=null;
                doctorId=null;
            }
            case THERAPY -> {
                teacherId=null;
                doctorId=null;
            }
            case MEDICAL -> {
                teacherId=null;
                therapistId=null;
            }
        }

    }

    public void updateTeacher(String newTeacherId){
        if(type.equals(AppointmentType.EDUCATIONAL) &&
                (teacherId == null || !teacherId.equals(newTeacherId))){
            teacherId = newTeacherId;
        }
    }

    public void updateTherapist(String newTherapistId){
        if(type.equals(AppointmentType.THERAPY) &&
                (therapistId == null || !therapistId.equals(newTherapistId))){
            therapistId = newTherapistId;
        }
    }

    public void updateDoctor(String newDoctorId){
        if(type.equals(AppointmentType.MEDICAL)
            && (doctorId == null || !doctorId.equals(newDoctorId))){
            doctorId = newDoctorId;
        }
    }

    public void updateStartTime(DateTimeRange newStartTime){
        if(!timeRange.getStart().isEqual(newStartTime.getStart())){
            timeRange = newStartTime;
            getEvents().add(new AppointmentGoogleUpdatedEvent(this));
        }
    }

    public void updateScheduleGoogle(YesNoType notificate){
        if(!isScheduledGoogle.getValue().equals(notificate.getValue())){
            isScheduledGoogle = notificate;
            switch (isScheduledGoogle) {
                case YES -> getEvents().add(new AppointmentGoogleUpdatedEvent(this));
                case NO -> {
                    asistentes = null;
                    getEvents().add(new AppointmentGoogleDeleteEvent(this));
                }
            }
        }
    }

    public void updateTitle(String newTitle){
        if(title == null || !title.equals(newTitle)){
            title=newTitle;
        }
    }

    public void updateAsistentes(String newAsistentes){
        if(isScheduledGoogle.equals(YesNoType.YES)){
            if(asistentes == null || !asistentes.equals(newAsistentes)){
                asistentes = newAsistentes;
                getEvents().add(new AppointmentGoogleUpdatedEvent(this));
            }
        }
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SchedulePatient getSchedulePatient() {
        return schedulePatient;
    }

    public void setSchedulePatient(SchedulePatient patient) {
        this.schedulePatient = patient;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacher) {
        this.teacherId = teacher;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapist) {
        this.therapistId = therapist;
    }

    public DateTimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(DateTimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public YesNoType getIsConflicted() {
        return isConflicted;
    }

    public void setIsConflicted(YesNoType isConflicted) {
        this.isConflicted = isConflicted;
    }

    public YesNoType getIsScheduledGoogle() {
        return isScheduledGoogle;
    }

    public void setIsScheduledGoogle(YesNoType isScheduledGoogle) {
        this.isScheduledGoogle = isScheduledGoogle;
    }

    public String getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(String asistentes) {
        this.asistentes = asistentes;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + getId() +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", schedulePatient=" + schedulePatient +
                ", teacherId='" + teacherId + '\'' +
                ", therapistId='" + therapistId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", timeRange=" + timeRange +
                ", isConflicted=" + isConflicted +
                ", isScheduledGoogle=" + isScheduledGoogle +
                ", asistentes='" + asistentes + '\'' +
                '}';
    }
}
