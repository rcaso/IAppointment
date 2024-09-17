package com.shavatech.management.infraestructure.handlers;

import com.shavatech.management.domain.entity.Diagnosis;
import com.shavatech.management.domain.events.DiagnosisRegisteredEvent;
import com.shavatech.management.domain.events.DiagnosisUpdatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;

import java.util.logging.Logger;

@ApplicationScoped
public class DiagnosisManagementHandler {

    private static final Logger logger = Logger.getLogger(DiagnosisManagementHandler.class.getName());

    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS) DiagnosisRegisteredEvent event) {
        Diagnosis diagnosis = event.getDiagnosisRegistered();
        logger.info("Se creo un diagnostico:"+diagnosis.getDescription());
    }

    public void handle(@Observes(during = TransactionPhase.AFTER_SUCCESS) DiagnosisUpdatedEvent event) {
        Diagnosis diagnosis = event.getDiagnosisUpdated();
        logger.info("Se actualizo diagnostico:"+diagnosis.getDescription());
    }
}
