package com.shavatech.management.infraestructure.websocket;

import com.shavatech.management.application.service.PatientHistoryResource;
import com.shavatech.management.application.service.PeopleResource;
import com.shavatech.management.application.service.SchedulePatientResource;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.context.api.NamedInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ThreadContext;

@ApplicationScoped
public class DispatchController {


    @Inject
    PatientHistoryResource patientHistoryResource;

    @Inject
    PeopleResource peopleResource;

    @Inject
    SchedulePatientResource schedulePatientResource;

    @Inject
    @NamedInstance("myContext")
    ThreadContext sameContext;

    public  void handleMessage(BackEndCommand command, WebSocketClientConnection connection) {
        switch (command.getCommandManagement()){
            case PEOPLE -> {
                switch (command.getCommandType()){
                    case LIST_PATIENTS -> {
                        command.setPeoples(peopleResource.getPatients());
                        connection.sendTextAndAwait(command);
                    }
                    case LIST_DOCTORS -> {
                        command.setPeoples(peopleResource.getDoctors());
                        connection.sendTextAndAwait(command);
                    }
                    case LIST_TEACHERS -> {
                        command.setPeoples(peopleResource.getTeachers());
                        connection.sendTextAndAwait(command);
                    }
                    case  LIST_THERAPISTS -> {
                        command.setPeoples(peopleResource.getTherapists());
                        connection.sendTextAndAwait(command);
                    }
                }
            }
            case PATIENT -> {
                switch (command.getCommandType()){
                    case LIST_PATIENT_HISTORY -> {
                        command.setPatientHistoryDTO(patientHistoryResource.getPatientHistory(command.getPatientId()));
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE_DIAGNOSIS -> {
                        patientHistoryResource.createDiagnosis(command.getPatientId(),command.getDiagnosisDTO());
                    }
                    case UPDATE_DIAGNOSIS -> {
                        patientHistoryResource.updateDiagnosis(command.getPatientId(),command.getDiagnosisDTO());
                    }
                }
            }
            case SCHEDULE -> {
                switch (command.getCommandType()){
                    case LIST_SCHEDULES -> {
                        command.setSchedulePatientDTO(schedulePatientResource.getScheduleDTO(command.getPatientId(),command.getYear()));
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE_APPOINTMENT -> {
                        //propagar el accessToken
                        ThreadLocalContextHolder.put("accessToken","Bearer " + command.getAccessToken());
                        schedulePatientResource.createAppointment(command.getScheduleId(),command.getAppointmentDTO());
                    }
                    case UPDATE_APPOINTMENT -> {
                        //propagar el accessToken
                        ThreadLocalContextHolder.put("accessToken", "Bearer " + command.getAccessToken());
                        schedulePatientResource.updateAppointment(command.getScheduleId(),command.getAppointmentDTO());
                    }

                    case DELETE_APPOINTMENT -> {
                        //propagar el accessToken
                        ThreadLocalContextHolder.put("accessToken", "Bearer " + command.getAccessToken());
                        schedulePatientResource.deleteAppointment(command.getScheduleId(),command.getAppointmentId());
                    }
                }
            }

        }
    }
}
