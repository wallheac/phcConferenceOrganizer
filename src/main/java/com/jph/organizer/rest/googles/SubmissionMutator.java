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
        persistSupporting(panelDomain, (ParticipantDomain) panel.get("chair"), PanelPositionDomain.CHAIR);
        persistSupporting(panelDomain, (ParticipantDomain) panel.get("commentator"), PanelPositionDomain.COMMENTATOR);
        persistSupporting(panelDomain, (ParticipantDomain) panel.get("organizer"), PanelPositionDomain.CONTACT);

        entityManager.close();
    }

    private void persistPanelistsAndPapers(List panelists, List papers, PanelDomain panelDomain) {
        for (int i = 0; i < panelists.size(); i++) {

            ParticipantDomain participantDomain = (ParticipantDomain) panelists.get(i);
            PaperDomain paperDomain = (PaperDomain) papers.get(i);

            if (validateParticipant(participantDomain)) {

                entityManager.persist(participantDomain);
                ParticipantRoleDomain participantRoleDomain = createParticipantRoleDomain(participantDomain, panelDomain,
                        PanelPositionDomain.PRESENTER);

                entityManager.persist(participantRoleDomain);
                participantDomain.setParticipantRoleDomain(participantRoleDomain);
                panelDomain.addParticipant(participantDomain);
                panelDomain.setParticipantRoleDomain(participantRoleDomain);

                paperDomain.addParticipant(participantDomain);
                paperDomain.setPanelId(panelDomain.getPanelId());
            }

            entityManager.persist(paperDomain);
        }
    }

    private void persistSupporting(PanelDomain panelDomain, ParticipantDomain participant, PanelPositionDomain position) {
        if(validateParticipant(participant)){
            ParticipantDomain matching = findMatchingPanelist(panelDomain.getParticipants(), participant);
            if (matching != null) {
                if (position == PanelPositionDomain.CHAIR) {
                    matching.getParticipantRoleDomain().setChair(true);
                }
                if (position == PanelPositionDomain.COMMENTATOR) {
                    matching.getParticipantRoleDomain().setCommentator(true);
                }
                if (position == PanelPositionDomain.CONTACT) {
                    matching.getParticipantRoleDomain().setContact(true);
                }
            } else {
                entityManager.persist(participant);
                ParticipantRoleDomain role = createParticipantRoleDomain(participant, panelDomain, position);
                entityManager.persist(role);
                participant.setParticipantRoleDomain(role);
                panelDomain.addParticipant(participant);
            }
        }
    }

    private boolean validateParticipant(ParticipantDomain participantDomain) {
        return participantDomain != null && !participantDomain.getFirstName().isEmpty() && !participantDomain.getLastName().isEmpty();
    }

    private ParticipantDomain findMatchingPanelist(List<ParticipantDomain> panelists, ParticipantDomain target) {
        return panelists.stream().filter(target::equals).findFirst().orElse(null);
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
