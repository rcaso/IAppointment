package com.shavatech.domain.management.entity;

import com.shavatech.domain.management.boundary.DoctorRecord;
import com.shavatech.domain.management.boundary.PatientRecord;
import com.shavatech.domain.management.boundary.TeacherRecord;
import com.shavatech.domain.management.boundary.TherapistRecord;

import java.io.Serializable;
import java.util.List;

public class BackEndCommand implements Serializable {

    CommandManagement commandManagement;

    CommandType commandType;

    List<DoctorRecord> doctors;

    List<PatientRecord> patients;

    List<TherapistRecord> therapists;

    List<TeacherRecord> teachers;

    DoctorRecord doctor;

    PatientRecord patient;

    TherapistRecord therapist;

    TeacherRecord teacher;

    public BackEndCommand(CommandManagement commandManagement, CommandType commandType) {
        this.commandManagement = commandManagement;
        this.commandType = commandType;
    }

    public BackEndCommand(){

    }

    public DoctorRecord getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorRecord doctor) {
        this.doctor = doctor;
    }

    public PatientRecord getPatient() {
        return patient;
    }

    public void setPatient(PatientRecord patient) {
        this.patient = patient;
    }

    public TherapistRecord getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistRecord therapist) {
        this.therapist = therapist;
    }

    public TeacherRecord getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherRecord teacher) {
        this.teacher = teacher;
    }

    public List<DoctorRecord> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorRecord> doctors) {
        this.doctors = doctors;
    }

    public List<PatientRecord> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientRecord> patients) {
        this.patients = patients;
    }

    public List<TherapistRecord> getTherapists() {
        return therapists;
    }

    public void setTherapists(List<TherapistRecord> therapists) {
        this.therapists = therapists;
    }

    public List<TeacherRecord> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherRecord> teachers) {
        this.teachers = teachers;
    }

    public CommandManagement getCommandManagement() {
        return commandManagement;
    }

    public void setCommandManagement(CommandManagement commandManagement) {
        this.commandManagement = commandManagement;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }


}
