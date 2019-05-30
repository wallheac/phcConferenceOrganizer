package com.jph.organizer.repository;

import com.jph.organizer.domain.ParticipantDomain;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<ParticipantDomain, Integer> {

}
