package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.Appointment;
import com.shavatech.management.domain.entity.SchedulePatient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@ApplicationScoped
public class SchedulePatientRepository implements PanacheRepository<SchedulePatient> {

    public SchedulePatient getSchedulePatientfromYear(String patient, Integer year){
        return find("patient.id=:patient and yearPeriod=:year", Parameters.with("patient",patient).and("year",year).map()).firstResult();
    }

    public void save(SchedulePatient schedule){
        persist(schedule);
    }

    public  void update(SchedulePatient schedule){
        getEntityManager().merge(schedule);
    }

    public PanacheQuery<AppointmentIdProjection> getAppointmentByTitle(String title, UUID schedulePatientId){
        return find("from Appointment a where a.title=:title and a.schedulePatient.id=:id",Parameters.with("title",title).and("id",schedulePatientId)).project(AppointmentIdProjection.class);
    }

}
