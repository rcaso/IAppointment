package com.shavatech.management.infraestructure.messaging;

import com.shavatech.management.domain.entity.*;
import com.shavatech.management.domain.repository.DoctorRepository;
import com.shavatech.management.domain.repository.PatientRepository;
import com.shavatech.management.domain.repository.TeacherRepository;
import com.shavatech.management.domain.repository.TherapistRepository;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class IncomingProcessor {

    @Inject
    DoctorRepository doctorRepository;
    @Inject
    PatientRepository patientRepository;
    @Inject
    TeacherRepository teacherRepository;
    @Inject
    TherapistRepository therapistRepository;

    @Incoming("people-sync")
    @Transactional
    public void syncpeople(JsonObject event){
        var peopleEvent =  event.mapTo(PeopleEvent.class);
        PeopleEventType people = PeopleEventType.getOfValue(peopleEvent.eventType);
        switch (people){
            case DOCTOR_CREATED, DOCTOR_UPDATED -> {
                Doctor doctor = new Doctor();
                doctor.setId(peopleEvent.id);
                doctor.setFullName(peopleEvent.fullName);
                doctor.setLastUpdated(peopleEvent.dateOccurred);
                doctorRepository.saveOrUpdate(doctor);
            }
            case PATIENT_CREATED, PATIENT_UPDATED -> {
                Patient patient = new Patient();
                patient.setId(peopleEvent.id);
                patient.setFullName(peopleEvent.fullName);
                patient.setLastUpdated(peopleEvent.dateOccurred);
                patientRepository.saveOrUpdate(patient);
            }
            case TEACHER_CREATED, TEACHER_UPDATED -> {
                Teacher teacher = new Teacher();
                teacher.setId(peopleEvent.id);
                teacher.setFullName(peopleEvent.fullName);
                teacher.setLastUpdated(peopleEvent.dateOccurred);
                teacherRepository.saveOrUpdate(teacher);
            }
            case THERAPIST_CREATED, THERAPIST_UPDATED -> {
                Therapist therapist = new Therapist();
                therapist.setId(peopleEvent.id);
                therapist.setFullName(peopleEvent.fullName);
                therapist.setLastUpdated(peopleEvent.dateOccurred);
                therapistRepository.saveOrUpdate(therapist);
            }
        }
    }
}
