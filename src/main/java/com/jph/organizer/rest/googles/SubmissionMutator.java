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

        entityManager.persist(panelDomain.getContact());
        entityManager.persist(panelDomain);

        List panelists = (List) panel.get("panelists");
        List papers = (List) panel.get("papers");
        for (int i = 0; i < panelists.size(); i++) {
            ParticipantDomain participantDomain = (ParticipantDomain) panelists.get(i);
            PaperDomain paperDomain = (PaperDomain) papers.get(i);

            addParticipantToPanel(participantDomain, panelDomain);
            entityManager.persist(participantDomain);

            entityManager.persist(createParticipantRoleDomain(participantDomain, panelDomain,
                    PanelPositionDomain.PRESENTER));

            paperDomain.addParticipant(participantDomain);
            paperDomain.setPanelId(panelDomain.getPanelId());

            entityManager.persist(paperDomain);
        }
        entityManager.persist(panel.get("chair"));
        entityManager.persist(createParticipantRoleDomain((ParticipantDomain) panel.get("chair"),
                panelDomain, PanelPositionDomain.CHAIR));

        entityManager.persist(panel.get("commentator"));
        entityManager.persist(createParticipantRoleDomain((ParticipantDomain) panel.get("commentator"),
                panelDomain, PanelPositionDomain.COMMENTATOR));

        entityManager.close();
    }

    private ParticipantRoleDomain createParticipantRoleDomain(ParticipantDomain participantDomain, PanelDomain panelDomain,
                                                              PanelPositionDomain panelPositionDomain) {
        ParticipantRoleDomain participantRoleDomain = new ParticipantRoleDomain();
        participantRoleDomain.setParticipantId(participantDomain.getParticipantId());
        participantRoleDomain.setPanelId(panelDomain.getPanelId());
        participantRoleDomain.setPanelPosition(panelPositionDomain.toString());

        return participantRoleDomain;
    }

    private void addParticipantToPanel(ParticipantDomain participantDomain, PanelDomain panelDomain) {
        panelDomain.addParticipant(participantDomain);
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
