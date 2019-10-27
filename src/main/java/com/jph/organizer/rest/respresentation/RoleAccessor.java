package com.jph.organizer.rest.respresentation;

import com.jph.organizer.domain.ParticipantRoleDomain;
import com.jph.organizer.repository.ParticipantRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAccessor {

    @Autowired
    ParticipantRoleRepository participantRoleRepository;

    public List<ParticipantRoleDomain> getParticipantRoleDomainsByPanelId(Integer panelId) {
        return participantRoleRepository.findParticipantRoleDomainsByParticipantRoleIdDomainPanelIdEquals(panelId);
    }
}
