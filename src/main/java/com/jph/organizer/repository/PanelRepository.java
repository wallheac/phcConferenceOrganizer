package com.jph.organizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanelRepository extends CrudRepository<String, Integer> {
}
