package com.shavatech.presentation.management.teacher;

import com.shavatech.domain.management.boundary.TeacherRecord;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("teachers")
@RegisterRestClient
public interface TeacherService {

    @GET
    public List<TeacherRecord> getAll();

    @POST
    public void createTeacher(TeacherRecord teacher);

    @PUT
    public void updateTeacher(TeacherRecord teacher);
}
