package com.shavatech.domain.management.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Doctor extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    public static List<Doctor>  listAll(){
        return listAll();
    }
}
