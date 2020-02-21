package com.jph.organizer.rest;

import com.jph.organizer.domain.DateDomain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateTransformer {
    public List<String> fromDateDomains(List<DateDomain> dates) {
        return dates.stream()
                .map(dateDomain -> dateDomain.getDate().toString())
                .collect(Collectors.toList());
    }
}
