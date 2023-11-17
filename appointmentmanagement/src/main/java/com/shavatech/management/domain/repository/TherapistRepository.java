package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.Doctor;
import com.shavatech.management.domain.entity.Therapist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TherapistRepository implements PanacheRepository<Therapist> {
    public void saveOrUpdate(Therapist therapist) {
        getEntityManager().merge(therapist);
    }
}
