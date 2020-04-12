package com.jph.organizer.rest.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeSlot {
    @JsonProperty
    private Instant instant;
    @JsonProperty
    private String dateString;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E hh:mm a")
            .withLocale(Locale.US)
            .withZone(ZoneId.systemDefault());

    public TimeSlot(Instant instant) {
        this.instant = instant;
        this.dateString = formatter.format(instant);
    }
}
