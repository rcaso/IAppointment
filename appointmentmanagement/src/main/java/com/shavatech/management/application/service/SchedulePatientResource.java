package com.shavatech.management.application.service;

import com.shavatech.domain.DateTimeRange;
import com.shavatech.infraestructure.AuditFieldListener;
import com.shavatech.management.application.dto.AppointmentDTO;
import com.shavatech.management.application.dto.SchedulePatientDTO;
import com.shavatech.management.domain.entity.*;
import com.shavatech.management.domain.repository.SchedulePatientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SchedulePatientResource {

    private static final Logger logger = Logger.getLogger(SchedulePatientResource.class.getName());

    @Inject
    SchedulePatientRepository schedulePatientRepository;

    @GET
    @Path("/patient/{patient}/{year}")
    public SchedulePatientDTO getScheduleDTO(@PathParam("patient") String patient,@PathParam("year") Integer year){
        Integer validYear = year;
        if(year == null){
            validYear = LocalDate.now().getYear();
        }
        SchedulePatient schedule = schedulePatientRepository.getSchedulePatientfromYear(patient,validYear);
        SchedulePatientDTO schedulePatientDTO = null;
        if (schedule!=null){
            schedulePatientDTO = convertToDTO(schedule);
        }
        return schedulePatientDTO;
    }

    @POST
    @Path("/{schedule}")
    @Transactional
    public void createAppointment(@PathParam("schedule") String scheduleId,AppointmentDTO appointmentDTO){
        Appointment appointment = convertToEntityAppointment(appointmentDTO);
        SchedulePatient schedule;
        if(scheduleId != null){
            schedule = schedulePatientRepository.find("id= ?1",UUID.fromString(scheduleId)).firstResult();
        } else {
            schedule = new SchedulePatient();
            Patient patient = new Patient();
            patient.setId(appointmentDTO.getPatientId());
            schedule.setPatient(patient);
            schedule.setYearPeriod(LocalDate.now().getYear());
            schedulePatientRepository.persistAndFlush(schedule);
            schedule.setAppointments(new ArrayList<>());
        }
        schedule.addNewAppointment(appointment);
        schedulePatientRepository.update(schedule);
    }

    @PUT
    @Path("/{schedule}")
    @Transactional
    public void updateAppointment(@PathParam("schedule") String scheduleId, AppointmentDTO appointmentDTO){
        Appointment appointment = convertToEntityAppointment(appointmentDTO);
        SchedulePatient schedule = schedulePatientRepository.find("id= ?1",UUID.fromString(scheduleId)).firstResult();
        schedule.getAppointments().stream().forEach(a->{
            if(a.getId().equals(appointment.getId())){
                a.updateType(appointment.getType());
                a.updateScheduleGoogle(appointment.getIsScheduledGoogle());
                a.updateTeacher(appointment.getTeacherId());
                a.updateTherapist(appointment.getTherapistId());
                a.updateTitle(appointment.getTitle());
                a.updateStartTime(appointment.getTimeRange());
            }
        });
        schedule.markConflictedAppintments();
        schedulePatientRepository.update(schedule);
    }

    @DELETE
    @Path("/{schedule}/{appointment}")
    @Transactional
    public void deleteAppointment(@PathParam("schedule") String scheduleId,@PathParam("appointment") String appointmentId){
        SchedulePatient schedule = schedulePatientRepository.find("id= ?1",UUID.fromString(scheduleId)).firstResult();
        if(schedule.getAppointments().removeIf(a->a.getId().equals(UUID.fromString(appointmentId)))){
            schedulePatientRepository.update(schedule);
            logger.info("Cita eliminada:"+appointmentId);
        }

    }

    private SchedulePatientDTO convertToDTO(SchedulePatient schedule){
        SchedulePatientDTO scheduleDTO = new SchedulePatientDTO();
        scheduleDTO.setScheduleId(schedule.getId().toString());
        scheduleDTO.setPatientId(schedule.getPatient().getId());
        scheduleDTO.setYear(schedule.getYearPeriod());
        scheduleDTO.setVersion(schedule.getVersion());
        List<AppointmentDTO> appointments = schedule.getAppointments().stream().map(s-> {
            AppointmentDTO appointment = new AppointmentDTO();
            appointment.setId(s.getId().toString());
            appointment.setAppointmentType(s.getType().getValue());
            appointment.setTitle(s.getTitle());
            appointment.setTeacherId(s.getTeacherId());
            appointment.setTherapistId(s.getTherapistId());
            appointment.setStart(s.getTimeRange().getStart());
            appointment.setEnd(s.getTimeRange().getEnd());
            if (s.getIsConflicted().getValue().equals(YesNoType.YES.getValue())) {
                appointment.setConflicted(true);
            } else {
                appointment.setConflicted(false);
            }
            if(s.getIsScheduledGoogle().getValue().equals(YesNoType.YES.getValue())){
                appointment.setScheduleGoogle(true);
            } else {
                appointment.setScheduleGoogle(false);
            }
            return appointment;
        }).toList();
        scheduleDTO.setAppointments(appointments);
        return  scheduleDTO;
    }

    private SchedulePatient convertToEntity(SchedulePatientDTO schedulePatientDTO){
        SchedulePatient schedule = new SchedulePatient();
        Patient patient = new Patient();
        patient.setId(schedulePatientDTO.getPatientId());
        schedule.setPatient(patient);
        schedule.setYearPeriod(schedulePatientDTO.getYear());
        schedule.setId(schedulePatientDTO.getScheduleId()!=null? UUID.fromString(schedulePatientDTO.getScheduleId()):null);
        schedule.setVersion(schedulePatientDTO.getVersion());
        List<Appointment> appointments = schedulePatientDTO.getAppointments().stream().map(s->{
            var appointment = new Appointment();
            if(s.getId()!=null){
                appointment.setId(UUID.fromString(s.getId()));
            }
            appointment.setTitle(s.getTitle());
            appointment.setType(AppointmentType.of(s.getAppointmentType()));
            appointment.setTeacherId(s.getTeacherId());
            appointment.setTherapistId(s.getTherapistId());
            DateTimeRange timeRange = new DateTimeRange(s.getStart(),s.getEnd());
            appointment.setTimeRange(timeRange);
            appointment.setIsConflicted(s.isConflicted()?YesNoType.YES:YesNoType.NO);
            appointment.setIsScheduledGoogle(s.isScheduleGoogle()?YesNoType.YES:YesNoType.NO);
            return appointment;
        }).toList();
        schedule.setAppointments(appointments);
        return schedule;
    }

    private Appointment convertToEntityAppointment(AppointmentDTO appointmentDTO){
        Appointment appointment = new Appointment();
        if(appointmentDTO.getId()!=null){
            appointment.setId(UUID.fromString(appointmentDTO.getId()));
        }
        appointment.setTitle(appointmentDTO.getTitle());
        appointment.setType(AppointmentType.of(appointmentDTO.getAppointmentType()));
        appointment.setTeacherId(appointmentDTO.getTeacherId());
        appointment.setTherapistId(appointmentDTO.getTherapistId());
        DateTimeRange timeRange = new DateTimeRange(appointmentDTO.getStart(),appointmentDTO.getEnd());
        appointment.setTimeRange(timeRange);
        appointment.setIsConflicted(appointmentDTO.isConflicted()?YesNoType.YES:YesNoType.NO);
        appointment.setIsScheduledGoogle(appointmentDTO.isScheduleGoogle()?YesNoType.YES:YesNoType.NO);
        return  appointment;
    }
}
