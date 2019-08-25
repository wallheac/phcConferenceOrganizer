package com.jph.organizer.repository;

import com.jph.organizer.domain.ParticipantRoleDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRoleRepository extends JpaRepository<ParticipantRoleDomain, Integer> {
}
