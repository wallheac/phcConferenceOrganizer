package com.jph.organizer.rest;

import com.jph.organizer.domain.DateDomain;
import com.jph.organizer.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateAccessor {

    @Autowired
    private DateRepository dateRepository;

    public List<DateDomain> getDates() {
        return dateRepository.findAll();
    }
}
