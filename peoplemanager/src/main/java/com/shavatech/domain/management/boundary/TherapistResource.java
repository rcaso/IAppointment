package com.shavatech.domain.management.boundary;

import com.shavatech.domain.IntegrationEvent;
import com.shavatech.domain.management.control.RecordController;
import com.shavatech.domain.management.entity.Teacher;
import com.shavatech.domain.management.entity.Therapist;
import com.shavatech.domain.management.entity.TherapistCreatedEvent;
import com.shavatech.domain.management.entity.TherapistUpdatedEvent;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("therapists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TherapistResource {

    @Inject
    RecordController recordController;

    @Inject
    Event<IntegrationEvent> integrationEventEvent;

    @GET
    public List<TherapistRecord> getAll(){
       return  recordController.getAllTherapist(Therapist.listAll(Sort.by("firstName")));
    }

    @Transactional
    @POST
    public void createTherapist(TherapistRecord therapistRecord) {
        var therapist = recordController.getTherapistFromRecord(therapistRecord);
        therapist.persist();
        integrationEventEvent.fire(new TherapistCreatedEvent(therapist));
    }

    @Transactional
    @PUT
    public void  updateTherapist(TherapistRecord therapist){
        Therapist entity = Therapist.findById(UUID.fromString(therapist.id()));
        entity.firstName = therapist.name();
        entity.lastName = therapist.lastName();
        entity.persist();
        integrationEventEvent.fire(new TherapistUpdatedEvent(entity));
    }

}
