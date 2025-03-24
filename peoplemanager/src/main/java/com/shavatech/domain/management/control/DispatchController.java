package com.shavatech.domain.management.control;

import com.shavatech.domain.management.boundary.DoctorResource;
import com.shavatech.domain.management.boundary.PatientResource;
import com.shavatech.domain.management.boundary.TeacherResource;
import com.shavatech.domain.management.boundary.TherapistResource;
import com.shavatech.domain.management.entity.BackEndCommand;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DispatchController {


    @Inject
    DoctorResource doctorResource;

    @Inject
    PatientResource patientResource;

    @Inject
    TeacherResource teacherResource;

    @Inject
    TherapistResource therapistResource;

    public  void handleMessage(BackEndCommand command, WebSocketClientConnection connection) {
        switch (command.getCommandManagement()){
            case DOCTOR -> {
                switch (command.getCommandType()){
                    case LIST -> {
                        command.setDoctors(doctorResource.getAll());
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE -> {
                        doctorResource.createDoctor(command.getDoctor());
                    }
                    case UPDATE -> {
                        doctorResource.updateDoctor(command.getDoctor());
                    }
                }
            }
            case PATIENT -> {
                switch (command.getCommandType()){
                    case LIST -> {
                        command.setPatients(patientResource.getAll());
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE -> {
                        patientResource.createPatient(command.getPatient());
                    }
                    case UPDATE -> {
                        patientResource.updatePatient(command.getPatient());
                    }
                }
            }
            case TEACHER -> {
                switch (command.getCommandType()){
                    case LIST -> {
                        command.setTeachers(teacherResource.getAll());
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE -> {
                        teacherResource.createTeacher(command.getTeacher());
                    }
                    case UPDATE -> {
                        teacherResource.updateTeacher(command.getTeacher());
                    }
                }
            }
            case THERAPIST -> {
                switch (command.getCommandType()){
                    case LIST -> {
                        command.setTherapists(therapistResource.getAll());
                        connection.sendTextAndAwait(command);
                    }
                    case CREATE -> {
                        therapistResource.createTherapist(command.getTherapist());
                    }
                    case UPDATE -> {
                        therapistResource.updateTherapist(command.getTherapist());
                    }
                }
            }
        }
    }
}
