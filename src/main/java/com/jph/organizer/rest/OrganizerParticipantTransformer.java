package com.jph.organizer.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.domain.ParticipantRoleDomain;
import com.jph.organizer.domain.ParticipantRoleIdDomain;
import com.jph.organizer.rest.representation.Participant;

@Component
public class OrganizerParticipantTransformer {

	@Autowired
	private OrganizerPaperTransformer organizerPaperTransformer;

	public List<ParticipantDomain> toParticipantDomains(List<Participant> participants) {
		List<ParticipantDomain> participantDomains = participants.stream()
				.map(participant -> {
					ParticipantDomain participantDomain = new ParticipantDomain(
							participant.getParticipantId(),
							participant.getFirstName(),
							participant.getLastName(),
							participant.getStatus(),
							participant.getInstitution(),
							participant.getEmail(),
							participant.getNotes());
					participantDomain.setParticipantRoleDomain(mapParticipantRoleDomain(participant));
					if (participant.getPaper() != null) {
						participantDomain.setPaperDomains(Arrays.asList(organizerPaperTransformer.toPaperDomain(participant.getPaper())));
					}
					return participantDomain;
				})
				.collect(Collectors.toList());

		participantDomains.forEach(participantDomain -> participantDomain.setParticipantRoleDomain(
				new ParticipantRoleDomain(new ParticipantRoleIdDomain(null, null), true, false, false, false)));

		return participantDomains;
	}

	private ParticipantRoleDomain mapParticipantRoleDomain(Participant participant) {
		ParticipantRoleDomain roleDomain = new ParticipantRoleDomain(new ParticipantRoleIdDomain(null, null), false, false, false, false);
		if (participant.getRoles().isEmpty()) {
			roleDomain.setPresenter(true);
		} else {
			List<String> roles = participant.getRoles();
			roles.stream().forEach(role -> {
				switch (role) {
					case "CONTACT":
						roleDomain.setContact(true);
					case "CHAIR":
						roleDomain.setContact(true);
					case "PRESENTER":
						roleDomain.setPresenter(true);
					case "COMMENTATOR":
						roleDomain.setCommentator(true);
				}
			});
		}
		return roleDomain;
	}

	public Participant fromParticipantDomain(ParticipantDomain participantDomain) {
		return new Participant(participantDomain.getParticipantId(), participantDomain.getFirstName(),
				participantDomain.getLastName(), participantDomain.getStatus(), participantDomain.getInstitution(),
				null, participantDomain.getEmail(), participantDomain.getNotes(), null);
	}
}
