package com.shavatech.domain;

import com.shavatech.infraestructure.AuditFieldListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditFieldListener.class)
public abstract  class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Transient
    private List<DomainEvent> events = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    public void setEvents(List<DomainEvent> events) {
        this.events = events;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
