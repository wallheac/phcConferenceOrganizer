package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.domain.ParticipantRoleDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Component
public class PanelSubmissionAccessor {

    public void createPanel(PanelDomain panelDomain, List<PaperDomain> paperDomains,
                            List<ParticipantDomain> participantDomains,
                            List<ParticipantRoleDomain> participantRoleDomains) {

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("db_phc_organizer");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(panelDomain);
        participantDomains.stream().forEach(
                participantDomain -> entityManager.persist(participantDomain));

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
