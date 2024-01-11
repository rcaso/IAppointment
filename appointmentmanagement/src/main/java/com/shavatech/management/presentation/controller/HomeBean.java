package com.shavatech.management.presentation.controller;

import com.shavatech.management.infraestructure.google.DateTimeEvent;
import com.shavatech.management.infraestructure.google.Event;
import com.shavatech.management.infraestructure.google.GoogleCalendarClient;
import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.RefreshToken;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ViewScoped
@Named
public class HomeBean implements Serializable {

    /**
     * Injection point for the ID Token issued by the OpenID Connect Provider
     */
    @Inject
    @IdToken
    JsonWebToken idToken;

    /**
     * Injection point for the Access Token issued by the OpenID Connect Provider
     */
    @Inject
    AccessTokenCredential accessToken;

    /**
     * Injection point for the Refresh Token issued by the OpenID Connect Provider
     */
    @Inject
    RefreshToken refreshToken;

    @Inject
    @RestClient
    GoogleCalendarClient googleCalendarClient;

    private List<Event> events;

    private String userName;

    private String scopes;

    private String refreshTokenLabel;

    public void loadHomePage(){
        Optional idTokeOption = Optional.ofNullable(idToken.getName());
        if(idTokeOption.isPresent()){
            userName = idTokeOption.get().toString();
        }
        Optional scope = Optional.ofNullable(accessToken.getToken());
        if (scope.isPresent()){
            scopes = scope.get().toString();
        }
        refreshTokenLabel = refreshToken.getToken();
        //ZonedDateTime now = ZonedDateTime.of(LocalDate.of(2022,12,31), LocalTime.of(23,59,59), ZoneId.of("-05:00"));
        var year =LocalDate.now().getYear()-1;
        JsonObject calendar = googleCalendarClient.list(year+"-12-31T23:59:59-05:00");
        events = new ArrayList<>();
        if (calendar != null){
            JsonArray eventResult = calendar.getJsonArray("items");
            if(eventResult !=null){
                for (int i = 0; i < eventResult.size(); i++) {
                    JsonObject  currentvent = eventResult.getJsonObject(i);
                    Event event = new Event();
                    event.id = currentvent.getString("id");
                    event.summary = currentvent.getString("summary");
                    JsonObject jstart = currentvent.getJsonObject("start");
                    DateTimeEvent start = new DateTimeEvent();
                    if(jstart!=null && jstart.containsKey("dateTime")){
                        start.dateTime=jstart.getString("dateTime");
                    } else {
                        start.dateTime=jstart.getString("date");
                    }
                    JsonObject jend = currentvent.getJsonObject("end");
                    DateTimeEvent end = new DateTimeEvent();
                    if(jend!=null && jend.containsKey("dateTime")){
                        end.dateTime=jend.getString("dateTime");
                    } else {
                        end.dateTime=jend.getString("date");
                    }
                    event.start=start;
                    event.end=end;
                    events.add(event);
                }
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getScopes() {
        return scopes;
    }

    public String getRefreshTokenLabel() {
        return refreshTokenLabel;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
