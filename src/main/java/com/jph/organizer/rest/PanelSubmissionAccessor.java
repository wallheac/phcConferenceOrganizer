package com.jph.organizer.rest;

import com.jph.organizer.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class PanelSubmissionAccessor {

    @PersistenceContext
    private EntityManager entityManager;

    public void createPanel(HashMap panel){
        PanelDomain panelDomain = (PanelDomain) panel.get("panel");

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

            paperDomain.setParticipantId(participantDomain.getParticipantId());
            paperDomain.setPanelId(panelDomain.getPanelId());

            entityManager.persist(paperDomain);
        }
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
}
