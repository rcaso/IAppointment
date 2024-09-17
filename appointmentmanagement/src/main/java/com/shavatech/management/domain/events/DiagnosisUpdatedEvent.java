package com.shavatech.management.domain.events;

import com.shavatech.domain.DomainEvent;
import com.shavatech.management.domain.entity.Diagnosis;

public class DiagnosisUpdatedEvent extends DomainEvent {
    private Diagnosis diagnosisUpdated;

    public DiagnosisUpdatedEvent(Diagnosis diagnosisUpdated) {
        this.diagnosisUpdated = diagnosisUpdated;
    }

    public Diagnosis getDiagnosisUpdated() {
        return diagnosisUpdated;
    }


}
