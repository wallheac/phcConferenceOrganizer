package com.jph.organizer.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.domain.ParticipantRoleDomain;
import com.jph.organizer.domain.ParticipantRoleIdDomain;
import com.jph.organizer.rest.representation.Participant;

@Component
public class OrganizerParticipantTransformer {
	
	public List<ParticipantDomain> toParticipantDomains(List<Participant> participants){
		List<ParticipantDomain> participantDomains =  participants.stream()
				.map(participant -> 
					new ParticipantDomain(
						participant.getParticipantId(),
						participant.getFirstName(),		
						participant.getLastName(),
						participant.getStatus(),
						participant.getInstitution(), 
						participant.getEmail(), 
						participant.getNotes()))
				.collect(Collectors.toList());
				participantDomains.forEach(participantDomain -> participantDomain.setParticipantRoleDomain(
						new ParticipantRoleDomain(new ParticipantRoleIdDomain(null, null), true, false, false, false)));
				
				return participantDomains;
	}
}
