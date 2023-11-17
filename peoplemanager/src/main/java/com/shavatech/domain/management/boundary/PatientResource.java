package com.shavatech.domain.management.boundary;

import com.shavatech.domain.IntegrationEvent;
import com.shavatech.domain.management.control.RecordController;
import com.shavatech.domain.management.entity.*;
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
@Path("patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    RecordController recordController;

    @Inject
    Event<IntegrationEvent> integrationEventEvent;

    @GET
    public List<PatientRecord> getAll(){
       return  recordController.getAllPatients(Patient.listAll(Sort.by("firstName")));
    }

    @Transactional
    @POST
    public void createPatient(PatientRecord patientRecord) {
    var patient = recordController.getPatientFromRecord(patientRecord);
    patient.persist();
    integrationEventEvent.fire(new PatientCreatedEvent(patient));
    }

    @Transactional
    @PUT
    public void  updatePatient(PatientRecord patient){
        Patient entity = Patient.findById(UUID.fromString(patient.id()));
        entity.firstName = patient.name();
        entity.lastName = patient.lastName();
        entity.birthDay = patient.birthDay();
        entity.persist();
        integrationEventEvent.fire(new PatientUpdatedEvent(entity));
    }
}
