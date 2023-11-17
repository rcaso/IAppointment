package com.shavatech.domain.management.boundary;

import com.shavatech.domain.management.control.RecordController;
import com.shavatech.domain.management.entity.Doctor;
import com.shavatech.domain.management.entity.DoctorCreatedEvent;
import com.shavatech.domain.management.entity.DoctorUpdatedEvent;
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
@Path("doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @Inject
    RecordController recordController;

    @Inject
    Event<DoctorCreatedEvent> createdEvent;

    @Inject
    Event<DoctorUpdatedEvent> updatedEvent;


    @GET
    public List<DoctorRecord> getAll(){
       return  recordController.getAllDoctor2Record(Doctor.listAll(Sort.by("firstName")));
    }

    @Transactional
    @POST
    public void createDoctor(DoctorRecord doctor) {
    var doc = recordController.getDoctorFromRecord(doctor);
    doc.persist();
    createdEvent.fire(new DoctorCreatedEvent(doc));
    }

    @Transactional
    @PUT
    public void  updateDoctor(DoctorRecord doctor){
        Doctor entity = Doctor.findById(UUID.fromString(doctor.id()));
        entity.firstName = doctor.name();
        entity.lastName = doctor.lastName();
        entity.persist();
        updatedEvent.fire(new DoctorUpdatedEvent(entity));
    }
}
