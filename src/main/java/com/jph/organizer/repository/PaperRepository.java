package com.jph.organizer.repository;
import com.jph.organizer.domain.PaperDomain;
import org.springframework.data.repository.CrudRepository;

public interface PaperRepository extends CrudRepository<PaperDomain, Integer>{

}
