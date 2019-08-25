package com.jph.organizer.repository;

import com.jph.organizer.domain.PanelDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanelRepository extends JpaRepository<PanelDomain, Integer> {
}
