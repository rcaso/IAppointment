package com.shavatech.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {
    public class  TestEvent extends DomainEvent{

    }

    @Test
    public void testTimeToCurrentTime(){
        TestEvent currentEvet = new TestEvent();
        assertTrue(currentEvet.getDateOccurred().isAfter(LocalDateTime.now().minusMinutes(1)));
    }
}