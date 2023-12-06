package com.shavatech.infraestructure;

import com.shavatech.domain.BaseEntity;
import com.shavatech.domain.DomainEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@ApplicationScoped
public class AuditFieldListener {

    @Inject
    Event<DomainEvent> domainEvent;

    private static final Logger logger = Logger.getLogger(AuditFieldListener.class.getName());
    @PrePersist void onPrePersist(BaseEntity o) {
        logger.info("Before persisting set createdDate"+o);
        o.setCreatedDate(LocalDateTime.now());
    }
    @PostPersist void onPostPersist(BaseEntity o) {
        logger.info("After persisting fire event on "+o);
        if(o.getEvents()!=null){
            o.getEvents().forEach(event -> {
                domainEvent.fire(event);
            });
        }

    }
    @PostLoad
    void onPostLoad(BaseEntity o) {
        logger.info("After Load "+o);
    }
    @PreUpdate
    void onPreUpdate(BaseEntity o) {
        logger.info("Before update set modifiedDate"+o);
        o.setModifiedDate(LocalDateTime.now());
    }
    @PostUpdate
    void onPostUpdate(BaseEntity o) {
        logger.info("After update fire event on "+o);
        if(o.getEvents()!=null){
            o.getEvents().forEach(event -> {
                domainEvent.fire(event);
            });
        }
    }
    @PreRemove
    void onPreRemove(BaseEntity o) {
        logger.info("Before remove "+o);
    }
    @PostRemove
    void onPostRemove(BaseEntity o) {
        logger.info("After remove "+o);
    }
}
