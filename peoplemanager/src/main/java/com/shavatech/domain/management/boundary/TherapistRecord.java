package com.shavatech.domain.management.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TherapistRecord(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("lastName") String lastName) {
}
