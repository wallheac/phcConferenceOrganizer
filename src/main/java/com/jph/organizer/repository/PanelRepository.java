package com.jph.organizer.repository;
import com.jph.organizer.domain.PanelDomain;
import org.springframework.data.repository.CrudRepository;

public interface PanelRepository extends CrudRepository<PanelDomain, Integer> {
}
