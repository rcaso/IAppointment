package com.shavatech.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract  class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modifiedDate;

    private String modifiedBy;

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
}
