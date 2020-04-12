package com.jph.organizer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity(name="Date")
@Table(name="dates")
public class DateDomain {
    @Id
    @Column(name="date_id")
    private int dayId;

    @Column(name="day_time")
    private Instant instant;


    public DateDomain() {
    }

    public DateDomain(int dayId, Instant instant) {
        this.dayId = dayId;
        this.instant = instant;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
