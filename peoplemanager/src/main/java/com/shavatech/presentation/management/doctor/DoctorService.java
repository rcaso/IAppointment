package com.shavatech.presentation.management.doctor;

import com.shavatech.domain.management.boundary.DoctorRecord;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("doctors")
@RegisterRestClient
public interface DoctorService {

    @GET
    public List<DoctorRecord> getAll();

    @POST
    public void createDoctor(DoctorRecord doctor);

    @PUT
    public void updateDoctor(DoctorRecord doctor);
}
