package com.shavatech.management.domain.repository;

import com.google.errorprone.annotations.Var;
import com.shavatech.management.domain.entity.PatientHistory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientHistoryRepository implements PanacheRepository<PatientHistory> {

    public PatientHistory findByPatientId(String patientId) {
        return find("patient.id", patientId).firstResult();
    }

    public PatientHistory findByPatientIdJPQL(String patientId) {
        StringBuilder sQueryBuilder = new StringBuilder("select distinct p from PatientHistory p JOIN FETCH p.diagnoses d  ");
        sQueryBuilder.append(" JOIN FETCH d.prescriptions pr where p.patient.id = :patientId ");
        var query = getEntityManager().createQuery(sQueryBuilder.toString(), PatientHistory.class);
        query.setParameter("patientId", patientId);
        var rows = query.getResultList();
        return (rows!= null && !rows.isEmpty())? rows.get(0):null;
        //return find("patient.id", patientId).firstResult();
    }

    public void save(PatientHistory patientHistory){
        persist(patientHistory);
    }

    public  void update(PatientHistory patientHistory){
        getEntityManager().merge(patientHistory);
    }
}
