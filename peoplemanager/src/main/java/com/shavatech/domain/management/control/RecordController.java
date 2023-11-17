package com.shavatech.domain.management.control;

import com.shavatech.domain.IntegrationEvent;
import com.shavatech.domain.management.boundary.DoctorRecord;
import com.shavatech.domain.management.boundary.PatientRecord;
import com.shavatech.domain.management.boundary.TeacherRecord;
import com.shavatech.domain.management.boundary.TherapistRecord;
import com.shavatech.domain.management.entity.*;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;



import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RecordController {

    public List<DoctorRecord> getAllDoctor2Record(List<Doctor> doctors){
        return doctors.stream().map(d->new DoctorRecord(d.id.toString(),d.firstName,d.lastName)).toList();
    }

    public List<PatientRecord> getAllPatients(List<Patient> patients){
        return patients.stream().map(p -> new PatientRecord(p.id.toString(),p.firstName,p.lastName,p.birthDay)).toList();
    }

    public List<TherapistRecord> getAllTherapist(List<Therapist> therapists){
        return therapists.stream().map(t-> new TherapistRecord(t.id.toString(),t.firstName,t.lastName)).toList();
    }

    public List<TeacherRecord> getAllTeacher(List<Teacher> teachers){
        return teachers.stream().map(t-> new TeacherRecord(t.id.toString(),t.firstName,t.lastName)).toList();
    }

    public Doctor getDoctorFromRecord(DoctorRecord record){
        var doctor = new Doctor();
        if (record.id()!= null && !record.id().isBlank()){
            doctor.id= UUID.fromString(record.id());
        }
        doctor.firstName=record.name();
        doctor.lastName=record.lastName();
        return doctor;
    }

    public Teacher getTeacherFromRecord(TeacherRecord record){
        var teacher = new Teacher();
        if(record.id()!=null && !record.id().isBlank()){
            teacher.id = UUID.fromString(record.id());
        }
        teacher.firstName=record.name();
        teacher.lastName=record.lastName();
        return teacher;
    }

    public Therapist getTherapistFromRecord(TherapistRecord record){
        var therapist = new Therapist();
        if(record.id()!=null && !record.id().isBlank()){
            therapist.id = UUID.fromString(record.id());
        }
        therapist.firstName=record.name();
        therapist.lastName=record.lastName();
        return therapist;
    }

    public Patient getPatientFromRecord(PatientRecord record){
        var patient = new Patient();
        if(record.id()!=null && !record.id().isBlank()){
            patient.id = UUID.fromString(record.id());
        }
        patient.firstName=record.name();
        patient.lastName=record.lastName();
        patient.birthDay=record.birthDay();
        return patient;
    }

    public JsonObject getJsonFromEvent(IntegrationEvent event){
        JsonObject json = new JsonObject();
        if(event instanceof DoctorCreatedEvent domainEvent){
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  DoctorUpdatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  PatientCreatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  PatientUpdatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  TeacherCreatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  TeacherUpdatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  TherapistCreatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        } else if (event instanceof  TherapistUpdatedEvent domainEvent) {
            json.put("id",domainEvent.getId());
            json.put("fullName",domainEvent.getFullName());
            json.put("dateOccurred",domainEvent.getDateOccurred());
            json.put("eventType",domainEvent.getEventType());
        }
        return json;
    }
}
