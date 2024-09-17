package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Diagnosis;

public class DiagnosisRegisteredEvent extends DomainEvent {
    private Diagnosis diagnosisRegistered;

    public DiagnosisRegisteredEvent(Diagnosis diagnosisRegistered) {
        this.diagnosisRegistered = diagnosisRegistered;
    }

    public Diagnosis getDiagnosisRegistered() {
        return diagnosisRegistered;
    }


}
