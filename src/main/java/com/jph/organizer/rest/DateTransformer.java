package com.jph.organizer.rest;

import com.jph.organizer.domain.DateDomain;
import com.jph.organizer.rest.representation.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class DateTransformer {
    public List<TimeSlot> fromDateDomains(List<DateDomain> dates) {
        Comparator<DateDomain> comparator = Comparator.comparing(DateDomain::getInstant);
        TreeSet<DateDomain> uniqueDateDomains = dates.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator)));

        return uniqueDateDomains.stream()
                .map(dateDomain ->
                        new TimeSlot(dateDomain.getInstant()))
                .collect(Collectors.toList());
    }
}
