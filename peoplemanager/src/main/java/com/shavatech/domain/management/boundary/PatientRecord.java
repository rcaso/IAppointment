package com.shavatech.domain.management.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record PatientRecord(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("lastName") String lastName,@JsonProperty("birthDay") LocalDate birthDay) {
}
