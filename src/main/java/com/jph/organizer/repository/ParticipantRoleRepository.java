package com.jph.organizer.repository;

import com.jph.organizer.domain.ParticipantRoleDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRoleRepository extends JpaRepository<ParticipantRoleDomain, Integer> {
    List<ParticipantRoleDomain> findParticipantRoleDomainsByParticipantRoleIdDomainPanelIdEquals(Integer panelId);
}
