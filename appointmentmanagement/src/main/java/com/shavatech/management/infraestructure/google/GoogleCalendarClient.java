package com.shavatech.management.infraestructure.google;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="google-calendar-api")
@Path("/calendars/primary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GoogleCalendarClient {
    @GET
    @Path("/events")
    public JsonObject list(@HeaderParam("Authorization") String authorization, @QueryParam("timeMin") String lowerDate);

    @POST
    @Path("/events")
    public JsonObject addEvent(@HeaderParam("Authorization") String authorization, @QueryParam("sendUpdates") String sendUpdates, Event event);

    @PUT
    @Path("/events/{eventId}")
    public JsonObject updateEvent(@HeaderParam("Authorization") String authorization, @PathParam("eventId") String eventId, Event event);

    @DELETE
    @Path("/events/{eventId}")
    public void  deleteEvent(@HeaderParam("Authorization") String authorization, @QueryParam("sendUpdates") String sendUpdates, @PathParam("eventId") String eventId);

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        return new WebApplicationException(response);
    }
}
