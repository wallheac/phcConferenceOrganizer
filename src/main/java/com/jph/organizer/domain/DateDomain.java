package com.jph.organizer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity(name="Date")
@Table(name="dates")
public class DateDomain {
    @Id
    @Column(name="date_id")
    private int dayId;

    @Column(name="day_time")
    private Date date;


    public DateDomain() {
    }

    public DateDomain(int dayId, Date date) {
        this.dayId = dayId;
        this.date = date;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
