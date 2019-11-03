package com.jph.organizer.repository;

import com.jph.organizer.domain.PaperDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<PaperDomain, Integer> {
    List<PaperDomain> findAllByPanelIdEquals(Integer panelId);
    List<PaperDomain> findPaperDomainsByPanelIdIsNull();
}
