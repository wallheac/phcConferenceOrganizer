package com.jph.organizer.repository;

import com.jph.organizer.domain.ParticipantDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantDomain, Integer> {
}
