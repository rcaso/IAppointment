package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.Doctor;
import com.shavatech.management.domain.entity.Teacher;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeacherRepository implements PanacheRepository<Teacher> {

    public void saveOrUpdate(Teacher teacher) {
        getEntityManager().merge(teacher);
    }
}
