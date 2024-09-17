package com.shavatech.management.application.mapper;

import com.shavatech.management.application.dto.DiagnosisDTO;
import com.shavatech.management.application.dto.PatientHistoryDTO;
import com.shavatech.management.application.dto.PrescriptionDTO;
import com.shavatech.management.domain.entity.Diagnosis;
import com.shavatech.management.domain.entity.PatientHistory;
import com.shavatech.management.domain.entity.Prescription;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.UUID;

@ApplicationScoped
public class PatientHistoryMapper implements Serializable {

    public PatientHistoryDTO toDTO(PatientHistory patientHistory) {
        PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO();
        patientHistoryDTO.setId(patientHistory.getId().toString());
        patientHistoryDTO.setVersion(patientHistory.getVersion());
        patientHistoryDTO.setPatientId(patientHistory.getPatient().getId());
        patientHistoryDTO.setDiagnosis(
            patientHistory.getDiagnoses().stream().map(d -> {
                DiagnosisDTO diagnosisDTO = new DiagnosisDTO();
                diagnosisDTO.setId(d.getId().toString());
                diagnosisDTO.setDiagnosisDate(d.getDiagnosisDate());
                diagnosisDTO.setDescription(d.getDescription());
                diagnosisDTO.setHistoryId(d.getPatientHistory().getId().toString());
                diagnosisDTO.setDoctorId(d.getDoctorId());
                diagnosisDTO.setAppointmentId(d.getAppointmentId());
                diagnosisDTO.setPrescriptions(d.getPrescriptions().stream().map(p->{
                    PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
                    prescriptionDTO.setId(p.getId().toString());
                    prescriptionDTO.setDiagnosisId(p.getDiagnosis().getId().toString());
                    prescriptionDTO.setDrug(p.getDrug());
                    prescriptionDTO.setDose(p.getDose());
                    prescriptionDTO.setFrequency(p.getFrequency());
                    prescriptionDTO.setDuration(p.getDuration());
                    return prescriptionDTO;
                }).toList());
                return diagnosisDTO;
            }).toList()
        );

        return patientHistoryDTO;
    }

    public PatientHistory fromDTO(PatientHistoryDTO patientHistoryDTO) {
        PatientHistory patientHistory = new PatientHistory();

        return patientHistory;
    }

    public Diagnosis fromDTO(DiagnosisDTO diagnosisDTO){
        Diagnosis diagnosis = new Diagnosis();
        if(diagnosisDTO.getId() != null){
            diagnosis.setId(UUID.fromString(diagnosisDTO.getId()));
        }
        diagnosis.setDiagnosisDate(diagnosisDTO.getDiagnosisDate());
        diagnosis.setDescription(diagnosisDTO.getDescription());
        if(diagnosisDTO.getHistoryId() != null){
            PatientHistory patientHistory = new PatientHistory();
            patientHistory.setId(UUID.fromString(diagnosisDTO.getHistoryId()));
            diagnosis.setPatientHistory(patientHistory);
        }
        diagnosis.setDoctorId(diagnosisDTO.getDoctorId());
        diagnosis.setAppointmentId(diagnosisDTO.getAppointmentId());
        diagnosis.setPrescriptions(diagnosisDTO.getPrescriptions().stream().map(p->{
            Prescription prescription = new Prescription();
            if(p.getId()!= null){
                prescription.setId(UUID.fromString(p.getId()));
            }
            prescription.setDiagnosis(diagnosis);
            prescription.setDrug(p.getDrug());
            prescription.setDose(p.getDose());
            prescription.setFrequency(p.getFrequency());
            prescription.setDuration(p.getDuration());
            return prescription;
        }).toList());
        return diagnosis;
    }
}
