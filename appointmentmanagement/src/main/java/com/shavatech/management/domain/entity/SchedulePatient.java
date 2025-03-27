package com.shavatech.management.domain.entity;

import com.shavatech.domain.AggregateRoot;
import com.shavatech.domain.DateTimeRange;
import com.shavatech.management.domain.events.AppointmentGoogleDeleteEvent;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.WEEKS;

@Entity
@Table(name = "schedule_patient")
public class SchedulePatient extends AggregateRoot {
    @ManyToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Appointment> appointments;

    @Column(name = "year_period")
    private Integer yearPeriod;

    public void addNewAppointment(Appointment appointment){
        appointment.setSchedulePatient(this);
        appointments.add(appointment);
        markConflictedAppintments();
        getEvents().add(new AppointmentScheduledEvent(appointment));
    }

    public void  addRepeatNewAppointment(Appointment appointment, RepeatType repeatType, LocalDate endDate ){
        if(repeatType != null && repeatType != RepeatType.NO && endDate != null){
            //Registramos para cita base
            addNewAppointment(appointment);
            //Creamos citas para las siguientes repeticiones
            List<Appointment> nextDates = generateNextAppointments(repeatType,appointment,endDate);
            nextDates.forEach(this::addNewAppointment);
        } else {
            addNewAppointment(appointment);
        }
    }

    private List<Appointment> generateNextAppointments(RepeatType repeatType,Appointment appointment, LocalDate endDate ){
        List<Appointment> nextDates = new ArrayList<>();
        LocalDate initialDate = appointment.getTimeRange().getEnd().toLocalDate();
        long rangeMax =0;
        if(repeatType == RepeatType.WEEKLY){
            rangeMax = WEEKS.between(initialDate,endDate);
        } else {
            rangeMax = DAYS.between(initialDate,endDate);
        }
        for(int i=1;i<=rangeMax;i++){
            Appointment newAppointment = null;
            DateTimeRange timeRange = null;
            if(repeatType == RepeatType.WEEKLY){
                LocalDateTime start = appointment.getTimeRange().getStart().plusWeeks(i);
                LocalDateTime end = appointment.getTimeRange().getEnd().plusWeeks(i);
                timeRange = new DateTimeRange(start, end);
            } else {
                LocalDateTime start = appointment.getTimeRange().getStart().plusDays(i);
                LocalDateTime end = appointment.getTimeRange().getEnd().plusDays(i);
                timeRange = new DateTimeRange(start, end);
            }
            newAppointment = createNextAppointment(appointment, timeRange);
            nextDates.add(newAppointment);
        }
        return nextDates;
    }

    private Appointment createNextAppointment(Appointment baseAppointment,DateTimeRange dateTimeRange){
        Appointment appointment = new Appointment();
        appointment.setTimeRange(dateTimeRange);
        appointment.setTitle(baseAppointment.getTitle());
        appointment.setType(baseAppointment.getType());
        appointment.setTeacherId(baseAppointment.getTeacherId());
        appointment.setTherapistId(baseAppointment.getTherapistId());
        appointment.setDoctorId(baseAppointment.getDoctorId());
        appointment.setIsConflicted(baseAppointment.getIsConflicted());
        appointment.setIsScheduledGoogle(baseAppointment.getIsScheduledGoogle());
        appointment.setAsistentes(baseAppointment.getAsistentes());
        return appointment;
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
