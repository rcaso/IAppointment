package com.shavatech.management.application.service;

import com.shavatech.management.application.dto.DiagnosisDTO;
import com.shavatech.management.application.dto.PatientHistoryDTO;
import com.shavatech.management.application.mapper.PatientHistoryMapper;
import com.shavatech.management.domain.entity.Diagnosis;
import com.shavatech.management.domain.entity.Patient;
import com.shavatech.management.domain.entity.PatientHistory;
import com.shavatech.management.domain.repository.PatientHistoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/patienthistories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientHistoryResource {

    private static final Logger logger = Logger.getLogger(PatientHistoryResource.class.getName());

    @Inject
    PatientHistoryRepository patientHistoryRepository;

    @Inject
    PatientHistoryMapper patientHistoryMapper;

    @GET
    @Path("/patient/{patient}")
    public PatientHistoryDTO getPatientHistory(@PathParam("patient") String patient){
        PatientHistoryDTO patientHistoryDTO = null;
        PatientHistory patientHistory = patientHistoryRepository.findByPatientId(patient);
        if (patientHistory != null){
            patientHistoryDTO = patientHistoryMapper.toDTO(patientHistory);
        }
        return patientHistoryDTO;
    }

    @POST
    @Path("/patient/{patient}")
    @Transactional
    public void createDiagnosis(@PathParam("patient") String patientId, DiagnosisDTO diagnosisDTO){
        Diagnosis diagnosis = patientHistoryMapper.fromDTO(diagnosisDTO);
        PatientHistory patientHistory;
        if(diagnosis.getPatientHistory()!= null){
            patientHistory = patientHistoryRepository.findByPatientId(patientId);
        } else {
            patientHistory = new PatientHistory();
            Patient patient = new Patient();
            patient.setId(patientId);
            patientHistory.setPatient(patient);
            patientHistoryRepository.persistAndFlush(patientHistory);
            patientHistory.setDiagnoses(new ArrayList<>());
        }
        patientHistory.addNewDiagnosis(diagnosis);
        patientHistoryRepository.update(patientHistory);
    }

    @PUT
    @Path("/patient/{patient}")
    @Transactional
    public void updateDiagnosis(@PathParam("patient") String patientId, DiagnosisDTO diagnosisDTO){
        Diagnosis diagnosis = patientHistoryMapper.fromDTO(diagnosisDTO);
        PatientHistory patientHistory = patientHistoryRepository.findByPatientId(patientId);
        patientHistory.getDiagnoses().forEach(d-> {
            if(d.getId().equals(diagnosis.getId())){
                d.updateDiagnosis(diagnosis);
            }
        });
        patientHistoryRepository.update(patientHistory);
    }


}
