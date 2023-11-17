package com.shavatech.domain.management.control;

import com.shavatech.domain.management.entity.*;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class IntegrationEventHandler {

    @Channel("people-sync")
    Emitter<JsonObject> emitter;

    @Inject
    RecordController recordController;

    public void onCreatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS)DoctorCreatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onUpdatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS)DoctorUpdatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onCreatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) PatientCreatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onUpdatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) PatientUpdatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onCreatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) TeacherCreatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onUpdatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) TeacherUpdatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onCreatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) TherapistCreatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }

    public void onUpdatedEvent(@Observes(during = TransactionPhase.AFTER_SUCCESS) TherapistUpdatedEvent event){
        JsonObject object = recordController.getJsonFromEvent(event);
        emitter.send(object);
    }
}
