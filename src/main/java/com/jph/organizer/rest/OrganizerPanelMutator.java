package com.jph.organizer.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jph.organizer.domain.*;
import com.jph.organizer.repository.PaperRepository;
import com.jph.organizer.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrganizerPanelMutator {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ParticipantRepository participantRepository;
	@Autowired
	private PaperRepository paperRepository;

	public void persistConstructedPanel(PanelDomain panelDomain, List<ParticipantDomain> participantDomains,
			List<PaperDomain> paperDomains) {
		
		entityManager.persist(panelDomain);

		List<ParticipantDomain> participantDomainsFromDb = participantDomains.stream()
				.map(participantDomain -> entityManager.find(ParticipantDomain.class, participantDomain.getParticipantId()))
				.collect(Collectors.toList());

		for (int i = 0; i < participantDomainsFromDb.size(); i++) {
			ParticipantDomain fromDb = participantDomainsFromDb.get(i);
			ParticipantRoleDomain participantRoleDomain = new ParticipantRoleDomain(
					new ParticipantRoleIdDomain(panelDomain.getPanelId(), fromDb.getParticipantId()),
					true, false, false, false);

			entityManager.persist(participantRoleDomain);

			//check for changes to participant fields
			if(!fromDb.equals(participantDomains.get(i))){
				incorporateChangedParticipantFields(participantDomains.get(i), fromDb);
			}
			//required changes to participant
			fromDb.getPanels().add(panelDomain);
			fromDb.setParticipantRoleDomain(participantRoleDomain);

			//get paper from DB
			int finalI = i;
			PaperDomain paperFromDb = fromDb.getPaperDomains().stream()
					.filter(paperDomain -> paperDomain.getPaperId().equals(paperDomains.get(finalI).getPaperId())).findFirst().get();

			//check for changes to paper and incorporate
			if(!paperFromDb.equals(paperDomains.get(i))) {
				incorporateChangedPaperFields(paperDomains.get(i), paperFromDb);
			}

			//required changes to paper
			paperFromDb.setPanelId(panelDomain.getPanelId());
			//TODO beware that later iterations may change this
			paperFromDb.setAccepted(true);

			entityManager.merge(fromDb);
			panelDomain.getParticipants().add(fromDb);
		}

		entityManager.close();
	}

	private void incorporateChangedPaperFields(PaperDomain paperDomain, PaperDomain paperFromDb) {
		paperFromDb.setTitle(paperDomain.getTitle());
	}

	private void incorporateChangedParticipantFields(ParticipantDomain participantDomain, ParticipantDomain fromDb) {
		fromDb.setEmail(participantDomain.getEmail());
		fromDb.setFirstName(participantDomain.getFirstName());
		fromDb.setLastName(participantDomain.getLastName());
		fromDb.setInstitution(participantDomain.getInstitution());
		fromDb.setNotes(participantDomain.getNotes());
		fromDb.setStatus(participantDomain.getStatus());
	}
}
