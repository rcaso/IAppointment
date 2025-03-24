package com.shavatech.management.domain.entity;

import com.shavatech.domain.BaseEntity;
import com.shavatech.management.domain.events.DiagnosisUpdatedEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Cacheable
public class Diagnosis extends BaseEntity {

    @Column(length = 200)
    private String description;

    @Column(name = "diagnosis_date")
    private LocalDateTime diagnosisDate;

    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "appointment_id")
    private String appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private PatientHistory patientHistory;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "diagnosis")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Prescription> prescriptions;

    public void updateDiagnosis(Diagnosis diagnosis) {
        this.description = diagnosis.getDescription();
        this.diagnosisDate = diagnosis.getDiagnosisDate();
        this.doctorId = diagnosis.getDoctorId();
        this.appointmentId = diagnosis.getAppointmentId();
        updatePrescriptions(diagnosis.getPrescriptions());
        getEvents().add(new DiagnosisUpdatedEvent(this));
    }

    public void updatePrescriptions(Set<Prescription> prescriptions) {
        List<Prescription> actualizar = prescriptions.stream().filter(p-> p.getId()!=null).toList();
        List<Prescription> nuevos = prescriptions.stream().filter(p-> p.getId() == null).toList();
        this.prescriptions.forEach(prescription -> {
            actualizar.forEach(a->{
                if(a.getId()!=null && a.getId().equals(prescription.getId())){
                    prescription.setDrug(a.getDrug());
                    prescription.setDose(a.getDose());
                    prescription.setFrequency(a.getFrequency());
                    prescription.setDuration(a.getDuration());
                }
            });
        });
        nuevos.forEach(this::addNewPrescription);
    }

    public void addNewPrescription(Prescription prescription) {
        prescription.setDiagnosis(this);
        prescriptions.add(prescription);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }
}
