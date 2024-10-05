package com.shavatech.management.presentation.controller;

import com.shavatech.management.application.dto.DiagnosisDTO;
import com.shavatech.management.application.dto.PatientHistoryDTO;
import com.shavatech.management.application.dto.PrescriptionDTO;
import com.shavatech.management.application.service.PatientHistoryResource;
import com.shavatech.management.application.service.PeopleResource;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@ViewScoped
@Named
public class PatientHistoryBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(PatientHistoryBean.class);
    @Inject
    PatientHistoryResource patientHistoryResource;

    @Inject
    PeopleResource peopleResource;

    private PatientHistoryDTO patientHistoryDTO;

    private DiagnosisDTO currentDiagnosisDTO;

    private Map<String,String> patients;

    private Map<String,String> doctors;

    private String diagnosisId;

    private String patientId;

    @PostConstruct
    public void initHistories() {
        diagnosisId = null;
        patientId=null;
        patients = peopleResource.getPatients();
        doctors = peopleResource.getDoctors();
        patientHistoryDTO = new PatientHistoryDTO();
    }

    public void initDiagnosis() {
        currentDiagnosisDTO = new DiagnosisDTO();
        patientHistoryDTO = patientHistoryResource.getPatientHistory(patientId);
        doctors = peopleResource.getDoctors();
        patients = peopleResource.getPatients();
        if(diagnosisId != null){
            //Modficacion de algun diagnostico
            patientHistoryDTO.getDiagnosis().forEach(d -> {
                if(d.getId().equals(diagnosisId)){
                    currentDiagnosisDTO = d;
                }
            });
        } else {
            //Si es nuevo diagnostico hay que referencial al historial
            if(patientHistoryDTO != null) {
                currentDiagnosisDTO.setHistoryId(patientHistoryDTO.getId());
            }

            currentDiagnosisDTO.setPrescriptions(new ArrayList<PrescriptionDTO>());
        }
    }

    public void onAddNewPrescription(){
        currentDiagnosisDTO.getPrescriptions().add(new PrescriptionDTO());
    }

    public String saveDiagnosis(){
        if (currentDiagnosisDTO.getId()==null){
            //nuevo
            patientHistoryResource.createDiagnosis(patientId,currentDiagnosisDTO);
        } else {
            // modificar
            patientHistoryResource.updateDiagnosis(patientId, currentDiagnosisDTO);
        }
        return "/secure/histories?faces-redirect=true";
    }


    public void searchPatientHistory() {
        if (patientHistoryDTO.getPatientId() != null && !patientHistoryDTO.getPatientId().isEmpty()) {
            var history = patientHistoryResource.getPatientHistory(patientHistoryDTO.getPatientId());
            if (history != null) {
                patientHistoryDTO.setId(history.getId());
                patientHistoryDTO.setVersion(history.getVersion());
                patientHistoryDTO.setDiagnosis(history.getDiagnosis());
                patientHistoryDTO.getDiagnosis().forEach(d-> {
                    doctors.forEach((k,v)->{
                        if (v.equals(d.getDoctorId())){
                            d.setDoctorName(k);
                        }
                    });
                });
            } else {
                patientHistoryDTO.setDiagnosis(null);
                patientHistoryDTO.setId(null);
            }
        } else {
            patientHistoryDTO.setDiagnosis(null);
            patientHistoryDTO.setId(null);
        }
    }


    public Map<String, String> getPatients() {
        return patients;
    }

    public void setPatients(Map<String, String> patients) {
        this.patients = patients;
    }

    public PatientHistoryDTO getPatientHistoryDTO() {
        return patientHistoryDTO;
    }

    public void setPatientHistoryDTO(PatientHistoryDTO patientHistoryDTO) {
        this.patientHistoryDTO = patientHistoryDTO;
    }

    public DiagnosisDTO getCurrentDiagnosisDTO() {
        return currentDiagnosisDTO;
    }

    public void setCurrentDiagnosisDTO(DiagnosisDTO currentDiagnosisDTO) {
        this.currentDiagnosisDTO = currentDiagnosisDTO;
    }

    public Map<String, String> getDoctors() {
        return doctors;
    }

    public void setDoctors(Map<String, String> doctors) {
        this.doctors = doctors;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
