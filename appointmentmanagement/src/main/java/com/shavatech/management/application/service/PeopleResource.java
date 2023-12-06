package com.shavatech.management.application.service;

import com.shavatech.management.domain.entity.Patient;
import com.shavatech.management.domain.entity.Teacher;
import com.shavatech.management.domain.entity.Therapist;
import com.shavatech.management.domain.repository.PatientRepository;
import com.shavatech.management.domain.repository.TeacherRepository;
import com.shavatech.management.domain.repository.TherapistRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Path("/peoples")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeopleResource {

    @Inject
    PatientRepository patientRepository;
    @Inject
    TeacherRepository teacherRepository;
    @Inject
    TherapistRepository therapistRepository;

    @Path("/patients")
    @GET
    public Map<String,String> getPatients(){
        List<Patient> patients = patientRepository.findAll().list();
        Map<String,String> patientMap = new HashMap<>();
        patients.forEach(p->{
            patientMap.put(p.getFullName(),p.getId());
        });
        return patientMap;
    }

    @Path("/teachers")
    @GET
    public Map<String,String> getTeachers(){
        List<Teacher> teachers = teacherRepository.findAll().list();
        Map<String,String> teacherMap = new HashMap<>();
        teachers.forEach(p->{
            teacherMap.put(p.getFullName(),p.getId());
        });
        return teacherMap;
    }

    @Path("/therapists")
    @GET
    public Map<String,String> getTherapists(){
        List<Therapist> therapists = therapistRepository.findAll().list();
        Map<String,String> therapistMap = new HashMap<>();
        therapists.forEach(p->{
            therapistMap.put(p.getFullName(),p.getId());
        });
        return therapistMap;
    }
}
