package com.shavatech.management.presentation.controller;

import com.shavatech.management.application.dto.AppointmentDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@ViewScoped
public class MedicalCalendarBean implements Serializable {

     private ScheduleModel model;

     private List<AppointmentDTO> appointments;

    public void loadSchedule() {
        appointments = new ArrayList<>();
        model = createSchedule(appointments);
    }

    private ScheduleModel createSchedule(List<AppointmentDTO> appointments){
        DefaultScheduleModel task = new DefaultScheduleModel();
        if(appointments!=null && !appointments.isEmpty()){

        }
        return task;
    }

    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }
}
