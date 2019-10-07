package com.jph.organizer.rest;

import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ParticipantAccessor {

    @Autowired
    private ParticipantRepository participantRepository;

    public Optional getParticipantById(Integer id) {
        return participantRepository.findById(id);

    }

    public List<ParticipantDomain> getParticipants() {
        return participantRepository.findAll();
    }
}
