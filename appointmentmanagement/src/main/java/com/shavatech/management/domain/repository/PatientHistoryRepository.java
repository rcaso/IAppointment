package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.PatientHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientHistoryRepository implements PanacheRepository<PatientHistory> {

    public PatientHistory findByPatientId(String patientId) {
        return find("patient.id", patientId).firstResult();
    }

    public void save(PatientHistory patientHistory){
        persist(patientHistory);
    }

    public  void update(PatientHistory patientHistory){
        getEntityManager().merge(patientHistory);
    }
}
