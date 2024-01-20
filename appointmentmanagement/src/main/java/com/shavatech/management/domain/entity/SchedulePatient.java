package com.shavatech.management.domain.entity;

import com.shavatech.domain.AggregateRoot;
import com.shavatech.management.domain.events.AppointmentGoogleDeleteEvent;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedule_patient")
public class SchedulePatient extends AggregateRoot {
    @ManyToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    @Column(name = "year_period")
    private Integer yearPeriod;

    public void addNewAppointment(Appointment appointment){
        appointment.setSchedulePatient(this);
        appointments.add(appointment);
        markConflictedAppintments();
        getEvents().add(new AppointmentScheduledEvent(appointment));
    }

    public boolean deleteAppointment(UUID idAppointment){
        //TODO implementar borrado
        boolean eliminado = false;
        Appointment appointmentDeleted = null;
        for(Appointment appointment : appointments){
            if(appointment.getId().equals(idAppointment)){
                appointmentDeleted = appointment;
                break;
            }
        }
        if(appointmentDeleted != null){
            appointments.remove(appointmentDeleted);
            markConflictedAppintments();
            getEvents().add(new AppointmentGoogleDeleteEvent(appointmentDeleted));
            eliminado = true;
        }
        return eliminado;
    }

    public void markConflictedAppintments(){
        for (Appointment appointment: appointments ) {
            List<Appointment> conflicted = appointments.stream().filter(a-> {
                boolean b = a.getSchedulePatient().getId().equals(appointment.getSchedulePatient().getId()) &&
                        a.getTimeRange().overLapping(appointment.getTimeRange()) &&
                        !a.equals(appointment);
                return b;
            }).toList();

            conflicted.forEach( c-> c.setIsConflicted(YesNoType.YES));
            if(conflicted != null && !conflicted.isEmpty()){
                appointment.setIsConflicted(YesNoType.YES);
            } else {
                appointment.setIsConflicted(YesNoType.NO);
            }
        }
    }

    public SchedulePatient(){

    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Integer getYearPeriod() {
        return yearPeriod;
    }

    public void setYearPeriod(Integer yearPeriod) {
        this.yearPeriod = yearPeriod;
    }
}
