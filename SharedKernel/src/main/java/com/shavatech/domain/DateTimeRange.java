package com.shavatech.domain;

import jakarta.persistence.Embeddable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
public class DateTimeRange {

    private LocalDateTime start;

    private LocalDateTime end;

    public DateTimeRange(){

    }

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public DateTimeRange(LocalDateTime start, Duration duration){
        this(start, start.plus(duration));
    }

    public int durationInMinutes(){
        return (int)Duration.between(start,end).toMinutes();
    }

    public DateTimeRange newDuration(Duration newDuration){
        return new DateTimeRange(this.start,newDuration);
    }

    public DateTimeRange newEnd(LocalDateTime newEnd){
        return new DateTimeRange(start,newEnd);
    }

    public DateTimeRange newStart(LocalDateTime newStart){
        return new DateTimeRange(newStart,end);
    }

    public  boolean overLapping(DateTimeRange dateTimeRange){
        return start.isBefore(dateTimeRange.getEnd()) && dateTimeRange.getStart().isBefore(end);
    }

    public static DateTimeRange createOneDayRange(LocalDateTime day){
        return new DateTimeRange(day,day.plusDays(1));
    }

    public static DateTimeRange createOneWeekRange(LocalDateTime startDay){
        return new DateTimeRange(startDay,startDay.plusDays(7));
    }

    public LocalDateTime getStart() {
        return start;
    }

    private void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    private void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
