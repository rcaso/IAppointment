package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.Doctor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepository<Doctor> {


    public void saveOrUpdate(Doctor doctor) {
        getEntityManager().merge(doctor);
    }
}
