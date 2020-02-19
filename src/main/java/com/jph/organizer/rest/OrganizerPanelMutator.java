package com.jph.organizer.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.jph.organizer.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrganizerPanelMutator {
	
	@PersistenceContext
	private EntityManager entityManager;

	public PanelDomain persistPanel(PanelDomain panelDomain, List<ParticipantDomain> participantDomains) throws PersistenceException {
	    PanelDomain panelDomainFromDb = null;
	    try {
	        panelDomainFromDb = entityManager.find(PanelDomain.class, panelDomain.getPanelId());

	        incorporatePanelChanges(panelDomain, panelDomainFromDb);

            List<ParticipantDomain> participantDomainsFromDb = panelDomainFromDb.getParticipants();

            for (int i = 0; i < participantDomainsFromDb.size(); i++) {
                ParticipantDomain fromDb = participantDomainsFromDb.get(i);
                ParticipantDomain fromUi = participantDomains.stream()
                        .filter(participantDomain -> participantDomain.getParticipantId().equals(fromDb.getParticipantId()))
                        .findFirst()
                        .orElse(null);

                incorporateChangedParticipantFields(fromUi, fromDb);
                incorporateChangedRoleDomains(fromUi, fromDb);
                //work on papers
                PaperDomain matchingPaperFromDb = fromDb.getPaperDomains().stream()
                        .filter(paper -> paper.getPaperId() == fromUi.getPaperDomains().get(0).getPaperId())
                        .findFirst()
                        .orElse(null);

                if(fromUi.getPaperDomains().size() > 0) {
                    incorporateChangedPaperFields(fromUi.getPaperDomains().get(0), matchingPaperFromDb);
                }
            }
            entityManager.merge(panelDomainFromDb);
        } catch(Exception e) {
            throw new PersistenceException("Problem persisting panel " + panelDomain.getPanelName(), e);
        }
        entityManager.close();
		return panelDomainFromDb;
	}

    private void incorporatePanelChanges(PanelDomain panelDomain, PanelDomain panelDomainFromDb) {
	    panelDomainFromDb.setPanelName(panelDomain.getPanelName());
	    panelDomainFromDb.setAccepted(panelDomain.getAccepted());
	    panelDomainFromDb.setNotes(panelDomain.getNotes());
	    panelDomainFromDb.setAvRequestDate(panelDomain.getAvRequestDate());
	    panelDomainFromDb.setAvRequested(panelDomain.getAvRequested());
	    panelDomainFromDb.setRequestor(panelDomain.getRequestor());
    }

    private void incorporateChangedRoleDomains(ParticipantDomain fromUi, ParticipantDomain fromDb) {
	    //Note: the equals method does not look at roleIdDomain
	    if(!fromUi.equals(fromDb)) {
            fromDb.getParticipantRoleDomain().setCommentator(fromUi.getParticipantRoleDomain().getCommentator());
            fromDb.getParticipantRoleDomain().setPresenter(fromUi.getParticipantRoleDomain().getPresenter());
            fromDb.getParticipantRoleDomain().setChair(fromUi.getParticipantRoleDomain().getChair());
            fromDb.getParticipantRoleDomain().setContact(fromUi.getParticipantRoleDomain().getContact());
        }
    }

    public PanelDomain persistConstructedPanel(PanelDomain panelDomain, List<ParticipantDomain> participantDomains,
			List<PaperDomain> paperDomains) throws PersistenceException {

		try {
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
				if (!fromDb.equals(participantDomains.get(i))) {
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
				if (!paperFromDb.equals(paperDomains.get(i))) {
					incorporateChangedPaperFields(paperDomains.get(i), paperFromDb);
				}

				//required changes to paper
				paperFromDb.setPanelId(panelDomain.getPanelId());
				//TODO beware that later iterations may change this
				paperFromDb.setAccepted(true);

				entityManager.merge(fromDb);
				panelDomain.getParticipants().add(fromDb);
			}
		} catch(Exception e) {
		    throw new PersistenceException("Problem persisting constructed panel " + panelDomain.getPanelName(), e);
		}

		entityManager.close();
		return panelDomain;
	}

	private void incorporateChangedPaperFields(PaperDomain paperDomain, PaperDomain paperFromDb) {
		paperFromDb.setTitle(paperDomain.getTitle());
		paperFromDb.setAccepted(paperDomain.getAccepted());
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
