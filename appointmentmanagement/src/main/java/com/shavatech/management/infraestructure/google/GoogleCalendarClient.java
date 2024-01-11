package com.shavatech.management.infraestructure.google;

import io.quarkus.oidc.token.propagation.AccessToken;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="google-calendar-api")
@AccessToken
@Path("/calendars/primary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GoogleCalendarClient {
    @GET
    @Path("/events")
    public JsonObject list(@QueryParam("timeMin") String lowerDate);

    @POST
    @Path("/events")
    public JsonObject addEvent(@QueryParam("sendUpdates") String sendUpdates, Event event);

    @PUT
    @Path("/events/{eventId}")
    public JsonObject updateEvent(@PathParam("eventId") String eventId, Event event);

    @DELETE
    @Path("/events/{eventId}")
    public void  deleteEvent(@QueryParam("sendUpdates") String sendUpdates, @PathParam("eventId") String eventId);

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        return new WebApplicationException(response);
    }
}
