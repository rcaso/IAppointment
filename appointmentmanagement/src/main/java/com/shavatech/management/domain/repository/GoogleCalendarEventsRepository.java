package com.shavatech.management.domain.repository;

import com.shavatech.management.domain.entity.GoogleCalendarEvents;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GoogleCalendarEventsRepository implements PanacheRepository<GoogleCalendarEvents> {
    public GoogleCalendarEvents saveOrMerge(GoogleCalendarEvents events){
        return getEntityManager().merge(events);
    }

    public void delete(String idAppointment){
        delete("idAppointment",idAppointment);
    }

    public GoogleCalendarEvents getGoogleEventFromAppointment(String appointmentId){
        return find("idAppointment=:appointmentId", Parameters.with("appointmentId",appointmentId).map()).firstResult();
    }
}
