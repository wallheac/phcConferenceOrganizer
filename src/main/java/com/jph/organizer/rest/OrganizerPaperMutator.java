package com.jph.organizer.rest;

import com.jph.organizer.domain.PaperDomain;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Component
@Transactional
public class OrganizerPaperMutator {

    @PersistenceContext
    private EntityManager entityManager;

    public PaperDomain persistPaper(PaperDomain paperDomain, String id) throws PersistenceException {
        PaperDomain paperDomainFromDb;

        try {
            paperDomainFromDb = entityManager.find(PaperDomain.class, Integer.parseInt(id));
            incorporateChangedPaperFields(paperDomain, paperDomainFromDb);
            entityManager.merge(paperDomainFromDb);
        } catch(Exception e) {
            throw new PersistenceException("Problem persisting paper " + paperDomain.getTitle(), e);
        }
        entityManager.close();
        return paperDomainFromDb;
    }

    private void incorporateChangedPaperFields(PaperDomain paperDomain, PaperDomain paperFromDb) {
        paperFromDb.setTitle(paperDomain.getTitle());
        paperFromDb.setAccepted(paperDomain.getAccepted());
    }
}
