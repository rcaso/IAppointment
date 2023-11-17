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
@Path("teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    @Inject
    RecordController recordController;

    @Inject
    Event<IntegrationEvent> integrationEventEvent;

    @GET
    public List<TeacherRecord> getAll(){
       return  recordController.getAllTeacher(Teacher.listAll(Sort.by("firstName")));
    }

    @Transactional
    @POST
    public void createTeacher(TeacherRecord teacherRecord) {
    var teacher = recordController.getTeacherFromRecord(teacherRecord);
    teacher.persist();
    integrationEventEvent.fire(new TeacherCreatedEvent(teacher));
    }

    @Transactional
    @PUT
    public void  updateTeacher(TeacherRecord teacher){
        Teacher entity = Teacher.findById(UUID.fromString(teacher.id()));
        entity.firstName = teacher.name();
        entity.lastName = teacher.lastName();
        entity.persist();
        integrationEventEvent.fire(new TeacherUpdatedEvent(entity));
    }
}
