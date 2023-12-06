package com.shavatech.management.domain.entity;

import com.shavatech.domain.BaseEntity;
import com.shavatech.domain.DateTimeRange;
import com.shavatech.management.domain.events.AppointmentGoogleConfiguratedEvent;
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

    @Embedded
    private DateTimeRange timeRange;

    private YesNoType isConflicted;

    private YesNoType isScheduledGoogle;

    public void updateType(AppointmentType newType){
        if (type.getValue().equals(newType.getValue())) return;

        type = newType;
        if(type.equals(AppointmentType.EDUCATIONAL.getValue())){
            therapistId=null;
        } else {
            teacherId=null;
        }
    }

    public void updateTeacher(String newTeacherId){
        if(type.equals(AppointmentType.EDUCATIONAL.getValue()) &&
            !teacherId.equals(newTeacherId)){
            teacherId = newTeacherId;
        }
    }

    public void updateTherapist(String newTherapistId){
        if(type.equals(AppointmentType.THERAPY.getValue()) &&
                !therapistId.equals(newTherapistId)){
            therapistId = newTherapistId;
        }
    }

    public void updateStartTime(DateTimeRange newStartTime){
        if(!timeRange.getStart().isEqual(newStartTime.getStart())){
            timeRange = newStartTime;
        }
    }

    public void updateScheduleGoogle(YesNoType notificate){
        if(!isScheduledGoogle.getValue().equals(notificate.getValue())){
            isScheduledGoogle = notificate;
            switch (isScheduledGoogle) {
                case YES -> getEvents().add(new AppointmentGoogleConfiguratedEvent(this));
                case NO -> getEvents().add(new AppointmentGoogleDeleteEvent(this));
            }
        }
    }

    public void updateTitle(String newTitle){
        if(!title.equals(newTitle)){
            title=newTitle;
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
}
