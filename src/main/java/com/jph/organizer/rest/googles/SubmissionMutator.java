package com.jph.organizer.rest.googles;

import com.jph.organizer.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class SubmissionMutator {

    @PersistenceContext
    private EntityManager entityManager;

    public void createPanel(HashMap panel) {
        PanelDomain panelDomain = (PanelDomain) panel.get("panel");

        entityManager.persist(panelDomain);

        List panelists = (List) panel.get("panelists");
        List papers = (List) panel.get("papers");

        persistPanelistsAndPapers(panelists, papers, panelDomain);

        ParticipantDomain chair = null;
        if (panel.get("chair") != null) {
            chair = (ParticipantDomain) panel.get("chair");
            if(!panelists.contains(chair)) {
                entityManager.persist(chair);
            }
            if (chair != null) {
                entityManager.persist(chair);
                ParticipantRoleDomain chairRole = createParticipantRoleDomain(chair, panelDomain, PanelPositionDomain.CHAIR);
                entityManager.persist(chairRole);
                chair.setParticipantRoleDomain(chairRole);
            }
        }

        if (panel.get("commentator") != null) {
            ParticipantDomain commentator = (ParticipantDomain) panel.get("commentator");
            if (!panelists.contains(commentator) && !chair.equals(commentator)) {
                entityManager.persist(commentator);
            }
            if(chair != null && chair.equals(commentator)){
                chair.getParticipantRoleDomain().setCommentator(true);
            }
            ParticipantDomain matchingPanelist = findMatchingPanelist(panelists, commentator);
            if (matchingPanelist != null) {
                matchingPanelist.getParticipantRoleDomain().setChair(true);
            }
        }

        entityManager.close();
    }

    private void persistPanelistsAndPapers(List panelists, List papers, PanelDomain panelDomain) {
        for (int i = 0; i < panelists.size(); i++) {

            ParticipantDomain participantDomain = (ParticipantDomain) panelists.get(i);
            PaperDomain paperDomain = (PaperDomain) papers.get(i);

            entityManager.persist(participantDomain);
            ParticipantRoleDomain participantRoleDomain = createParticipantRoleDomain(participantDomain, panelDomain,
                    PanelPositionDomain.PRESENTER);

            entityManager.persist(participantRoleDomain);
            participantDomain.setParticipantRoleDomain(participantRoleDomain);
            panelDomain.setParticipantRoleDomain(participantRoleDomain);

            paperDomain.addParticipant(participantDomain);
            paperDomain.setPanelId(panelDomain.getPanelId());

            entityManager.persist(paperDomain);
        }
    }

    private ParticipantDomain findMatchingPanelist(List panelists, ParticipantDomain target) {
        return (ParticipantDomain) panelists.stream().filter(target::equals).findFirst().orElse(null);
    }

    private ParticipantRoleDomain createParticipantRoleDomain(ParticipantDomain participantDomain, PanelDomain panelDomain,
                                                              PanelPositionDomain panelPositionDomain) {
        ParticipantRoleDomain participantRoleDomain = new ParticipantRoleDomain
                (new ParticipantRoleIdDomain(panelDomain.getPanelId(), participantDomain.getParticipantId()), false, false, false, false);

        calculateRoles(participantRoleDomain, panelPositionDomain);

        return participantRoleDomain;
    }

    private void calculateRoles(ParticipantRoleDomain participantRoleDomain, PanelPositionDomain panelPositionDomain) {
        participantRoleDomain.setContact(panelPositionDomain.equals(PanelPositionDomain.CONTACT));
        participantRoleDomain.setChair(panelPositionDomain.equals(PanelPositionDomain.CHAIR));
        participantRoleDomain.setCommentator(panelPositionDomain.equals(PanelPositionDomain.COMMENTATOR));
        participantRoleDomain.setPresenter(panelPositionDomain.equals(PanelPositionDomain.PRESENTER));
    }

    public void createPaperSubmission(HashMap paperMap) {
        List<ParticipantDomain> participants = (List<ParticipantDomain>) paperMap.get("participants");
        PaperDomain paper = (PaperDomain) paperMap.get("paper");
        entityManager.persist(paper);
        for (ParticipantDomain participant : participants) {
            entityManager.persist(participant);
            paper.addParticipant(participant);
            participant.addPaper(paper);
        }
        entityManager.close();
    }
}
