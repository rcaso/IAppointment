package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.Doctor;
import com.shavatech.management.domain.entity.Patient;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {
    public void saveOrUpdate(Patient patient) {
        getEntityManager().merge(patient);
    }
}
