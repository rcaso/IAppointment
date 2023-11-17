package com.shavatech.presentation.management.therapist;

import com.shavatech.domain.management.boundary.TherapistRecord;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("therapists")
@RegisterRestClient
public interface TherapistService {

    @GET
    public List<TherapistRecord> getAll();

    @POST
    public void createTherapist(TherapistRecord therapist);

    @PUT
    public void updateTherapist(TherapistRecord therapist);
}
