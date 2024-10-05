package com.shavatech.management.domain.entity;

import com.shavatech.domain.AggregateRoot;
import com.shavatech.management.domain.events.AppointmentScheduledEvent;
import com.shavatech.management.domain.events.DiagnosisRegisteredEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Table(name = "patient_history")
@Cacheable
public class PatientHistory extends AggregateRoot {
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "patientHistory")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Diagnosis> diagnoses;

    public void addNewDiagnosis(Diagnosis diagnosis){
        diagnosis.setPatientHistory(this);
        diagnoses.add(diagnosis);
        getEvents().add(new DiagnosisRegisteredEvent(diagnosis));
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
