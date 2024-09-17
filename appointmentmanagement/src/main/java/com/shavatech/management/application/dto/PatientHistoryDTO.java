package com.shavatech.management.application.dto;

import java.io.Serializable;
import java.util.List;

public class PatientHistoryDTO implements Serializable {

    private String id;

    private String patientId;

    private List<DiagnosisDTO> diagnosis;

    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<DiagnosisDTO> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<DiagnosisDTO> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
