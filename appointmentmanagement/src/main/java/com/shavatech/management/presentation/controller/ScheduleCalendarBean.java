package com.shavatech.management.presentation.controller;

import com.shavatech.management.application.dto.AppointmentDTO;
import com.shavatech.management.application.dto.SchedulePatientDTO;
import com.shavatech.management.application.service.PeopleResource;
import com.shavatech.management.application.service.SchedulePatientResource;
import com.shavatech.management.domain.entity.AppointmentType;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class ScheduleCalendarBean implements Serializable {

    @Inject
    SchedulePatientResource schedulePatientResource;

    @Inject
    PeopleResource peopleResource;

    private ScheduleModel model;

    private SchedulePatientDTO schedulePatientDTO;

    private List<AppointmentDTO> appointmentDTOS;

    private AppointmentDTO currentAppointmentDTO;

    private Map<String,String> patients;

    private Map<String,String> teachers;

    private Map<String,String> therapists;

    @PostConstruct
    public void loadSchedule() {
        patients = peopleResource.getPatients();
        teachers = peopleResource.getTeachers();
        therapists = peopleResource.getTherapists();
        schedulePatientDTO = new SchedulePatientDTO();
        schedulePatientDTO.setYear(LocalDate.now().getYear());
        model = createSchedule(appointmentDTOS);
    }

    public void getAppointmentsPatient(){
        if (schedulePatientDTO.getPatientId()!=null && !schedulePatientDTO.getPatientId().isBlank()){
            var schedule = schedulePatientResource.getScheduleDTO(schedulePatientDTO.getPatientId(),schedulePatientDTO.getYear());
            if(schedule!=null){
                schedulePatientDTO.setScheduleId(schedule.getScheduleId());
                schedulePatientDTO.setVersion(schedule.getVersion());
                appointmentDTOS = schedule.getAppointments();
            } else {
                appointmentDTOS = null;
                schedulePatientDTO.setScheduleId(null);
            }
        } else {
            appointmentDTOS = null;
            schedulePatientDTO.setScheduleId(null);
        }
        model = createSchedule(appointmentDTOS);
    }

    public void saveAppointment(){
        String title;
        if(currentAppointmentDTO.getAppointmentType().equals(AppointmentType.THERAPY.getValue())){
            therapists.forEach((k,v )-> {
                if(v.equals(currentAppointmentDTO.getTherapistId())){
                    currentAppointmentDTO.setTitle(currentAppointmentDTO.getStart().format(DateTimeFormatter.ofPattern("HH:mm"))
                            +"-"+currentAppointmentDTO.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"))+"->"+k);
                }
            });
         } else {
            teachers.forEach((k,v)->{
                if (v.equals(currentAppointmentDTO.getTeacherId())){
                    currentAppointmentDTO.setTitle(currentAppointmentDTO.getStart().format(DateTimeFormatter.ofPattern("HH:mm"))
                            +"-"+currentAppointmentDTO.getEnd().format(DateTimeFormatter.ofPattern("HH:mm"))+"->"+k);
                }
            });
        }

        if(currentAppointmentDTO.getId()==null){
            //Nuevo
            currentAppointmentDTO.setPatientId(schedulePatientDTO.getPatientId());
            schedulePatientResource.createAppointment(schedulePatientDTO.getScheduleId(),currentAppointmentDTO);
        } else {
            //Actualizar
            schedulePatientResource.updateAppointment(schedulePatientDTO.getScheduleId(),currentAppointmentDTO);
        }
        getAppointmentsPatient();
    }

    public void  deleteShedule(){
        schedulePatientResource.deleteAppointment(schedulePatientDTO.getScheduleId(),currentAppointmentDTO.getId());
        getAppointmentsPatient();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        //Modificar cita
        var event = (DefaultScheduleEvent) selectEvent.getObject();
        currentAppointmentDTO = loadScheduleEvent(event);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectDate){
        //Crear nueva cita
        var event = DefaultScheduleEvent.builder().startDate(selectDate.getObject()).endDate(selectDate.getObject().plusMinutes(45)).build();
        currentAppointmentDTO = loadScheduleEvent(event);
    }

    private AppointmentDTO loadScheduleEvent(DefaultScheduleEvent event){
        AppointmentDTO appointmentDTO;
        if(event.getData() != null){
            appointmentDTO = (AppointmentDTO)event.getData();
        } else {
            appointmentDTO = new AppointmentDTO();
            appointmentDTO.setStart(event.getStartDate());
            appointmentDTO.setEnd(event.getEndDate());
        }
        return appointmentDTO;
    }

    private ScheduleModel createSchedule(List<AppointmentDTO> appointments){
        DefaultScheduleModel task = new DefaultScheduleModel();
        if(appointments!=null && !appointments.isEmpty()){
            appointments.stream().map(a->{
                DefaultScheduleEvent defaultTask = new DefaultScheduleEvent();
                defaultTask.setId(a.getId());
                defaultTask.setTitle(a.getTitle());
                defaultTask.setStartDate(a.getStart());
                defaultTask.setEndDate(a.getEnd());
                if(a.isConflicted()){
                    defaultTask.setBorderColor("#CB4335");
                } else {
                    defaultTask.setBorderColor("#27AE60");
                }
                defaultTask.setData(a);
                return  defaultTask;
            }).forEachOrdered(defaultTask->{
                task.addEvent(defaultTask);
            });
        }
        return task;
    }




    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public SchedulePatientDTO getSchedulePatientDTO() {
        return schedulePatientDTO;
    }

    public void setSchedulePatientDTO(SchedulePatientDTO schedulePatientDTO) {
        this.schedulePatientDTO = schedulePatientDTO;
    }

    public AppointmentDTO getCurrentAppointmentDTO() {
        return currentAppointmentDTO;
    }

    public void setCurrentAppointmentDTO(AppointmentDTO currentAppointmentDTO) {
        this.currentAppointmentDTO = currentAppointmentDTO;
    }

    public Map<String, String> getPatients() {
        return patients;
    }

    public void setPatients(Map<String, String> patients) {
        this.patients = patients;
    }

    public Map<String, String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Map<String, String> teachers) {
        this.teachers = teachers;
    }

    public Map<String, String> getTherapists() {
        return therapists;
    }

    public void setTherapists(Map<String, String> therapists) {
        this.therapists = therapists;
    }
}
