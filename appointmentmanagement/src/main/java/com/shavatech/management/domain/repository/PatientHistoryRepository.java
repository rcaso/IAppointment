package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.PatientHistory;
import com.shavatech.management.domain.entity.Prescription;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PatientHistoryRepository implements PanacheRepository<PatientHistory> {

    public PatientHistory findByPatientId(String patientId) {
        return find("patient.id", patientId).firstResult();
    }

    public PatientHistory findByPatientIdJPQL(String patientId) {
        StringBuilder sQueryBuilder = new StringBuilder("select p from PatientHistory p ");
        sQueryBuilder.append(" where p.patient.id = :patientId ");
        var query = getEntityManager().createQuery(sQueryBuilder.toString(), PatientHistory.class);
        query.setParameter("patientId", patientId);
        query.setHint("jakarta.persistence.fetchgraph", getEntityManager().createEntityGraph("PatientHistory.diagnoses"));
        var rows = query.getResultList();
        if (!rows.isEmpty()){
            var patientHistory = rows.get(0);
            getEntityManager().detach(patientHistory);
            if (patientHistory.getDiagnoses() != null && !patientHistory.getDiagnoses().isEmpty()){
                List<String> diagnoses = patientHistory.getDiagnoses().stream().map(d->d.getId().toString()).toList();
                var prescriptionsList  = getEntityManager().createQuery("select p from Prescription p where p.diagnosis.id in :diagnoses",Prescription.class)
                .setParameter("diagnoses", diagnoses).getResultList();
                patientHistory.getDiagnoses().forEach(d-> {
                    d.setPrescriptions(new ArrayList<>());
                    prescriptionsList.forEach(p-> {
                       if(p.getDiagnosis().getId().equals(d.getId())){
                            d.addNewPrescription(p);
                       }
                    });
                });
            }
            return patientHistory;
        } else {
            return null;
        }
    }

    public void save(PatientHistory patientHistory){
        persist(patientHistory);
    }

    public  void update(PatientHistory patientHistory){
        getEntityManager().merge(patientHistory);
    }
}
