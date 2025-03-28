package com.shavatech.domain.management.control;

import com.shavatech.domain.management.boundary.DoctorResource;
import com.shavatech.domain.management.boundary.PatientResource;
import com.shavatech.domain.management.boundary.TeacherResource;
import com.shavatech.domain.management.boundary.TherapistResource;
import com.shavatech.domain.management.entity.BackEndCommand;
import io.quarkus.websockets.next.WebSocketClientConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class DispatchController {

    private static final Logger LOG = Logger.getLogger(DispatchController.class.getName());

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
                        if(connection!= null && connection.isOpen()){
                            connection.sendTextAndAwait(command);
                        } else {
                            LOG.log(Level.SEVERE, "No se puede enviar listado de doctores porque la conexion esta cerrada");
                        }

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
                        if(connection!= null && connection.isOpen()){
                            connection.sendTextAndAwait(command);
                        } else {
                            LOG.log(Level.SEVERE,"No se puede enviar listado de patients porque la conexion esta cerrada");
                        }

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
                        if(connection!= null && connection.isOpen()){
                            connection.sendTextAndAwait(command);
                        } else  {
                            LOG.log(Level.SEVERE,"No se puede enviar los teachers porque la conexion esta cerrada");
                        }
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
                        if(connection!= null && connection.isOpen()){
                            connection.sendTextAndAwait(command);
                        } else  {
                            LOG.log(Level.SEVERE,"No se puede enviar listado de therapists porque la conexion esta cerrada");
                        }
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
