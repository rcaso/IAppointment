package com.shavatech.presentation.management.patient;

import com.shavatech.domain.management.boundary.PatientRecord;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("patients")
@RegisterRestClient
public interface PatientService {

    @GET
    public List<PatientRecord> getAll();

    @POST
    public void createPatient(PatientRecord patient);

    @PUT
    public void updatePatient(PatientRecord patient);
}
