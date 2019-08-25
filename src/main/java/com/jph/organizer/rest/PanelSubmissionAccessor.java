package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.domain.ParticipantRoleDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PanelSubmissionAccessor {

    @PersistenceContext
    private EntityManager entityManager;

    public void createPanel(PanelDomain panelDomain, List<PaperDomain> paperDomains,
                            List<ParticipantDomain> participantDomains,
                            List<ParticipantRoleDomain> participantRoleDomains) {

        entityManager.persist(panelDomain);

        for(int i = 0; i < participantDomains.size(); i++) {
            panelDomain.addParticipant(participantDomains.get(i));

            entityManager.persist(participantDomains.get(i));

            ParticipantRoleDomain participantRole = participantRoleDomains.get(i);
            participantRole.setPanelId(panelDomain.getPanelId());
            participantRole.setParticipantId(participantDomains.get(i).getParticipantId());
            participantRole.setPanelPosition(participantRoleDomains.get(i).getPanelPosition());

            entityManager.persist(participantRole);
        }

        for(int i = 0; i < paperDomains.size(); i++) {
            paperDomains.get(i).setPanelId(panelDomain.getPanelId());
            paperDomains.get(i).setParticipantId(participantDomains.get(i).getParticipantId());

            entityManager.persist(paperDomains.get(i));
        }

        entityManager.close();
    }
}
