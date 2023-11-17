package com.shavatech.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationEventTest {

    public class  TestEvent extends IntegrationEvent{

    }

    @Test
    public void testTimeToCurrentTime(){
        TestEvent currentEvent = new TestEvent();
        assertTrue(currentEvent.getDateOccurred().isAfter(LocalDateTime.now().minusMinutes(1)));
    }
}