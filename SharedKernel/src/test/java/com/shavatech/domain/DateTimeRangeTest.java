package com.shavatech.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeRangeTest {

    LocalDateTime testDateTime;
    @BeforeEach
    public  void init(){
        testDateTime = LocalDateTime.of(2023,10,25,15,0,0);
    }

    @Test
    void durationInMinutes() {
       var dateTimeRange = new DateTimeRange(testDateTime, Duration.ofMinutes(1));
       assertEquals(1,dateTimeRange.durationInMinutes());
    }

    @Test
    void newDuration() {
        var dtr = new DateTimeRange(testDateTime, Duration.ofHours(2));
        var newDtr = dtr.newDuration(Duration.ofHours(4));
        assertTrue(dtr.durationInMinutes() < newDtr.durationInMinutes());
    }

    @Test
    void newEnd() {
        var dtr = new DateTimeRange(testDateTime, Duration.ofHours(1));
        var newDtr = dtr.newEnd(testDateTime.plusHours(2));
        assertTrue(newDtr.getEnd().isAfter(dtr.getEnd()));
    }

    @Test
    void newStart() {
        var dtr = new DateTimeRange(testDateTime, Duration.ofMinutes(30));
        var newDtr = dtr.newStart(testDateTime.minusMinutes(30));
        assertEquals(60,newDtr.durationInMinutes());
    }

    @Test
    void overLapping() {
        var dtr = new DateTimeRange(testDateTime, Duration.ofMinutes(45));
        assertTrue(dtr.overLapping(dtr));
        var dtrStartOverlapping = new DateTimeRange(testDateTime.plusMinutes(15),Duration.ofMinutes(45));
        assertTrue(dtr.overLapping(dtrStartOverlapping));
        var dtrEndOverlapping = new DateTimeRange(testDateTime.minusMinutes(15),Duration.ofMinutes(15));
        assertFalse(dtr.overLapping(dtrEndOverlapping));
        var dtrOnlyEnd = new DateTimeRange(dtr.getEnd().minusMinutes(5),Duration.ofMinutes(30));
        assertTrue(dtr.overLapping(dtrOnlyEnd));
        var dtrNotOverlapping = new DateTimeRange(dtr.getEnd(), Duration.ofMinutes(30));
        assertFalse(dtr.overLapping(dtrNotOverlapping));
    }

    @Test
    void createOneDayRange() {
        var dtr = DateTimeRange.createOneDayRange(testDateTime);
        assertTrue(dtr.getStart().plusDays(1).isEqual(dtr.getEnd()));
    }

    @Test
    void createOneWeekRange() {
        var dtr = DateTimeRange.createOneWeekRange(testDateTime);
        assertTrue(dtr.getStart().plusDays(7).isEqual(dtr.getEnd()));
    }
}