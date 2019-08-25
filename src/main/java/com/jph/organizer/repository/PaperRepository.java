package com.jph.organizer.repository;

import com.jph.organizer.domain.PaperDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<PaperDomain, Integer> {
}
