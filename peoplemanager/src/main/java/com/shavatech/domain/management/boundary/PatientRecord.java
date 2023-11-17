package com.shavatech.domain.management.boundary;

import java.time.LocalDate;

public record PatientRecord(String id, String name, String lastName, LocalDate birthDay) {
}
